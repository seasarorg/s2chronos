package org.seasar.chronos.examples.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.examples.annotation.ExampleTrigger;

@Task
@ExampleTrigger(div = 3)
public class ExampleTriggerTask extends AbstractCommonTask {

}
