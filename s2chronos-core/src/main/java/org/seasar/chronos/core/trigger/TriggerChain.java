package org.seasar.chronos.core.trigger;

import java.util.List;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.framework.util.tiger.CollectionsUtil;

/**
 * 複数のトリガーを扱えるトリガーチェインクラスです．
 * 
 * @author j5ik2o
 * 
 */
public class TriggerChain extends AbstractTrigger {

	private static final long serialVersionUID = -1L;

	private List<TaskTrigger> triggerList = CollectionsUtil.newArrayList();

	public boolean isEndTask() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isEndTask()) {
				return true;
			}
		}
		return false;
	}

	public boolean isStartTask() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isStartTask()) {
				return true;
			}
		}
		return false;
	}

	public void setEndTask(boolean endTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setEndTask(endTask);
		}
	}

	public void setStartTask(boolean startTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setEndTask(startTask);
		}
	}

	public void addTrigger(TaskTrigger trigger) {
		triggerList.add(trigger);
	}

	public void removeTrigger(TaskTrigger trigger) {
		triggerList.remove(trigger);
	}
}
