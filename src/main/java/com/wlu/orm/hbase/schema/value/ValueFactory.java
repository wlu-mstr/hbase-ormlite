package com.wlu.orm.hbase.schema.value;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.hadoop.hbase.util.Bytes;

import com.wlu.orm.hbase.util.util;

//import test.testFields;

public class ValueFactory {

	static Class<Integer> INTEGER = Integer.class;
	static Class<Double> DOUBLE = Double.class;
	static Class<Float> FLOAT = Float.class;
	static Class<String> STRING = String.class;

	public static <T> Value Create(T instance) {

		if (instance == null) {
			return new NullValue();
		}

		Class<? extends Object> instanceClass = instance.getClass();
		if (instanceClass.equals(INTEGER)) {
			return new IntValue((Integer) instance);
		} else if (instanceClass.equals(DOUBLE)) {
			return new DoubleValue((Double) instance);
		} else if (instanceClass.equals(FLOAT)) {
			return new FloatValue((Float) instance);
		} else if (instanceClass.equals(STRING)) {
			return new StringValue((String) instance);
		} else {
			return new StringValue((String) instance.toString());
		}
		// return null;
	}

	/**
	 * Create a Object with type clazz from byte[]
	 * 
	 * @param clazz
	 * @param bytes
	 * @return
	 */
	public static Object CreateObject(Class<?> clazz, byte[] bytes) {
		if (clazz.equals(int.class)) {
			return new Integer(Bytes.toInt(bytes));
		} else if (clazz.equals(double.class)) {
			return new Double(Bytes.toDouble(bytes));
		} else if (clazz.equals(float.class)) {
			return new Float(Bytes.toFloat(bytes));
		} else if (clazz.equals(INTEGER)) {
			return new Integer(Bytes.toInt(bytes));
		} else if (clazz.equals(DOUBLE)) {
			return new Double(Bytes.toDouble(bytes));
		} else if (clazz.equals(FLOAT)) {
			return new Float(Bytes.toFloat(bytes));
		} else if (clazz.equals(STRING)) {
			return new String(Bytes.toString(bytes));
		} else {
			return null;
		}
	}

	/*
	 * used when directly create a Value
	 */
	public static Value TypeCreate(String value) {
		return new StringValue(value);
	}

	// TODO: add more

	class st {
		String name;
		String age;

		public st() {
			name = "jack";
			age = "234";
		}
	}

	public static void main(String args[]) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
//		testFields tf = new testFields("abc", 1, 1.0f, 2d);
//		for (Field field : tf.getClass().getDeclaredFields()) {
//
//			Value v = Create(util.GetFromField(tf, field));
//			if (v == null) {
//				continue;
//			}
//			System.out.println(v.getType());
//		}

	}
}
