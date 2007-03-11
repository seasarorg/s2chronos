package org.seasar.chronos.job.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncResult {

	private Future<JobResult> futureJobResult = null;

	private JobResult jobResult = null;

	private Object state = null;

	public AsyncResult(Future<JobResult> futureJobResult, Object state) {
		this.futureJobResult = futureJobResult;
		this.state = state;
	}

	public AsyncResult(JobResult jobResult, Object state) {
		this.jobResult = jobResult;
		this.state = state;
	}

	public Future<JobResult> getFutureJobResult() {
		return futureJobResult;
	}

	public JobResult getJobResult() throws InterruptedException,
			ExecutionException {
		if (futureJobResult == null) {
			return futureJobResult.get();
		}
		return jobResult;
	}

	public Object getState() {
		return state;
	}

}
