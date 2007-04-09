package org.seasar.chronos.store.dxo;

import java.util.List;

import org.seasar.chronos.store.entity.TriggerEntity;
import org.seasar.chronos.trigger.DelayTrigger;

public interface DelayTriggerDxo {

	public TriggerEntity toEntity(DelayTrigger component);

	public DelayTrigger toComponent(TriggerEntity entity);

	public void fromComponentToEntity(DelayTrigger trigger, TriggerEntity entity);

	public void fromEntityFromComponent(TriggerEntity entity,
			DelayTrigger component);

	public List<TriggerEntity> toEntities(List<DelayTrigger> components);

	public List<DelayTrigger> toComponents(List<TriggerEntity> entity);

	public void fromComponentsToEntities(List<DelayTrigger> components,
			List<TriggerEntity> entities);

	public void fromEntitiesToComponents(List<TriggerEntity> entities,
			List<DelayTrigger> components);
}
