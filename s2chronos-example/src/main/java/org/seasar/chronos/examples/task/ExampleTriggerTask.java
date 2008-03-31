package org.seasar.chronos.examples.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.examples.annotation.ExampleTrigger;

/**
 * ExampleTriggerに対応するタスクです．
 * <p>
 * トリガーはExampleTriggerアノテーションで設定されます．対応するトリガークラスはCをつけたCExampleTriggerクラスです．
 * </p>
 * @author junichi
 *
 */
@Task
@ExampleTrigger(div = 3)
public class ExampleTriggerTask extends AbstractCommonTask {

}
