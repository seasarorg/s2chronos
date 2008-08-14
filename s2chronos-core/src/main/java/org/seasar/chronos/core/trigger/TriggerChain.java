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
public class TriggerChain implements TaskTrigger {

	private static final long serialVersionUID = -1L;
	
	private Long triggerId;

	private String name;

	private Object task;

	private String description;

	private List<TaskTrigger> triggerList = CollectionsUtil.newArrayList();

	public void addTrigger(TaskTrigger trigger) {
		triggerList.add(trigger);
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public Object getTask() {
		return task;
	}

	public Long getTriggerId() {
		return triggerId;
	}

	public boolean isEndTask() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isEndTask()) {
				return true;
			}
		}
		return false;
	}

	public boolean isExecute() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isExecute()) {
				return true;
			}
		}
		return false;
	}

	public boolean isReScheduleTask() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isReScheduleTask()) {
				return true;
			}
		}
		return false;
	}

	public boolean isShutdownTask() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isShutdownTask()) {
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

	public void removeTrigger(TaskTrigger trigger) {
		triggerList.remove(trigger);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndTask(boolean endTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setEndTask(endTask);
		}
	}

	public void setExecute(boolean execute) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setExecute(execute);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReScheduleTask(boolean reScheduleTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setReScheduleTask(reScheduleTask);
		}	
	}

	public void setShutdownTask(boolean shutdownTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setShutdownTask(shutdownTask);
		}
	}

	public void setStartTask(boolean startTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setEndTask(startTask);
		}
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public void setTriggerId(Long triggerId) {
		this.triggerId = triggerId;
	}
}
