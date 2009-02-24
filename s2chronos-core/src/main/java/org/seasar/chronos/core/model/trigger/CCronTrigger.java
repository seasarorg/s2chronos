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

import org.seasar.chronos.core.model.trigger.cron.CronExpression;
import org.seasar.framework.exception.ParseRuntimeException;
import org.seasar.framework.log.Logger;

/**
 * CRON形式に対応したトリガークラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public class CCronTrigger extends AbstractTrigger {
	private static Logger log = Logger.getLogger(CCronTrigger.class);

	private CronExpression expression;

	private Date buildTime = new Date(System.currentTimeMillis());

	/**
	 * デフォルトコンストラクタです．
	 */
	public CCronTrigger() {
		super("cronTrigger");
	}

	/**
	 * コンストラクタです．
	 * 
	 * @param cronExpression
	 *            CROND形式文字列
	 */
	public CCronTrigger(String cronExpression) {
		super("cronTrigger");
		this.setCronExpression(cronExpression);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.trigger.AbstractTrigger#isReScheduleTask()
	 */
	@Override
	public boolean isReScheduleTask() {
		return true;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.trigger.AbstractTrigger#equals(java.lang
	 * .Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		CCronTrigger src = (CCronTrigger) obj;
		if (this.expression != null) {
			result = result & expression.equals(src.expression);
		}
		return result;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.trigger.AbstractTrigger#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * CROND形式文字列を設定します．
	 * 
	 * @param cronExpression
	 *            CROND形式文字列
	 */
	public void setCronExpression(String cronExpression) {
		try {
			this.expression = new CronExpression(cronExpression);
		} catch (ParseException e) {
			log.log("ECHRONOS0200", null);
			throw new ParseRuntimeException(e);
		}
	}

	/**
	 * CROND形式文字列を返します．
	 * 
	 * @return CROND形式文字列
	 */
	public String getCronExpression() {
		return this.expression.getCronExprssion();
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
	 * @see org.seasar.chronos.core.model.TaskTrigger#isStartTask()
	 */
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

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setEndTask(boolean)
	 */
	public void setEndTask(boolean endTask) {
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setStartTask(boolean)
	 */
	public void setStartTask(boolean startTask) {
	}
}
