package org.seasar.chronos.task.strategy.impl;

import org.seasar.chronos.S2TestCaseBase;
import org.seasar.chronos.smartdeploy.task.SmartTask;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;

public class TaskExecuteStrategyImplTest extends S2TestCaseBase {

	private TaskExecuteStrategy taskExecuteStrategy;

	private int id;

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
