package com.wlu.orm.hbase.tests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wlu.orm.hbase.connection.HBaseConnection;
import com.wlu.orm.hbase.exceptions.HBaseOrmException;
import com.wlu.orm.hbase.schema.DataMapper;
import com.wlu.orm.hbase.schema.DataMapperFacory;


public class DataMapperTest {

	HBaseConnection hbaseconnection;

    public DataMapperTest() throws IOException {
        hbaseconnection = new HBaseConnection();
    }

    public void testDataMapper() throws IOException {
		try {
			DataMapperFacory<User> UserMapperFactory = new DataMapperFacory<User>(
					User.class);

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

			DataMapper<User> dm1 = UserMapperFactory.Create(user);
			DataMapperFacory<Post> PostMapperFactory = new DataMapperFacory<Post>(
					Post.class);
			System.out.println(UserMapperFactory.TableCreateScript());
			DataMapper<Post> postmapper = PostMapperFactory.Create(new Post());
			System.out.println(PostMapperFactory.TableCreateScript());
			dm1.insert(hbaseconnection);

		} catch (HBaseOrmException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}
}
