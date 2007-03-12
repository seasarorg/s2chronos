package org.seasar.chronos.job;

import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.job.impl.MethodGroupMap;

public interface TaskExecuteHandler {

	public MethodInvoker getMethodInvoker();

	public void setMethodInvoker(MethodInvoker methodInvoker);

	public MethodGroupMap getMethodGroupMap();

	public void setMethodGroupMap(MethodGroupMap methodGroupMap);

	public Transition handleRequest(String startTaskName) throws Throwable;

}
