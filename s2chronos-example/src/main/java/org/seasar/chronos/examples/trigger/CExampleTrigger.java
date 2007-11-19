package org.seasar.chronos.examples.trigger;

import org.seasar.chronos.core.trigger.AbstractTrigger;

public class CExampleTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private int div;

	public boolean isEndTask() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean isStartTask() {
		return System.currentTimeMillis() % div == 0;
	}

	public void setEndTask(Boolean endTask) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void setStartTask(Boolean startTask) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isReSchedule() {
		return true;
	}

	public int getDiv() {
		return div;
	}

	public void setDiv(int div) {
		this.div = div;
	}

}
