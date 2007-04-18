package org.seasar.chronos.core.task.strategy.impl;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.smartdeploy.task.SmartTask;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;

public class TaskExecuteStrategyImplTest extends S2TestCaseBase {

	private TaskExecuteStrategy taskExecuteStrategy;

	private Long id;

	public void testDeserialize() {

	}

	public void testSerialize() {
		ComponentDef taskComponentDef = this.getComponentDef(SmartTask.class);
		try {
			String className = taskComponentDef.getComponentClass().getName();
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundRuntimeException(e);
		}
		HotdeployUtil.start();

		taskExecuteStrategy.setTask(taskComponentDef.getComponent());
		taskExecuteStrategy.setTaskClass(taskComponentDef.getComponentClass());
		taskExecuteStrategy.prepare();
		taskExecuteStrategy.save();
		this.id = taskExecuteStrategy.getTaskId();
		HotdeployUtil.stop();
	}
}
