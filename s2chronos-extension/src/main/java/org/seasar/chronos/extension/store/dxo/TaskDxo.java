package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.task.TaskProperties;
import org.seasar.chronos.extension.store.entity.TaskEntity;
import org.seasar.extension.dxo.annotation.ConversionRule;

public interface TaskDxo {

	@ConversionRule("objectId : taskId")
	public void fromComponentsToEntities(List<TaskProperties> components,
			List<TaskEntity> entities);

	@ConversionRule("objectId : taskId")
	public void fromComponentToEntity(TaskProperties component,
			TaskEntity entity);

	@ConversionRule("taskId : objectId")
	public void fromEntitiesToComponents(List<TaskEntity> entities,
			List<TaskProperties> components);

	@ConversionRule("taskId : objectId")
	public void fromEntityToComponent(TaskEntity entity,
			TaskProperties component);

	@ConversionRule("taskId : objectId")
	public TaskProperties toComponent(TaskEntity entity);

	@ConversionRule("taskId : objectId")
	public List<TaskProperties> toComponents(List<TaskEntity> entity);

	@ConversionRule("objectId : taskId")
	public List<TaskEntity> toEntities(List<TaskProperties> components);

	@ConversionRule("objectId : taskId")
	public TaskEntity toEntity(TaskProperties component);

}
