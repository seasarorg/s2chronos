package org.seasar.chronos.store;

public interface TaskStore {

	public void saveToStore(Object task);

	public Object loadFromStore(long id);

	public void loadFromStore(long id, Object task);

}
