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

import java.util.Date;

public class CTimedTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private Date startTime;

	private Date endTime;

	public CTimedTrigger() {
		super("timedTrigger");
	}

	@Override
	public boolean isReScheduleTask() {
		return false;
	}

	public void setStartTime(Date startDate) {
		this.startTime = (Date) startDate.clone();
	}

	public Date getStartTime() {
		return (Date) startTime.clone();
	}

	public void setEndTime(Date endDate) {
		this.endTime = (Date) endDate.clone();
	}

	public Date getEndTime() {
		return (Date) endTime.clone();
	}

	public boolean isStartTask() {
		if (this.isExecute()) {
			return false;
		}
		boolean startTimeCheck = false;
		// 開始時刻の確認
		if (startTime != null) {
			startTimeCheck = (System.currentTimeMillis() >= startTime.getTime());
		}
		return startTimeCheck;
	}

	public boolean isEndTask() {
		boolean endTimeCheck = false;
		// 終了時刻の確認
		if (endTime != null) {
			endTimeCheck = (System.currentTimeMillis() >= endTime.getTime());
		}
		return endTimeCheck;
	}

	public void setStartTask(boolean startTask) {

	}

	public void setEndTask(boolean endTask) {

	}

}
