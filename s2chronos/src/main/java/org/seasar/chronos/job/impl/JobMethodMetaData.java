package org.seasar.chronos.job.impl;

import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.framework.beans.BeanDesc;

public class JobMethodMetaData {

	private BeanDesc beanDesc;

	public JobMethodMetaData(BeanDesc beanDesc) {
		this.beanDesc = beanDesc;
	}

	public JoinType getJoinType() {
		return JoinType.Wait;
	}

	public String getNext() {
		return null;
	}

	public long getCloneSize() {
		return 1;
	}
}
