package org.seasar.chronos.store.dxo;

import java.util.List;

import org.seasar.chronos.store.entity.TimedTriggerEntity;
import org.seasar.chronos.trigger.TimedTrigger;

public interface TimedTriggerDxo {

	public TimedTriggerEntity toEntity(TimedTrigger component);

	public TimedTrigger toComponent(TimedTriggerEntity entity);

	public void fromComponentToEntity(TimedTrigger trigger,
			TimedTriggerEntity entity);

	public void fromEntityFromComponent(TimedTriggerEntity entity,
			TimedTrigger component);

	public List<TimedTriggerEntity> toEntities(List<TimedTrigger> components);

	public List<TimedTrigger> toComponents(List<TimedTriggerEntity> entity);

	public void fromComponentsToEntities(List<TimedTrigger> components,
			List<TimedTriggerEntity> entities);

	public void fromEntitiesToComponents(List<TimedTriggerEntity> entities,
			List<TimedTrigger> components);
}
