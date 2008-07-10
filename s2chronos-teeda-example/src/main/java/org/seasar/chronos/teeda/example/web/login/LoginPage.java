package org.seasar.chronos.teeda.example.web.login;

import javax.faces.internal.FacesMessageUtil;

import org.seasar.chronos.teeda.example.dto.UserAuthDto;
import org.seasar.chronos.teeda.example.web.add.AddPage;
import org.seasar.teeda.extension.annotation.validator.Required;

public class LoginPage {

	@Required
	public String userId;

	@Required
	public String password;

	public UserAuthDto userAuthDto;

	public void initialize() {

	}

	public void prerender() {

	}

	public Class<?> doLogin() {
		if ("admin".equals(userId) && "admin".equals(password)) {
			this.userAuthDto.setUserId(userId);
			this.userAuthDto.setAuthed(true);
			return AddPage.class;
		}
		FacesMessageUtil.addWarnMessage("errors.login.failed");
		return null;
	}

}
