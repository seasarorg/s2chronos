package org.seasar.chronos.example.trigger;

import org.seasar.chronos.core.model.trigger.AbstractTrigger;

public class CCustomTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private int div;

	public boolean isEndTask() {
		return false;
	}

	public boolean isStartTask() {
		return System.currentTimeMillis() % div == 0;
	}

	public void setEndTask(boolean endTask) {

	}

	public void setStartTask(boolean startTask) {

	}

	@Override
	public boolean isReScheduleTask() {
		return true;
	}

	public int getDiv() {
		return div;
	}

	public void setDiv(int div) {
		this.div = div;
	}

}
