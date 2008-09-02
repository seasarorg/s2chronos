package org.seasar.chronos.sastruts.example.form;

import java.io.Serializable;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;
import org.seasar.struts.annotation.Required;

@Component(instance = InstanceType.SESSION)
@SuppressWarnings("serial")
public class LoginForm implements Serializable {

	@Required
	public String userId;

	@Required
	public String password;

}
