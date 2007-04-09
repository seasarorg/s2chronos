package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.trigger.NonDelayTrigger;
import org.seasar.chronos.extension.store.entity.TriggerEntity;

public interface NonDelayTriggerDxo {

	public TriggerEntity toEntity(NonDelayTrigger component);

	public NonDelayTrigger toComponent(TriggerEntity entity);

	public void fromComponentToEntity(NonDelayTrigger trigger,
			TriggerEntity entity);

	public void fromEntityFromComponent(TriggerEntity entity,
			NonDelayTrigger component);

	public List<TriggerEntity> toEntities(List<NonDelayTrigger> components);

	public List<NonDelayTrigger> toComponents(List<TriggerEntity> entity);

	public void fromComponentsToEntities(List<NonDelayTrigger> components,
			List<TriggerEntity> entities);

	public void fromEntitiesToComponents(List<TriggerEntity> entities,
			List<NonDelayTrigger> components);
}
