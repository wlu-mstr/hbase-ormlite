package com.wlu.orm.hbase.schema;

import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

public class FamilyQualifierSchema {
	private byte[] family = null;
	private byte[] qualifier = null;
	// this is only used as Schema for Delete/etc: from sub(family) class's
	// field's name to qualifier name
	private Map<String, byte[]> subFieldToQualifier = null;

	public byte[] getFamily() {
		return family;
	}

	public void setFamily(byte[] family) {
		this.family = family;
	}

	public byte[] getQualifier() {
		return qualifier;
	}

	public void setQualifier(byte[] qualifier) {
		this.qualifier = qualifier;
	}

	public Map<String, byte[]> getSubFieldToQualifier() {
		return subFieldToQualifier;
	}
	
	public void setSubFieldToQualifier(Map<String, byte[]> subFieldToQualifier) {
		this.subFieldToQualifier = subFieldToQualifier;
	}

	public String toString() {
		String string = "Family:\t";
		string += Bytes.toString(family) + "\n";
		if (qualifier != null) {
			string += "Qualifier:\t";
			string += Bytes.toString(qualifier) + "\n";
		}
		if (subFieldToQualifier != null) {
			string += "SubQualifiers:\t";
			for (String s : subFieldToQualifier.keySet()) {
				string += "Field: " + s + " -> Qualifier: "
						+ Bytes.toString(subFieldToQualifier.get(s)) + "\n";
			}
		}
		return string;
	}

}
