package org.seasar.chronos.task.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.annotation.job.method.Clone;
import org.seasar.chronos.annotation.job.method.Group;
import org.seasar.chronos.annotation.job.method.Join;
import org.seasar.chronos.annotation.job.method.NextTask;
import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.framework.beans.BeanDesc;

public class TaskMethodMetaData {

	private Method method;

	public TaskMethodMetaData(BeanDesc beanDesc, String methodName) {
		this(beanDesc.getMethod(methodName));
	}

	public TaskMethodMetaData(Method method) {
		this.method = method;
	}

	public JoinType getJoinType() {
		Join join = method.getAnnotation(Join.class);
		if (join != null) {
			return join.value();
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
		Clone clone = method.getAnnotation(Clone.class);
		if (clone != null) {
			return clone.value();
		}
		return 1;
	}

	public String getGroupName() {
		Group group = method.getAnnotation(Group.class);
		if (group != null) {
			return group.value();
		}
		return null;
	}
}
