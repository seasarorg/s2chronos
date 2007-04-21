package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.trigger.TimedTrigger;
import org.seasar.chronos.extension.store.entity.TriggerEntity;
import org.seasar.extension.dxo.annotation.ConversionRule;

public interface TimedTriggerDxo {

	@ConversionRule("objectId : triggerId")
	public void fromComponentsToEntities(List<TimedTrigger> components,
			List<TriggerEntity> entities);

	@ConversionRule("objectId : triggerId")
	public void fromComponentToEntity(TimedTrigger trigger, TriggerEntity entity);

	@ConversionRule("triggerId : objectId")
	public void fromEntitiesToComponents(List<TriggerEntity> entities,
			List<TimedTrigger> components);

	@ConversionRule("triggerId : objectId")
	public void fromEntityToComponent(TriggerEntity entity,
			TimedTrigger component);

	@ConversionRule("triggerId : objectId")
	public TimedTrigger toComponent(TriggerEntity entity);

	@ConversionRule("triggerId : objectId")
	public List<TimedTrigger> toComponents(List<TriggerEntity> entity);

	@ConversionRule("objectId : triggerId")
	public List<TriggerEntity> toEntities(List<TimedTrigger> components);

	@ConversionRule("objectId : triggerId")
	public TriggerEntity toEntity(TimedTrigger component);

}
