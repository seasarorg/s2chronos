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

public class CDelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private long delay = 0;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public CDelayTrigger() {
		super("delayTrigger");
	}

	public CDelayTrigger(long delay) {
		this();
		this.delay = delay;
	}

	@Override
	public boolean isReScheduleTask() {
		return false;
	}

	public void setDelay(long delay) {
		this.delay = System.currentTimeMillis() + delay;
	}

	public long getDelay() {
		return delay;
	}

	public boolean isStartTask() {
		if (this.isExecuting()) {
			return false;
		}

		boolean startTimeCheck = false;

		// 開始時刻の確認
		startTimeCheck = (System.currentTimeMillis() >= delay);

		return startTimeCheck;
	}

	public boolean isEndTask() {
		return false;
	}

	public void setStartTask(boolean startTask) {

	}

	public void setEndTask(boolean endTask) {

	}

}
