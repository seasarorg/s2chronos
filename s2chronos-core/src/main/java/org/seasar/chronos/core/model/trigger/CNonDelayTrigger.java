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
package org.seasar.chronos.core.model.trigger;

/**
 * 即時実行のためのトリガークラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public class CNonDelayTrigger extends AbstractTrigger {
	/**
	 * コンストラクタです．
	 */
	public CNonDelayTrigger() {
		super("nonDelayTrigger");
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.trigger.AbstractTrigger#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.trigger.AbstractTrigger#isReScheduleTask()
	 */
	@Override
	public boolean isReScheduleTask() {
		return false;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isStartTask()
	 */
	public boolean isStartTask() {
		if (this.isExecuting() || this.isExecuted()) {
			return false;
		}
		return true;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isEndTask()
	 */
	public boolean isEndTask() {
		return false;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setStartTask(boolean)
	 */
	public void setStartTask(boolean startTask) {
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setEndTask(boolean)
	 */
	public void setEndTask(boolean endTask) {
	}
}
