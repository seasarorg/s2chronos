package org.seasar.chronos.impl;

import java.util.concurrent.Future;

import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

public class TaskContena {

	private ComponentDef componentDef;

	private Object task;

	private Class taskClass;

	private TaskExecutorService taskExecutorService;

	private Future<TaskExecutorService> future;

	public TaskContena() {

	}

	public TaskContena(ComponentDef componentDef) {
		this.setComponentDef(componentDef);
		this.setTaskClass(componentDef.getComponentClass());
	}

	public ComponentDef getComponentDef() {
		return componentDef;
	}

	public void setComponentDef(ComponentDef componentDef) {
		this.componentDef = componentDef;
	}

	public Object getTask() {
		return task;
	}

	public void setTask(Object target) {
		this.task = target;
	}

	public Class getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(Class targetClass) {
		this.taskClass = targetClass;
	}

	public Future<TaskExecutorService> getTaskStaterFuture() {
		return future;
	}

	public void setTaskStaterFuture(Future<TaskExecutorService> future) {
		this.future = future;
	}

	public TaskExecutorService getTaskExecutorService() {
		return taskExecutorService;
	}

	public void setTaskExecutorService(TaskExecutorService taskExecutorService) {
		this.taskExecutorService = taskExecutorService;
	}

}
