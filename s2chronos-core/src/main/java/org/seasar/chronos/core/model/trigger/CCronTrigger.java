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

import java.text.ParseException;
import java.util.Date;

import org.seasar.chronos.core.trigger.cron.CronExpression;
import org.seasar.framework.exception.ParseRuntimeException;
import org.seasar.framework.log.Logger;

public class CCronTrigger extends AbstractTrigger {

	private static Logger log = Logger.getLogger(CCronTrigger.class);

	private static final long serialVersionUID = 1L;

	private CronExpression expression;

	private Date buildTime = new Date(System.currentTimeMillis());

	public CCronTrigger() {
		super("cronTrigger");
	}

	public CCronTrigger(String cronExpression) {
		super("cronTrigger");
		this.setExpression(cronExpression);
	}

	@Override
	public boolean isReScheduleTask() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		CCronTrigger src = (CCronTrigger) obj;
		if (this.expression != null) {
			result = result & expression.equals(src.expression);
		}
		return result;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public String getCronExpression() {
		return this.expression.getCronExprssion();
	}

	public boolean isEndTask() {
		return false;
	}

	public boolean isStartTask() {
		if (this.isExecuting()) {
			return false;
		}
		long nowTime = System.currentTimeMillis();
		Date nextValidTime = this.expression.getNextValidTimeAfter(buildTime);
		// log.debug("nextValidTime = " + nextValidTime);
		// log.debug("nowTime = " + nowTime + ", nextValidTime = "
		// + nextValidTime.getTime());
		if (nowTime > nextValidTime.getTime()) {
			buildTime = new Date(nowTime);
			return true;
		}
		return false;
	}

	public void setExpression(String cronExpression) {
		try {
			this.expression = new CronExpression(cronExpression);
		} catch (ParseException e) {
			log.log("ECHRONOS0200", null);
			throw new ParseRuntimeException(e);
		}
	}

	public String getExpression() {
		return this.expression.getCronExprssion();
	}

	public void setEndTask(boolean endTask) {

	}

	public void setStartTask(boolean startTask) {

	}

}
