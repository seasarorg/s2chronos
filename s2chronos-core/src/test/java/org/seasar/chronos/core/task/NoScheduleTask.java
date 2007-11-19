package org.seasar.chronos.core.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;

@Task(autoSchedule = false, name = "noScheduleTask")
@NonDelayTrigger
public class NoScheduleTask extends AbstractCommonTask {

}
