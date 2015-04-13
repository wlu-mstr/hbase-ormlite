package com.wlu.orm.hbase.tests;

import com.wlu.orm.hbase.annotation.DatabaseField;
import com.wlu.orm.hbase.annotation.DatabaseTable;

@DatabaseTable(canBeFamily = true)
public class Resume_BasicInfo {
	@DatabaseField()
	String First_Name;
	@DatabaseField()
	String Second_Name;
	@DatabaseField()
	String Data_of_Birth;
	@DatabaseField()
	String Gender;
	@DatabaseField()
	String Residency;
	@DatabaseField()
	String ID_number;
	@DatabaseField()
	String Email;
	@DatabaseField()
	String Yrs_of_Experience;
	@DatabaseField()
	String Telephone_number;

	public Resume_BasicInfo() {
	}

	public String getFirst_Name() {
		return First_Name;
	}

	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}

	public String getSecond_Name() {
		return Second_Name;
	}

	public void setSecond_Name(String second_Name) {
		Second_Name = second_Name;
	}

	public String getData_of_Birth() {
		return Data_of_Birth;
	}

	public void setData_of_Birth(String data_of_Birth) {
		Data_of_Birth = data_of_Birth;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getResidency() {
		return Residency;
	}

	public void setResidency(String residency) {
		Residency = residency;
	}

	public String getID_number() {
		return ID_number;
	}

	public void setID_number(String iD_number) {
		ID_number = iD_number;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getYrs_of_Experience() {
		return Yrs_of_Experience;
	}

	public void setYrs_of_Experience(String yrs_of_Experience) {
		Yrs_of_Experience = yrs_of_Experience;
	}

	public String getTelephone_number() {
		return Telephone_number;
	}

	public void setTelephone_number(String telephone_number) {
		Telephone_number = telephone_number;
	}

	@Override
	public String toString() {
		return "Resume_BasicInfo [First_Name=" + First_Name + ", Second_Name="
				+ Second_Name + ", Data_of_Birth=" + Data_of_Birth
				+ ", Gender=" + Gender + ", Residency=" + Residency
				+ ", ID_number=" + ID_number + ", Email=" + Email
				+ ", Yrs_of_Experience=" + Yrs_of_Experience
				+ ", Telephone_number=" + Telephone_number + "]";
	}

}
