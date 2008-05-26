package org.seasar.chronos.extension.persitense;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TestTask implements Serializable {
	public String name = "aaaa";
	public Integer id = 10;

	public TestTask testTask = this;

	public String toString() {
		return new ToStringBuilder(this).toString();
	}
}