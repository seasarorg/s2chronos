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
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.tiger.CollectionsUtil;

/**
 * ユーザにメールを通知するタスククラスです。
 */
@Task
@CronTrigger(expression = "0 */1 * * * ?")
public class MemberMailNoticeTask {

	/** Logger */
	private final Logger log = Logger.getLogger(MemberMailNoticeTask.class);

	/** jdbcManager */
	public JdbcManager jdbcManager;

	/** registerMai */
	public RegisterMai regsiterMai;

	/** ユーザ登録の最終更新日時 */
	private Timestamp registerLastUpdateDate;

	/** ユーザ退会の最終更新日時 */
	private Timestamp unregisterLastUpdateDate;

	/** 入会したユーザ一覧 */
	private List<User> registerUserList = CollectionsUtil.newArrayList();

	/** 退会したユーザ一覧 */
	private List<User> unregisterUserList = CollectionsUtil.newArrayList();

	/**
	 * 初期化メソッドです。
	 */
	public void initialize() {

	}

	/**
	 * 開始メソッドです。
	 */
	@NextTask("registerMail")
	public void start() {
		buildRegisterUserList();
		buildUnregisterUserList();
	}

	/**
	 * 入会したユーザ一覧を作成します。
	 */
	private void buildRegisterUserList() {
		BeanMap beanMap = jdbcManager
				.selectBySql(
						BeanMap.class,
						"SELECT MAX(UPDATE_DATE) AS LAST_UPDATE_DATE FROM USER　WHERE　USER_STATUS != ?",
						User.STATUS_DISABLE).getSingleResult();
		registerLastUpdateDate = (Timestamp) beanMap.get("lastUpdateDate");
		if (registerLastUpdateDate != null) {
			registerUserList = jdbcManager.from(User.class).where(
					"UPDATE_DATE >= ? AND USER_STATUS != ?",
					registerLastUpdateDate, User.STATUS_DISABLE)
					.getResultList();
		}
	}

	/**
	 * 退会したユーザ一覧を作成します。
	 */
	private void buildUnregisterUserList() {
		BeanMap beanMap = jdbcManager
				.selectBySql(
						BeanMap.class,
						"SELECT MAX(UPDATE_DATE) AS LAST_UPDATE_DATE FROM USER　WHERE　USER_STATUS = ?",
						User.STATUS_DISABLE).getSingleResult();
		unregisterLastUpdateDate = (Timestamp) beanMap.get("lastUpdateDate");
		if (unregisterLastUpdateDate != null) {
			unregisterUserList = jdbcManager.from(User.class).where(
					"UPDATE_DATE >= ? AND USER_STATUS = ?",
					registerLastUpdateDate, User.STATUS_DISABLE)
					.getResultList();
		}
	}

	/**
	 * 入会したユーザにメールを送信します。
	 */
	@NextTask("unregisterMail")
	public void doRegisterMail() {
		for (User user : registerUserList) {
			MailDto dto = new MailDto();
			dto.setFrom("kato@globalspace-it.com");
			dto.setTo(user.email);
			dto.setUserName(user.userName);
			this.regsiterMai.sendRegisterMail(dto);
		}
	}

	/**
	 * 退会したユーザにメールを送信します。
	 */
	public void doUnregisterMail() {
		for (User user : unregisterUserList) {
			MailDto dto = new MailDto();
			dto.setFrom("kato@globalspace-it.com");
			dto.setTo(user.email);
			dto.setUserName(user.userName);
			this.regsiterMai.sendUnregisterMail(dto);
		}
	}

	/**
	 * 終了メソッドです。
	 */
	public void finish() {
		registerUserList.clear();
		unregisterUserList.clear();
	}

	/**
	 * 例外をキャッチします。
	 * 
	 * @param ex
	 *            例外
	 */
	public void catchException(Exception ex) {
		log.error(ex);
	}

}
