package org.seasar.chronos.sastruts.example.task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.chronos.sastruts.example.entity.User;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.log.Logger;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * 入会中のユーザ一覧をCSVで出力する。
 */
@Task
@CronTrigger(expression = "0 */5 * * * ?")
public class ExportActiveMemberTask {

	/** Logger */
	private final Logger log = Logger.getLogger(EventExecuteTask.class);

	/** JdbcManager */
	public JdbcManager jdbcManager;

	/**
	 * 初期化メソッドです。
	 */
	public void initialize() {

	}

	/**
	 * 開始メソッドです。
	 */
	@NextTask("exportCsv")
	public void start() {

	}

	public void doExportCsv() throws IOException {
		List<User> userList = jdbcManager.from(User.class).where(
				"USER_STATUS  != ?", User.STATUS_DISABLE).getResultList();
		if (userList.size() > 0) {
			CSVWriter writer = new CSVWriter(new FileWriter(
					"C:/temp/output.csv"));
			try {
				for (User user : userList) {
					String[] arg = { user.userId.toString(), user.userName,
							user.email };
					writer.writeNext(arg);
				}
			} finally {
				writer.close();
			}
		}
	}

	/**
	 * 終了メソッドです。
	 */
	public void finish() {

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
