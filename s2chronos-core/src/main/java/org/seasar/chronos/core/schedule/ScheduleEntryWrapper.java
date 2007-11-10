package org.seasar.chronos.core.schedule;

import java.util.concurrent.Future;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

public class ScheduleEntryWrapper extends AbstractScheduleEntry {

	private TaskScheduleEntry taskScheduleEntry;

	public ScheduleEntryWrapper(TaskScheduleEntry taskScheduleEntry) {
		this.taskScheduleEntry = taskScheduleEntry;
	}

	public ComponentDef getComponentDef() {
		return taskScheduleEntry.getComponentDef();
	}

	public Long getScheduleId() {
		return taskScheduleEntry.getScheduleId();
	}

	 public Object getTask() {
		return taskScheduleEntry.getTask();
	}

	public Class<?> getTaskClass() {
		return taskScheduleEntry.getTaskClass();
	}

	public TaskExecutorService getTaskExecutorService() {
		return taskScheduleEntry.getTaskExecutorService();
	}

	public Future<TaskExecutorService> getTaskStaterFuture() {
		return taskScheduleEntry.getTaskStaterFuture();
	}

	public TaskStateType getTaskStateType() {
		return taskScheduleEntry.getTaskStateType();
	}

	public void setComponentDef(ComponentDef componentDef) {
		taskScheduleEntry.setComponentDef(componentDef);
	}

	public void setScheduleId(Long scheduleId) {
		taskScheduleEntry.setScheduleId(scheduleId);
	}

	public void setTask(Object target) {
		taskScheduleEntry.setTask(target);
	}

	public void setTaskClass(Class<?> targetClass) {
		taskScheduleEntry.setTaskClass(targetClass);
	}

	public void setTaskExecutorService(TaskExecutorService taskExecutorService) {
		taskScheduleEntry.setTaskExecutorService(taskExecutorService);
	}

	public void setTaskStaterFuture(Future<TaskExecutorService> future) {
		taskScheduleEntry.setTaskStaterFuture(future);
	}

	public void setTaskStateType(TaskStateType taskStateType) {
		taskScheduleEntry.setTaskStateType(taskStateType);
	}

}
