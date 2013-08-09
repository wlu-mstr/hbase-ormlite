package com.wlu.orm.hbase.tests;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;

@DatabaseTable(canBeFamily = true)
public class Resume_WorkExperience {
	@DatabaseField()
	String Time_period;
	@DatabaseField()
	String Company;
	@DatabaseField()
	String Department;
	@DatabaseField()
	String JobTitle;
	@DatabaseField()
	String Description;

	public String getTime_period() {
		return Time_period;
	}

	public void setTime_period(String time_period) {
		Time_period = time_period;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Resume_WorkExperience() {
		super();
	}

	@Override
	public String toString() {
		return "Resume_WorkExperience [Time_period=" + Time_period
				+ ", Company=" + Company + ", Department=" + Department
				+ ", JobTitle=" + JobTitle + ", Description=" + Description
				+ "]";
	}
	
	

}
