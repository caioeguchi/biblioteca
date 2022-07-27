package com.caionilson.core.tema8.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.caionilson.core.tema8.model.User;

public class UserDAO {

	private List<User> users;

	public UserDAO() {
		users = new ArrayList<>();
	}

	public void addUser(User user) {
		users.add(user);
		writeFile();
	}

	public List<User> getUserList() {
		return users;
	}

	public User getUserId(int userId) {
		for (User user : users) {
			if (user.getUserId() == userId) {
				return user;
			}
		}

		return null;
	}

	public void writeFile() {
		try (FileWriter fileWriter = new FileWriter("user.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

			bufferedWriter.write("\nID - Nome - Livros alugados - Total de livros alugados\n");

			for (User user : users) {
				bufferedWriter.write("\n" + 
						user.getUserId() + " - " + 
						user.getUserName() + " - " +
						user.getUserLoan() + " - " +
						user.getUserLoanTotal( )+ "\n");
			}
			bufferedWriter.close();

		} catch (IOException io) {
			System.out.println("Arquivo não encontrado");
		}
	}

	public List<User> readFile() {
		try {
			FileReader fileReader = new FileReader("user.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String currentLine = bufferedReader.readLine();
			currentLine = bufferedReader.readLine();

			while (currentLine != null) {
				String[] userAttributes = currentLine.split("\\s-\\s");
				String userName = userAttributes[1];
				int userLoan = Integer.parseInt(userAttributes[2]);
				int userLoanTotal = Integer.parseInt(userAttributes[3]);

				User user = new User(userName, userLoan, userLoanTotal);
				users.add(user);

				currentLine = bufferedReader.readLine();
			}

			bufferedReader.close();
		} catch (IOException io) {
			System.out.println("Arquivo não encontrado");
		}

		return users;
	}
}
