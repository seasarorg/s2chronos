package org.seasar.chronos.job.impl;

import org.seasar.chronos.delegate.AsyncResult;
import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.chronos.job.TaskExecuteHandler;
import org.seasar.chronos.job.Transition;
import org.seasar.framework.log.Logger;

public class JobGroupMethodExecuteHandlerImpl extends
		AbstractTaskExecuteHandler {

	private static final String METHOD_PREFIX_NAME_START = "start";

	private static final String METHOD_PREFIX_NAME_END = "end";

	private static Logger log = Logger
			.getLogger(JobGroupMethodExecuteHandlerImpl.class);

	private TaskExecuteHandler jobMethodExecuteHandler;

	@Override
	public void setMethodGroupMap(MethodGroupMap methodGroupMap) {
		super.setMethodGroupMap(methodGroupMap);
		jobMethodExecuteHandler.setMethodGroupMap(methodGroupMap);
	}

	@Override
	public void setMethodInvoker(MethodInvoker methodInvoker) {
		super.setMethodInvoker(methodInvoker);
		jobMethodExecuteHandler.setMethodInvoker(methodInvoker);
	}

	@Override
	public Transition handleRequest(String startTaskName) throws Throwable {
		String firstChar = startTaskName.substring(0, 1);
		String afterString = startTaskName.substring(1);

		String groupName = firstChar.toUpperCase() + afterString;

		String nextTask = invokeStartJobGroupMethod(groupName);

		if (nextTask == null) {
			log.debug("ジョブグループが開始しませんでした");
			return new Transition(true, nextTask);
		} else if (this.methodGroupMap.existGroup(nextTask)) {
			log.debug("startGroupの次はメソッドを指定してください");
			throw new InvalidNextJobMethodException(
					"startGroupの次はメソッドを指定してください");
		}

		jobMethodExecuteHandler.handleRequest(nextTask);

		nextTask = invokeEndJobGroupMethod(groupName);

		if (nextTask == null) {
			return new Transition(true, nextTask);
		} else if (this.methodGroupMap.existGroup(nextTask)) {
			Transition transition = handleRequest(nextTask);
			return transition;
		}
		return new Transition(false, nextTask);
	}

	private String invokeEndJobGroupMethod(String groupName) throws Throwable {
		String methodName = METHOD_PREFIX_NAME_END + groupName;
		return invokeGroupMethod(methodName);
	}

	private String invokeStartJobGroupMethod(String groupName) throws Throwable {
		String methodName = METHOD_PREFIX_NAME_START + groupName;
		return invokeGroupMethod(methodName);
	}

	private String invokeGroupMethod(String methodName) throws Throwable {
		MethodInvoker mi = this.getMethodInvoker();
		AsyncResult ar = mi.beginInvoke(methodName);
		mi.endInvoke(ar);
		JobMethodMetaData md = new JobMethodMetaData(mi.getMethod(methodName));
		return md.getNextTask();
	}

	public void setJobMethodExecuteHandler(
			TaskExecuteHandler jobMethodExecuteHandler) {
		this.jobMethodExecuteHandler = jobMethodExecuteHandler;
	}

}
