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

import java.util.Calendar;

public class CMonthlyTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public boolean isEndTask() {
		return false;
	}

	public synchronized boolean isStartTask() {
		Calendar calendar = Calendar.getInstance();
		int max = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, max);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
			return true;
		}
		return false;
	}

	public void setEndTask(boolean endTask) {

	}

	public void setStartTask(boolean startTask) {

	}

	@Override
	public boolean isReSchedule() {
		return true;
	}

}
