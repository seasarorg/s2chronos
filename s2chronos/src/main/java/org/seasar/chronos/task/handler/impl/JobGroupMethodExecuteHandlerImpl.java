package org.seasar.chronos.task.handler.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ListIterator;

import org.seasar.chronos.delegate.AsyncResult;
import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.task.Transition;
import org.seasar.chronos.task.handler.TaskExecuteHandler;
import org.seasar.chronos.task.impl.JobMethodMetaData;
import org.seasar.chronos.task.impl.MethodGroupManager;
import org.seasar.framework.log.Logger;

public class JobGroupMethodExecuteHandlerImpl extends
		AbstractTaskExecuteHandler {

	private static Logger log = Logger
			.getLogger(JobGroupMethodExecuteHandlerImpl.class);

	private TaskExecuteHandler jobMethodExecuteHandler;

	@Override
	public void setMethodGroupMap(MethodGroupManager methodGroupManager) {
		super.setMethodGroupMap(methodGroupManager);
		jobMethodExecuteHandler.setMethodGroupMap(methodGroupManager);
	}

	@Override
	public void setMethodInvoker(MethodInvoker methodInvoker) {
		super.setMethodInvoker(methodInvoker);
		jobMethodExecuteHandler.setMethodInvoker(methodInvoker);
	}

	private String getFirstFunction(String taskName) {
		List<Method> ret = this.methodGroupManager.getMethodList(taskName);
		return ret.listIterator().next().getName();
	}

	@Override
	public Transition handleRequest(String startTaskName)
			throws InterruptedException {
		String firstChar = startTaskName.substring(0, 1);
		String afterString = startTaskName.substring(1);

		String groupName = firstChar.toUpperCase() + afterString;

		String nextTask = invokeStartJobGroupMethod(groupName);

		if (nextTask == null) {
			String firstFunction = getFirstFunction(startTaskName);
			nextTask = toTaskName(firstFunction);
		} else if (this.methodGroupManager.existGroup(nextTask)) {
			log.debug("startGroupの次はメソッドを指定してください");
			// throw new InvalidNextJobMethodException(
			// "startGroupの次はメソッドを指定してください");
			return new Transition(true, nextTask);
		}

		Transition transition = jobMethodExecuteHandler.handleRequest(nextTask);

		nextTask = invokeEndJobGroupMethod(groupName);

		if (nextTask == null) {
			List<Method> allMethod = this.methodGroupManager.getAllMethodList();
			if (transition.getLastTaskName() != null) {
				String lastTaskName = toMethodName(transition.getLastTaskName());
				ListIterator<Method> li = allMethod.listIterator();
				Method method = null;
				while (li.hasNext()) {
					method = li.next();
					if (method.getName().equals(lastTaskName)) {
						if (li.hasNext()) {
							method = li.next();
							nextTask = toTaskName(method.getName());
						}
						break;
					}
				}
				if (nextTask != null) {
					JobMethodMetaData md = new JobMethodMetaData(method);
					String nextGroupName = md.getGroupName();
					if (nextGroupName != null) {
						nextTask = nextGroupName;
					}
				}
			}
		}
		// if (nextTask == null) {
		// List<String> groupList = this.methodGroupManager.getGroupList();
		// int nextIndex = groupList.indexOf(startTaskName) + 1;
		// nextTask = groupList.get(nextIndex);
		// }
		if (nextTask == null) {
			return new Transition(true, nextTask);
		} else if (this.methodGroupManager.existGroup(nextTask)) {
			return handleRequest(nextTask);
		}
		return new Transition(false, nextTask);
	}

	private String invokeEndJobGroupMethod(String groupName)
			throws InterruptedException {
		String methodName = METHOD_PREFIX_NAME_END + groupName;
		return invokeGroupMethod(methodName);
	}

	private String invokeStartJobGroupMethod(String groupName)
			throws InterruptedException {
		String methodName = METHOD_PREFIX_NAME_START + groupName;
		return invokeGroupMethod(methodName);
	}

	private String invokeGroupMethod(String methodName)
			throws InterruptedException {
		MethodInvoker mi = this.getMethodInvoker();
		if (mi.hasMethod(methodName)) {
			AsyncResult ar = mi.beginInvoke(methodName);
			mi.endInvoke(ar);
			JobMethodMetaData md = new JobMethodMetaData(mi
					.getMethod(methodName));
			return md.getNextTask();
		}
		return null;
	}

	public void setJobMethodExecuteHandler(
			TaskExecuteHandler jobMethodExecuteHandler) {
		this.jobMethodExecuteHandler = jobMethodExecuteHandler;
	}

}
