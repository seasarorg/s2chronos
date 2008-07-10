package org.seasar.chronos.sastruts.example.action;

import org.seasar.chronos.sastruts.example.dto.UserAuthDto;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.annotation.IntegerType;
import org.seasar.struts.annotation.Required;

public class AddAction {

	public UserAuthDto userAuthDto;

	@Required
	@IntegerType
	public String arg1;

	@Required
	@IntegerType
	public String arg2;

	public Integer result;

	@Execute(validator = false)
	public String index() {
		return "index.jsp";
	}

	@Execute(input = "index.jsp")
	public String submit() {
		result = Integer.valueOf(arg1) + Integer.valueOf(arg2);
		return "index.jsp";
	}

}