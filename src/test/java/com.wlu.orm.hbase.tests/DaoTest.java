package com.wlu.orm.hbase.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wlu.orm.hbase.connection.HBaseConnection;
import com.wlu.orm.hbase.dao.Dao;
import com.wlu.orm.hbase.dao.DaoImpl;
import com.wlu.orm.hbase.exceptions.HBaseOrmException;
import com.wlu.orm.hbase.schema.value.ValueFactory;


public class DaoTest {

	public void testDao() throws HBaseOrmException {
		// HBaseConnection hbaseconnection = new HBaseConnection(
		// "hadoop008,hadoop009,hadoop010,hadoop006,hadoop007", "2181", 10);
		HBaseConnection hbaseconnection = new HBaseConnection("wlu-hadoop01",
				"2181", 10);

		Profile p = new Profile("jacky", "14", "Hangzhou, Wen 2 road, #391");
		HashMap<String, String> mp1 = new HashMap<String, String>();
		Map<String, PageContents> mp3 = new HashMap<String, PageContents>();
		List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			mp1.put(String.format("id1%06d", i), "this is page " + i);
			list2.add(String.format("id2%06d", i));
			mp3.put(String.format("id3%06d", i), new PageContents(
					"this is page <type 3> " + i));
		}

		LikePages lp = new LikePages(mp1, list2, mp3);

		User user = new User("1234", p, lp, 8080);
		List<String> userList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			userList.add(String.format("qualifer-%04d", i));
		}
		user.setAlist(userList);

		Dao<User> dao = new DaoImpl<User>(User.class, hbaseconnection);
		dao.CreateTableIfNotExist();
		dao.Insert(user);
		// Delete user's name
		// dao.Delete(user, "profile", "name");
		// Delete a page
		// dao.Delete(user, "likePages", "id1000000");
		// p = new Profile("hellen", "20", "Beijing, Chaoyang #1");
		// user = new User("001", p, null, 8080);
		// dao.Insert(user);

		// dao.Delete(user, "profile", "address");

		@SuppressWarnings("unused")
		User userq = dao.QueryById(ValueFactory.TypeCreate("1234"));
		System.out.println(userq);

		userq.getProfile().setName("Tomy");
		List<String> fl = new ArrayList<String>();
		fl.add("profile");
		dao.Update(userq, fl);
		userq = dao.QueryById(ValueFactory.TypeCreate("1234"));
		System.out.println(userq);
		// try {
		//
		// dao.Delete(user, "profile", "aint");
		// } catch (Exception e) {
		// assertTrue(e.getMessage().contains("is not set as qualifier"));
		// }
	}
}
