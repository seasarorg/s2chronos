package org.seasar.chronos.core;

import java.util.concurrent.Future;

import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

public interface TaskScheduleEntry extends Serializable {

	public ComponentDef getComponentDef();

	public Long getScheduleId();

	public Object getTask();

	public Class getTaskClass();

	public TaskExecutorService getTaskExecutorService();

	public Future<TaskExecutorService> getTaskStaterFuture();

	public TaskStateType getTaskStateType();

	public void setComponentDef(ComponentDef componentDef);

	public void setScheduleId(Long scheduleId);

	public void setTask(Object target);

	public void setTaskClass(Class targetClass);

	public void setTaskExecutorService(TaskExecutorService taskExecutorService);

	public void setTaskStaterFuture(Future<TaskExecutorService> future);

	public void setTaskStateType(TaskStateType taskStateType);

}
