package com.wlu.orm.hbase.dao;

import java.io.IOException;
import java.util.List;

import com.wlu.orm.hbase.exceptions.HBaseOrmException;
import com.wlu.orm.hbase.schema.value.Value;

public interface Dao<T> {

	/**
	 * Create a HBase Table according to it's annotations. <br>
	 * If the table already exits, delete and then recreate.
	 * 
	 */
	public void createTable() throws IOException;

	/**
	 * Create a HBase Table according to it's annotations. <br>
	 * If the table already exits, return.
	 * 
	 */
	public void createTableIfNotExist() throws IOException;

	/**
	 * Insert one record (row) to HBase table
	 * 
	 * @param data
	 */
	public void insert(T data) throws HBaseOrmException;

	public void deleteById(Value rowkey) throws HBaseOrmException;

	/**
	 * delete the whole data from HBase. (delete the row with data's rowkey)
	 * 
	 * @param data
	 */
	public void deleteById(T data) throws HBaseOrmException;

	/**
	 * Specify field name and delete specific family:qualifier
	 * 
	 */
	public void delete(T data, String familyFieldName, String qualifierFieldName) throws HBaseOrmException;

	/**
	 * Specify field name and delete whole specific family
	 */
	public void delete(T data, String familyFieldName) throws HBaseOrmException;

	/**
	 * Same as DeleteById(T data)
	 * 
	 * @param data
	 */
	public void delete(T data) throws HBaseOrmException;

	/**
	 * update the record in table according to data's id (rowkey). <br>
	 * We don't know the dirty part of the data compared to record in the table,
	 * even don't know whether the data has already exists in the table. So, we
	 * just <b>Insert</b> the data to the table...
	 * 
	 * @param data
	 */
	public void update(T data) throws HBaseOrmException;

	/**
	 * 
	 * @param data
	 * @param familyFieldName
	 */
	public void update(T data, List<String> familyFieldName) throws HBaseOrmException;


	public T queryById(Value id) throws HBaseOrmException;

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
	public List<T> queryWithFilter(String filter, boolean returnWholeObject);

	/**
	 * Set returnWholeObject as false for the function above
	 * 
	 * @param filter
	 * @return
	 */
	public List<T> queryWithFilter(String filter);
}
