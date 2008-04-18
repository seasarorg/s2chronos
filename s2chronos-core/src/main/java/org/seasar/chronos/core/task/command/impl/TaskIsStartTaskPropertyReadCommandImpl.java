package org.seasar.chronos.core.task.command.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.chronos.core.task.TaskAnnotationReader.TriggerAnnotationHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskIsStartTaskPropertyReadCommandImpl extends
		AbstractTaskPropertyCommand {

	private TaskAnnotationReader taskAnnotationReader;
	private TaskPropertyWriter taskPropertyWriter;

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		boolean start = false;
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (taskTrigger == null) {
			taskAnnotationReader.loadTask(this.getTaskPropertyReader(
					methodInvocation).getTaskClass());
			taskTrigger = taskAnnotationReader
					.getTriggerAnnotationClass(new TriggerAnnotationHandler() {
						public TaskTrigger process(Annotation annotaion,
								Class<?> triggerAnnotationClass) {
							if (triggerAnnotationClass != null) {
								TaskTrigger taskTrigger = (TaskTrigger) ReflectionUtil
										.newInstance(triggerAnnotationClass);
								build(taskTrigger, annotaion,
										triggerAnnotationClass);
								return taskTrigger;
							}
							return null;
						}

						private void build(TaskTrigger taskTrigger,
								Annotation annotaion,
								Class<?> triggerAnnotationClass) {
							BeanDesc beanDesc = BeanDescFactory
									.getBeanDesc(triggerAnnotationClass);
							Method[] mis = annotaion.annotationType()
									.getMethods();
							for (Method m : mis) {
								String methodName = m.getName();
								if (beanDesc.hasPropertyDesc(methodName)) {
									PropertyDesc pd = beanDesc
											.getPropertyDesc(methodName);
									Object value = ReflectionUtil.invoke(m,
											annotaion, new Object[] {});
									if (m.getReturnType().equals(
											pd.getPropertyType())) {
										pd.setValue(taskTrigger, value);
									} else {
										// StringからDateに変換する処理
										if (pd.getPropertyType().equals(
												Date.class)
												&& m.getReturnType().equals(
														String.class)
												&& !StringUtil
														.isEmpty((String) value)) {
											SimpleDateFormat sdf = new SimpleDateFormat(
													"yyyy/MM/dd HH:mm:ss");

											Date date = null;
											try {
												date = sdf
														.parse((String) value);
											} catch (ParseException e) {
												;
											}
											pd.setValue(taskTrigger, date);
										}
									}
								}
							}

						}
					});
			taskPropertyWriter.loadTask(this.getTaskPropertyReader(
					methodInvocation).getTask(), this.getTaskPropertyReader(
					methodInvocation).getBeanDesc());
			taskPropertyWriter.setTrigger(taskTrigger);
		}
		if (taskTrigger == null) {
			start = (Boolean) methodInvocation.proceed();
		} else {
			start = taskTrigger.isStartTask();
		}
		return start;
	}

	public void setTaskAnnotationReader(
			TaskAnnotationReader taskAnnotationReader) {
		this.taskAnnotationReader = taskAnnotationReader;
	}

	public void setTaskPropertyWriter(TaskPropertyWriter taskPropertyWriter) {
		this.taskPropertyWriter = taskPropertyWriter;
	}
}
