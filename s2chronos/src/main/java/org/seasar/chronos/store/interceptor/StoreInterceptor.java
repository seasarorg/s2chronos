package org.seasar.chronos.store.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.store.annotation.Store;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class StoreInterceptor implements MethodInterceptor {

	private S2Container s2Container;

	public void setS2Container(S2Container s2Container) {
		this.s2Container = s2Container;
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object[] arguments = methodInvocation.getArguments();
		Method mh = methodInvocation.getMethod();
		Class<?> declaringClass = mh.getDeclaringClass();
		Store store = (Store) declaringClass.getAnnotation(Store.class);

		Class<?> beanClass = store.bean();
		String className = beanClass.getCanonicalName();
		String daoClassName = className + "Dao";
		String dxoClassName = className + "Dxo";
		String methodName = mh.getName();

		Class<?> daoClass = ReflectionUtil.forNameNoException(daoClassName);
		Class<?> dxoClass = ReflectionUtil.forNameNoException(dxoClassName);
		S2Dao s2Dao = (S2Dao) daoClass.getAnnotation(S2Dao.class);
		Class<?> daoEntityClass = s2Dao.bean();

		Object dao = s2Container.getComponent(daoClass);
		Object dxo = s2Container.getComponent(dxoClass);

		if ("saveToStore".equals(methodName)) {

			Method dxoToEntityMethod = ReflectionUtil.getMethod(dxoClass,
					"toEntity", beanClass);
			Object entity = ReflectionUtil.invoke(dxoToEntityMethod, dxo,
					arguments[0]);

			Method daoUpdateMethod = ReflectionUtil.getMethod(daoClass,
					"update", daoEntityClass);
			Method daoInsertMethod = ReflectionUtil.getMethod(daoClass,
					"insert", daoEntityClass);
			try {
				ReflectionUtil.invoke(daoUpdateMethod, dao, entity);
			} catch (SQLRuntimeException ex) {
				ReflectionUtil.invoke(daoInsertMethod, dao, entity);
			}

		} else if ("loadFormStore".equals(methodName)) {
			Method daoSelectByIdMethod = ReflectionUtil.getMethod(daoClass,
					"selectById", daoEntityClass);
			Object entity = ReflectionUtil.invoke(daoSelectByIdMethod, dao,
					arguments[0]);

			Method dxoToTriggerMethod = ReflectionUtil.getMethod(dxoClass,
					"toComponent", beanClass);
			Object trigger = ReflectionUtil.invoke(dxoToTriggerMethod, dxo,
					entity);
			return trigger;
		}
		return null;
	}

}
