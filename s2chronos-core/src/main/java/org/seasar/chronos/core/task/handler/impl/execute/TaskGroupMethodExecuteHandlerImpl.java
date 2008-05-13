package org.seasar.chronos.core.task.handler.impl.execute;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ListIterator;

import org.seasar.chronos.core.delegate.AsyncResult;
import org.seasar.chronos.core.delegate.MethodInvoker;
import org.seasar.chronos.core.task.Transition;
import org.seasar.chronos.core.task.handler.TaskExecuteHandler;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskExecuteHandler;
import org.seasar.chronos.core.task.impl.TaskMethodManager;
import org.seasar.chronos.core.task.impl.TaskMethodMetaData;
import org.seasar.framework.log.Logger;

public class TaskGroupMethodExecuteHandlerImpl extends
		AbstractTaskExecuteHandler {

	private static final long serialVersionUID = 7197512242818142008L;

	private static Logger log = Logger
			.getLogger(TaskGroupMethodExecuteHandlerImpl.class);

	private TaskExecuteHandler taskMethodExecuteHandler;

	public void setTaskMethodExecuteHandler(
			TaskExecuteHandler taskMethodExecuteHandler) {
		this.taskMethodExecuteHandler = taskMethodExecuteHandler;
	}

	@Override
	public void setMethodGroupMap(TaskMethodManager taskMethodManager) {
		super.setMethodGroupMap(taskMethodManager);
		taskMethodExecuteHandler.setMethodGroupMap(taskMethodManager);
	}

	@Override
	public void setMethodInvoker(MethodInvoker methodInvoker) {
		super.setMethodInvoker(methodInvoker);
		taskMethodExecuteHandler.setMethodInvoker(methodInvoker);
	}

	private String getFirstFunction(String taskName) {
		List<Method> ret = this.taskMethodManager.getMethodList(taskName);
		return ret.listIterator().next().getName();
	}

	@Override
	public Transition handleRequest(String startTaskName)
			throws InterruptedException {
		String firstChar = startTaskName.substring(0, 1);
		String afterString = startTaskName.substring(1);

		String groupName = firstChar.toUpperCase() + afterString;

		Transition ts = this.getTerminateTransition();
		if (ts != null) {
			return ts;
		}

		String nextTask = invokeStartJobGroupMethod(groupName);

		if (nextTask == null) {
			String firstFunction = getFirstFunction(startTaskName);
			nextTask = toTaskName(firstFunction);
		} else if (this.taskMethodManager.existGroup(nextTask)) {
			log.debug("startGroupの次はメソッドを指定してください");
			// throw new InvalidNextJobMethodException(
			// "startGroupの次はメソッドを指定してください");
			return new Transition(true, nextTask);
		}

		ts = this.getTerminateTransition();
		if (ts != null) {
			return ts;
		}

		Transition transition = taskMethodExecuteHandler
				.handleRequest(nextTask);

		ts = this.getTerminateTransition();
		if (ts != null) {
			return ts;
		}

		nextTask = invokeEndJobGroupMethod(groupName);

		if (nextTask == null) {
			List<Method> allMethod = this.taskMethodManager.getAllMethodList();
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
					TaskMethodMetaData md = new TaskMethodMetaData(method);
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
			return new Transition(true);
		} else if (this.taskMethodManager.existGroup(nextTask)) {
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
			TaskMethodMetaData md = new TaskMethodMetaData(mi
					.getMethod(methodName));
			return md.getNextTask();
		}
		return null;
	}

}
