package com.wlu.orm.hbase.schema.value;

/**
 * Used by schema module 
 * @author wlu
 *
 */
public interface Value {
	public byte[] toBytes();
	
	public String getType();

}
