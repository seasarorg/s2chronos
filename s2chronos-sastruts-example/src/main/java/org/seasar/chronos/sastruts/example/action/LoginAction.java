package org.seasar.chronos.sastruts.example.action;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.chronos.sastruts.example.dto.UserAuthDto;
import org.seasar.chronos.sastruts.example.form.LoginForm;
import org.seasar.chronos.sastruts.example.task.LoginLoggerTask;
import org.seasar.framework.log.Logger;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

@SuppressWarnings("serial")
public class LoginAction {
	private static final Logger LOG = Logger.getLogger(LoginAction.class);
	public UserAuthDto userAuthDto;

	@ActionForm
	@Resource
	protected LoginForm loginForm;
	
	public LoginLoggerTask loginLoggerTask;
	
	public LoginAction(){
		
	}

	@Execute(validator = false)
	public String index() {
		return "index.html";
	}

	@Execute(validate = "validate", input = "index.html", redirect=true)
	public String submit() {
		//LOG.debug("submit start = "+loginLoggerTask.loginInfoList);
		loginLoggerTask.addLoginInfo(loginForm.userId, System.currentTimeMillis());
		//LOG.debug("submit end = "+loginLoggerTask.loginInfoList);
		return "../event/";
	}

	public ActionMessages validate() {
		ActionMessages errors = new ActionMessages();
		if ("admin".equals(loginForm.userId)
				&& "admin".equals(loginForm.password)) {
			this.userAuthDto.setUserId(loginForm.userId);
			this.userAuthDto.setAuthed(true);
		} else {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.login.failed"));
			this.userAuthDto.setAuthed(false);
		}
		return errors;
	}

}
