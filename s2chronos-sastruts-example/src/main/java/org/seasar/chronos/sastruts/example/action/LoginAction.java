package org.seasar.chronos.sastruts.example.action;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.chronos.sastruts.example.dto.UserAuthDto;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.annotation.Required;

public class LoginAction {

	public JdbcManager jdbcManager;

	public UserAuthDto userAuthDto;

	@Required
	public String userId;

	@Required
	public String password;

	@Execute(validator = false)
	public String index() {
		return "index.jsp";
	}

	@Execute(validate = "validate", input = "index.jsp")
	public String submit() {
		return "../add/index.jsp";
	}

	public ActionMessages validate() {
		ActionMessages errors = new ActionMessages();

		if ("admin".equals(userId) && "admin".equals(password)) {
			this.userAuthDto.setUserId(userId);
			this.userAuthDto.setAuthed(true);
		} else {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.login.failed"));
			this.userAuthDto.setAuthed(false);
		}
		return errors;
	}

}
