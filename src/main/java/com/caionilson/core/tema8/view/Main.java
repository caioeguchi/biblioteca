package com.caionilson.core.tema8.view;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Scanner;

import com.caionilson.core.tema8.Exception.LibraryException;
import com.caionilson.core.tema8.dao.UserDAO;
import com.caionilson.core.tema8.model.Book;
import com.caionilson.core.tema8.model.Loan;
import com.caionilson.core.tema8.model.User;
import com.caionilson.core.tema8.service.Library;

public class Main {

	public static void main(String[] args) {
		Library library = new Library();

		Scanner sc = new Scanner(System.in);

		int op = 0;
		do {
			try {
				System.out.println("-----------------------------");
				System.out.println(
						"Biblioteca do Caio Nilson: " + 
						"\n 1  - Cadastrar livro " + 
						"\n 2  - Listar livros " + 
						"\n 3  - Excluir livro " + 
						"\n 4  - Buscar livro por título ou autor" + 
						"\n 5  - Registrar cliente" +
						"\n 6  - Listar clientes" +
						"\n 7  - Emprestar livro " + 
						"\n 8  - Renovar livro " + 
						"\n 9  - Devolver livro " + 
						"\n 10 - Lista de livros emprestados " + 
						"\n 11 - Top 10 clientes " + 
						"\n 12 - Clientes atrasados " + 
						"\n 13 - Sair ");
				op = Integer.parseInt(sc.nextLine());
				switch (op) {
				
				//quando o usuario digitar 1, o programa cadastra o titulo e o autor do livro no arquivo
				case 1:
					System.out.println("\nDigite o nome do título do livro ");
					String bookTitle = sc.nextLine();
					System.out.println("\nDigite o nome do autor do livro");
					String bookAuthor = sc.nextLine();
					try {
						library.addBook(bookTitle, bookAuthor);
						System.out.println("\nLivro cadastrado\n");
					} catch (LibraryException e) {
						System.out.println(e.getMessage());
					}
					break;

					//quando o usuario digitar 2, o programa exibe os livros regitrados
				case 2:
					System.out.println("-----------------------------");
					System.out.println("\nLista de livros: ");
					System.out.println("ID - Título - Autor - Alugado\n");
					for (Book book : library.getBookList()) {
						System.out.println(book.getBookId() + " - " + book.getBookTitle() + " - " + book.getBookAuthor()
								+ " - " + book.isBookLoan());
					}
					break;
					
					//quando o usuario digitar 3, o programa exclui o livro do arquivo
				case 3:
					System.out.println("\nDigite o ID do livro que será excluido");
					int bookId = sc.nextInt();
					try {
						library.removeBook(bookId);
					} catch (LibraryException e) {
						System.out.println(e.getMessage());
					}
					break;

					//quando o usuario digitar 4, o programa exibe o livro que está sendo procurado, de acordo com o titulo, ou o autor do livro
				case 4:
					System.out.println("\nDigite o título ou autor do livro");
					String searchBook = sc.nextLine();
					library.searchBook(searchBook);
					List<Book> findBook = library.searchBook(searchBook);
					if (findBook.isEmpty()) {
						System.out.println("Livro não encontrado");
					} else {
						System.out.println("-----------------------------");
						System.out.println("ID - Título - Autor\n");
						for (Book book : findBook) {
							System.out.println(
									book.getBookId() + " - " + book.getBookTitle() + " - " + book.getBookAuthor());
						}
					}
					break;

					//quando o usuario digitar 5, o programa cadastra o usuário no arquivo
				case 5:
					System.out.println("\nDigite o nome do cliente");
					String userName = sc.nextLine();
					library.addUser(userName);

					System.out.println("\nCliente cadastrado\n");
					break;

					//quando o usuario digitar 6, o programa exibe a lista de clientes
				case 6:
					System.out.println("-----------------------------");
					System.out.println("\nLista de clientes:");
					System.out.println("ID - Nome - Livros alugados - Total de livros alugados\n");
					for (User user : library.getUserList()) {
						System.out.println(user.getUserId() + " - " + user.getUserName() + " - " + user.getUserLoan()
								+ " - " + user.getUserLoanTotal());
					}
					break;

					//quando o usuario digitar 7, o programa aluga o livro para o usuario que está alugando
				case 7:
					System.out.println("\nDigite o ID do livro que será emprestado");
					int loanBookId = sc.nextInt();
					System.out.println("\nDigite o ID do cliente que está locando");
					int loanUserId = sc.nextInt();

					try {
						library.loanAdd(loanUserId, loanBookId);
					} catch (LibraryException e) {
						System.out.println(e.getMessage());
					}
					break;

					//quando o usuario digitar 8, o programa renova o livro que foi emprestado para o dia de hoje
				case 8:
					System.out.println("\nDigite o ID do livro que será renovado por mais 7 dias");
					int renewBookId = sc.nextInt();
					System.out.println("\nDigite o ID do usuário que está renovando");
					int renewUserId = sc.nextInt();

					try {
						library.loanRenew(renewUserId, renewBookId);
						System.out.println("\nLivro renovado\n");
					} catch (LibraryException e) {
						System.out.println(e.getMessage());
					}
					break;

					//quando o usuario digitar 9, o programa devolve o livro que for mencionado, e mostra quanto o usuário deve pelo atraso
				case 9:
					System.out.println("\nDigite o ID do livro que será devolvido");
					int returnBookId = sc.nextInt();
					System.out.println("\nDigite o ID do usuário que está devolvendo");
					int returnUserId = sc.nextInt();
					UserDAO userDAO = library.getUserService();

					try {
						library.loanReturn(returnUserId, returnBookId);
						System.out.println("O cliente está devendo R$"
								+ userDAO.getUserId(returnUserId).getUserLoanChargeDays() + " pelos dias atrasados");
						System.out.println("\nLivro devolvido\n");
					} catch (LibraryException e) {
						System.out.println(e.getMessage());
					}

					break;

					//quando o usuario digitar 10, o programa exibe todos os livros emprestados
				case 10:
					System.out.println("-----------------------------");
					System.out.println("\nLista de livros emprestados");
					List<Loan> loans = library.loanList();
					if (loans.isEmpty()) {
						System.out.println("\nNão há livros emprestados");
					} else {
						System.out.println("\nCliente - Livro\n");
						for (Loan loan : loans) {
							System.out.println(loan.getUser().getUserName() + " - " + loan.getBook().getBookTitle());
						}
					}
					break;

					//quando o usuario digitar 11, o programa exibe a lista dos 10 clientes que mais alugaram livros
				case 11:
					System.out.println("-----------------------------");
					System.out.println("\nLista dos top10 clientes");
					List<User> top10 = library.top10Users();
					if (top10.isEmpty()) {
						System.out.println("Não tem usuários cadastrados");
					} else {
						System.out.println("Usuário - Total de livros alugados");
						for (User user : top10) {
							System.out.println(user.getUserName() + " - " + user.getUserLoanTotal());
						}
					}
					break;

					//quando o usuario digitar 12, o programa exibe a lista de clientes atrasados
				case 12:
					System.out.println("-----------------------------");
					System.out.println("\nLista dos clientes atrasados");
					List<Loan> delayLoan = library.loanDelay();
					if (delayLoan.isEmpty()) {
						System.out.println("\nNão há usuários atrasados");
					} else {
						System.out.println("Usuário - Livro - Dias atrasados");
						for (Loan loan : delayLoan) {
							System.out.println(loan.getUser().getUserName() + " - " + loan.getBook().getBookTitle()
									+ " - " + (Period.between(LocalDate.now(), loan.getReturnDate())).getDays());
						}
					}
					break;

				default:
					System.out.println("\nPrograma finalizado");
					break;
				}
			} catch (Exception e) {}
			
			//quando o usuario digitar 13, o programa finaliza
		} while (op != 13);
	}
}
