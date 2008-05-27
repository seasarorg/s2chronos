package org.seasar.chronos.extension.persitense.aop;

import java.io.ObjectStreamException;
import java.lang.annotation.Annotation;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.seasar.framework.aop.intertype.AbstractInterType;

public class SerializeInterType extends AbstractInterType {

	public interface SerializeAnnotationHandler {
		public boolean isSerialize(Class<?> clazz);
	}

	public static class DefaultSerializeAnnotationHandler implements
			SerializeAnnotationHandler {
		public boolean isSerialize(Class<?> clazz) {
			Annotation annotation = clazz.getAnnotation(Serialize.class);
			return annotation != null ? true : false;
		}
	}

	private static SerializeAnnotationHandler annotationHandler = new DefaultSerializeAnnotationHandler();

	@Override
	protected void introduce() throws CannotCompileException, NotFoundException {
		if (annotationHandler.isSerialize(this.getTargetClass())) {
			String className = this.getTargetClass().getName();
			this
					.addMethod(
							Object.class,
							"writeReplace",
							null,
							new Class[] { ObjectStreamException.class },
							String
									.format(
											"{ return org.seasar.chronos.extension.persitense.SerializableObject.create(\"%s\", this); }",
											className));
		}
	}

}
