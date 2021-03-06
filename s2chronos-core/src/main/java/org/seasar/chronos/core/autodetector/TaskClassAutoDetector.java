/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.autodetector;

import java.lang.annotation.Annotation;
import java.util.List;

import org.seasar.chronos.core.task.TaskValidator;
import org.seasar.framework.autodetector.impl.AbstractClassAutoDetector;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InitMethod;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourcesUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;
import org.seasar.framework.util.ResourcesUtil.Resources;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

@Component
public class TaskClassAutoDetector extends AbstractClassAutoDetector {

	private static final String PACKAGE_NAME_TASK = "task";

	protected final List<Class<? extends Annotation>> annotations = CollectionsUtil
			.newArrayList();

	protected NamingConvention namingConvention;

	protected ClassLoader classLoader;

	private TaskValidator taskValidator;

	public TaskClassAutoDetector() {
		// this.annotations.add(Task.class);
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
		if (this.namingConvention != null) {
			final String taskPackageName = PACKAGE_NAME_TASK;
			for (final String rootPackageName : this.namingConvention
					.getRootPackageNames()) {
				final String packageName = ClassUtil.concatName(
						rootPackageName, taskPackageName);
				this.addTargetPackageName(packageName);
			}
		}
	}

	public void addAnnotation(final Class<? extends Annotation> annotation) {
		// this.annotations.add(annotation);
	}

	@SuppressWarnings("unchecked")
	public void detect(final ClassHandler handler) {
		for (int i = 0; i < getTargetPackageNameSize(); i++) {
			final String packageName = getTargetPackageName(i);
			for (final Resources resources : ResourcesUtil
					.getResourcesTypes(packageName)) {
				try {
					resources.forEach(new ClassHandler() {

						public void processClass(final String packageName,
								final String shortClassName) {
							if (packageName.startsWith(packageName)
									&& TaskClassAutoDetector.this.isTask(
											packageName, shortClassName)) {
								handler.processClass(packageName,
										shortClassName);
							}
						}
					});
				} finally {
					resources.close();
				}
			}
		}
	}

	protected boolean isTask(final String packageName,
			final String shortClassName) {
		final String name = ClassUtil.concatName(packageName, shortClassName);
		final Class<?> clazz = this.getClass(name);
		return taskValidator.isValid(clazz);
	}

	protected Class<?> getClass(final String className) {
		if (this.classLoader != null) {
			return ReflectionUtil.forName(className, this.classLoader);
		}
		return ReflectionUtil.forNameNoException(className);
	}

	public void setTaskValidator(TaskValidator taskValidator) {
		this.taskValidator = taskValidator;
	}
}
