package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.trigger.DelayTrigger;
import org.seasar.chronos.extension.store.entity.TriggerEntity;

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
