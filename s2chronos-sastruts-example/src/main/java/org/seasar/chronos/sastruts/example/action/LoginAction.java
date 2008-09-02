package org.seasar.chronos.sastruts.example.action;

import javax.annotation.Resource;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.chronos.sastruts.example.dto.UserAuthDto;
import org.seasar.chronos.sastruts.example.form.LoginForm;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class LoginAction {

	public UserAuthDto userAuthDto;

	@ActionForm
	@Resource
	protected LoginForm loginForm;

	@Execute(validator = false)
	public String index() {
		return "index.html";
	}

	@Execute(validate = "validate", input = "index.html")
	public String submit() {
		return "../add/?redirect=true";
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
