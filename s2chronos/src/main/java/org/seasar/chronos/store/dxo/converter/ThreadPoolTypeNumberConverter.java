package org.seasar.chronos.store.dxo.converter;

import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.impl.IntegerConverter;

public class ThreadPoolTypeNumberConverter extends IntegerConverter {

	public Object convert(Object source, Class destClass,
			ConversionContext context) {
		if (source == null) {
			return null;
		}
		if (source.getClass().isEnum()) {
			Enum type = Enum.class.cast(source);
			return type.ordinal();
		}
		return super.convert(source, destClass, context);
	}

}
