package org.seasar.chronos.trigger;

public interface Trigger {

	/**
	 * トリガーが終了状態ならtrue、それ以外ならfalseを返します。
	 * 
	 * @return
	 */
	public boolean canEnd();

	/**
	 * トリガーが開始状態ならtrue、それ以外ならfalseを返します。
	 * 
	 * @return
	 */
	public boolean canStart();

	public String getDescription();

	public long getId();

	public Object getJob();

	// public JobAdaptor getJobAdaptor();

	public String getName();

	public boolean isExecuted();

	public void setDescription(String description);

	public void setExecuted(boolean executed);

	public void setId(long triggerId);

	public void setJob(Object jobComponent);

	public void setName(String name);

}