package org.seasar.chronos.sastruts.example.dto;

import java.io.Serializable;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.SESSION)
public class UserAuthDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private boolean authed;

	public boolean isAuthed() {
		return authed;
	}

	public void setAuthed(boolean authed) {
		this.authed = authed;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
