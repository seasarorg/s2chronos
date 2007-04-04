package org.seasar.chronos.store.entity;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "CRON_TRIGGER")
public class CronTriggerEntity extends TriggerBase {

	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
