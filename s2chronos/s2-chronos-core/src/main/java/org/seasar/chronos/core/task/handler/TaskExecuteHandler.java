package org.seasar.chronos.core.task.handler;

import java.io.Serializable;

import org.seasar.chronos.core.delegate.MethodInvoker;
import org.seasar.chronos.core.task.Transition;
import org.seasar.chronos.core.task.impl.TaskMethodManager;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public interface TaskExecuteHandler extends Serializable {

	public void setTaskExecuteStrategy(TaskExecuteStrategy taskExecuteStrategy);

	public TaskExecuteStrategy getTaskExecuteStrategy();

	public MethodInvoker getMethodInvoker();

	public void setMethodInvoker(MethodInvoker methodInvoker);

	public TaskMethodManager getMethodGroupMap();

	public void setMethodGroupMap(TaskMethodManager taskMethodManager);

	public Transition handleRequest(String startTaskName)
			throws InterruptedException;

}
