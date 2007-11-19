package org.seasar.chronos.core.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

public class TriggerCreator extends ComponentCreatorImpl {

	private static final String NAME_SUFFIX_TRIGGER = "Trigger";

	public TriggerCreator(NamingConvention namingConvention) {
		super(namingConvention);
		this.setNameSuffix(NAME_SUFFIX_TRIGGER);
		this.setInstanceDef(InstanceDefFactory.SINGLETON);
		this.setExternalBinding(false);
		this.setEnableAbstract(false);
		this.setEnableInterface(false);
	}

	public ComponentCustomizer getTriggerCustomizer() {
		return super.getCustomizer();
	}

	public void setTriggerCustomizer(ComponentCustomizer customizer) {
		super.setCustomizer(customizer);
	}

}
