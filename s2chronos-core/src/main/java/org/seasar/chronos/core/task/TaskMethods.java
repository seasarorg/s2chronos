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
package org.seasar.chronos.core.task;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public interface TaskMethods {

	public void setGetterSignal(Object getterSignal);

	public void hotdeployStart();

	public void hotdeployStop();

	public void prepare();

	public void unprepare();

	public void initialize() throws InterruptedException;

	public String start() throws InterruptedException;

	public void execute(String startTaskName) throws InterruptedException;

	public void waitOne() throws InterruptedException;

	public boolean cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public String finish() throws InterruptedException;

	public void destroy() throws InterruptedException;

	public void setComponentDef(ComponentDef componentDef);

	public TaskPropertyReader getTaskPropertyReader();

	public TaskPropertyWriter getTaskPropertyWriter();

	public void catchException(Exception exception);
}
