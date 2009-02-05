package org.seasar.chronos.core.test.trigger;

import org.seasar.chronos.core.model.trigger.AbstractTrigger;

public class CCustomTrigger extends AbstractTrigger {
	private static final long serialVersionUID = 1L;

	private int div;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public boolean isEndTask() {
		return false;
	}

	public boolean isStartTask() {
		long mill = System.currentTimeMillis();
		long mod = mill % div;
		System.out.println("mill = " + mill);
		System.out.println("mod = " + mod);
		return mod == 0;
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
