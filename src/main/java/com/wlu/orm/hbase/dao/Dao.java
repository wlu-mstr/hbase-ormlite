package com.wlu.orm.hbase.dao;

import java.util.List;

import com.wlu.orm.hbase.schema.value.Value;

public interface Dao<T> {

	/**
	 * Create a HBase Table according to it's annotations. <br>
	 * If the table already exits, delete and then recreate.
	 * 
	 * @param clazz
	 */
	public void CreateTable();

	/**
	 * Create a HBase Table according to it's annotations. <br>
	 * If the table already exits, return.
	 * 
	 * @param clazz
	 */
	public void CreateTableIfNotExist();

	/**
	 * Insert one record (row) to HBase table
	 * 
	 * @param data
	 */
	public void Insert(T data);

	public void DeleteById(Value rowkey);

	/**
	 * delete the whole data from HBase. (delete the row with data's rowkey)
	 * 
	 * @param data
	 */
	public void DeleteById(T data);

	/**
	 * Specify field name and delete specific family:qualifier
	 * 
	 * @param data
	 * @param family
	 * @param qualifier
	 */
	public void Delete(T data, String familyFieldName, String qualifierFieldName);

	/**
	 * Specify field name and delete whole specific family
	 * 
	 * @param data
	 * @param family
	 * @param qualifier
	 */
	public void Delete(T data, String familyFieldName);

	/**
	 * Same as DeleteById(T data)
	 * 
	 * @param data
	 */
	public void Delete(T data);

	/**
	 * update the record in table according to data's id (rowkey). <br>
	 * We don't know the dirty part of the data compared to record in the table,
	 * even don't know whether the data has already exists in the table. So, we
	 * just <b>Insert</b> the data to the table...
	 * 
	 * @param data
	 */
	public void Update(T data);

	/**
	 * 
	 * @param data
	 * @param familyFieldName
	 */
	public void Update(T data, List<String> familyFieldName);


	public T QueryById(Value id);

	/**
	 * Query according to the filter. For filters, such as qualifier filters, we
	 * can only get data of THAT qualifier, not data of the whole row. We need
	 * to query for the second time and return the whole object if needed.
	 * 
	 * @param filter
	 * @param returnWholeObject
	 *            whether need to return the whole object
	 * @return
	 */
	public List<T> QueryWithFilter(String filter, boolean returnWholeObject);

	/**
	 * Set returnWholeObject as false for the function above
	 * 
	 * @param filter
	 * @return
	 */
	public List<T> QueryWithFilter(String filter);
}
