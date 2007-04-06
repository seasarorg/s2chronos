package org.seasar.chronos.store.trigger;

import org.seasar.chronos.store.dxo.TimedTriggerDxo;
import org.seasar.chronos.store.entity.TriggerEntity;
import org.seasar.chronos.trigger.TimedTrigger;
import org.seasar.framework.exception.SQLRuntimeException;

public class TimedTriggerStore extends AbstractTriggerStore {

	private TimedTriggerDxo timedTriggerDxo;

	public void loadFromStore(long id, TimedTrigger trigger) {
		TriggerEntity triggerEntity = this.getTriggerDao().selectById(id);
		this.timedTriggerDxo.fromEntityFromComponent(triggerEntity, trigger);
	}

	public void saveToStore(TimedTrigger trigger) {
		TriggerEntity triggerEntity = this.timedTriggerDxo.toEntity(trigger);
		triggerEntity.setExecType("TT");
		try {
			this.getTriggerDao().update(triggerEntity);
		} catch (SQLRuntimeException ex) {
			this.getTriggerDao().insert(triggerEntity);
		}
	}

	public void setTimedTriggerDxo(TimedTriggerDxo timedTriggerDxo) {
		this.timedTriggerDxo = timedTriggerDxo;
	}

}
