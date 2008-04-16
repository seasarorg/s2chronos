package org.seasar.chronos.core.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskAnnotationReader.TriggerAnnotationHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TriggerPropertyReadInterceptor implements MethodInterceptor {

	private TaskAnnotationReader taskAnnotationReader;

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		TaskPropertyReader tpr = (TaskPropertyReader) methodInvocation
				.getThis();
		if (methodInvocation.getMethod().getName().equals("isEndTask")) {
			boolean end = false;
			TaskTrigger taskTrigger = tpr.getTrigger(null);
			if (taskTrigger == null) {
				end = (Boolean) methodInvocation.proceed();
			} else {
				end = taskTrigger.isEndTask();
			}
			return end;
		} else if (methodInvocation.getMethod().getName().equals(
				"getThreadPoolSize")) {
			int threadPoolSize = 1;
			TaskThreadPool taskThreadPool = tpr.getThreadPool(null);
			if (taskThreadPool == null) {
				return methodInvocation.proceed();
			} else {
				threadPoolSize = taskThreadPool.getThreadPoolSize();
			}
			return threadPoolSize;
		} else if (methodInvocation.getMethod().getName().equals(
				"getThreadPoolType")) {
			ThreadPoolType threadPoolType = null;
			TaskThreadPool taskThreadPool = tpr.getThreadPool(null);
			if (taskThreadPool == null) {
				return methodInvocation.proceed();
			} else {
				threadPoolType = taskThreadPool.getThreadPoolType();
			}
			return threadPoolType;
		} else if (methodInvocation.getMethod().getName()
				.equals("isReSchedule")) {
			boolean reSchedule = (Boolean) methodInvocation.proceed();
			TaskTrigger taskTrigger = tpr.getTrigger(null);
			if (taskTrigger != null) {
				reSchedule = reSchedule || taskTrigger.isReSchedule();
			}
			return reSchedule;
		} else if (methodInvocation.getMethod().getName().equals("isStartTask")) {
			boolean start = false;
			TaskTrigger taskTrigger = tpr.getTrigger(null);
			if (taskTrigger == null) {
				taskAnnotationReader.loadTask(tpr.getTaskClass());
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
													&& m
															.getReturnType()
															.equals(
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
				BeanDesc beanDesc = tpr.getBeanDesc();
				if (beanDesc.hasPropertyDesc("trigger")) {
					PropertyDesc pd = beanDesc.getPropertyDesc("trigger");
					pd.setValue(tpr.getTask(), taskTrigger);
				}
			}
			if (taskTrigger == null) {
				start = (Boolean) methodInvocation.proceed();
			} else {
				start = taskTrigger.isStartTask();
			}
			return start;
		}
		return methodInvocation.proceed();
	}

	public void setTaskAnnotationReader(
			TaskAnnotationReader taskAnnotationReader) {
		this.taskAnnotationReader = taskAnnotationReader;
	}
}
