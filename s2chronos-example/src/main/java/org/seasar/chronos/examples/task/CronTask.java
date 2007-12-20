package org.seasar.chronos.examples.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;

@Task
@CronTrigger(expression = "*/1 * * * *")
public class CronTask extends AbstractCommonTask {

}
