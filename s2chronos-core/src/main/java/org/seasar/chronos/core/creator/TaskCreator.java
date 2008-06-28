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
package org.seasar.chronos.core.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * タスククラス用のCreatorです．
 * 
 * @author j5ik2o
 * 
 */
public class TaskCreator extends ComponentCreatorImpl {

	private static final String NAME_SUFFIX_TASK = "Task";

	public TaskCreator(NamingConvention namingConvention) {
		super(namingConvention);
		this.setNameSuffix(NAME_SUFFIX_TASK);
		this.setInstanceDef(InstanceDefFactory.SINGLETON);
		this.setExternalBinding(false);
		this.setEnableAbstract(true);
		this.setEnableInterface(true);
	}

	public ComponentCustomizer getTaskCustomizer() {
		return super.getCustomizer();
	}

	public void setTaskCustomizer(ComponentCustomizer customizer) {
		super.setCustomizer(customizer);
	}

}
