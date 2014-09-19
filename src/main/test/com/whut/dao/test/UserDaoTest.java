package com.whut.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.whut.dao.impl.UserDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/conf/application-*.xml"})
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void testLoad() {
		/*userDao.findBy("username", "ed");
		System.out.println("finished");*/
	}
}
