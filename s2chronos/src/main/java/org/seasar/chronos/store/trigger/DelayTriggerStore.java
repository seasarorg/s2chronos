package org.seasar.chronos.store.trigger;

import org.seasar.chronos.store.dxo.DelayTriggerDxo;
import org.seasar.chronos.store.entity.TriggerEntity;
import org.seasar.chronos.trigger.DelayTrigger;
import org.seasar.framework.exception.SQLRuntimeException;

public class DelayTriggerStore extends AbstractTriggerStore {

	private DelayTriggerDxo delayTriggerDxo;

	public void loadFromStore(long id, DelayTrigger trigger) {
		TriggerEntity triggerEntity = this.getTriggerDao().selectById(id);
		this.delayTriggerDxo.fromEntityFromComponent(triggerEntity, trigger);
	}

	public void saveToStore(DelayTrigger trigger) {
		TriggerEntity triggerEntity = this.delayTriggerDxo.toEntity(trigger);
		triggerEntity.setExecType("DT");
		try {
			this.getTriggerDao().update(triggerEntity);
		} catch (SQLRuntimeException ex) {
			this.getTriggerDao().insert(triggerEntity);
		}
	}

	public void setDelayTriggerDxo(DelayTriggerDxo delayTriggerDxo) {
		this.delayTriggerDxo = delayTriggerDxo;
	}
}
