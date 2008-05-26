package org.seasar.chronos.extension.persitense;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SerializableObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String className;

	private Object target;

	public static SerializableObject create(String className, Object target) {
		return new SerializableObject(className, target);
	}

	public SerializableObject() {

	}

	public SerializableObject(String className, Object target) {
		this.className = className;
		this.target = target;
	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(className);
		stream.writeObject(createFieldMap());
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {
		try {
			className = String.class.cast(stream.readObject());
			Class sourceClazz = Class.forName(className);
			Class clazz = SerializeFactory.createProxy(sourceClazz);
			target = clazz.newInstance();
			Map<String, Object> fieldMap = Map.class.cast(stream.readObject());
			restoreFields(fieldMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object readResolve() throws ObjectStreamException {
		return target;
	}

	private Map createFieldMap() {
		try {
			Map<String, Object> fieldMap = new HashMap<String, Object>();
			Class<? extends Object> clazz = target.getClass();
			while (clazz != Object.class) {
				Field[] fields = clazz.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (!fields[i].isAccessible()) {
						fields[i].setAccessible(true);
					}
					Object value = fields[i].get(target);
					if (value != null) {
						fieldMap.put(fields[i].getName(), value);
					}
				}
				clazz = clazz.getSuperclass();
			}
			return fieldMap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void restoreFields(Map<String, Object> fieldMap) {
		try {
			Class clazz = target.getClass();
			while (clazz != Object.class) {
				Field[] fields = clazz.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (!fields[i].isAccessible()) {
						fields[i].setAccessible(true);
					}
					Object value = fieldMap.get(fields[i].getName());
					if (value instanceof SerializableObject) { // ポイント！！
						value = SerializableObject.class.cast(value)
								.readResolve();
					}
					fields[i].set(target, value);
				}
				clazz = clazz.getSuperclass();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}

	public Object getTarget() {
		return target;
	}

}
