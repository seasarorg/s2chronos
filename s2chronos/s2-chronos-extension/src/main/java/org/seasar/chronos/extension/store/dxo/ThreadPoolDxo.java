package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.extension.store.entity.ThreadPoolEntity;

public interface ThreadPoolDxo {

	public ThreadPoolEntity toEntity(TaskThreadPool component);

	public TaskThreadPool toComponent(ThreadPoolEntity entity);

	public void fromComponentToEntity(TaskThreadPool component,
			ThreadPoolEntity entity);

	public void fromEntityFromComponent(ThreadPoolEntity entity,
			TaskThreadPool component);

	public List<ThreadPoolEntity> toEntities(List<TaskThreadPool> components);

	public List<TaskThreadPool> toComponents(List<ThreadPoolEntity> entity);

	public void fromComponentsToEntities(List<TaskThreadPool> components,
			List<ThreadPoolEntity> entities);

	public void fromEntitiesToComponents(List<ThreadPoolEntity> entities,
			List<TaskThreadPool> components);

}
