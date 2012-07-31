package com.wlu.orm.hbase.exceptions;

@SuppressWarnings("serial")
public class HBaseOrmException extends Exception{

	public HBaseOrmException(String msg){
		super(msg);
	}
}
