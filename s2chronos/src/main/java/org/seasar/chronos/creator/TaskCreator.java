package org.seasar.chronos.creator;

import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

public class TaskCreator extends ComponentCreatorImpl {

	private static final String NAME_SUFFIX_TASK = "Task";

	public TaskCreator(NamingConvention namingConvention) {
		super(namingConvention);
		this.setNameSuffix(NAME_SUFFIX_TASK);
		this.setInstanceDef(InstanceDefFactory.PROTOTYPE);
	}

}
