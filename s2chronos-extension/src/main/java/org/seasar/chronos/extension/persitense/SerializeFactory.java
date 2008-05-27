package org.seasar.chronos.extension.persitense;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

public class SerializeFactory {

	private static final String PROXY_CLASS_NAME_FORMAT = "%s.%sProxy";

	public static Class<?> createProxy(Class<?> target)
			throws NotFoundException, CannotCompileException {
		String pkgName = SerializeFactory.class.getPackage().getName();
		String proxyClassName = String.format(PROXY_CLASS_NAME_FORMAT, pkgName, target
				.getSimpleName());
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
		StringBuilder sb = new StringBuilder();
		sb
				.append("public Object writeReplace() throws java.io.ObjectStreamException {");
		sb
				.append(String
						.format(
								"return org.seasar.chronos.extension.persitense.SerializableObject.create(\"%s\", this); }",
								className));
		CtMethod method = CtMethod.make(sb.toString(), target);
		target.addMethod(method);
	}
}
