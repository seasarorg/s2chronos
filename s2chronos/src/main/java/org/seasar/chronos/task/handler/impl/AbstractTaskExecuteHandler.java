package org.seasar.chronos.task.handler.impl;

import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.task.Transition;
import org.seasar.chronos.task.handler.TaskExecuteHandler;
import org.seasar.chronos.task.impl.MethodGroupManager;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.log.Logger;

public abstract class AbstractTaskExecuteHandler implements TaskExecuteHandler {

	protected static final String METHOD_PREFIX_NAME_DO = "do";

	protected static final String METHOD_PREFIX_NAME_START = "start";

	protected static final String METHOD_PREFIX_NAME_END = "end";

	private static Logger log = Logger
			.getLogger(AbstractTaskExecuteHandler.class);

	private MethodInvoker methodInvoker;

	public MethodGroupManager methodGroupManager;

	public abstract Transition handleRequest(String startTaskName)
			throws InterruptedException;

	private TaskExecuteStrategy taskExecuteStrategy;

	public void setTaskExecuteStrategy(TaskExecuteStrategy taskExecuteStrategy) {
		this.taskExecuteStrategy = taskExecuteStrategy;
	}

	public TaskExecuteStrategy getTaskExecuteStrategy() {
		return this.taskExecuteStrategy;
	}

	public void setMethodInvoker(MethodInvoker methodInvoker) {
		this.methodInvoker = methodInvoker;
	}

	public MethodInvoker getMethodInvoker() {
		return methodInvoker;
	}

	public void setMethodGroupMap(MethodGroupManager methodGroupManager) {
		this.methodGroupManager = methodGroupManager;
	}

	public MethodGroupManager getMethodGroupMap() {
		return methodGroupManager;
	}

	protected String toMethodName(String taskName) {
		StringBuffer sb = new StringBuffer();
		String firstChar = taskName.substring(0, 1);
		String afterString = taskName.substring(1);
		sb.append(METHOD_PREFIX_NAME_DO);
		sb.append(firstChar.toUpperCase());
		sb.append(afterString);
		return sb.toString();
	}

	protected String toTaskName(String functionName) {
		StringBuffer sb = new StringBuffer("");
		if (functionName.startsWith(METHOD_PREFIX_NAME_DO)) {
			functionName = functionName.substring(2);
			String firstChar = functionName.substring(0, 1).toLowerCase();
			String afterString = functionName.substring(1);
			sb.append(firstChar).append(afterString);
		}
		return sb.toString();
	}

	protected Transition getTerminateTransition() {
		return getTerminateTransition(null);
	}

	protected Transition getTerminateTransition(String lastTaskName) {
		boolean terminate = getTaskExecuteStrategy().getEndTask();
		if (terminate) {
			log.warn("タスクが終了しました");
			return new Transition(true, null, lastTaskName);
		}
		return null;
	}
}
