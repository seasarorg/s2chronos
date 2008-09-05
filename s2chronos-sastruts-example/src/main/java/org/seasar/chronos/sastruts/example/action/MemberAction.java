package org.seasar.chronos.sastruts.example.action;

import java.util.List;

import javax.annotation.Resource;

import org.seasar.chronos.sastruts.example.entity.User;
import org.seasar.chronos.sastruts.example.form.MemberForm;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class MemberAction {

	@Resource
	protected JdbcManager jdbcManager;

	@ActionForm
	@Resource
	protected MemberForm memberForm;

	public List<BeanMap> userItems = CollectionsUtil.newArrayList();;

	@Execute(validator = false)
	public String index() {
		memberForm.initialize();
		List<User> userList = this.jdbcManager.from(User.class).orderBy(
				"userId").getResultList();
		for (User user : userList) {
			userItems.add(Beans.createAndCopy(BeanMap.class, user).execute());
		}
		return "index.html";
	}

	@Execute(validator = false)
	public String register() {
		return "register.html";
	}

	@Execute(input = "register.html")
	public String submit() {
		User user = Beans.createAndCopy(User.class, memberForm).execute();
		user.userStatus = User.STATUS_ENABLE;
		jdbcManager.insert(user).execute();
		return "../member/?redirect=true";
	}

	@Execute(validator = false)
	public String delete() {
		this.jdbcManager.updateBySql("DELETE FROM USER WHERE USER_ID = ? ",
				Long.class).params(this.memberForm.deleteUserId).execute();
		return "../member/?redirect=true";
	}

}
