package org.seasar.chronos.sastruts.example.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.sastruts.example.dto.UserAuthDto;

public class LoginInterceptor implements MethodInterceptor {

	public UserAuthDto userAuthDto;
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if ( userAuthDto != null && userAuthDto.isAuthed()){
			return invocation.proceed();
		}
		return "../login/index.jsp";
	}

}
