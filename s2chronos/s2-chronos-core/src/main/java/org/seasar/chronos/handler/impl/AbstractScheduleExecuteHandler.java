package org.seasar.chronos.handler.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.seasar.chronos.event.SchedulerEventHandler;
import org.seasar.chronos.handler.ScheduleExecuteHandler;
import org.seasar.chronos.impl.TaskContenaStateManager;
import org.seasar.chronos.logger.Logger;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public abstract class AbstractScheduleExecuteHandler implements
		ScheduleExecuteHandler {

	protected static Logger log = Logger
			.getLogger(AbstractScheduleExecuteHandler.class);

	protected TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

	protected ExecutorService executorService;

	protected SchedulerEventHandler schedulerEventHandler;

	@Binding(bindingType = BindingType.NONE)
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	protected AtomicBoolean pause;

	protected AtomicBoolean paused;

	public void setPause(AtomicBoolean pause) {
		this.pause = pause;
	}

	public abstract void handleRequest() throws InterruptedException;

	public void setSchedulerEventHandler(
			SchedulerEventHandler schedulerEventHandler) {
		this.schedulerEventHandler = schedulerEventHandler;
	}

	public void setPaused(AtomicBoolean paused) {
		this.paused = paused;
	}

}
