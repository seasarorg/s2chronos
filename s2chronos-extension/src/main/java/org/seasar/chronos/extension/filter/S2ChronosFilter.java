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
package org.seasar.chronos.extension.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.autodetector.TaskClassAutoDetector;
import org.seasar.chronos.core.model.schedule.TaskScheduleEntryManager;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class S2ChronosFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(S2ChronosFilter.class);

	protected TaskScheduleEntryManager taskScheduleEntryManager =
		TaskScheduleEntryManager.getInstance();

	@SuppressWarnings("serial")
	static class SessionPropertyCache implements Serializable {
		private final Map<String, Object> propertyCache =
			CollectionsUtil.newHashMap();

		private final Object selfKey;

		/**
		 * セルフキーを返します．
		 * 
		 * @return セルフキー
		 */
		public Object getSelfKey() {
			return selfKey;
		}

		/**
		 * コンストラクタです．
		 * 
		 * @param selfKey
		 *            セルフキー
		 */
		public SessionPropertyCache(Object key) {
			selfKey = key;
		}

		/**
		 * キーに応じたプロパティキャッシュを返します．
		 * 
		 * @param key
		 * @return
		 */
		public synchronized static SessionPropertyCache getInstance(
				HttpSession session, String key) {
			SessionPropertyCache propertyCacheInstance =
				(SessionPropertyCache) session.getAttribute(key);
			if (propertyCacheInstance == null) {
				propertyCacheInstance = new SessionPropertyCache(key);
				session.setAttribute(key, propertyCacheInstance);
			}
			return propertyCacheInstance;
		}

		/**
		 * キーに対応するプロパティを返します．
		 * 
		 * @param key
		 *            キー
		 * @return プロパティ
		 */
		public Object get(String key) {
			return this.propertyCache.get(key);
		}

		/**
		 * キーとプロパティを設定します．
		 * 
		 * @param key
		 *            キー
		 * @param value
		 *            プロパティ
		 */
		public void put(String key, Object value) {
			this.propertyCache.put(key, value);
		}

		/**
		 * @param key
		 * @return
		 */
		public boolean remove(String key) {
			return this.propertyCache.remove(key) == null ? false : true;
		}

		/**
		 * 
		 */
		public void clear() {
			this.propertyCache.clear();
		}
	}

	static class BeforeClassHandler implements ClassHandler {
		private final S2Container s2Container;

		private final HttpSession session;

		public BeforeClassHandler(S2Container s2Container, HttpSession session) {
			this.s2Container = s2Container;
			this.session = session;
		}

		public void processClass(String packageName, String shortClassName) {
			String name = ClassUtil.concatName(packageName, shortClassName);
			Class<?> clazz = ReflectionUtil.forNameNoException(name);
			if (clazz == null) {
				return;
			}
			Task taskAnnotaion = clazz.getAnnotation(Task.class);
			if (taskAnnotaion == null) {
				return;
			}
			Object task = s2Container.getComponent(clazz);
			BeanDesc bd = BeanDescFactory.getBeanDesc(clazz);
			SessionPropertyCache pc =
				SessionPropertyCache.getInstance(session, clazz.getName());
			for (int i = 0; i < bd.getPropertyDescSize(); i++) {
				PropertyDesc pd = bd.getPropertyDesc(i);
				if (pd.isWritable()) {
					LOG.debug("before : "
						+ pd.getPropertyName()
						+ ":"
						+ pd.getValue(task));
					Object value = pc.get(pd.getPropertyName());
					if (value != null) {
						pd.setValue(task, value);
					}
				}
			}
			// session.invalidate();
		}
	}

	static class AfterClassHandler implements ClassHandler {
		private final S2Container s2Container;

		private final HttpSession session;

		public AfterClassHandler(S2Container s2Container, HttpSession session) {
			this.s2Container = s2Container;
			this.session = session;
		}

		public void processClass(String packageName, String shortClassName) {
			String name = ClassUtil.concatName(packageName, shortClassName);
			Class<?> clazz = ReflectionUtil.forNameNoException(name);
			if (clazz == null) {
				return;
			}
			Task taskAnnotaion = clazz.getAnnotation(Task.class);
			if (taskAnnotaion == null) {
				return;
			}
			Object task = s2Container.getComponent(clazz);
			BeanDesc bd = BeanDescFactory.getBeanDesc(clazz);
			SessionPropertyCache pc =
				SessionPropertyCache.getInstance(session, clazz.getName());
			for (int i = 0; i < bd.getPropertyDescSize(); i++) {
				PropertyDesc pd = bd.getPropertyDesc(i);
				if (pd.isReadable()) {
					LOG.debug("after : "
						+ pd.getPropertyName()
						+ ":"
						+ pd.getValue(task));
					pc.put(pd.getPropertyName(), pd.getValue(task));
				}
			}
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		LOG.debug(">>>Chronos-Filter start");
		HttpSession session = null;
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hreq = (HttpServletRequest) request;
			session = hreq.getSession(false);
			if (session == null) {
				session = hreq.getSession(true);
			}
		}
		final S2Container s2Container =
			S2ContainerServlet.getContainer().getRoot();
		TaskClassAutoDetector detector = null;
		if (HotdeployUtil.isHotdeploy()) {
			detector =
				(TaskClassAutoDetector) s2Container
					.getComponent(TaskClassAutoDetector.class);
			detector.detect(new BeforeClassHandler(s2Container, session));
		}
		chain.doFilter(request, response);
		if (HotdeployUtil.isHotdeploy()) {
			detector.detect(new AfterClassHandler(s2Container, session));
		}
		LOG.debug("<<<Chronos-Filter end");
	}

	public void init(FilterConfig config) throws ServletException {
	}
}
