package org.seasar.chronos.extension.store.dxo;

import java.util.List;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.extension.store.entity.ThreadPoolEntity;
import org.seasar.extension.dxo.annotation.ConversionRule;

public interface ThreadPoolDxo {

	@ConversionRule("objectId : threadPoolId")
	public void fromComponentsToEntities(List<TaskThreadPool> components,
			List<ThreadPoolEntity> entities);

	@ConversionRule("objectId : threadPoolId")
	public void fromComponentToEntity(TaskThreadPool component,
			ThreadPoolEntity entity);

	@ConversionRule("threadPoolId : objectId")
	public void fromEntitiesToComponents(List<ThreadPoolEntity> entities,
			List<TaskThreadPool> components);

	@ConversionRule("threadPoolId : objectId")
	public void fromEntityToComponent(ThreadPoolEntity entity,
			TaskThreadPool component);

	@ConversionRule("threadPoolId : objectId")
	public TaskThreadPool toComponent(ThreadPoolEntity entity);

	@ConversionRule("threadPoolId : objectId")
	public List<TaskThreadPool> toComponents(List<ThreadPoolEntity> entity);

	@ConversionRule("objectId : threadPoolId")
	public List<ThreadPoolEntity> toEntities(List<TaskThreadPool> components);

	@ConversionRule("objectId : threadPoolId")
	public ThreadPoolEntity toEntity(TaskThreadPool component);

}
