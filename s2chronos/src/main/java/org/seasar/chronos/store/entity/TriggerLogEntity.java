package org.seasar.chronos.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "TRIGGER_LOG")
public class TriggerLogEntity {

	private BigDecimal versionNo;
}
