package org.seasar.chronos.sastruts.example.task;

import java.sql.Timestamp;
import java.util.List;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.chronos.sastruts.example.dto.MailDto;
import org.seasar.chronos.sastruts.example.entity.User;
import org.seasar.chronos.sastruts.example.mai.RegisterMai;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.util.tiger.CollectionsUtil;

@Task
@CronTrigger(expression = "0 */1 * * * ?")
public class MemberMailNoticeTask {

	public JdbcManager jdbcManager;

	public RegisterMai regsiterMai;

	private Timestamp lastUpdateDate;

	private List<User> registerUserList = CollectionsUtil.newArrayList();

	public void initialize() {

	}

	@NextTask("registerMail")
	public void start() {
		BeanMap beanMap = jdbcManager.selectBySql(BeanMap.class,
				"SELECT MAX(UPDATE_DATE) AS LAST_UPDATE_DATE FROM USER")
				.getSingleResult();
		lastUpdateDate = (Timestamp) beanMap.get("lastUpdateDate");
		if (lastUpdateDate != null) {
			List<User> userList = jdbcManager.from(User.class).where(
					"UPDATE_DATE >= ?", lastUpdateDate).getResultList();
			for (User user : userList) {
				registerUserList.add(user);
			}
		}
	}

	@NextTask("unregisterMail")
	public void doRegisterMail() {
		for (User user : registerUserList) {
			MailDto dto = new MailDto();
			dto.setFrom("kato@globalspace-it.com");
			dto.setTo(user.email);
			dto.setUserName(user.userName);
			// this.regsiterMai.sendMail(dto);
		}
	}

	public void doUnregisterMail() {

	}

	public void finish() {
		registerUserList.clear();
	}
}
