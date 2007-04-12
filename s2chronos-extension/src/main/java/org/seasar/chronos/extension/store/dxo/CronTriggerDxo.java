package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.trigger.CronTrigger;
import org.seasar.chronos.extension.store.entity.TriggerEntity;

public interface CronTriggerDxo {

	public TriggerEntity toEntity(CronTrigger component);

	public CronTrigger toComponent(TriggerEntity entity);

	public void fromComponentToEntity(CronTrigger trigger, TriggerEntity entity);

	public void fromEntityFromComponent(TriggerEntity entity,
			CronTrigger component);

	public List<TriggerEntity> toEntities(List<CronTrigger> components);

	public List<CronTrigger> toComponents(List<TriggerEntity> entity);

	public void fromComponentsToEntities(List<CronTrigger> components,
			List<TriggerEntity> entities);

	public void fromEntitiesToComponents(List<TriggerEntity> entities,
			List<CronTrigger> components);

}
