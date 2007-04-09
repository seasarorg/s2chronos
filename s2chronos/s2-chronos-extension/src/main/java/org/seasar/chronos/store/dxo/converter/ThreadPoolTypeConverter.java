package org.seasar.chronos.store.dxo.converter;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.impl.AbstractConverter;

public class ThreadPoolTypeConverter extends AbstractConverter {

	public Object convert(Object source, Class destClass,
			ConversionContext context) {
		if (source == null) {
			return null;
		}
		if (source instanceof ThreadPoolType) {
			ThreadPoolType type = ThreadPoolType.class.cast(source);
			return type.ordinal();
		}
		return null;
	}

	public Class getDestClass() {
		return Integer.class;
	}

	public Class[] getSourceClasses() {
		return new Class[] { ThreadPoolType.class };
	}

}
