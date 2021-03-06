/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.chronos.teeda.example.web.add;

import org.seasar.chronos.teeda.example.dto.UserAuthDto;
import org.seasar.teeda.extension.annotation.validator.GreaterThanConstant;

public class AddPage {

	public UserAuthDto userAuthDto;

	@GreaterThanConstant
	public int arg1;

	@GreaterThanConstant
	public int arg2;

	public int result;

	public Class<?> initialize() {
		System.out.println("add init");
		return null;
	}

	public Class<?> prerender() {
		System.out.println("add prerender");
		return null;
	}

	public String doOnceCalculate() {
		result = arg1 + arg2;
		return null;
	}

	public boolean isArg1Disabled() {
		return false;
	}

}