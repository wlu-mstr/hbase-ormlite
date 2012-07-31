package com.wlu.orm.hbase.tests;

import java.util.ArrayList;
import java.util.List;


import com.wlu.orm.hbase.connection.HBaseConnection;
import com.wlu.orm.hbase.dao.Dao;
import com.wlu.orm.hbase.dao.DaoImpl;
import com.wlu.orm.hbase.exceptions.HBaseOrmException;
import com.wlu.orm.hbase.schema.value.ValueFactory;

import junit.framework.TestCase;

public class ResumeTest extends TestCase {

	static HBaseConnection hbaseconnection = null;
	static Dao<Resume> dao = null;
	static {
		hbaseconnection = new HBaseConnection("wlu-hadoop01", "2181", 10);
		try {
			dao = new DaoImpl<Resume>(Resume.class, hbaseconnection);
		} catch (HBaseOrmException e) {
			e.printStackTrace();
		}
	}

	public void testCreateTable() {
		dao.CreateTableIfNotExist();
	}

	public void testInsert() {
		Resume_BasicInfo jacky_b = new Resume_BasicInfo();
		jacky_b.setSecond_Name("Jacky");
		jacky_b.setFirst_Name("Chen");
		jacky_b.setGender("Male");
		jacky_b.setData_of_Birth("1980-1-1");
		jacky_b.setEmail("jacky@sun.com");
		jacky_b.setResidency("DC");
		jacky_b.setYrs_of_Experience("5");

		Resume_Education jacky_e = new Resume_Education();
		jacky_e.setTime_period("2000-6");
		jacky_e.setSchool("Uta University");
		jacky_e.setDegree("Master");
		jacky_e.setMajor("Electronic Enginnering");

		Resume_WorkExperience jacky_w = new Resume_WorkExperience();
		jacky_w.setTime_period("2006-3");
		jacky_w.setCompany("Sun corp.");
		jacky_w.setJobTitle("Software Enginner");
		jacky_w.setDepartment("Data Service Team");
		jacky_w.setDescription("I worked here for about 6 years and know a lot about ...");

		Resume jacky = new Resume();
		jacky.setId("00000001234");
		jacky.setResume_title("Jacky's Personal Resume");
		jacky.setBasicInfo(jacky_b);
		jacky.setEducation(jacky_e);
		jacky.setWorkExperience(jacky_w);

		dao.Insert(jacky);

	}

	public void testQuery() {
		Resume jacky = dao.QueryById(ValueFactory.TypeCreate("00000001234"));
		System.out.println(jacky);
	}

	public void testUpdate() {
		Resume jacky = dao.QueryById(ValueFactory.TypeCreate("00000001234"));
		System.out.println(jacky);
		jacky.getBasicInfo().setTelephone_number("010045087");
		jacky.getBasicInfo()
				.setResidency("Wen er Road, #391, Hangzhou, China.");
		jacky.getWorkExperience().setJobTitle("Senior Software Enginner");
		List<String> fl = new ArrayList<String>();
		fl.add("basicInfo");
		fl.add("workExperience");

		dao.Update(jacky, fl);
		System.out.println(jacky);
	}
	
	public void testDelete(){
		dao.DeleteById(ValueFactory.TypeCreate("00000001234"));
	}
}
