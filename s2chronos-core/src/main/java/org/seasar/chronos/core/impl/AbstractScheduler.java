package org.seasar.chronos.core.impl;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.autodetector.TaskClassAutoDetector;
import org.seasar.chronos.core.logger.Logger;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.core.util.TaskPropertyUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.container.util.SmartDeployUtil;
import org.seasar.framework.container.util.Traversal;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.util.ClassTraversal;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public abstract class AbstractScheduler implements Scheduler {

	protected static Logger log = Logger.getLogger(AbstractScheduler.class);

	protected S2Container s2container;

	protected TaskClassAutoDetector taskClassAutoDetector;

	public AbstractScheduler() {
		super();
	}

	/**
	 * タスク名からComponentDefを返します．
	 * 
	 * @param taskName
	 *            タスク名
	 * @return ComponentDef
	 */
	protected ComponentDef findTaskComponentDefByTaskName(final String taskName) {
		TaskScheduleEntryManager tcsm = TaskScheduleEntryManager.getInstance();
		Object componentDef = tcsm
				.forEach(new TaskScheduleEntryManager.TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							TaskScheduleEntry taskScheduleEntry) {
						TaskExecutorService tes = taskScheduleEntry
								.getTaskExecutorService();
						String _taskName = TaskPropertyUtil.getTaskName(tes);
						log.debug("[[[" + taskName + ":" + _taskName + "]]]");
						if (taskName.equals(_taskName)) {
							return taskScheduleEntry.getComponentDef();
						}
						return null;
					}
				});
		if (componentDef != null) {
			return (ComponentDef) componentDef;
		}
		return null;
	}

	/**
	 * S2コンテナ(非SMART Deploy)上のコンポーネントを検索し，スケジューラに登録します．
	 * 
	 * @param s2Container
	 *            S2コンテナ
	 */
	protected void registChildTaskComponent(S2Container s2Container) {
		Traversal.forEachComponent(s2Container,
				new Traversal.ComponentDefHandler() {
					public Object processComponent(ComponentDef componentDef) {
						Class<?> clazz = componentDef.getComponentClass();
						Task task = (Task) clazz.getAnnotation(Task.class);
						if (task != null) {
							scheduleTask(componentDef);
						}
						return null;
					}

				});
	}

	protected abstract void registTaskFromS2Container();

	/**
	 * SMART Deploy上のコンポーネントを検索し，スケジューラに登録します．
	 * 
	 * @param s2Container
	 *            S2コンテナ
	 */
	protected void registTaskFromS2ContainerOnSmartDeploy(
			final S2Container s2Container) {
		if (SmartDeployUtil.isSmartdeployMode(s2Container)) {
			this.taskClassAutoDetector
					.detect(new ClassTraversal.ClassHandler() {
						public void processClass(String packageName,
								String shortClassName) {
							String name = ClassUtil.concatName(packageName,
									shortClassName);
							Class clazz = ReflectionUtil
									.forNameNoException(name);
							scheduleTask(s2Container, clazz);
						}
					});
		}
	}

	protected TaskScheduleEntry scheduleTask(ComponentDef componentDef) {
		return scheduleTask(componentDef, false);
	}

	protected TaskScheduleEntry scheduleTask(ComponentDef componentDef,
			boolean force) {
		Class<?> clazz = componentDef.getComponentClass();
		Task task = clazz.getAnnotation(Task.class);
		if (!task.autoSchedule() && !force) {
			return null;
		}
		TaskScheduleEntry taskScheduleEntry = (TaskScheduleEntry) this.s2container
				.getComponent(TaskScheduleEntry.class);
		taskScheduleEntry.setComponentDef(componentDef);

		final TaskExecutorService tes = (TaskExecutorService) this.s2container
				.getComponent(TaskExecutorService.class);
		taskScheduleEntry.setTaskExecutorService(tes);

		try {
			String className = taskScheduleEntry.getComponentDef()
					.getComponentClass().getName();
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundRuntimeException(e);
		}
		HotdeployUtil.start();
		// ここでタスクに対してDIが実行されます
		tes.setTask(componentDef.getComponent());
		tes.setTaskClass(componentDef.getComponentClass());
		tes.setGetterSignal(this);
		tes.setScheduler(this);
		tes.prepare();
		taskScheduleEntry.setTask(tes.getTask());
		return taskScheduleEntry;
	}

	protected TaskScheduleEntry scheduleTask(final S2Container s2Container,
			Class componentClass) {
		ComponentDef componentDef = s2Container.getComponentDef(componentClass);
		return scheduleTask(componentDef);
	}

	public void setS2Container(S2Container s2container) {
		this.s2container = s2container;
	}

	public void setTaskClassAutoDetector(
			TaskClassAutoDetector taskClassAutoDetector) {
		this.taskClassAutoDetector = taskClassAutoDetector;
	}
}