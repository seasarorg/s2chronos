package org.seasar.chronos.extension.task;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.seasar.chronos.extension.persitense.aop.Serialize;

@Serialize
public class TestTask implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "aaaa";
	private Integer id = 10;
	private TestTask testTask = this;

	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TestTask getTestTask() {
		return testTask;
	}

	public void setTestTask(TestTask testTask) {
		this.testTask = testTask;
	}
}