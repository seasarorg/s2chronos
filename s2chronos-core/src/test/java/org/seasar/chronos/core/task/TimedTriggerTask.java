package org.seasar.chronos.core.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.TimedTrigger;

@Task
@TimedTrigger(startTime = "00????e 15:15:00")
public class TimedTriggerTask {

}
