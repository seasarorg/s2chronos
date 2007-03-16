package org.seasar.chronos.impl;

import java.util.concurrent.Future;

import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

public class TaskContena {

	private ComponentDef componentDef;

	private Object target;

	private Class targetClass;

	private TaskExecutorService taskExecutorService;

	private Future<TaskExecutorService> future;

	public TaskContena(ComponentDef componentDef) {
		this.setComponentDef(componentDef);
		this.setTarget(componentDef.getComponent());
		this.setTargetClass(componentDef.getComponentClass());
	}

	public ComponentDef getComponentDef() {
		return componentDef;
	}

	public void setComponentDef(ComponentDef componentDef) {
		this.componentDef = componentDef;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class targetClass) {
		this.targetClass = targetClass;
	}

	public Future<TaskExecutorService> getFuture() {
		return future;
	}

	public void setFuture(Future<TaskExecutorService> future) {
		this.future = future;
	}

	public TaskExecutorService getTaskExecutorService() {
		return taskExecutorService;
	}

	public void setTaskExecutorService(TaskExecutorService taskExecutorService) {
		this.taskExecutorService = taskExecutorService;
	}

}
