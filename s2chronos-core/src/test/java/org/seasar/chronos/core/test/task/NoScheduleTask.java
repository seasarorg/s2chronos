package org.seasar.chronos.core.test.task;

import org.seasar.chronos.core.annotation.task.Task;

@Task(autoSchedule = false)
public class NoScheduleTask extends AbstractCommonTask {

}
