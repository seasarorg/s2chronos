/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.task.handler.impl.property.read;

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
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskIsStartTaskPropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

	@SuppressWarnings("unused")
	private static Logger log = Logger
			.getLogger(TaskIsStartTaskPropertyReadHandlerImpl.class);

	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
	private TaskAnnotationReader taskAnnotationReader;
	private TaskPropertyWriter taskPropertyWriter;

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		boolean start = (Boolean) methodInvocation.proceed();
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (taskTrigger == null) {
			taskAnnotationReader.setup(this.getTaskPropertyReader(
					methodInvocation).getTaskClass());
			taskTrigger = taskAnnotationReader
					.getTriggerAnnotationClass(new TriggerAnnotationHandler() {
						public TaskTrigger process(Annotation annotaion,
								Class<?> triggerAnnotationClass) {
							if (triggerAnnotationClass != null) {
								try {
									TaskTrigger taskTrigger = (TaskTrigger) ReflectionUtil
											.newInstance(triggerAnnotationClass);
									build(taskTrigger, annotaion,
											triggerAnnotationClass);
									return taskTrigger;
								} catch (Exception e) {
									e.printStackTrace();
								}
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
									convertProperty(taskTrigger, m, pd, value);
								}
							}

						}

						private void convertProperty(TaskTrigger taskTrigger,
								Method m, PropertyDesc pd, Object value) {
							if (m.getReturnType().equals(pd.getPropertyType())) {
								pd.setValue(taskTrigger, value);
							} else {
								// StringからDateに変換する処理
								if (pd.getPropertyType().equals(Date.class)
										&& m.getReturnType().equals(
												String.class)
										&& !StringUtil.isEmpty((String) value)) {
									SimpleDateFormat sdf = new SimpleDateFormat(
											YYYY_MM_DD_HH_MM_SS);

									Date date = null;
									try {
										date = sdf.parse((String) value);
									} catch (ParseException e) {
										;
									}
									pd.setValue(taskTrigger, date);
								}
							}
						}
					});
			taskPropertyWriter.setup(this.getTaskPropertyReader(
					methodInvocation).getTask(), this.getTaskPropertyReader(
					methodInvocation).getBeanDesc());
			taskPropertyWriter.setTrigger(taskTrigger);
		}
		if (taskTrigger != null) {
			start = start || taskTrigger.isStartTask();
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
