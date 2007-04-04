package org.seasar.chronos.store.dxo;

import java.util.List;

import org.seasar.chronos.store.entity.NonDelayTriggerEntity;
import org.seasar.chronos.trigger.NonDelayTrigger;

public interface NonDelayTriggerDxo {

	public NonDelayTriggerEntity toEntity(NonDelayTrigger component);

	public NonDelayTrigger toComponent(NonDelayTriggerEntity entity);

	public void fromComponentToEntity(NonDelayTrigger trigger,
			NonDelayTriggerEntity entity);

	public void fromEntityFromComponent(NonDelayTriggerEntity entity,
			NonDelayTrigger component);

	public List<NonDelayTriggerEntity> toEntities(
			List<NonDelayTrigger> component);

	public List<NonDelayTrigger> toComponents(List<NonDelayTriggerEntity> entity);

	public void fromComponentsToEntities(List<NonDelayTrigger> components,
			List<NonDelayTriggerEntity> entities);

	public void fromEntitiesToComponents(List<NonDelayTriggerEntity> entities,
			List<NonDelayTrigger> components);

}
