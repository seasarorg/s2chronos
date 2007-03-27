package org.seasar.chronos.task.state;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskExecuteContext {

	private TaskExecuteState currentState;

	private ComponentDef taskComponentDef;

	private Object getterSignal;

	public TaskExecuteStrategy getTaskExecuteStrategy() {
		return this.currentState.getTaskExecuteStrategy();
	}

	public void changeState(TaskExecuteState nextState) {
		this.currentState = nextState;
	}

	@Binding(bindingType = BindingType.NONE)
	public void setTaskComponentDef(ComponentDef taskComponentDef) {
		this.taskComponentDef = taskComponentDef;
	}

	@Binding(bindingType = BindingType.NONE)
	public void setGetterSignal(Object getterSignal) {
		this.getterSignal = getterSignal;
	}

	public void prepare() {
		this.getTaskExecuteStrategy()
				.setTaskComponentDef(this.taskComponentDef);
		this.getTaskExecuteStrategy().setGetterSignal(this.getterSignal);
		this.currentState.prepare(this);
	}

	public String initialize() throws InterruptedException {
		return this.currentState.initialize(this);
	}

	public void execute(String startTaskName) throws InterruptedException {
		this.currentState.execute(this, startTaskName);
	}

	public boolean cancel() {
		return this.currentState.cancel(this);
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.currentState.await(this, time, timeUnit);
	}

	public void destroy() throws InterruptedException {
		this.currentState.destroy(this);
	}

	public void waitOne() throws InterruptedException {
		this.currentState.waitOne();
	}

}
