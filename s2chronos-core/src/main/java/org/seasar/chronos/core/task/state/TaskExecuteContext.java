package org.seasar.chronos.core.task.state;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public interface TaskExecuteContext {

	public TaskExecuteStrategy getTaskExecuteStrategy();

	public void changeState(TaskExecuteState nextState);

	public TaskExecuteState getTaskExecuteStateInitialized();

	public TaskExecuteState getTaskExecuteStateNonInitialize();

	@Binding(bindingType = BindingType.NONE)
	public void setGetterSignal(Object getterSignal);

	public void prepare();

	public String initialize() throws InterruptedException;

	public void execute(String startTaskName) throws InterruptedException;

	public boolean cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public String destroy() throws InterruptedException;

	public void waitOne() throws InterruptedException;

}