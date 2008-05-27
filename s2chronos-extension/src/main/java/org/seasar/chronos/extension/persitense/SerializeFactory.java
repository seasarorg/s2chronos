package org.seasar.chronos.extension.persitense;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class SerializeFactory {

	public static Class<?> createProxy(Class<?> target)
			throws NotFoundException, CannotCompileException {
		String proxyClassName = "org.seasar.chronos.extension.persitense."
				+ target.getSimpleName() + "Proxy";
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		try {
			Class<?> clazz = Class.forName(proxyClassName, true, classLoader);
			return clazz;
		} catch (ClassNotFoundException e) {
		}

		ClassPool cp = new ClassPool();
		cp.appendClassPath(new LoaderClassPath(classLoader));
		CtClass formCtClass = cp.get(target.getName());
		CtClass formProxyCtClass = cp.makeClass(proxyClassName, formCtClass);
		formProxyCtClass.setSuperclass(formCtClass);
		addWriteReplaceMethod(target.getName(), formProxyCtClass);
		return formProxyCtClass.toClass();
	}

	private static void addWriteReplaceMethod(String className, CtClass target)
			throws CannotCompileException {
		String body = "public Object writeReplace() throws java.io.ObjectStreamException { return org.seasar.chronos.extension.persitense.SerializableObject.create(\""
				+ className + "\", this); }";
		CtMethod method = CtMethod.make(body, target);
		target.addMethod(method);
	}
}
