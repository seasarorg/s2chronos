/*
 * Copyright 2007-2009 the Seasar Foundation and the Others.
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
package org.seasar.chronos.extension.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.exception.CancellationRuntimeException;
import org.seasar.chronos.core.exception.InterruptedRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.framework.log.Logger;

@SuppressWarnings("serial")
public class S2ChronosServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(S2ChronosServlet.class);

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
			LOG.error("スケジューラの停止に失敗しました", ex);
		} finally {
			super.destroy();
		}
	}
}
