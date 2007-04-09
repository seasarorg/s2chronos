package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.task.TaskProperties;
import org.seasar.chronos.extension.store.entity.TaskEntity;

public interface TaskDxo {

	public TaskEntity toEntity(TaskProperties component);

	public TaskProperties toComponent(TaskEntity entity);

	public void fromComponentToEntity(TaskProperties component,
			TaskEntity entity);

	public void fromEntityFromComponent(TaskEntity entity,
			TaskProperties component);

	public List<TaskEntity> toEntities(List<TaskProperties> components);

	public List<TaskProperties> toComponents(List<TaskEntity> entity);

	public void fromComponentsToEntities(List<TaskProperties> components,
			List<TaskEntity> entities);

	public void fromEntitiesToComponents(List<TaskEntity> entities,
			List<TaskProperties> components);

}
