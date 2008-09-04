package org.seasar.chronos.sastruts.example.task;

import java.util.List;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.JoinTask;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.type.JoinType;
import org.seasar.chronos.sastruts.example.entity.User;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.util.tiger.CollectionsUtil;

@Task
public class MemberMailNoticeTask {

	public JdbcManager jdbcManager;

	private List<String> registerMailList = CollectionsUtil.newArrayList();

	@NextTask("registerMail")
	public void initialize() {
		List<User> userList = jdbcManager.from(User.class).getResultList();
		for (User usr : userList) {

		}
	}

	@NextTask("unregisterMail")
	@JoinTask(JoinType.NoWait)
	public void registerMail() {

	}

	public void unregisterMail() {

	}

	public void finish() {

	}
}
