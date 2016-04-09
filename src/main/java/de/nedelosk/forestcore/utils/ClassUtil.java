package de.nedelosk.forestcore.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ClassUtil {

	private static final Map<Class<?>, Class<?>> primitiveClassMap = new HashMap<Class<?>, Class<?>>();

	static {
		primitiveClassMap.put(Boolean.TYPE, Boolean.class);
		primitiveClassMap.put(Byte.TYPE, Byte.class);
		primitiveClassMap.put(Character.TYPE, Character.class);
		primitiveClassMap.put(Short.TYPE, Short.class);
		primitiveClassMap.put(Integer.TYPE, Integer.class);
		primitiveClassMap.put(Long.TYPE, Long.class);
		primitiveClassMap.put(Double.TYPE, Double.class);
		primitiveClassMap.put(Float.TYPE, Float.class);
		primitiveClassMap.put(Void.TYPE, Void.TYPE);
	}

	public static Class<?> primitiveToClass(final Class<?> cls) {
		Class<?> convertedClass = cls;
		if (cls != null && cls.isPrimitive()) {
			convertedClass = primitiveClassMap.get(cls);
		}
		return convertedClass;
	}

	public static Class<?> classToPrimitive(final Class<?> cls) {
		Class<?> convertedClass = cls;
		if (cls != null) {
			for(Entry<Class<?>, Class<?>> entry : primitiveClassMap.entrySet()) {
				if (entry.getValue() == cls) {
					convertedClass = entry.getKey();
				}
			}
		}
		return convertedClass;
	}
}
