package org.seasar.chronos.trigger.impl;

public class GenericNonDelayTrigger extends AbstractTrigger {

	public boolean canEnd() {
		return false;
	}

	public boolean canStart() {
		return true;
	}

}
