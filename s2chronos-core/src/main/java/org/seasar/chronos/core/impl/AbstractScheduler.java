package org.seasar.chronos.core.impl;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.autodetector.TaskClassAutoDetector;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.core.util.TaskPropertyUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.SmartDeployUtil;
import org.seasar.framework.container.util.Traversal;
import org.seasar.framework.log.Logger;
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
	protected boolean registChildTaskComponent(S2Container s2Container) {
		return registChildTaskComponent(s2Container, null);
	}

	protected boolean registChildTaskComponentByTarget(S2Container s2Container,
			final Class<?> targetTaskComponentClass) {
		return registChildTaskComponent(s2Container, targetTaskComponentClass);
	}

	private boolean registChildTaskComponent(S2Container s2Container,
			final Class<?> targetTaskComponentClass) {
		Object result = (Object) Traversal.forEachComponent(s2Container,
				new Traversal.ComponentDefHandler() {
					public Object processComponent(ComponentDef componentDef) {
						Class<?> clazz = componentDef.getComponentClass();
						if (clazz != null) {
							Task task = (Task) clazz.getAnnotation(Task.class);
							if (task != null) {
								if (targetTaskComponentClass == null) {
									scheduleTask(componentDef);
								} else if (targetTaskComponentClass
										.equals(componentDef
												.getComponentClass())) {
									return scheduleTask(componentDef, true);
								}
							}
						}
						return null;
					}

				});
		return result != null;
	}

	protected abstract void registTaskFromS2Container();

	/**
	 * SMART Deploy上のコンポーネントを検索し，スケジューラに登録します．
	 * 
	 * @param s2Container
	 *            S2コンテナ
	 */
	protected boolean registTaskFromS2ContainerOnSmartDeploy(
			final S2Container s2Container) {
		return registTaskFromS2ContainerOnSmartDeploy(s2Container, null);
	}

	protected boolean registTaskFromS2ContainerOnSmartDeployByTarget(
			final S2Container s2Container,
			final Class<?> targetTaskComponentClass) {
		return registTaskFromS2ContainerOnSmartDeploy(s2Container,
				targetTaskComponentClass);
	}

	private boolean detectResult = false;

	private boolean registTaskFromS2ContainerOnSmartDeploy(
			final S2Container s2Container,
			final Class<?> targetTaskComponentClass) {
		detectResult = false;
		if (SmartDeployUtil.isSmartdeployMode(s2Container)) {
			this.taskClassAutoDetector
					.detect(new ClassTraversal.ClassHandler() {
						public void processClass(String packageName,
								String shortClassName) {
							String name = ClassUtil.concatName(packageName,
									shortClassName);
							Class<?> clazz = ReflectionUtil
									.forNameNoException(name);
							if (targetTaskComponentClass == null) {
								if (null != scheduleTask(s2Container, clazz)) {
									detectResult = true;
								}
							} else if (targetTaskComponentClass.equals(clazz)) {
								if (null != scheduleTask(s2Container, clazz,
										true)) {
									detectResult = true;
								}
							}
						}
					});
		}
		return detectResult;
	}

	protected TaskScheduleEntry scheduleTask(ComponentDef componentDef) {
		return scheduleTask(componentDef, false);
	}

	protected TaskScheduleEntry scheduleTask(ComponentDef taskComponentDef,
			boolean force) {
		Class<?> clazz = taskComponentDef.getComponentClass();
		Task task = clazz.getAnnotation(Task.class);
		if (!task.autoSchedule() && !force) {
			return null;
		}
		TaskScheduleEntry taskScheduleEntry = (TaskScheduleEntry) this.s2container
				.getComponent(TaskScheduleEntry.class);
		taskScheduleEntry.setComponentDef(taskComponentDef);

		final TaskExecutorService tes = (TaskExecutorService) this.s2container
				.getComponent(TaskExecutorService.class);
		taskScheduleEntry.setTaskExecutorService(tes);

		tes.setComponentDef(taskComponentDef);
		tes.setGetterSignal(this);
		tes.setScheduler(this);
		// taskScheduleEntry.setComponentDef(taskComponentDef);
		return taskScheduleEntry;
	}

	protected TaskScheduleEntry scheduleTask(final S2Container s2Container,
			final Class<?> taskClass, boolean force) {
		ComponentDef componentDef = s2Container.getComponentDef(taskClass);
		return scheduleTask(componentDef, force);
	}

	protected TaskScheduleEntry scheduleTask(final S2Container s2Container,
			final Class<?> taskClass) {
		ComponentDef componentDef = s2Container.getComponentDef(taskClass);
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