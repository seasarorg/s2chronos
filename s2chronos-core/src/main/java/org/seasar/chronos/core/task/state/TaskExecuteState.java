package org.seasar.chronos.core.task.state;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.task.state.impl.AbstractTaskExecuteContext;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public interface TaskExecuteState {

	public TaskExecuteStrategy getTaskExecuteStrategy();

	public String initialize(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException;

	public boolean cancel(TaskExecuteContext context);

	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public String destroy(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public void waitOne() throws InterruptedException;

}
