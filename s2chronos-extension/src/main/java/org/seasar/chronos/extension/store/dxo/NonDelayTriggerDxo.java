package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.trigger.NonDelayTrigger;
import org.seasar.chronos.extension.store.entity.TriggerEntity;
import org.seasar.extension.dxo.annotation.ConversionRule;

public interface NonDelayTriggerDxo {

	@ConversionRule("objectId : triggerId")
	public void fromComponentsToEntities(List<NonDelayTrigger> components,
			List<TriggerEntity> entities);

	@ConversionRule("objectId : triggerId")
	public void fromComponentToEntity(NonDelayTrigger trigger,
			TriggerEntity entity);

	@ConversionRule("triggerId : objectId")
	public void fromEntitiesToComponents(List<TriggerEntity> entities,
			List<NonDelayTrigger> components);

	@ConversionRule("triggerId : objectId")
	public void fromEntityToComponent(TriggerEntity entity,
			NonDelayTrigger component);

	@ConversionRule("triggerId : objectId")
	public NonDelayTrigger toComponent(TriggerEntity entity);

	@ConversionRule("triggerId : objectId")
	public List<NonDelayTrigger> toComponents(List<TriggerEntity> entity);

	@ConversionRule("objectId : triggerId")
	public List<TriggerEntity> toEntities(List<NonDelayTrigger> components);

	@ConversionRule("objectId : triggerId")
	public TriggerEntity toEntity(NonDelayTrigger component);
}
