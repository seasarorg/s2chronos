package org.seasar.chronos.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;

/**
 * クーロンタスクです．
 * <p>
 * トリガーはCronTriggerアノテーションです．
 * </p>
 */
@Task
@CronTrigger(cronExpression = "0 */1 * * * ?")
public class CronTask extends AbstractCommonTask {

}
