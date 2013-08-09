package com.wlu.orm.hbase.schema.value;

import org.apache.hadoop.hbase.util.Bytes;

public class DoubleValue implements Value {

	private double doubleValue;

	public DoubleValue(double doubleValue) {
		super();
		this.doubleValue = doubleValue;
	}

	

	public double getIntValue() {
		return doubleValue;
	}



	public void setIntValue(double intValue) {
		this.doubleValue = intValue;
	}



	@Override
	public byte[] toBytes() {
		return Bytes.toBytes(doubleValue);
	}

	@Override
	public String getType() {
		return "Double Value";
	}

}
