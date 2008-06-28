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
package org.seasar.chronos.core.task.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.core.annotation.task.method.CloneTask;
import org.seasar.chronos.core.annotation.task.method.JoinTask;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.task.method.TaskGroup;
import org.seasar.chronos.core.annotation.type.JoinType;
import org.seasar.framework.beans.BeanDesc;

public class TaskMethodMetaData {

	private static final long serialVersionUID = -9051823745597260320L;

	private final Method method;

	public TaskMethodMetaData(BeanDesc beanDesc, String methodName) {
		this(beanDesc.getMethod(methodName));
	}

	public TaskMethodMetaData(Method method) {
		this.method = method;
	}

	public Class<?> getReturnType() {
		return this.method.getReturnType();
	}

	public JoinType getJoinType() {
		JoinTask joinTask = method.getAnnotation(JoinTask.class);
		if (joinTask != null) {
			return joinTask.value();
		}
		return JoinType.Wait;
	}

	public String getNextTask() {
		NextTask nextTask = method.getAnnotation(NextTask.class);
		if (nextTask != null) {
			return nextTask.value();
		}
		return null;
	}

	public long getCloneSize() {
		CloneTask cloneTask = method.getAnnotation(CloneTask.class);
		if (cloneTask != null) {
			return cloneTask.value();
		}
		return 1;
	}

	public String getGroupName() {
		TaskGroup taskGroup = method.getAnnotation(TaskGroup.class);
		if (taskGroup != null) {
			return taskGroup.value();
		}
		return null;
	}
}
