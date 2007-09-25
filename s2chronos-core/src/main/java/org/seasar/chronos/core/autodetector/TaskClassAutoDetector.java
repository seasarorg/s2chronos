package org.seasar.chronos.core.autodetector;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.framework.autodetector.impl.AbstractClassAutoDetector;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InitMethod;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassLoaderUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

@Component
public class TaskClassAutoDetector extends AbstractClassAutoDetector {

	private static final String PACKAGE_NAME_TASK = "task";

	protected final List<Class<? extends Annotation>> annotations = CollectionsUtil
			.newArrayList();

	protected NamingConvention namingConvention;

	protected ClassLoader classLoader;

	public TaskClassAutoDetector() {
		annotations.add(Task.class);
	}

	@Binding(bindingType = BindingType.MAY)
	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	@Binding(bindingType = BindingType.MAY)
	public void setClassLoader(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@InitMethod
	public void init() {
		if (namingConvention != null) {
			final String taskPackageName = PACKAGE_NAME_TASK;
			for (final String rootPackageName : namingConvention
					.getRootPackageNames()) {
				final String packageName = ClassUtil.concatName(
						rootPackageName, taskPackageName);
				addTargetPackageName(packageName);
			}
		}
	}

	public void addAnnotation(final Class<? extends Annotation> annotation) {
		annotations.add(annotation);
	}

	@SuppressWarnings("unchecked")
	public void detect(final ClassHandler handler) {
		for (int i = 0; i < getTargetPackageNameSize(); i++) {
			final String packageName = getTargetPackageName(i);
			for (final Iterator<URL> it = ClassLoaderUtil
					.getResources(packageName.replace('.', '/')); it.hasNext();) {
				detect(handler, packageName, it.next());
			}
		}
	}

	protected void detect(final ClassHandler handler,
			final String taskPackageName, final URL url) {
		final Strategy strategy = getStrategy(url.getProtocol());
		strategy.detect(taskPackageName, url, new ClassHandler() {
			public void processClass(final String packageName,
					final String shortClassName) {
				if (packageName.startsWith(taskPackageName)
						&& isTask(packageName, shortClassName)) {
					handler.processClass(packageName, shortClassName);
				}
			}
		});
	}

	protected boolean isTask(final String packageName,
			final String shortClassName) {
		final String name = ClassUtil.concatName(packageName, shortClassName);
		final Class<?> clazz = getClass(name);
		for (final Annotation ann : clazz.getAnnotations()) {
			if (annotations.contains(ann.annotationType())) {
				return true;
			}
		}
		return false;
	}

	protected Class<?> getClass(final String className) {
		if (classLoader != null) {
			return ReflectionUtil.forName(className, classLoader);
		}
		return ReflectionUtil.forNameNoException(className);
	}
}
