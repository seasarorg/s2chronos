package org.seasar.chronos.core.model.trigger;

import java.util.List;

import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.framework.util.tiger.CollectionsUtil;

/**
 * 複数のトリガーを扱えるトリガーチェインクラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public class TriggerChain implements TaskTrigger {
	private Long triggerId;

	private String name;

	private Object task;

	private String description;

	private boolean reScheduleTask;

	private final List<TaskTrigger> triggerList =
	    CollectionsUtil.newArrayList();

	private final List<Boolean> executedCounts = CollectionsUtil.newArrayList();

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isForceUnScheduleTask()
	 */
	public boolean isForceUnScheduleTask() {
		return false;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setForceUnScheduleTask(boolean)
	 */
	public void setForceUnScheduleTask(boolean forceUnScheduleTask) {
	}

	/**
	 * トリガーを追加します．
	 * 
	 * @param trigger
	 *            {@link TaskTrigger}
	 */
	public void addTrigger(TaskTrigger trigger) {
		triggerList.add(trigger);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getTask()
	 */
	public Object getTask() {
		return task;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getTriggerId()
	 */
	public Long getTriggerId() {
		return triggerId;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isEndTask()
	 */
	public boolean isEndTask() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isEndTask()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isExecuting()
	 */
	public boolean isExecuting() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isExecuting()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isReScheduleTask()
	 */
	public boolean isReScheduleTask() {
		return reScheduleTask;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isShutdownTask()
	 */
	public boolean isShutdownTask() {
		for (TaskTrigger trigger : triggerList) {
			if (trigger.isShutdownTask()) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isStartTask()
	 */
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

	/**
	 * トリガーを削除します．
	 * 
	 * @param trigger
	 *            {@link TaskTrigger}
	 */
	public void removeTrigger(TaskTrigger trigger) {
		triggerList.remove(trigger);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setDescription(java.lang.String
	 * )
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setEndTask(boolean)
	 */
	public void setEndTask(boolean endTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setEndTask(endTask);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setExecuting(boolean)
	 */
	public void setExecuting(boolean executing) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setExecuting(executing);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setReScheduleTask(boolean)
	 */
	public void setReScheduleTask(boolean reScheduleTask) {
		this.reScheduleTask = reScheduleTask;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setShutdownTask(boolean)
	 */
	public void setShutdownTask(boolean shutdownTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setShutdownTask(shutdownTask);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setStartTask(boolean)
	 */
	public void setStartTask(boolean startTask) {
		for (TaskTrigger trigger : triggerList) {
			trigger.setStartTask(startTask);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setTask(java.lang.Object)
	 */
	public void setTask(Object task) {
		this.task = task;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setTriggerId(java.lang.Long)
	 */
	public void setTriggerId(Long triggerId) {
		this.triggerId = triggerId;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isExecuted()
	 */
	public boolean isExecuted() {
		return executedCounts.size() == this.triggerList.size();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setExecuted(boolean)
	 */
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
