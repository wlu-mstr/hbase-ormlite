package com.wlu.orm.hbase.tests;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;

@DatabaseTable(canBeFamily = true)
public class Profile {
	@DatabaseField(qualifierName = "qualifier_name")
	private String name;
	@DatabaseField()
	private String age;
	@DatabaseField()
	private String address;
	
	private int aint;

	public Profile(){
		
	}
	
	public Profile(String name, String age, String address) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Profile [name=" + name + ", age=" + age + ", address="
				+ address + ", aint=" + aint + "]";
	}
	
	

}