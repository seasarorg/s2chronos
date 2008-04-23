package org.seasar.chronos.core.task.command.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.task.handler.TaskPropertyHandler;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskGetThreadPoolSizePropertyReadCommandImplTest {

	private TaskPropertyHandler taskGetThreadPoolSizePropertyReadCommand;

	@Test
	public void testExecute() {
		MethodInvocation methodInvocation = new MethodInvocation() {

			public Method getMethod() {
				return null;
			}

			public Object[] getArguments() {
				return null;
			}

			public AccessibleObject getStaticPart() {
				return null;
			}

			public Object getThis() {
				return null;
			}

			public Object proceed() throws Throwable {
				return null;
			}

		};
		try {
			taskGetThreadPoolSizePropertyReadCommand.execute(methodInvocation);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
