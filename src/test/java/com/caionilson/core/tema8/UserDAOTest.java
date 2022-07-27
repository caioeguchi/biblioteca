package com.caionilson.core.tema8;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.caionilson.core.tema8.dao.UserDAO;
import com.caionilson.core.tema8.model.User;

public class UserDAOTest {
	UserDAO userDAO = new UserDAO();

	@Test
	public void isAddingUsersTest() {
		String name = "name";

		User user = new User(name);

		UserDAO userDAO = new UserDAO();
		userDAO.addUser(user);

		Assert.assertTrue(userDAO.getUserList().contains(userDAO.getUserList().get(0)));
	}

	@Test
	public void listAllUsersTest() {
		String name1 = "name1";
		String name2 = "name2";
		String name3 = "name3";

		User user1 = new User(name1);
		User user2 = new User(name2);
		User user3 = new User(name3);

		userDAO.addUser(user1);
		userDAO.addUser(user2);
		userDAO.addUser(user3);

		List<User> userList = userDAO.getUserList();

		ArrayList<User> expected = new ArrayList<>();
		expected.add(new User("name1"));
		expected.add(new User("name2"));
		expected.add(new User("name3"));

		Assert.assertEquals(expected.get(0).getUserName(), userList.get(0).getUserName());
		Assert.assertEquals(expected.get(1).getUserName(), userList.get(1).getUserName());
		Assert.assertEquals(expected.get(2).getUserName(), userList.get(2).getUserName());
	}

	@Test
	public void isGettingUserId() {
		User user = new User(1, "name");

		userDAO.addUser(user);

		Assert.assertEquals(userDAO.getUserList().get(0), userDAO.getUserId(1));
	}

}
