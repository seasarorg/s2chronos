package org.seasar.chronos.store;

import org.seasar.chronos.store.dxo.NonDelayTriggerDxo;
import org.seasar.chronos.store.entity.TriggerEntity;
import org.seasar.chronos.trigger.NonDelayTrigger;
import org.seasar.framework.exception.SQLRuntimeException;

public class NonDelayTriggerStore extends AbstractTriggerStore {

	private NonDelayTriggerDxo nonDelayTriggerDxo;

	public void setNonDelayTriggerDxo(NonDelayTriggerDxo nonDelayTriggerDxo) {
		this.nonDelayTriggerDxo = nonDelayTriggerDxo;
	}

	public void loadFromStore(long id, NonDelayTrigger trigger) {
		TriggerEntity triggerEntity = this.getTriggerDao().selectById(id);
		this.nonDelayTriggerDxo.fromEntityFromComponent(triggerEntity, trigger);
	}

	public void saveToStore(NonDelayTrigger trigger) {
		TriggerEntity triggerEntity = this.nonDelayTriggerDxo.toEntity(trigger);
		triggerEntity.setExecType("NT");
		try {
			this.getTriggerDao().update(triggerEntity);
		} catch (SQLRuntimeException ex) {
			this.getTriggerDao().insert(triggerEntity);
		}
	}

}
