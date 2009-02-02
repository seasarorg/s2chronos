/* 
 * Copyright 2008 the Seasar Foundation and the Others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package org.seasar.chronos.core.task.strategy.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.Destroy;
import org.seasar.chronos.core.annotation.task.method.End;
import org.seasar.chronos.core.annotation.task.method.Initialize;
import org.seasar.chronos.core.annotation.task.method.Start;
import org.seasar.chronos.core.delegate.AsyncResult;
import org.seasar.chronos.core.delegate.MethodInvoker;
import org.seasar.chronos.core.executor.ExecutorServiceFactory;
import org.seasar.chronos.core.model.TaskScheduleEntry;
import org.seasar.chronos.core.model.TaskThreadPool;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.model.ThreadPoolType;
import org.seasar.chronos.core.model.schedule.TaskScheduleEntryManager;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.chronos.core.task.TaskType;
import org.seasar.chronos.core.task.Transition;
import org.seasar.chronos.core.task.handler.TaskExecuteHandler;
import org.seasar.chronos.core.task.handler.impl.execute.TaskGroupMethodExecuteHandlerImpl;
import org.seasar.chronos.core.task.handler.impl.execute.TaskMethodExecuteHandlerImpl;
import org.seasar.chronos.core.task.impl.TaskMethodManager;
import org.seasar.chronos.core.task.impl.TaskMethodMetaData;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;
import org.seasar.chronos.core.util.ObjectUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.SerializeUtil;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskExecuteStrategyImpl implements TaskExecuteStrategy {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(TaskExecuteStrategyImpl.class);

	private static final String METHOD_PREFIX_NAME_DO = "do";

	private static final String METHOD_NAME_INITIALIZE = "initialize";

	private static final String METHOD_NAME_DESTROY = "destroy";

	private static final String[] METHOD_NAME_START = { "start", "begin" };

	private static final String[] METHOD_NAME_END = { "end", "finish" };

	private static final String METHOD_NAME_DEFAULT_TASK_NAME = "execute";

	private static final String METHOD_NAME_DEFAULT_TASK_METHOD_NAME = "doExecute";

	private static final ThreadPoolType DEFAULT_THREADPOOL_TYPE = ThreadPoolType.CACHED;

	private static final int DEFAULT_THREAD_POOLSIZE = 1;

	private static ConcurrentHashMap<TaskThreadPool, ExecutorService> threadPoolExecutorServiceMap = CollectionsUtil
			.newConcurrentHashMap();

	private S2Container s2Container;

	private Object task;

	private Class<?> taskClass;

	private BeanDesc beanDesc;

	private MethodInvoker taskMethodInvoker;

	private TaskMethodManager taskMethodManager;

	private TaskExecuteHandler taskMethodExecuteHandler;

	private TaskExecuteHandler taskGroupMethodExecuteHandler;

	private TaskPropertyReader taskPropertyReader;

	private TaskPropertyWriter taskPropertyWriter;

	private Object getterSignal;

	private Scheduler scheduler;

	private long taskId = 0;

	private boolean prepared = false;

	private ExecutorServiceFactory executorServiceFactory;

	private ComponentDef componentDef;

	private boolean hotdeployStart;

	private boolean hotdeployDisable;

	public TaskExecuteStrategyImpl() {

	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.taskMethodInvoker.awaitInvokes(time, timeUnit);
	}

	public boolean cancel() {
		this.taskMethodInvoker.cancelInvokes();
		return true;
	}

	public void catchException(Exception exception) {
		if (this.beanDesc.hasMethod("catchException")) {
			this.beanDesc.invoke(this.task, "catchException",
					new Object[] { exception });
		}
	}

	public boolean checkMoveAnotherTask(final String nextTaskName) {
		TaskScheduleEntryManager tcsm = TaskScheduleEntryManager.getInstance();
		Object result = tcsm
				.forEach(new TaskScheduleEntryManager.TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							TaskScheduleEntry taskScheduleEntry) {
						Class<?> clazz = taskScheduleEntry.getTaskClass();
						Task task = clazz.getAnnotation(Task.class);
						if (task == null) {
							return null;
						}
						String taskName = task.name();
						if (nextTaskName.equals(taskName)) {
							return new Object();
						}
						return null;
					}
				});
		if (result != null) {
			return true;
		}
		return false;
	}

	private ExecutorService createJobMethodExecutorService(Object target) {
		ExecutorService result = executorServiceFactory.create(this
				.getThreadPoolType(target), this.getThreadPoolSize(target));
		return result;
	}

	protected TaskExecuteHandler createTaskGroupMethodExecuteHandler(
			TaskExecuteHandler taskMethdoExecuteHandler) {
		TaskGroupMethodExecuteHandlerImpl result = new TaskGroupMethodExecuteHandlerImpl();
		result.setTaskMethodExecuteHandler(this.taskMethodExecuteHandler);
		return result;
	}

	protected TaskExecuteHandler createTaskMethodExecuteHandler() {
		return new TaskMethodExecuteHandlerImpl();
	}

	public void destroy() throws InterruptedException {
		String methodNameByAnnotation = getMethodNameByAnnotationClass(Destroy.class);
		String methodName = methodNameByAnnotation != null ? methodNameByAnnotation
				: METHOD_NAME_DESTROY;
		if (this.taskMethodInvoker.hasMethod(methodName)) {
			AsyncResult ar = this.taskMethodInvoker.beginInvoke(methodName);
			this.taskMethodInvoker.endInvoke(ar);
			return;
		}
	}

	public String end() throws InterruptedException {
		String nextTask = null;
		String methodNameByAnnotation = getMethodNameByAnnotationClass(End.class);
		List<String> methodNameList = new ArrayList<String>();
		if (methodNameByAnnotation != null) {
			methodNameList.add(methodNameByAnnotation);
		}
		methodNameList.addAll(Arrays.asList(METHOD_NAME_END));
		for (String methodName : methodNameList) {
			if (this.taskMethodInvoker.hasMethod(methodName)) {
				AsyncResult ar = this.taskMethodInvoker.beginInvoke(methodName);
				this.taskMethodInvoker.endInvoke(ar);
				TaskMethodMetaData md = new TaskMethodMetaData(this.beanDesc,
						methodName);
				nextTask = md.getNextTask();
				break;
			}
		}
		this.setExecuting(false);
		this.notifyGetterSignal();
		return nextTask;
	}

	public void execute(String startTaskName) throws InterruptedException {
		TaskType type = this.isGroupMethod(startTaskName) ? TaskType.JOBGROUP
				: TaskType.JOB;
		String nextTaskName = startTaskName;
		while (true) {
			TaskExecuteHandler teh = this.getTaskExecuteHandler(type);
			Transition transition = this.handleRequest(teh, nextTaskName);
			this.notifyGetterSignal();
			if (transition == null || transition.isProcessResult()) {
				break;
			}
			type = type == TaskType.JOB ? TaskType.JOBGROUP : TaskType.JOB;
			nextTaskName = transition.getNextTaskName();
		}
	}

	/**
	 * TaskThreadPoolに対応するExecutorServiceを返します．
	 * 
	 * @param taskThreadPool
	 *            スレッドプール
	 * @return
	 */
	private ExecutorService getCacheExecutorsService(
			TaskThreadPool taskThreadPool) {
		ExecutorService executorService = threadPoolExecutorServiceMap
				.get(taskThreadPool);
		if (executorService == null) {
			executorService = this
					.createJobMethodExecutorService(taskThreadPool);
			threadPoolExecutorServiceMap.put(taskThreadPool, executorService);
		}
		return executorService;
	}

	public String getDescription() {
		return this.taskPropertyReader.getDescription(null);
	}

	public Exception getException() {
		return this.taskPropertyReader.getException(null);
	}

	/**
	 * ExecutorServiceを返します．
	 * <p>
	 * TaskにTaskThreadPoolが存在する場合はキャッシュから対応するExecutorServiceを返します．<br>
	 * 存在しない場合はTaskのThreadTypeとThreadPoolSizeから作成して返します．<br>
	 * 複数のタスク間でスレッドプールを共有したければTaskThreadPoolを利用すること．
	 * </p>
	 * 
	 * @return
	 */
	private ExecutorService getExecutorService() {
		TaskThreadPool taskThreadPool = this.getThreadPool();
		ExecutorService jobMethodExecutorService = null;
		if (taskThreadPool == null) {
			jobMethodExecutorService = this
					.createJobMethodExecutorService(this.task);
		} else {
			jobMethodExecutorService = this
					.getCacheExecutorsService(taskThreadPool);
		}

		return jobMethodExecutorService;
	}

	public Scheduler getScheduler() {
		return this.scheduler;
	}

	public Object getTask() {
		return this.task;
	}

	public Class<?> getTaskClass() {
		return this.taskClass;
	}

	private TaskExecuteHandler getTaskExecuteHandler(TaskType type) {
		return type == TaskType.JOB ? this.taskMethodExecuteHandler
				: this.taskGroupMethodExecuteHandler;
	}

	public long getTaskId() {
		this.taskId = this.taskPropertyReader.getTaskId(0L);
		if (this.taskId == 0) {
			this.taskId = ObjectUtil.generateObjectId();
		}
		return this.taskId;
	}

	public String getTaskName() {
		return this.taskPropertyReader.getTaskName(null);
	}

	public TaskPropertyReader getTaskPropertyReader() {
		return taskPropertyReader;
	}

	public TaskPropertyWriter getTaskPropertyWriter() {
		return taskPropertyWriter;
	}

	public TaskThreadPool getThreadPool() {
		return this.taskPropertyReader.getThreadPool(null);
	}

	public int getThreadPoolSize() {
		return this.getThreadPoolSize(this.task);
	}

	private int getThreadPoolSize(Object target) {
		return this.taskPropertyReader
				.getThreadPoolSize(DEFAULT_THREAD_POOLSIZE);
	}

	public ThreadPoolType getThreadPoolType() {
		return this.getThreadPoolType(this.task);
	}

	private ThreadPoolType getThreadPoolType(Object target) {
		return this.taskPropertyReader
				.getThreadPoolType(DEFAULT_THREADPOOL_TYPE);
	}

	public TaskTrigger getTrigger() {
		return this.taskPropertyReader.getTrigger(null);
	}

	private Transition handleRequest(TaskExecuteHandler taskExecuteHandler,
			String startTaskName) throws InterruptedException {
		taskExecuteHandler.setTaskExecuteStrategy(this);
		taskExecuteHandler.setMethodInvoker(this.taskMethodInvoker);
		taskExecuteHandler.setMethodGroupMap(this.taskMethodManager);
		return taskExecuteHandler.handleRequest(startTaskName);
	}

	public synchronized void hotdeployStart() {
		if (!hotdeployDisable && HotdeployUtil.isHotdeploy()) {
			String name = this.componentDef.getComponentClass().getName();
			ReflectionUtil.forNameNoException(name);
			HotdeployUtil.start();
			this.hotdeployStart = true;
		}
	}

	public synchronized void hotdeployStop() {
		if (!hotdeployDisable && HotdeployUtil.isHotdeploy()) {
			if (this.hotdeployStart) {
				HotdeployUtil.stop();
				this.hotdeployStart = false;
			}
		}
	}

	public void initialize() throws InterruptedException {
		String methodNameByAnnotation = getMethodNameByAnnotationClass(Initialize.class);
		String methodName = methodNameByAnnotation != null ? methodNameByAnnotation
				: METHOD_NAME_INITIALIZE;
		if (this.taskMethodInvoker.hasMethod(methodName)) {
			AsyncResult ar = this.taskMethodInvoker.beginInvoke(methodName);
			this.taskMethodInvoker.endInvoke(ar);
			return;
		}
	}

	private <T extends Annotation> String getMethodNameByAnnotationClass(
			Class<T> annotationClass) throws InterruptedException {
		Method[] mis = this.taskClass.getMethods();
		for (Method mi : mis) {
			if (mi.getAnnotation(annotationClass) != null) {
				String methodName = mi.getName();
				return methodName;
			}
		}
		return null;
	}

	public boolean isEndTask() {
		return this.taskPropertyReader.isEndTask(false);
	}

	public boolean isExecuted() {
		return this.taskPropertyReader.isExecuted(false);
	}

	public boolean isExecuting() {
		return this.taskPropertyReader.isExecuting(false);
	}

	private boolean isGroupMethod(String groupName) {
		return this.taskMethodManager.existGroup(groupName);
	}

	public boolean isHotdeployDisable() {
		return hotdeployDisable;
	}

	public boolean isPrepared() {
		return this.prepared;
	}

	public boolean isReScheduleTask() {
		return this.taskPropertyReader.isReScheduleTask(false);
	}

	public boolean isShutdownTask() {
		return this.taskPropertyReader.isShutdownTask(false);
	}

	public boolean isStartTask() {
		return this.taskPropertyReader.isStartTask(false);
	}

	public boolean isForceUnScheduleTask() {
		return this.taskPropertyReader.isForceUnScheduleTask(false);
	}

	public void load() {
		FileInputStream fis = null;
		try {
			File targetFile = new File("C:\\temp\\", this.getTaskClass()
					.getCanonicalName());
			if (targetFile.exists()) {
				fis = new FileInputStream(targetFile);
				int size = fis.read();
				byte[] objectArray = new byte[size];
				fis.read(objectArray);
				this.task = SerializeUtil.fromBinaryToObject(objectArray);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
		}

		Map<String, Object> taskProperties = new HashMap<String, Object>();
		int size = this.beanDesc.getPropertyDescSize();
		for (int i = 0; i < size; i++) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc(i);
			Object value = pd.getValue(this.task);
			taskProperties.put(pd.getPropertyName(), value);
		}
	}

	private void notifyGetterSignal() {
		synchronized (this.getterSignal) {
			this.getterSignal.notify();
		}
	}

	public void prepare() {

		this.task = this.componentDef.getComponent();
		this.taskClass = this.componentDef.getComponentClass();

		this.taskMethodExecuteHandler = this.createTaskMethodExecuteHandler();
		this.taskGroupMethodExecuteHandler = this
				.createTaskGroupMethodExecuteHandler(this.taskMethodExecuteHandler);

		this.taskMethodExecuteHandler.setTaskExecuteStrategy(this);
		this.taskGroupMethodExecuteHandler.setTaskExecuteStrategy(this);

		this.beanDesc = BeanDescFactory.getBeanDesc(this.taskClass);

		this.taskPropertyReader = (TaskPropertyReader) this.s2Container
				.getComponent(TaskPropertyReader.class);
		this.taskPropertyWriter = (TaskPropertyWriter) this.s2Container
				.getComponent(TaskPropertyWriter.class);

		this.taskPropertyReader.setup(task, beanDesc);
		this.taskPropertyWriter.setup(task, beanDesc);

		this.taskMethodManager = new TaskMethodManager(this.taskClass,
				METHOD_PREFIX_NAME_DO);

		if (this.taskMethodInvoker == null) {
			ExecutorService jobMethodExecutorService = this
					.getExecutorService();
			this.taskMethodInvoker = new MethodInvoker(
					jobMethodExecutorService, this.task, this.beanDesc);
			this.taskMethodInvoker
					.setExecutorServiceFactory(this.executorServiceFactory);
		}
		this.prepared = true;
		this.save();
		this.load();
	}

	public void save() {

	}

	public void setComponentDef(ComponentDef componentDef) {
		this.componentDef = componentDef;
	}

	public void setEndTask(boolean endTask) {
		this.taskPropertyWriter.setEndTask(endTask);
	}

	public void setException(Exception exception) {
		this.taskPropertyWriter.setException(exception);
	}

	public void setExecuted(boolean executed) {
		this.taskPropertyWriter.setExecuted(executed);
	}

	public void setExecuting(boolean executing) {
		this.taskPropertyWriter.setExecuting(executing);
	}

	public void setExecutorServiceFactory(
			ExecutorServiceFactory executorServiceFactory) {
		this.executorServiceFactory = executorServiceFactory;
	}

	public void setGetterSignal(Object getterSignal) {
		this.getterSignal = getterSignal;
	}

	public void setHotdeployDisable(boolean hotdeployDisable) {
		this.hotdeployDisable = hotdeployDisable;
	}

	public void setS2Container(S2Container container) {
		s2Container = container;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.taskPropertyWriter.setShutdownTask(shutdownTask);
	}

	public void setStartTask(boolean startTask) {
		this.taskPropertyWriter.setStartTask(startTask);
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public void setTaskClass(Class<?> taskClass) {
		this.taskClass = taskClass;
	}

	public void setTaskId(long taskId) {
		if (this.taskPropertyWriter.hasTaskId()) {
			this.taskPropertyWriter.setTaskId(taskId);
			return;
		}
		this.taskId = taskId;
	}

	@Binding(bindingType = BindingType.NONE)
	public void setThreadPool(TaskThreadPool taskThreadPool) {
		this.taskPropertyWriter.setThreadPool(taskThreadPool);
	}

	@Binding(bindingType = BindingType.NONE)
	public void setTrigger(TaskTrigger taskTrigger) {
		this.taskPropertyWriter.setTrigger(taskTrigger);
	}

	public void setForceUnScheduleTask(boolean unScheduleTask) {
		this.taskPropertyWriter.setForceUnScheduleTask(unScheduleTask);
	}

	public String start() throws InterruptedException {
		this.setExecuted(true);
		this.setExecuting(true);
		String methodNameByAnnotation = getMethodNameByAnnotationClass(Start.class);
		List<String> methodNameList = new ArrayList<String>();
		if (methodNameByAnnotation != null) {
			methodNameList.add(methodNameByAnnotation);
		}
		methodNameList.addAll(Arrays.asList(METHOD_NAME_START));
		for (String methodName : methodNameList) {
			if (this.taskMethodInvoker.hasMethod(methodName)) {
				AsyncResult ar = this.taskMethodInvoker.beginInvoke(methodName);
				this.taskMethodInvoker.endInvoke(ar);
				TaskMethodMetaData md = new TaskMethodMetaData(this.beanDesc,
						methodName);
				this.notifyGetterSignal();
				String nextTaskName = md.getNextTask();
				if (nextTaskName == null
						&& this.taskMethodInvoker
								.hasMethod(METHOD_NAME_DEFAULT_TASK_METHOD_NAME)) {
					return METHOD_NAME_DEFAULT_TASK_NAME;
				}
				return nextTaskName;
			}
		}
		if (this.taskMethodInvoker
				.hasMethod(METHOD_NAME_DEFAULT_TASK_METHOD_NAME)) {
			return METHOD_NAME_DEFAULT_TASK_NAME;
		}

		return null;
	}

	public void unprepare() {
		prepared = false;
		taskGroupMethodExecuteHandler = null;
	}

	public void waitOne() throws InterruptedException {
		this.taskMethodInvoker.waitInvokes();
	}

}
