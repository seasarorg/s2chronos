package org.seasar.chronos.core.schedule;

import java.util.concurrent.Future;

import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

public class ScheduleEntry extends AbstractScheduleEntry {

	private static final long serialVersionUID = -5656680297381222017L;

	private ComponentDef componentDef;

	private Object task;

	private Class<?> taskClass;

	private TaskStateType taskStateType;

	private TaskExecutorService taskExecutorService;

	private Future<TaskExecutorService> future;

	public ScheduleEntry() {

	}

	public ComponentDef getComponentDef() {
		return componentDef;
	}

	public Object getTask() {
		return task;
	}

	public Class<?> getTaskClass() {
		return taskClass;
	}

	public TaskExecutorService getTaskExecutorService() {
		return taskExecutorService;
	}

	public Future<TaskExecutorService> getTaskStaterFuture() {
		return future;
	}

	public TaskStateType getTaskStateType() {
		return taskStateType;
	}

	public void save() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

		// String taskName = TaskPropertyUtil.getTaskName(tes);
		// String description = TaskPropertyUtil.getDescription(tes);
		// Long taskId = TaskPropertyUtil.getTaskId(tes);
		// ScheduleEntity se = new ScheduleEntity();
		// se.setTaskName(taskName);
		// se.setDescription(description);
		// se.setTaskId(taskId);
		// se.setStatus(type.ordinal());
	}

	public void setComponentDef(ComponentDef componentDef) {
		this.componentDef = componentDef;
		this.setTaskClass(componentDef.getComponentClass());
	}

	public void setTask(Object target) {
		this.task = target;
	}

	public void setTaskClass(Class<?> targetClass) {
		this.taskClass = targetClass;
	}

	public void setTaskExecutorService(TaskExecutorService taskExecutorService) {
		this.taskExecutorService = taskExecutorService;
	}

	public void setTaskStaterFuture(Future<TaskExecutorService> future) {
		this.future = future;
	}

	public void setTaskStateType(TaskStateType taskStateType) {
		this.taskStateType = taskStateType;
	}

}
