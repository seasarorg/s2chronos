package org.seasar.chronos.extension.task;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TestTask implements Serializable {

	private static final long serialVersionUID = 1L;

	public String name = "aaaa";
	public Integer id = 10;

	public TestTask testTask = this;

	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}
}