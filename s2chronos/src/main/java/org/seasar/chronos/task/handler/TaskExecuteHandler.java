package org.seasar.chronos.task.handler;

import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.task.Transition;
import org.seasar.chronos.task.impl.MethodGroupManager;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;

public interface TaskExecuteHandler {

	public void setTaskExecuteStrategy(TaskExecuteStrategy taskExecuteStrategy);

	public TaskExecuteStrategy getTaskExecuteStrategy();

	public MethodInvoker getMethodInvoker();

	public void setMethodInvoker(MethodInvoker methodInvoker);

	public MethodGroupManager getMethodGroupMap();

	public void setMethodGroupMap(MethodGroupManager methodGroupManager);

	public Transition handleRequest(String startTaskName)
			throws InterruptedException;

}
