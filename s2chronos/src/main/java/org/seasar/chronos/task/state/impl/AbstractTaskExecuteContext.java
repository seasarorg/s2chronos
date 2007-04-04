package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.state.TaskExecuteState;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public abstract class AbstractTaskExecuteContext implements TaskExecuteContext {

	private TaskExecuteState currentState;

	private Object task;

	private Class taskClass;

	private Object getterSignal;

	private TaskExecuteStrategy taskExecuteStrategy;

	private final TaskExecuteState taskExecuteStateInitialized;

	public final TaskExecuteState getTaskExecuteStateInitialized() {
		return taskExecuteStateInitialized;
	}

	private final TaskExecuteState taskExecuteStateNonInitialized;

	public final TaskExecuteState getTaskExecuteStateNonInitialize() {
		return taskExecuteStateNonInitialized;
	}

	public Object getTask() {
		return task;
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public Class getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(Class taskClass) {
		this.taskClass = taskClass;
	}

	protected abstract TaskExecuteStrategy createTaskExecuteStrategy();

	public AbstractTaskExecuteContext() {
		this.taskExecuteStrategy = createTaskExecuteStrategy();
		this.taskExecuteStateInitialized = new TaskExecuteStateInitialized(
				taskExecuteStrategy);
		this.taskExecuteStateNonInitialized = new TaskExecuteStateNonInitialized(
				taskExecuteStrategy);
		this.currentState = taskExecuteStateNonInitialized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#getTaskExecuteStrategy()
	 */
	public TaskExecuteStrategy getTaskExecuteStrategy() {
		return this.currentState.getTaskExecuteStrategy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#changeState(org.seasar.chronos.task.state.TaskExecuteState)
	 */
	public void changeState(TaskExecuteState nextState) {
		this.currentState = nextState;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// org.seasar.chronos.task.state.impl.TaskExecuteContext#setTaskComponentDef(org.seasar.framework.container.ComponentDef)
	// */
	// @Binding(bindingType = BindingType.NONE)
	// public void setTaskComponentDef(ComponentDef taskComponentDef) {
	// this.taskComponentDef = taskComponentDef;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#setGetterSignal(java.lang.Object)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setGetterSignal(Object getterSignal) {
		this.getterSignal = getterSignal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#prepare()
	 */
	public void prepare() {
		this.getTaskExecuteStrategy().setTask(this.task);
		this.getTaskExecuteStrategy().setTaskClass(this.taskClass);
		this.getTaskExecuteStrategy().setGetterSignal(this.getterSignal);
		this.currentState.prepare(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#initialize()
	 */
	public String initialize() throws InterruptedException {
		return this.currentState.initialize(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#execute(java.lang.String)
	 */
	public void execute(String startTaskName) throws InterruptedException {
		this.currentState.execute(this, startTaskName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#cancel()
	 */
	public boolean cancel() {
		return this.currentState.cancel(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#await(long,
	 *      java.util.concurrent.TimeUnit)
	 */
	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.currentState.await(this, time, timeUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#destroy()
	 */
	public String destroy() throws InterruptedException {
		return this.currentState.destroy(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.state.impl.TaskExecuteContext#waitOne()
	 */
	public void waitOne() throws InterruptedException {
		this.currentState.waitOne();
	}

}
