package org.seasar.chronos.teeda.example.dao;

import org.seasar.chronos.teeda.example.entity.S2Session;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.annotation.tiger.Sql;

@S2Dao(bean = S2Session.class)
public interface S2SessionDao {

	@Sql("DELETE FROM S2SESSION WHERE LAST_ACCESS > NOW()+/*expireTime*/")
	public int deleteOldSession(int expireTime);

}
