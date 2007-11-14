package org.seasar.chronos.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskProperties;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public final class TaskPropertyUtil {

	public static String getDescription(TaskProperties prop) {
		String result = prop.getDescription();
		return result;
	}

	public static boolean isEndTask(TaskProperties prop) {
		boolean end = false;
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			end = prop.isEndTask();
		} else {
			end = taskTrigger.isEndTask();
		}
		return end;
	}

	public static boolean isShutdownTask(TaskProperties prop) {
		boolean shutdown = prop.isShutdownTask();
		return shutdown;
	}

	public static boolean isStartTask(TaskProperties prop) {
		boolean start = false;
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			taskTrigger = getAnnotionTrigger(prop);
		}
		if (taskTrigger == null) {
			start = prop.isStartTask();
		} else {
			start = taskTrigger.isStartTask();
		}
		return start;
	}

	private static TaskTrigger getAnnotionTrigger(TaskProperties prop) {
		TaskTrigger taskTrigger = null;
		Class<?> clazz = prop.getTaskClass();
		Annotation[] annotaions = clazz.getAnnotations();
		for (Annotation annotaion : annotaions) {
			Class<?> annotaionClass = annotaion.annotationType();
			String annotationName = annotaionClass.getSimpleName();
			// サフィックスがTriggerなアノテーションを検索する
			if (annotationName.endsWith("Trigger")) {
				String triggerClassName = "org.seasar.chronos.core.trigger.C"
						+ annotationName;
				// 対象のTaskTriggerのクラスを取得する
				Class<?> triggerClass = ReflectionUtil
						.forNameNoException(triggerClassName);
				if (triggerClass != null) {
					taskTrigger = (TaskTrigger) ReflectionUtil
							.newInstance(triggerClass);
					BeanDesc beanDesc = BeanDescFactory
							.getBeanDesc(triggerClass);
					// アノテーションにある属性をすべてTaskTriggerの対応するプロパティに渡す
					Method[] mis = annotaionClass.getMethods();
					for (Method m : mis) {
						String methodName = m.getName();
						if (beanDesc.hasPropertyDesc(methodName)) {
							PropertyDesc pd = beanDesc
									.getPropertyDesc(methodName);
							Object value = ReflectionUtil.invoke(m, annotaion,
									new Object[] {});
							if (m.getReturnType().equals(pd.getPropertyType())) {
								pd.setValue(taskTrigger, value);
							} else {
								// StringからDateに変換する処理
								if (pd.getPropertyType().equals(Date.class)
										&& m.getReturnType().equals(
												String.class)) {
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyy/MM/dd HH:mm:ss");
									try {
										Date date = sdf.parse((String) value);
										pd.setValue(taskTrigger, date);
									} catch (ParseException e) {
										;
									}
								}
							}

						}
					}
					// 生成されたTaskTriggerをTaskPropertiesに渡す
					prop.setTrigger(taskTrigger);
					break;
				}
			}
		}
		return taskTrigger;
	}

	public static long getTaskId(TaskProperties prop) {
		long result = prop.getTaskId();
		return result;
	}

	public static String getTaskName(TaskProperties prop) {
		String taskName = prop.getTaskName();
		if (taskName == null) {
			Class<?> clazz = prop.getTaskClass();
			Task task = (Task) clazz.getAnnotation(Task.class);
			if (task != null && StringUtil.isEmpty(task.name())) {
				Component component = (Component) clazz
						.getAnnotation(Component.class);
				return component != null ? component.name() : taskName;
			}
			return task != null ? task.name() : taskName;
		}
		return taskName;
	}

	public static int getThreadPoolSize(TaskProperties prop) {
		int threadPoolSize = 1;
		TaskThreadPool taskThreadPool = prop.getThreadPool();
		if (taskThreadPool == null) {
			return prop.getThreadPoolSize();
		} else {
			taskThreadPool.getThreadPoolSize();
		}
		return threadPoolSize;
	}

	public static ThreadPoolType getThreadPoolType(TaskProperties prop) {
		ThreadPoolType threadPoolType = null;
		TaskThreadPool taskThreadPool = prop.getThreadPool();
		if (taskThreadPool == null) {
			return prop.getThreadPoolType();
		} else {
			taskThreadPool.getThreadPoolType();
		}
		return threadPoolType;
	}

	public static void setEndTask(TaskProperties prop, boolean endTask) {
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			prop.setEndTask(endTask);
		} else {
			taskTrigger.setEndTask(endTask);
		}
	}

	public static void setShutdownTask(TaskProperties prop, boolean shutdownTask) {
		prop.setShutdownTask(shutdownTask);
	}

	public static void setStartTask(TaskProperties prop, boolean startTask) {
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			prop.setStartTask(startTask);
		} else {
			taskTrigger.setStartTask(startTask);
		}
	}

	public static boolean isReSchedule(TaskProperties prop) {
		boolean reSchedule = false;
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			reSchedule = prop.isReSchedule();
		} else {
			reSchedule = taskTrigger.isReSchedule();
		}
		return reSchedule;
	}
}
