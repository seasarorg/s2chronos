package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.extension.store.entity.ScheduleEntity;
import org.seasar.extension.dxo.annotation.ConversionRule;

public interface ScheduleDxo {

	@ConversionRule("objectId : scheduleId")
	public void fromComponentsToEntities(List<TaskScheduleEntry> components,
			List<ScheduleEntity> entities);

	@ConversionRule("objectId : scheduleId")
	public void fromComponentToEntity(TaskScheduleEntry component,
			ScheduleEntity entity);

	@ConversionRule("scheduleId : objectId")
	public void fromEntitiesToComponents(List<ScheduleEntity> entities,
			List<TaskScheduleEntry> components);

	@ConversionRule("scheduleId : objectId")
	public void fromEntityToComponent(ScheduleEntity entity,
			TaskScheduleEntry component);

	@ConversionRule("scheduleId : objectId")
	public TaskScheduleEntry toComponent(ScheduleEntity entity);

	@ConversionRule("scheduleId : objectId")
	public List<TaskScheduleEntry> toComponents(List<ScheduleEntity> entity);

	@ConversionRule("objectId : scheduleId")
	public List<ScheduleEntity> toEntities(List<TaskScheduleEntry> components);

	@ConversionRule("objectId : scheduleId")
	public ScheduleEntity toEntity(TaskScheduleEntry component);
}
