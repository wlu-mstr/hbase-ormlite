package com.wlu.orm.hbase.tests;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;

@DatabaseTable(tableName = "HBasePost")
public class Post {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField(familyName = "family")
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	} 
	
	public Post(){
		
	}

}
