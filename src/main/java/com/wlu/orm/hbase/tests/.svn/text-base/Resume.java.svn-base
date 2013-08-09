package com.wlu.orm.hbase.tests;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;

@DatabaseTable(tableName = "ResumeArchive")
public class Resume {
	@DatabaseField(id = true)
	String id;
	@DatabaseField(familyName = "resume_title", qualifierName = "resume_title")
	String resume_title;
	@DatabaseField(familyName = "user_basic_information")
	Resume_BasicInfo basicInfo;
	@DatabaseField(familyName = "user_education")
	Resume_Education education;
	@DatabaseField(familyName = "user_work_experience")
	Resume_WorkExperience workExperience;

	public Resume() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResume_title() {
		return resume_title;
	}

	public void setResume_title(String resume_title) {
		this.resume_title = resume_title;
	}

	public Resume_BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(Resume_BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public Resume_Education getEducation() {
		return education;
	}

	public void setEducation(Resume_Education education) {
		this.education = education;
	}

	public Resume_WorkExperience getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(Resume_WorkExperience workExperience) {
		this.workExperience = workExperience;
	}

	@Override
	public String toString() {
		return "Resume [id=" + id + "\n resume_title=" + resume_title
				+ "\n basicInfo=" + basicInfo + "\n education=" + education
				+ "\n workExperience=" + workExperience + "]";
	}

}
