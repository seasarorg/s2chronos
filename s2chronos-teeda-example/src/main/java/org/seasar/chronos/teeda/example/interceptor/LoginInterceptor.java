package org.seasar.chronos.teeda.example.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.teeda.example.dto.UserAuthDto;
import org.seasar.chronos.teeda.example.web.login.LoginPage;

public class LoginInterceptor implements MethodInterceptor {

	public UserAuthDto userAuthDto;
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if ( userAuthDto != null && userAuthDto.isAuthed()){
			return invocation.proceed();
		}
		return LoginPage.class;
	}

}
