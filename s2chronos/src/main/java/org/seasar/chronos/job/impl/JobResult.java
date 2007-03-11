package org.seasar.chronos.job.impl;

import java.util.List;

public class JobResult {

	private List<Object> resultList;

	private boolean wait;

	private String next;

	JobResult(List<Object> resultList, String next, boolean wait) {
		this.resultList = resultList;
		this.next = next;
		this.wait = wait;
	}

	public String getNext() {
		return next;
	}

	public boolean isWait() {
		return wait;
	}

	public Object getResult() {
		return resultList.get(0);
	}

	public List<Object> getResultList() {
		return resultList;
	}
}
