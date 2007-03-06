package org.seasar.s2chronos.job;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
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

	public ResultSet callJob(String jobName) {

		String firstChar = jobName.substring(0, 1);
		String afterString = jobName.substring(1);
		String function;
		HashMap<String, Method> jobMethod = allJobMethodMap.get(jobName);
		if (jobMethod != null) {
			function = START + firstChar.toUpperCase() + afterString;
			ResultSet resultSet = methodInvoke(function);
			List<ResultSet> joins = new ArrayList<ResultSet>();
			do {
				resultSet = callJob(resultSet.getResult());
				joins.add(resultSet);
			} while (resultSet.getResult() != null);
			for (ResultSet r : joins) {
				try {
					r.getThread().join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			function = END + firstChar.toUpperCase() + afterString;
		} else {
			function = DO + firstChar.toUpperCase() + afterString;
		}

		ResultSet result = methodInvoke(function);
		return result;

	}

	private class MethodRunnable implements Runnable {

		private final String methodName;

		public MethodRunnable(String methodName) {
			this.methodName = methodName;
		}

		public void run() {
			beanDesc.invoke(job, methodName, null);
		}

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

	private ResultSet methodInvoke(String function) {
		String result = null;
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
		MethodRunnable methodRunnable = new MethodRunnable(function);
		Thread thread = new Thread(methodRunnable);
		thread.start();
		if (wait) {
			try {
				log.debug("join start");
				do {
					thread.join(100);
				} while (thread.isAlive());
			} catch (InterruptedException e) {
				log.warn("interrupt", e);
			}
			log.debug("join end");
		}
		ResultSet resultSet = new ResultSet(thread, result);
		return resultSet;
	}

	public String destroy() {
		if (!this.jobInitialized) {
			return null;
		}
		return (String) this.beanDesc.invoke(this.job, "destroy", null);
	}

	public boolean canExecute() {
		return (Boolean) this.beanDesc.invoke(this.job, "canExecute", null);
	}
}
