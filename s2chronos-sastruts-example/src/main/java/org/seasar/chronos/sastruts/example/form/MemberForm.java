package org.seasar.chronos.sastruts.example.form;

import org.seasar.struts.annotation.EmailType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

public class MemberForm {

	public Long userId;

	@Required
	@Maxlength(maxlength = 255)
	public String userName;

	@Required
	@EmailType
	public String email;

	@Maxlength(maxlength = 255)
	public String lastName;

	@Maxlength(maxlength = 255)
	public String firstName;

	public Integer userStatus;

	public Long versionNo;

	public Long targetUserId;

	public void initialize() {
		userId = null;
		userName = null;
		email = null;
		lastName = null;
		firstName = null;
		userStatus = null;
		targetUserId = null;
		versionNo = null;

	}

}
