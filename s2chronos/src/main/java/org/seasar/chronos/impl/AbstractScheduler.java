package org.seasar.chronos.impl;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.autodetector.TaskClassAutoDetector;
import org.seasar.chronos.logger.Logger;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.SmartDeployUtil;
import org.seasar.framework.container.util.Traversal;
import org.seasar.framework.util.ClassTraversal;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public abstract class AbstractScheduler implements Scheduler {

	protected static Logger log = Logger.getLogger(AbstractScheduler.class);

	protected S2Container s2container;

	public void setS2Container(S2Container s2container) {
		this.s2container = s2container;
	}

	protected TaskClassAutoDetector taskClassAutoDetector;

	public void setTaskClassAutoDetector(
			TaskClassAutoDetector taskClassAutoDetector) {
		this.taskClassAutoDetector = taskClassAutoDetector;
	}

	public AbstractScheduler() {
		super();
	}

	protected TaskContena scheduleTask(final S2Container s2Container,
			Class componentClass) {
		ComponentDef componentDef = s2Container.getComponentDef(componentClass);
		return scheduleTask(componentDef);
	}

	protected TaskContena scheduleTask(ComponentDef componentDef) {
		Class<?> clazz = componentDef.getComponentClass();
		Task task = clazz.getAnnotation(Task.class);
		if (!task.autoSchedule()) {
			return null;
		}
		TaskContena tc = new TaskContena(componentDef);
		final TaskExecutorService tes = (TaskExecutorService) this.s2container
				.getComponent(TaskExecutorService.class);
		tc.setTaskExecutorService(tes);
		tes.setTaskComponentDef(tc.getComponentDef());
		tes.setGetterSignal(this);
		tes.setScheduler(this);
		// ここでタスクに対してDIが実行されます
		tes.prepare();
		tc.setTask(tes.getTask());
		return tc;
	}

	/**
	 * タスク名からComponentDefを返します．
	 * 
	 * @param taskName
	 *            タスク名
	 * @return ComponentDef
	 */
	protected ComponentDef findTaskComponentDefByTaskName(final String taskName) {
		TaskContenaStateManager tcsm = TaskContenaStateManager.getInstance();
		Object componentDef = tcsm
				.forEach(new TaskContenaStateManager.TaskContenaHanlder() {
					public Object processTaskContena(TaskContena taskContena) {
						ComponentDef componentDef = taskContena
								.getComponentDef();
						Class<?> clazz = componentDef.getComponentClass();
						Task task = (Task) clazz.getAnnotation(Task.class);
						if (task == null) {
							return null;
						}
						String _taskName = task.name();
						log.debug("[[[" + taskName + ":" + _taskName + "]]]");
						if (taskName.equals(_taskName)) {
							return componentDef;
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

	protected abstract void registTaskFromS2Container();
}