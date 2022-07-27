package com.caionilson.core.tema8.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.caionilson.core.tema8.model.*;

public class LoanDAO {
	private List<Loan> loans;

	public LoanDAO() {
		loans = new ArrayList<>();
	}

	public void addLoan(User user, Book book, LocalDate loanDate, LocalDate returnDate) {
		Loan loan = new Loan(user, book, loanDate, returnDate);
		loans.add(loan);
		writeFile();
	}

	public Loan getLoanBookId(int bookId) {
		for (Loan loan : loans) {
			if (loan.getBook().getBookId() == bookId) {
				return loan;
			}
		}
		return null;
	}

	public List<Loan> getLoanList() {
		return loans;
	}

	public void removeLoan(Loan loanRemoved) {
		loans.remove(loanRemoved);
	}

	public void writeFile() {
		try (FileWriter fileWriter = new FileWriter("loans.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

			bufferedWriter.write("\nBook ID - User ID - Loan date - Return date\n");

			for (Loan loan : loans) {
				bufferedWriter.write("\n" + loan.getBook().getBookId() + " - " + loan.getUser().getUserId() + " - "
						+ loan.getLoanDate().getDayOfMonth() + "/" + loan.getLoanDate().getMonthValue() + "/"
						+ loan.getLoanDate().getYear() + " - " + loan.getReturnDate().getDayOfMonth() + "/"
						+ loan.getReturnDate().getMonthValue() + "/" + loan.getReturnDate().getYear());
			}
			bufferedWriter.close();
			fileWriter.close();

		} catch (IOException io) {
			System.out.println("Arquivo não encontrado");
		}
	}

	public List<Loan> readFile() {
		BookDAO books = new BookDAO();
		books.readFile();

		UserDAO users = new UserDAO();
		users.readFile();

		try {
			FileReader fileReader = new FileReader("loans.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String currentLine = bufferedReader.readLine();
			currentLine = bufferedReader.readLine();

			while (currentLine != null) {
				String[] loanAttributes = currentLine.split("\\s-\\s");
				Book book = books.getBookId(Integer.parseInt(loanAttributes[0]));
				User user = users.getUserId(Integer.parseInt(loanAttributes[1]));
				DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String lDate = loanAttributes[2];
				LocalDate loanDate = LocalDate.parse(lDate, date);
				String rDate = loanAttributes[3];
				LocalDate returnDate = LocalDate.parse(rDate, date);

				Loan loan = new Loan(user, book, loanDate, returnDate);
				loans.add(loan);

				currentLine = bufferedReader.readLine();
			}

			bufferedReader.close();
		} catch (IOException io) {
			System.out.println("Arquivo não encontrado");
		}

		return loans;
	}
}
