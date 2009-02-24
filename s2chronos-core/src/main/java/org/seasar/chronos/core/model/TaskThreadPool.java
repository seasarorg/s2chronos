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
package org.seasar.chronos.core.model;

import java.io.Serializable;

/**
 * スレッドプールのインターフェイスです．
 * 
 * @author j5ik2o
 */
public interface TaskThreadPool extends Serializable {
	/**
	 * スレッドプールIDを返します．
	 * 
	 * @return スレッドプールID
	 */
	public Long getThreadPoolId();

	/**
	 * スレッドプールサイズを返します．
	 * 
	 * @return スレッドプールサイズ
	 */
	public Integer getThreadPoolSize();

	/**
	 * スレッドプールタイプを返します．
	 * 
	 * @return {@link ThreadPoolType}
	 */
	public ThreadPoolType getThreadPoolType();

	/**
	 * スレッドプールIDを設定します．
	 * 
	 * @param id
	 *            　スレッドプールID
	 */
	public void setThreadPoolId(Long id);

	/**
	 * スレッドプールサイズを設定します．
	 * 
	 * @param threadPoolSize
	 *            スレッドプールサイズ
	 */
	public void setThreadPoolSize(Integer threadPoolSize);

	/**
	 * スレッドプールタイプを設定します．
	 * 
	 * @param threadPoolType
	 *            {@link ThreadPoolType}
	 */
	public void setThreadPoolType(ThreadPoolType threadPoolType);
}
