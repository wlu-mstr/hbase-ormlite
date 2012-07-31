package com.wlu.orm.hbase.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;

@DatabaseTable(canBeFamily = true)
public class LikePages {
	@DatabaseField(isQualifierValueMap = true)
	private HashMap<String, String> pages1;
	@DatabaseField(isQualiferList = true)
	private List<String> pages2;
	@DatabaseField(isQualifierValueMap = true)
	private Map<String, PageContents> pages3;

	public LikePages(HashMap<String, String> pages1, List<String> pages2,
			Map<String, PageContents> pages3) {
		super();
		this.pages1 = pages1;
		this.pages2 = pages2;
		this.pages3 = pages3;
	}
	
	public LikePages(){
		
	}

	public HashMap<String, String> getPages1() {
		return pages1;
	}

	public void setPages1(HashMap<String, String> pages1) {
		this.pages1 = pages1;
	}

	public List<String> getPages2() {
		return pages2;
	}

	public void setPages2(List<String> pages2) {
		this.pages2 = pages2;
	}

	public Map<String, PageContents> getPages3() {
		return pages3;
	}

	public void setPages3(Map<String, PageContents> pages3) {
		this.pages3 = pages3;
	}

}

class PageContents {
	String content;

	public PageContents(String s) {
		content = s;
	}

	public String toString() {
		return "Content of the page is: " + content;
	}
}