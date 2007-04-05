package org.seasar.chronos.store.dxo;

import java.util.List;

import org.seasar.chronos.store.entity.TriggerEntity;
import org.seasar.chronos.trigger.TimedTrigger;

public interface TimedTriggerDxo {

	public TriggerEntity toEntity(TimedTrigger component);

	public TimedTrigger toComponent(TriggerEntity entity);

	public void fromComponentToEntity(TimedTrigger trigger,
			TriggerEntity entity);

	public void fromEntityFromComponent(TriggerEntity entity,
			TimedTrigger component);

	public List<TriggerEntity> toEntities(List<TimedTrigger> components);

	public List<TimedTrigger> toComponents(List<TriggerEntity> entity);

	public void fromComponentsToEntities(List<TimedTrigger> components,
			List<TriggerEntity> entities);

	public void fromEntitiesToComponents(List<TriggerEntity> entities,
			List<TimedTrigger> components);
}
