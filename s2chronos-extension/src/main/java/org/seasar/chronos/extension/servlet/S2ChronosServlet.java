package org.seasar.chronos.extension.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.exception.CancellationRuntimeException;
import org.seasar.chronos.core.exception.InterruptedRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.framework.log.Logger;

public class S2ChronosServlet extends HttpServlet {

	private static final long serialVersionUID = -3292027493330778721L;

	private static Logger log = Logger.getLogger(S2ChronosServlet.class);

	private Scheduler scheduler;

	@Override
	public void init() throws ServletException {
		super.init();
		S2Container s2Container = S2ContainerServlet.getContainer();
		this.scheduler = (Scheduler) s2Container.getComponent(Scheduler.class);
		this.scheduler.start();
	}

	@Override
	public void destroy() {
		try {
			this.scheduler.shutdown();
			this.scheduler.join();
		} catch (InterruptedRuntimeException ex) {
			;
		} catch (CancellationRuntimeException ex) {
			;
		} catch (Exception ex) {
			log.error("スケジューラの停止に失敗しました", ex);
		} finally {
			super.destroy();
		}
	}

}
