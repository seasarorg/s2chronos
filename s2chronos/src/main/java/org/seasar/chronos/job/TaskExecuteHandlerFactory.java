package org.seasar.chronos.job;

import org.seasar.chronos.job.impl.JobGroupMethodExecuteHandlerImpl;
import org.seasar.chronos.job.impl.JobMethodExecuteHandlerImpl;

public class TaskExecuteHandlerFactory {

	public static TaskExecuteHandler create(TaskType type) {
		TaskExecuteHandler result = null;
		if (type == TaskType.JOBGROUP) {
			result = new JobGroupMethodExecuteHandlerImpl();
		} else {
			result = new JobMethodExecuteHandlerImpl();
		}
		return result;
	}

}
