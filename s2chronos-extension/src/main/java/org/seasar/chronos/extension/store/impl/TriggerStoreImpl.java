package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.trigger.CronTrigger;
import org.seasar.chronos.core.trigger.DelayTrigger;
import org.seasar.chronos.core.trigger.NonDelayTrigger;
import org.seasar.chronos.core.trigger.TimedTrigger;
import org.seasar.chronos.extension.store.TriggerStore;
import org.seasar.chronos.extension.store.dao.TriggerDao;
import org.seasar.chronos.extension.store.dxo.CronTriggerDxo;
import org.seasar.chronos.extension.store.dxo.DelayTriggerDxo;
import org.seasar.chronos.extension.store.dxo.NonDelayTriggerDxo;
import org.seasar.chronos.extension.store.dxo.TimedTriggerDxo;
import org.seasar.chronos.extension.store.entity.TriggerEntity;

public class TriggerStoreImpl implements TriggerStore {

	public TriggerDao triggerDao;

	public CronTriggerDxo cronTriggerDxo;

	public DelayTriggerDxo delayTriggerDxo;

	public NonDelayTriggerDxo nonDelayTriggerDxo;

	public TimedTriggerDxo timedTriggerDxo;

	public TaskTrigger loadFromStore(Long id) {
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

	public void loadFromStore(Long id, TaskTrigger trigger) {
		TriggerEntity triggerEntity = triggerDao.selectById(id);
		if (triggerEntity == null) {
			return;
		}
		if ("CT".equals(triggerEntity.getExecType())) {
			cronTriggerDxo.fromEntityToComponent(triggerEntity,
					(CronTrigger) trigger);
		} else if ("DT".equals(triggerEntity.getExecType())) {
			delayTriggerDxo.fromEntityToComponent(triggerEntity,
					(DelayTrigger) trigger);
		} else if ("NT".equals(triggerEntity.getExecType())) {
			nonDelayTriggerDxo.fromEntityToComponent(triggerEntity,
					(NonDelayTrigger) trigger);
		} else if ("TT".equals(triggerEntity.getExecType())) {
			timedTriggerDxo.fromEntityToComponent(triggerEntity,
					(TimedTrigger) trigger);
		}
	}

	public Long saveToStore(TaskTrigger trigger) {
		TriggerEntity triggerEntity = this.triggerDao.selectByObjectId(trigger
				.getTriggerId());
		if (triggerEntity == null) {
			if (trigger instanceof CronTrigger) {
				triggerEntity = cronTriggerDxo.toEntity((CronTrigger) trigger);
				triggerEntity.setExecType("CT");
			} else if (trigger instanceof DelayTrigger) {
				triggerEntity = delayTriggerDxo
						.toEntity((DelayTrigger) trigger);
				triggerEntity.setExecType("DT");
			} else if (trigger instanceof NonDelayTrigger) {
				triggerEntity = nonDelayTriggerDxo
						.toEntity((NonDelayTrigger) trigger);
				triggerEntity.setExecType("NT");
			} else if (trigger instanceof TimedTrigger) {
				triggerEntity = timedTriggerDxo
						.toEntity((TimedTrigger) trigger);
				triggerEntity.setExecType("TT");
			}
			this.triggerDao.insert(triggerEntity);
		} else {
			if (trigger instanceof CronTrigger) {
				cronTriggerDxo.fromComponentToEntity((CronTrigger) trigger,
						triggerEntity);
			} else if (trigger instanceof DelayTrigger) {
				delayTriggerDxo.fromComponentToEntity((DelayTrigger) trigger,
						triggerEntity);
			} else if (trigger instanceof NonDelayTrigger) {
				nonDelayTriggerDxo.fromComponentToEntity(
						(NonDelayTrigger) trigger, triggerEntity);
			} else if (trigger instanceof TimedTrigger) {
				timedTriggerDxo.fromComponentToEntity((TimedTrigger) trigger,
						triggerEntity);
			}
			this.triggerDao.update(triggerEntity);
		}
		return triggerEntity.getId();
	}

	public void setCronTriggerDxo(CronTriggerDxo cronTriggerDxo) {
		this.cronTriggerDxo = cronTriggerDxo;
	}

	public void setDelayTriggerDxo(DelayTriggerDxo delayTriggerDxo) {
		this.delayTriggerDxo = delayTriggerDxo;
	}

	public void setNonDelayTriggerDxo(NonDelayTriggerDxo nonDelayTriggerDxo) {
		this.nonDelayTriggerDxo = nonDelayTriggerDxo;
	}

	public void setTimedTriggerDxo(TimedTriggerDxo timedTriggerDxo) {
		this.timedTriggerDxo = timedTriggerDxo;
	}

	public void setTriggerDao(TriggerDao triggerDao) {
		this.triggerDao = triggerDao;
	}

}
