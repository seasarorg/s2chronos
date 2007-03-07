package org.seasar.s2chronos.job;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.s2chronos.ThreadPoolType;
import org.seasar.s2chronos.annotation.job.method.Group;
import org.seasar.s2chronos.annotation.job.method.Join;
import org.seasar.s2chronos.annotation.job.method.Next;
import org.seasar.s2chronos.annotation.type.JoinType;

public class JobAdaptor {

	private Logger log = Logger.getLogger(JobAdaptor.class);

	private static final String DO = "do";

	private static final String END = "end";

	private static final String START = "start";

	private static final boolean WAIT_DEFAULT = true;

	private boolean jobInitialized = false;

	private final Object job;

	private List<Method> allJobMethodList = new ArrayList<Method>();

	private HashMap<String, HashMap<String, Method>> allJobMethodMap = new HashMap<String, HashMap<String, Method>>();

	private BeanDesc beanDesc;

	private ExecutorService executorService = null;

	public JobAdaptor(Object job) {
		this.job = job;
		this.beanDesc = BeanDescFactory.getBeanDesc(this.job.getClass());
		this.preparedJob();
	}

	public String initialize() {
		String result = null;

		beanDesc.invoke(this.job, "initialize", null);

		jobInitialized = true;

		Method method = beanDesc.getMethod("initialize");
		Next next = method.getAnnotation(Next.class);
		if (next != null) {
			result = next.value();
		}
		return result;
	}

	private void preparedJob() {

		PropertyDesc threadPoolType = this.beanDesc
				.getPropertyDesc("threadPoolType");
		ThreadPoolType type = (ThreadPoolType) threadPoolType.getValue(job);

		if (type == ThreadPoolType.FIXED) {
			int threadSize = getThreadPoolSize();
			executorService = Executors.newFixedThreadPool(threadSize);
		} else if (type == ThreadPoolType.CACHED) {
			executorService = Executors.newCachedThreadPool();
		} else if (type == ThreadPoolType.SINGLE) {
			executorService = Executors.newSingleThreadExecutor();
		} else if (type == ThreadPoolType.SCHEDULED) {
			int threadSize = getThreadPoolSize();
			executorService = Executors.newScheduledThreadPool(threadSize);
		}

		Class clazz = this.beanDesc.getBeanClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith(DO)) {
				allJobMethodList.add(method);
				Group group = method.getAnnotation(Group.class);
				String groupName;
				if (group != null) {
					groupName = group.value();
				} else {
					groupName = clazz.getName();
				}
				HashMap<String, Method> jobMethodMap = allJobMethodMap
						.get(groupName);
				if (null == jobMethodMap) {
					jobMethodMap = new HashMap<String, Method>();
					allJobMethodMap.put(groupName, jobMethodMap);
				}
				jobMethodMap.put(methodName, method);

			}
		}

	}

	private int getThreadPoolSize() {
		PropertyDesc threadPoolSize = this.beanDesc
				.getPropertyDesc("threadPoolSize");
		int threadSize = (Integer) threadPoolSize.getValue(job);
		return threadSize;
	}

	public String callJob(String jobName) throws Exception {
		String result = null;
		String firstChar = jobName.substring(0, 1);
		String afterString = jobName.substring(1);

		HashMap<String, Method> jobMethod = allJobMethodMap.get(jobName);
		if (jobMethod != null) {
			String startGroupFunctionName = START + firstChar.toUpperCase()
					+ afterString;
			result = (String) beanDesc.getMethod(startGroupFunctionName)
					.getAnnotation(Next.class).value();
			beanDesc.invoke(job, startGroupFunctionName, null);

			// ジョブを呼び出す
			do {
				result = callJob(result);
			} while (result != null);

			String endGroupFunction = END + firstChar.toUpperCase()
					+ afterString;
			result = (String) beanDesc.getMethod(endGroupFunction)
					.getAnnotation(Next.class).value();
			beanDesc.invoke(job, endGroupFunction, null);
		} else {
			final String function = DO + firstChar.toUpperCase() + afterString;

			boolean wait = WAIT_DEFAULT;
			Method method = this.beanDesc.getMethod(function);
			Join join = method.getAnnotation(Join.class);
			if (join != null) {
				wait = (join.value() == JoinType.Wait);
			}
			Next next = method.getAnnotation(Next.class);
			if (next != null) {
				result = next.value();
			}

			// スレッドプールでジョブを呼び出す
			Future<Object> future = executorService
					.submit(new Callable<Object>() {

						public Object call() throws Exception {
							return beanDesc.invoke(job, function, null);
						}

					});

			if (wait) {
				try {
					future.get();
				} catch (Exception e) {
					throw e;
				}
			}
		}

		return result;

	}

	class ResultSet {

		private final Thread thread;

		private final String result;

		public ResultSet(Thread thread, String result) {
			this.thread = thread;
			this.result = result;
		}

		public String getResult() {
			return result;
		}

		public Thread getThread() {
			return thread;
		}

	}

	public void cancel() {
		this.beanDesc.invoke(this.job, "cancel", null);
	}

	public void destroy() {
		if (!this.jobInitialized) {
			return;
		}
		this.beanDesc.invoke(this.job, "destroy", null);
	}

	public boolean canExecute() {
		return (Boolean) this.beanDesc.invoke(this.job, "canExecute", null);
	}
}
