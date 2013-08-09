package com.wlu.orm.hbase.schema;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;
import com.wlu.orm.hbase.exceptions.HBaseOrmException;

/**
 * This is factory of DataMapper, each Type can has one DataMapperFactory and
 * the factory can create DataMapper according to the instance
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public class DataMapperFacory<T> {
	Log LOG = LogFactory.getLog(DataMapperFacory.class);
	public String tablename;
	// for schema
	public Map<Field, FamilyQualifierSchema> fixedSchema;
	// for data type
	public Map<Field, FieldDataType> fieldDataType;
	public Field rowkeyField;
	public Class<?> dataClass;

	public DataMapperFacory(Class<T> dataClass_) throws HBaseOrmException {
		dataClass = dataClass_;
		// set tablename
		setTableName();
		// set fixed schema
		fixedSchema = new HashMap<Field, FamilyQualifierSchema>();
		fieldDataType = new HashMap<Field, FieldDataType>();
		setFixedSchemaAndDataType();
	}

	public DataMapper<T> Create(T instance) throws HBaseOrmException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		// check type
		if (!instance.getClass().equals(dataClass)) {
			return null;
		}
		// a new DataMapper constructed with the fixed members
		DataMapper<T> dm = new DataMapper<T>(tablename, fixedSchema,
				fieldDataType, rowkeyField, dataClass);
		// 1. copy the fixed schema to datafieldToSchema. </br>
		// 2. fill value according to ... to Value of datafieldToSchema; </br>
		// notice:
		dm.CopyToDataFieldSchemaFromFixedSchema();
		dm.CopyToDataFieldsFromInstance(instance);
		return dm;
	}

	/**
	 * Create an empty DataMapper for the instance, uses can further:<br>
	 * <code>
	 * <li>SetRowKey</li><br>
	 * <li>SetFieldValue(FieldName, Object)</li><br>
	 * <li>SetFieldValue(FieldName, SubFieldName, Object)</li><br>
	 * </code>
	 * 
	 * @param instance
	 * @return
	 * @throws HBaseOrmException
	 */
	public DataMapper<T> CreateEmpty(T instance) throws HBaseOrmException {
		// check type
		if (!instance.getClass().equals(dataClass)) {
			return null;
		}
		// a new DataMapper constructed with the fixed members
		DataMapper<T> dm = new DataMapper<T>(tablename, fixedSchema,
				fieldDataType, rowkeyField, dataClass);

		dm.CopyToDataFieldSchemaFromFixedSchema();

		return dm;
	}

	public DataMapper<T> CreateEmpty(Class<?> clazz) throws HBaseOrmException {
		// check type
		if (!clazz.equals(dataClass)) {
			return null;
		}
		// a new DataMapper constructed with the fixed members
		DataMapper<T> dm = new DataMapper<T>(tablename, fixedSchema,
				fieldDataType, rowkeyField, dataClass);

		dm.CopyToDataFieldSchemaFromFixedSchema();

		return dm;
	}

	/**
	 * a helper method to return script to create the HBase table according to
	 * fixedSchema
	 * 
	 * @return Script to create create the table
	 */
	public String TableCreateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("create '");
		sb.append(tablename + "', ");
		for (Field field : fixedSchema.keySet()) {
			FamilyQualifierSchema sc = fixedSchema.get(field);
			String family = Bytes.toString(sc.getFamily());
			sb.append("{NAME => '" + family + "'},");
		}

		return sb.toString().substring(0, sb.length() - 1);
	}

	public HTableDescriptor TableCreateDescriptor() {

		HTableDescriptor td = new HTableDescriptor(Bytes.toBytes(tablename));
		for (Field field : fixedSchema.keySet()) {
			FamilyQualifierSchema sc = fixedSchema.get(field);
			td.addFamily(new HColumnDescriptor(sc.getFamily()));
		}

		return td;

	}

	/**
	 * if annotation is not set, use class name instead
	 */
	private void setTableName() {
		DatabaseTable databaseTable = (DatabaseTable) dataClass
				.getAnnotation(DatabaseTable.class);
		if (databaseTable == null || databaseTable.tableName().length() == 0) {
			LOG.warn("Table name is not specified as annotation, use class name instead");
			tablename = dataClass.getSimpleName();
		} else {
			tablename = databaseTable.tableName();
		}
	}

	/**
	 * <li>if annotation for a field is not set, the field is omitted. <br> <li>
	 * if is a rowkey; <br> <li>
	 * others
	 * 
	 * @throws HBaseOrmException
	 */
	private void setFixedSchemaAndDataType() throws HBaseOrmException {
		// TODO: maybe need to deal with inheritance scenario: dataClass's
		// super-class
		for (Field field : dataClass.getDeclaredFields()) {
			DatabaseField databaseField = field
					.getAnnotation(DatabaseField.class);
			if (databaseField == null) {
				// not included in database
				fieldDataType.put(field, new FieldDataType(FieldDataType.SKIP,
						null));
				continue;
			}
			if (databaseField.id()) {
				// set the field as id
				rowkeyField = field;
				continue;
			}

			FamilyQualifierSchema fqv = FQSchemaAndDataTypeBuildFromField(
					databaseField, field);

			fixedSchema.put(field, fqv);
		}
	}

	/**
	 * Set family, qualifier schema according to the field's annotation. <b>Side
	 * effect:</b> set field data type for each field and also for fields of
	 * class as family class (sub level class)
	 * 
	 * @param databaseField
	 * @param field
	 * @return
	 * @throws HBaseOrmException
	 */
	private FamilyQualifierSchema FQSchemaAndDataTypeBuildFromField(
			DatabaseField databaseField, Field field) throws HBaseOrmException {

		String family;
		String qualifier;
		Map<String, byte[]> subFieldToQualifier = null;
		// TODO
		// 1. primitive type or string
		if (field.getType().isPrimitive()
				|| field.getType().equals(String.class)) {
			if (databaseField.familyName().length() == 0) {
				throw new HBaseOrmException(
						"For primitive typed field "
								+ dataClass.getName()
								+ "."
								+ field.getName()
								+ " we must define family with annotation: familyName=\"familyname\".");
			} else {
				family = getDatabaseColumnName(databaseField.familyName(),
						field);
				qualifier = getDatabaseColumnName(
						databaseField.qualifierName(), field);
				fieldDataType.put(field, new FieldDataType(
						FieldDataType.PRIMITIVE, field.getType()));
			}
		} else if (field.getType().equals(List.class)
				|| databaseField.isQualiferList()) {
			// only set family, qualifier is ...
			family = getDatabaseColumnName(databaseField.familyName(), field);
			qualifier = null;
			if (databaseField.isQualiferList()) {
				LOG.warn("Field "
						+ field.getName()
						+ " is not 'List' (maybe a ArrayList??) but set as qualifierList. May be wrong when converted to 'List' ...");
			}
			fieldDataType.put(field, new FieldDataType(FieldDataType.LIST,
					field.getType()));
		}
		// Map,,
		else if (field.getType().equals(Map.class)
				|| databaseField.isQualifierValueMap()) {
			// only set family, qualifier is ...
			family = getDatabaseColumnName(databaseField.familyName(), field);
			qualifier = null;
			if (databaseField.isQualifierValueMap()) {
				LOG.warn("Field "
						+ field.getName()
						+ " is not 'Map' (maybe a HashMap??) but set as qualifierValueMap. May be wrong when converted to 'Map' ...");
			}
			fieldDataType.put(field,
					new FieldDataType(FieldDataType.MAP, field.getType()));
		}
		// others
		else {
			// non-primitive and not List
			family = getDatabaseColumnName(databaseField.familyName(), field);
			qualifier = null;

			// check whether is a sub level class as family
			DatabaseTable subdatabasetable = (DatabaseTable) field.getType()
					.getAnnotation(DatabaseTable.class);
			if (subdatabasetable != null && subdatabasetable.canBeFamily()) {
				// create a FieldDataType and later
				FieldDataType fieldDataTypeForFamilyClass = new FieldDataType(
						FieldDataType.SUBLEVELFAMCLASS, field.getType());
				fieldDataType.put(field, fieldDataTypeForFamilyClass);

				for (Field subfield : field.getType().getDeclaredFields()) {
					DatabaseField subdatabasefield = subfield
							.getAnnotation(DatabaseField.class);
					if (subdatabasefield == null) {
						fieldDataTypeForFamilyClass.addSubLevelDataType(
								subfield, new FieldDataType(FieldDataType.SKIP,
										null));
						continue;
					}
					byte[] subqualifiername;
					// 2012-7-12, wlu: if is list or map, skip this
					if (subdatabasefield.isQualiferList()) {
						fieldDataTypeForFamilyClass.addSubLevelDataType(
								subfield, new FieldDataType(FieldDataType.LIST,
										subfield.getType()));
						continue;
					} else if (subdatabasefield.isQualifierValueMap()) {
						fieldDataTypeForFamilyClass.addSubLevelDataType(
								subfield, new FieldDataType(FieldDataType.MAP,
										subfield.getType()));
						continue;
					}
					// field name as qualifier name. Whatever primitive or
					// String or UDF class, we treat is as PRIMITIVE
					else {
						fieldDataTypeForFamilyClass.addSubLevelDataType(
								subfield,
								new FieldDataType(FieldDataType.PRIMITIVE,
										subfield.getType()));
						subqualifiername = Bytes.toBytes(getDatabaseColumnName(
								subdatabasefield.qualifierName(), subfield));
					}
					// initial once
					if (subFieldToQualifier == null) {
						subFieldToQualifier = new HashMap<String, byte[]>();
					}
					subFieldToQualifier.put(subfield.getName(),
							subqualifiername);
				}
			}
		}

		// TODO
		FamilyQualifierSchema fqv = new FamilyQualifierSchema();

		fqv.setFamily(Bytes.toBytes(family));
		if (qualifier == null) {
			fqv.setQualifier(null);
		} else {
			fqv.setQualifier(Bytes.toBytes(qualifier));
		}
		fqv.setSubFieldToQualifier(subFieldToQualifier);

		return fqv;

	}

	private String getDatabaseColumnName(String string, Field field) {
		if (string.length() == 0) {
			LOG.info("Field "
					+ dataClass.getName()
					+ "."
					+ field.getName()
					+ " need to take care of ... field name is used as column name");
			return field.getName();
		}
		return string;
	}

}
