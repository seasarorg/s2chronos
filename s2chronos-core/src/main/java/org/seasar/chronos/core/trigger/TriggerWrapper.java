/* 
 * Copyright 2008 the Seasar Foundation and the Others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.TaskTrigger;

public class TriggerWrapper extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private TaskTrigger taskTrigger;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public TriggerWrapper(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	public boolean isEndTask() {
		return taskTrigger.isEndTask();
	}

	public boolean isStartTask() {
		return taskTrigger.isStartTask();
	}

	public void setEndTask(boolean endTask) {
		taskTrigger.setEndTask(endTask);
	}

	public void setStartTask(boolean startTask) {
		taskTrigger.setStartTask(startTask);
	}

}
