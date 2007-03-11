package org.seasar.chronos.job.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.annotation.job.method.Clone;
import org.seasar.chronos.annotation.job.method.Group;
import org.seasar.chronos.annotation.job.method.Join;
import org.seasar.chronos.annotation.job.method.Next;
import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.framework.beans.BeanDesc;

public class JobMethodMetaData {

	private Method method;

	public JobMethodMetaData(BeanDesc beanDesc, String methodName) {
		this(beanDesc.getMethod(methodName));
	}

	public JobMethodMetaData(Method method) {
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
		Next next = method.getAnnotation(Next.class);
		if (next != null) {
			return next.value();
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
