package org.seasar.chronos.sastruts.example.form;

import org.seasar.struts.annotation.Required;

public class LoginForm {

	@Required
	public String userId;

	@Required
	public String password;

}
