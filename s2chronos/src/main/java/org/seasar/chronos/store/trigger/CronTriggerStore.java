package org.seasar.chronos.store.trigger;

import org.seasar.chronos.store.dxo.CronTriggerDxo;
import org.seasar.chronos.store.entity.TriggerEntity;
import org.seasar.chronos.trigger.CronTrigger;
import org.seasar.framework.exception.SQLRuntimeException;

public class CronTriggerStore extends AbstractTriggerStore {

	private CronTriggerDxo cronTriggerDxo;

	public CronTriggerDxo getCronTriggerDxo() {
		return cronTriggerDxo;
	}

	public void setCronTriggerDxo(CronTriggerDxo cronTriggerDxo) {
		this.cronTriggerDxo = cronTriggerDxo;
	}

	public void loadFromStore(long id, CronTrigger trigger) {
		TriggerEntity triggerEntity = this.getTriggerDao().selectById(id);
		this.cronTriggerDxo.fromEntityFromComponent(triggerEntity, trigger);
	}

	public void saveToStore(CronTrigger trigger) {
		TriggerEntity triggerEntity = this.cronTriggerDxo.toEntity(trigger);
		triggerEntity.setExecType("CT");
		try {
			this.getTriggerDao().update(triggerEntity);
		} catch (SQLRuntimeException ex) {
			this.getTriggerDao().insert(triggerEntity);
		}
	}

}
