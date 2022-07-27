package com.caionilson.core.tema8.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.caionilson.core.tema8.Exception.LibraryException;
import com.caionilson.core.tema8.model.Book;

public class BookDAO {

	private List<Book> books;

	public BookDAO() {
		books = new ArrayList<>();
	}

	public void addBook(Book book) {
		books.add(book);
		writeFile();
	}

	public List<Book> getBookList() {
		return books;
	}

	public Book getBookId(int bookId) {
		for (Book book : books) {
			if (book.getBookId() == bookId) {
				return book;
			}
		}
		return null;
	}

	public Book getBook(String bookTitle, String bookAuthor) {
		return books.stream().filter(
				l -> l.getBookTitle().equalsIgnoreCase(bookTitle) && l.getBookAuthor().equalsIgnoreCase(bookAuthor))
				.findAny().orElse(null);
	}

	public void removeBook(int bookId) {
		Book bookRemoved = getBookId(bookId);

		if (bookRemoved.isBookLoan())
			throw new LibraryException("Livro esta alugado e não pode ser removido");

		books.remove(bookRemoved);
	}

	public void writeFile() {
		try (FileWriter fileWriter = new FileWriter("books.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

			bufferedWriter.write("\nID - Título - Autor - Alugado\n");

			for (Book book : books) {
				bufferedWriter.write("\n" + book.getBookId() + " - " + book.getBookTitle() + " - "
						+ book.getBookAuthor() + " - " + book.isBookLoan() + "\n");
			}
			bufferedWriter.close();

		} catch (IOException io) {
			System.out.println("Arquivo não encontrado");
		}
	}

	public List<Book> readFile() {
		try {
			FileReader fileReader = new FileReader("books.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String currentLine = bufferedReader.readLine();
			currentLine = bufferedReader.readLine();

			while (currentLine != null) {
				String[] bookAttributes = currentLine.split("\\s-\\s");
				String bookTitle = bookAttributes[1];
				String bookAuthor = bookAttributes[2];
				boolean bookLoan = (bookAttributes[3].equals("true")) ? true : false;

				Book book = new Book(bookTitle, bookAuthor);
				book.setBookLoan(bookLoan);
				books.add(book);

				currentLine = bufferedReader.readLine();
			}

			bufferedReader.close();
		} catch (IOException io) {
			System.out.println("Arquivo não encontrado");
		}

		return books;
	}
}
