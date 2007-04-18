package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.task.TaskProperties;
import org.seasar.chronos.extension.store.entity.TaskEntity;
import org.seasar.extension.dxo.annotation.ConversionRule;

public interface TaskDxo {

	public void fromComponentsToEntities(List<TaskProperties> components,
			List<TaskEntity> entities);

	public void fromComponentToEntity(TaskProperties component,
			TaskEntity entity);

	public void fromEntitiesToComponents(List<TaskEntity> entities,
			List<TaskProperties> components);

	@ConversionRule("taskId : taskCode")
	public void fromEntityToComponent(TaskEntity entity,
			TaskProperties component);

	@ConversionRule("taskId : taskCode")
	public TaskProperties toComponent(TaskEntity entity);

	public List<TaskProperties> toComponents(List<TaskEntity> entity);

	public List<TaskEntity> toEntities(List<TaskProperties> components);

	@ConversionRule("taskCode : taskId")
	public TaskEntity toEntity(TaskProperties component);

}
