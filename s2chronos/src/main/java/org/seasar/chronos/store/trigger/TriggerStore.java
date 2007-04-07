package org.seasar.chronos.store.trigger;

import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.store.dao.TriggerDao;
import org.seasar.chronos.store.dxo.CronTriggerDxo;
import org.seasar.chronos.store.dxo.DelayTriggerDxo;
import org.seasar.chronos.store.dxo.NonDelayTriggerDxo;
import org.seasar.chronos.store.dxo.TimedTriggerDxo;
import org.seasar.chronos.store.entity.TriggerEntity;
import org.seasar.chronos.trigger.CronTrigger;
import org.seasar.chronos.trigger.DelayTrigger;
import org.seasar.chronos.trigger.NonDelayTrigger;
import org.seasar.chronos.trigger.TimedTrigger;
import org.seasar.framework.exception.SQLRuntimeException;

public final class TriggerStore {

	private TriggerDao triggerDao;

	private CronTriggerDxo cronTriggerDxo;

	private DelayTriggerDxo delayTriggerDxo;

	private NonDelayTriggerDxo nonDelayTriggerDxo;

	private TimedTriggerDxo timedTriggerDxo;

	public TaskTrigger loadFromStore(int id) {
		TriggerEntity triggerEntity = triggerDao.selectById(id);
		if (triggerEntity == null) {
			return null;
		}
		if ("CT".equals(triggerEntity.getExecType())) {
			return cronTriggerDxo.toComponent(triggerEntity);
		} else if ("DT".equals(triggerEntity.getExecType())) {
			return delayTriggerDxo.toComponent(triggerEntity);
		} else if ("NT".equals(triggerEntity.getExecType())) {
			return nonDelayTriggerDxo.toComponent(triggerEntity);
		} else if ("TT".equals(triggerEntity.getExecType())) {
			return timedTriggerDxo.toComponent(triggerEntity);
		}
		return null;
	}

	public void loadFromStore(int id, TaskTrigger trigger) {
		TriggerEntity triggerEntity = triggerDao.selectById(id);
		if ("CT".equals(triggerEntity.getExecType())) {
			cronTriggerDxo.fromEntityFromComponent(triggerEntity,
					(CronTrigger) trigger);
		} else if ("DT".equals(triggerEntity.getExecType())) {
			delayTriggerDxo.fromEntityFromComponent(triggerEntity,
					(DelayTrigger) trigger);
		} else if ("NT".equals(triggerEntity.getExecType())) {
			nonDelayTriggerDxo.fromEntityFromComponent(triggerEntity,
					(NonDelayTrigger) trigger);
		} else if ("TT".equals(triggerEntity.getExecType())) {
			timedTriggerDxo.fromEntityFromComponent(triggerEntity,
					(TimedTrigger) trigger);
		}
	}

	public void saveToStore(TaskTrigger trigger) {
		TriggerEntity triggerEntity = null;
		if (trigger instanceof CronTrigger) {
			triggerEntity = cronTriggerDxo.toEntity((CronTrigger) trigger);
		} else if (trigger instanceof DelayTrigger) {
			triggerEntity = delayTriggerDxo.toEntity((DelayTrigger) trigger);
		} else if (trigger instanceof NonDelayTrigger) {
			triggerEntity = nonDelayTriggerDxo
					.toEntity((NonDelayTrigger) trigger);
		} else if (trigger instanceof TimedTrigger) {
			triggerEntity = timedTriggerDxo.toEntity((TimedTrigger) trigger);
		}
		try {
			this.triggerDao.update(triggerEntity);
		} catch (SQLRuntimeException ex) {
			this.triggerDao.insert(triggerEntity);
		}
	}

}
