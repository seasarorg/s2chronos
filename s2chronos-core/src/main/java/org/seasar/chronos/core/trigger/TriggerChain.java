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

	private boolean reScheduleTask;

	private List<TaskTrigger> triggerList = CollectionsUtil.newArrayList();

	private List<Boolean> executedCounts = CollectionsUtil.newArrayList();

	public boolean isForceUnScheduleTask() {
		return false;
	}

	public void setForceUnScheduleTask(boolean forceUnScheduleTask) {

	}

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

	public boolean isExecuting() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isExecuting()) {
				return true;
			}
		}
		return false;
	}

	public boolean isReScheduleTask() {
		return reScheduleTask;
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
		if (this.isExecuting() || this.isExecuted()) {
			return false;
		}
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isStartTask()) {
				trigger.setExecuted(true);
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

	public void setExecuting(boolean executing) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setExecuting(executing);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReScheduleTask(boolean reScheduleTask) {
		this.reScheduleTask = reScheduleTask;
	}

	public void setShutdownTask(boolean shutdownTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setShutdownTask(shutdownTask);
		}
	}

	public void setStartTask(boolean startTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setStartTask(startTask);
		}
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public void setTriggerId(Long triggerId) {
		this.triggerId = triggerId;
	}

	public boolean isExecuted() {
		return executedCounts.size() == this.triggerList.size();
	}

	public void setExecuted(boolean executed) {
		if (executed) {
			executedCounts.add(executed);
		} else {
			if (executedCounts.size() > 0) {
				executedCounts.remove(0);
			}
		}
	}

}
