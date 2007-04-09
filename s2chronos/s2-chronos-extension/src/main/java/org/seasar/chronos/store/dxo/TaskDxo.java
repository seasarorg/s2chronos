package org.seasar.chronos.store.dxo;

import java.util.List;

import org.seasar.chronos.store.entity.TaskEntity;
import org.seasar.chronos.task.TaskProperties;

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
