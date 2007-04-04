package org.seasar.chronos.store.dxo;

import java.util.List;

import org.seasar.chronos.store.entity.CronTriggerEntity;
import org.seasar.chronos.trigger.CronTrigger;

public interface CronTriggerDxo {

	public CronTriggerEntity toEntity(CronTrigger component);

	public CronTrigger toComponent(CronTriggerEntity entity);

	public void fromComponentToEntity(CronTrigger component,
			CronTriggerEntity entity);

	public void fromEntityFromComponent(CronTriggerEntity entity,
			CronTrigger component);

	public List<CronTriggerEntity> toEntities(List<CronTrigger> components);

	public List<CronTrigger> toComponents(List<CronTriggerEntity> entity);

	public void fromComponentToEntities(List<CronTrigger> components,
			List<CronTriggerEntity> entities);

	public void fromEntitiesToComponents(List<CronTriggerEntity> entities,
			List<CronTrigger> components);

}
