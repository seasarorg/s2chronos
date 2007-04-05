package org.seasar.chronos.store.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.store.annotation.Store;
import org.seasar.dao.annotation.tiger.S2Dao;
import org.seasar.dao.util.ImplementInterfaceWalker;
import org.seasar.dao.util.ImplementInterfaceWalker.Status;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class StoreInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private S2Container s2Container;

	public void setS2Container(S2Container s2Container) {
		this.s2Container = s2Container;
	}

	private static class HandlerImpl implements
			ImplementInterfaceWalker.Handler {

		Store foundStoreAnnotation;

		public Status accept(Class ifs) {
			final Store store = getStoreAnnotaionFromStore(ifs);
			if (store != null) {
				foundStoreAnnotation = store;
				return ImplementInterfaceWalker.BREAK;
			}
			return ImplementInterfaceWalker.CONTINUE;
		}

	}

	private static Store getStoreAnnotaionFromStore(Class<?> storeClass) {
		if (storeClass.isAnnotationPresent(Store.class)) {
			Store store = (Store) storeClass.getAnnotation(Store.class);
			return store;
		}
		return null;
	}

	private Store getStoreAnnotaion(Class<?> storeClass) {
		final Store store = getStoreAnnotaionFromStore(storeClass);
		if (store != null) {
			return store;
		}

		HandlerImpl handlerImpl = new HandlerImpl();
		ImplementInterfaceWalker.walk(storeClass, handlerImpl);
		return handlerImpl.foundStoreAnnotation;
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method mh = methodInvocation.getMethod();
		String methodName = mh.getName();
		if (!MethodUtil.isAbstract(mh)) {
			return methodInvocation.proceed();
		}

		Object[] arguments = methodInvocation.getArguments();

		Class<?> clazz = getTargetClass(methodInvocation);
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
		Store store = getStoreAnnotaion(beanDesc.getBeanClass());

		Class<?> beanClass = store.bean();
		Class<?> daoClass = store.dao();
		Class<?> dxoClass = store.dxo();
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

			if (arguments.length == 1) {
				Method dxoToTriggerMethod = ReflectionUtil.getMethod(dxoClass,
						"toComponent", beanClass);
				Object trigger = ReflectionUtil.invoke(dxoToTriggerMethod, dxo,
						entity);
				return trigger;
			} else {
				Method dxoFromEntityFromComponent = ReflectionUtil.getMethod(
						dxoClass, "fromEntityFromComponent", daoEntityClass,
						beanClass);
				ReflectionUtil.invoke(dxoFromEntityFromComponent, entity,
						arguments[1]);
			}
		}
		return null;
	}

}
