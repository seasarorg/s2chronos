package org.seasar.chronos.task.strategy.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.seasar.chronos.S2TestCaseBase;
import org.seasar.chronos.smartdeploy.task.SmartTask;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;

public class TaskExecuteStrategyImplTest extends S2TestCaseBase {

	private static final String SERIALIZABLE_OBJECT_DAT_FILENAME = "serializableObject_TaskExecuteStrategy.dat";

	private TaskExecuteStrategy taskExecuteStrategy = new TaskExecuteStrategyImpl();

	public void testSerialize() {
		ComponentDef taskComponentDef = this.getComponentDef(SmartTask.class);
		try {
			String className = taskComponentDef.getComponentClass().getName();
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundRuntimeException(e);
		}
		HotdeployUtil.start();
		try {
			OutputStream outputStream = new FileOutputStream(
					SERIALIZABLE_OBJECT_DAT_FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);

			taskExecuteStrategy.setTask(taskComponentDef.getComponent());
			taskExecuteStrategy.setTaskClass(taskComponentDef
					.getComponentClass());
			oos.writeObject(taskExecuteStrategy);
			oos.flush();
			oos.close();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HotdeployUtil.stop();
	}

	public void testDeserialize() {
		ComponentDef taskComponentDef = this.getComponentDef(SmartTask.class);
		try {
			String className = taskComponentDef.getComponentClass().getName();
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundRuntimeException(e);
		}
		HotdeployUtil.start();
		try {
			InputStream inputStream = new FileInputStream(
					SERIALIZABLE_OBJECT_DAT_FILENAME);
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			TaskExecuteStrategy _taskExecuteStrategy = (TaskExecuteStrategy) ois
					.readObject();
			assertEquals(taskComponentDef.getComponent(), _taskExecuteStrategy
					.getTask());
			ois.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		HotdeployUtil.stop();
	}
}
