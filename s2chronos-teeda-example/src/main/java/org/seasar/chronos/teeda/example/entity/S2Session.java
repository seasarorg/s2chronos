package org.seasar.chronos.teeda.example.entity;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "S2SESSION")
public class S2Session {

	private String sessionId;

	private String name;

	private byte[] value;

	private java.sql.Timestamp lastAccess;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public java.sql.Timestamp getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(java.sql.Timestamp lastAccess) {
		this.lastAccess = lastAccess;
	}

}
