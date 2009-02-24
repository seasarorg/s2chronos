package org.seasar.chronos.core.model.trigger;

import java.util.Date;

import org.seasar.chronos.core.model.TaskTrigger;

/**
 * 実行時間範囲を指定できるトリガークラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public class ScopedTimeTrigger extends TriggerWrapper {
	private CCronTrigger scopedStartCronTrigger;

	private CCronTrigger scopedEndCronTrigger;

	private Date scopedStartTime;

	private Date scopedEndTime;

	/**
	 * コンストラクタです．
	 * 
	 * @param taskTrigger
	 *            {@link TaskTrigger}
	 */
	public ScopedTimeTrigger(TaskTrigger taskTrigger) {
		super(taskTrigger);
	}

	/**
	 * 終了時間の{@link CCronTrigger}を返します．
	 * 
	 * @return {@link CCronTrigger}
	 */
	public CCronTrigger getScopedEndCronTrigger() {
		return scopedEndCronTrigger;
	}

	/**
	 * 終了日時を返します．
	 * 
	 * @return 終了日時
	 */
	public Date getScopedEndTime() {
		return (Date) scopedEndTime.clone();
	}

	/**
	 * 開始時間の{@link CCronTrigger}を返します．
	 * 
	 * @return {@link CCronTrigger}
	 */
	public CCronTrigger getScopedStartCronTrigger() {
		return scopedStartCronTrigger;
	}

	/**
	 * 開始日時を返します．
	 * 
	 * @return 開始日時
	 */
	public Date getScopedStartTime() {
		return (Date) scopedStartTime.clone();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.trigger.TriggerWrapper#isEndTask()
	 */
	@Override
	public boolean isEndTask() {
		boolean endScopedTimeCheck = false;
		if (scopedEndTime != null) {
			endScopedTimeCheck =
			    (System.currentTimeMillis() >= scopedEndTime.getTime());
		}
		if (scopedEndCronTrigger != null) {
			endScopedTimeCheck =
			    endScopedTimeCheck || scopedEndCronTrigger.isStartTask();
		}
		return endScopedTimeCheck || super.isEndTask();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.trigger.TriggerWrapper#isStartTask()
	 */
	@Override
	public boolean isStartTask() {
		boolean startScopedTimeCheck = false;
		if (scopedStartTime != null) {
			startScopedTimeCheck =
			    (System.currentTimeMillis() >= scopedStartTime.getTime());
		}
		if (scopedStartCronTrigger != null) {
			startScopedTimeCheck =
			    startScopedTimeCheck || scopedStartCronTrigger.isStartTask();
		}
		boolean endScopedTimeCheck = false;
		if (scopedEndTime != null) {
			endScopedTimeCheck =
			    (System.currentTimeMillis() < scopedEndTime.getTime());
		}
		if (scopedEndCronTrigger != null) {
			endScopedTimeCheck =
			    endScopedTimeCheck || !scopedEndCronTrigger.isStartTask();
		}
		return startScopedTimeCheck
		    && endScopedTimeCheck
		    && super.isStartTask();
	}

	/**
	 * 終了時間の{@link CCronTrigger}を設定します．
	 * 
	 * @param scopedEndCronTrigger
	 *            {@link CCronTrigger}
	 */
	public void setScopedEndCronTrigger(CCronTrigger scopedEndCronTrigger) {
		this.scopedEndCronTrigger = scopedEndCronTrigger;
	}

	/**
	 * 終了日時を設定します．
	 * 
	 * @param scopedEndTime
	 *            終了日時
	 */
	public void setScopedEndTime(Date scopedEndTime) {
		this.scopedEndTime = (Date) scopedEndTime.clone();
	}

	/**
	 * 開始時間の{@link CCronTrigger}を設定します．
	 * 
	 * @param scopedStartCronTrigger
	 *            {@link CCronTrigger}
	 */
	public void setScopedStartCronTrigger(CCronTrigger scopedStartCronTrigger) {
		this.scopedStartCronTrigger = scopedStartCronTrigger;
	}

	/**
	 * 開始日時を設定します．
	 * 
	 * @param scopedStartTime
	 *            開始日時
	 */
	public void setScopedStartTime(Date scopedStartTime) {
		this.scopedStartTime = (Date) scopedStartTime.clone();
	}
}
