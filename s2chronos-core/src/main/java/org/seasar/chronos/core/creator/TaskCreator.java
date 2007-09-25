package org.seasar.chronos.core.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

public class TaskCreator extends ComponentCreatorImpl {

	private static final String NAME_SUFFIX_TASK = "Task";

	public TaskCreator(NamingConvention namingConvention) {
		super(namingConvention);
		this.setNameSuffix(NAME_SUFFIX_TASK);
		this.setInstanceDef(InstanceDefFactory.SINGLETON);
		this.setExternalBinding(false);
		this.setEnableAbstract(false);
		this.setEnableInterface(false);
	}

	public ComponentCustomizer getTaskCustomizer() {
		return super.getCustomizer();
	}

	public void setTaskCustomizer(ComponentCustomizer customizer) {
		super.setCustomizer(customizer);
	}

}
