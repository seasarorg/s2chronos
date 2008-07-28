/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.task.handler.impl.property.write;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskSetStartTaskPropertyWriteHandlerImpl extends
		AbstractTaskPropertyHandler {

	private TaskPropertyReader taskPropertyReader;

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		taskPropertyReader.setup(methodInvocation.getThis(),
				methodInvocation.getClass());
		TaskTrigger taskTrigger = taskPropertyReader.getTrigger(null);
		if (taskTrigger == null) {
			methodInvocation.proceed();
		} else {
			taskTrigger
					.setStartTask((Boolean) methodInvocation.getArguments()[0]);
		}
		return null;
	}

	public void setTaskPropertyReader(TaskPropertyReader taskPropertyReader) {
		this.taskPropertyReader = taskPropertyReader;
	}

}
