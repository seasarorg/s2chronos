package org.seasar.chronos.task.state;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public interface TaskExecuteState {

	public String initialize(TaskExecuteContext context,
			ComponentDef jobComponentDef) throws InterruptedException;

	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException;

	public void cancel(TaskExecuteContext context);

	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public void destroy(TaskExecuteContext context) throws InterruptedException;

}
