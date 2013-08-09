package com.wlu.orm.hbase.schema.value;

import org.apache.hadoop.hbase.util.Bytes;

public class StringValue implements Value {

	private String stringValue;

	public StringValue(String stringValue) {
		super();
		this.stringValue = stringValue;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	@Override
	public byte[] toBytes() {
		return Bytes.toBytes(stringValue);
	}

	@Override
	public String getType() {
		return "String Value";
	}

}
