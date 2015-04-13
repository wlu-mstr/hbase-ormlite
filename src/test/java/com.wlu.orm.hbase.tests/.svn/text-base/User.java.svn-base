package com.wlu.orm.hbase.tests;

import java.util.List;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;

@DatabaseTable(tableName = "HBaseUser")
public class User {
	@DatabaseField(id = true)
	private String id;
	@DatabaseField(familyName = "family_profile")
	private Profile profile;
	@DatabaseField(familyName = "family_likepages")
	private LikePages likePages;
	@DatabaseField(familyName = "family_other", qualifierName = "AInt")
	private int aint;
	@DatabaseField(familyName = "family_alist", isQualiferList = true)
	private List<String> alist;

	public List<String> getAlist() {
		return alist;
	}

	public void setAlist(List<String> alist) {
		this.alist = alist;
	}

	private int bint;

	public User(String id, Profile profile, LikePages likePages, int aint) {
		super();
		this.id = id;
		this.profile = profile;
		this.likePages = likePages;
		this.aint = aint;
	}

	public User() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public LikePages getLikePages() {
		return likePages;
	}

	public void setLikePages(LikePages likePages) {
		this.likePages = likePages;
	}

	public int getAint() {
		return aint;
	}

	public void setAint(int aint) {
		this.aint = aint;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", profile=" + profile + ", likePages="
				+ likePages + ", aint=" + aint + ", alist=" + alist + ", bint="
				+ bint + "]";
	}

	
}
