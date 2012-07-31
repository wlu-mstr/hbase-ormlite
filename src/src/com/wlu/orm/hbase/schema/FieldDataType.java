package com.wlu.orm.hbase.schema;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldDataType {

	// both in table class and sub-level family class
	public static final int SKIP = -1;
	public static final int PRIMITIVE = 0;
	public static final int LIST = 1;
	public static final int MAP = 2;
	// only in table class
	public static final int SUBLEVELFAMCLASS = 3;

	// specific type
	public int datatype;
	public Class<?> dataclass;
	// used only for sub level class
	public Map<Field, FieldDataType> SubLevelDataTypes = null;

	public FieldDataType(int idatatype, Class<?> idataclass) {
		datatype = idatatype;
		dataclass = idataclass;
	}

	public boolean isSkip() {
		return datatype == SKIP;
	}

	public boolean isPrimitive() {
		return datatype == PRIMITIVE;
	}

	public boolean isList() {
		return datatype == LIST;
	}

	public boolean isMap() {
		return datatype == MAP;
	}

	public boolean isSubLevelClass() {
		return datatype == SUBLEVELFAMCLASS;
	}

	public void addSubLevelDataType(Field field, FieldDataType fieldDatatype) {
		if (SubLevelDataTypes == null) {
			SubLevelDataTypes = new HashMap<Field, FieldDataType>();
		}
		SubLevelDataTypes.put(field, fieldDatatype);
	}

	public FieldDataType getSubLevelDataType(Field field) {
		return SubLevelDataTypes.get(field);
	}

	public String toString() {
		String string = "";
		switch (datatype) {
		case -1:
			string += "Skip. \t " + dataclass;
			break;
		case 0:
			string += "Primitive. \t " + dataclass;
			break;
		case 1:
			string += "List. \t " + dataclass;
			break;
		case 2:
			string += "Map. \t " + dataclass;
			break;
		case 3:
			string += "Sub level class. \t " + dataclass + ": \n";
			if (SubLevelDataTypes != null) {
				for (Field field : SubLevelDataTypes.keySet()) {
					string += field.getName() + " -> "
							+ SubLevelDataTypes.get(field) + "\n\t\t";
				}
			}
			break;
		}
		return string+"\n";
	}
}
