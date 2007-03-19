package org.seasar.chronos.task.state;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.ComponentDef;

public interface TaskExecuteState {

	public TaskExecuteStrategy getTaskExecuteStrategy();

	public void setTaskComponentDef(ComponentDef taskComponentDef);

	public void prepare(TaskExecuteContext context);

	public String initialize(TaskExecuteContext context)
			throws InterruptedException;

	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException;

	public void cancel(TaskExecuteContext context);

	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public void destroy(TaskExecuteContext context) throws InterruptedException;

	public void waitOne() throws InterruptedException;

}
