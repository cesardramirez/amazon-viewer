package com.platzi.amazonviewer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.platzi.amazonviewer.model.Book;
import com.platzi.amazonviewer.model.Chapter;
import com.platzi.amazonviewer.model.Magazine;
import com.platzi.amazonviewer.model.Movie;
import com.platzi.amazonviewer.model.Serie;
import com.platzi.makereport.Report;
import com.platzi.util.AmazonUtil;

public class Main {
	static ArrayList<Movie> movies;
	static ArrayList<Serie> series;
	static ArrayList<Book> books;
	static ArrayList<Magazine> magazines;
	
	public static void main(String[] args) throws IOException {
		showMenu();
	}
	
	public static void showMenu() throws IOException {
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		movies = Movie.makeMoviesList();
		series = Serie.makeMoviesList();
		books = Book.makeBookList();
		magazines = Magazine.makeMagazineList();

		do {
			System.out.println("*** BIENVENIDOS AMAZON VIEWER ***\n");
			System.out.println("1. Movies\n2. Series\n3. Books\n4. Magazines\n5. Report\n6. Report Today\n0. Exit");

			int opcion = AmazonUtil.validateUserResponseOptionMenu(0, 6, "Digite una opción del menú: ");

			if (opcion == 0) {
				System.out.println("Saliendo...");
				exit = true;
			} else if (opcion == 1) {
				showMovies(movies);
			} else if (opcion == 2) {
				showSeries(series);
			} else if (opcion == 3) {
				showBooks(books);
			} else if (opcion == 4) {
				showMagazines();
			} else if (opcion == 5) {
				makeReport();
			} else if (opcion == 6) {
				makeReport(new Date());
			}
			System.out.println();
		} while (!exit);
		sc.close();
	}
	
	public static void showMovies(ArrayList<Movie> movies) {
		System.out.println("\n:: MOVIES ::");
		
		for (int i = 0; i < movies.size(); i++) {
			System.out.println((i + 1) + ". " + movies.get(i).getTitle() + "\tViewed: " + movies.get(i).isViewed() + "\tTime Viewed: " + movies.get(i).getTimeViewed() + " seg");
		}
		
		int opcion = AmazonUtil.validateUserResponseOptionMenu(0, movies.size(), "Elige la película que deseas ver: ");
		
		Movie movieSelected = movies.get(opcion - 1);
		movieSelected.setViewed(true);
		Date dateI = movieSelected.startToSee(new Date());

		seenThread();

		movieSelected.stopToSee(dateI, new Date());
		System.out.println("Viste \"" + movieSelected.getTitle() + "\" en " + movieSelected.getTimeViewed() + " segundos !! ");
	}
	
	public static void showSeries(ArrayList<Serie> series) {
		System.out.println("\n:: SERIES ::");

		for (int i = 0; i < series.size(); i++) {
			System.out.println((i + 1) + ". " + series.get(i).getTitle() + "\tViewed: " + series.get(i).isViewed());
		}
		
		int serieSelected = AmazonUtil.validateUserResponseOptionMenu(0, series.size(), "Elige la serie que deseas ver: ");
		showChapters(series.get(serieSelected-1).getChapters());
	}

	public static void showChapters(ArrayList<Chapter> chaptersOfSerieSelected) {
		System.out.println("\n:: CHAPTERS ::");

		for (int i = 0; i < chaptersOfSerieSelected.size(); i++) {
			System.out.println((i + 1) + ". " + chaptersOfSerieSelected.get(i).getTitle() + "\tViewed: " + chaptersOfSerieSelected.get(i).isViewed());
		}
		
		int opcion = AmazonUtil.validateUserResponseOptionMenu(0, chaptersOfSerieSelected.size(), "Elige el capítulo que deseas ver: ");
		Chapter chapterSelected = chaptersOfSerieSelected.get(opcion - 1);
		chapterSelected.setViewed(true);
		Date dateI = chapterSelected.startToSee(new Date());

		seenThread();

		chapterSelected.stopToSee(dateI, new Date());
		System.out.println("Viste \"" + chapterSelected.getTitle() + "\" en " + chapterSelected.getTimeViewed() + " segundos !! ");
	}
	
	public static void showBooks(ArrayList<Book> books) {
		System.out.println("\n:: BOOKS ::");
		
		for (int i = 0; i < books.size(); i++) {
			System.out.println((i + 1) + ". " + books.get(i).getTitle() + "\tRead: " + books.get(i).isRead() + "\tTime Read: " + books.get(i).getTimeRead() + " seg");
		}
		
		int opcion = AmazonUtil.validateUserResponseOptionMenu(0, books.size(), "Elige el libro que deseas leer: ");
		
		Book bookSelected = books.get(opcion - 1);
		bookSelected.setRead(true);
		Date dateI = bookSelected.startToSee(new Date());
		
		seenThread();
		
		bookSelected.stopToSee(dateI, new Date());
		System.out.println("Viste \"" + bookSelected.getTitle() + "\" en " + bookSelected.getTimeRead() + " segundos !! ");
	}
	
	public static void showMagazines() {
		System.out.println("\n:: MAGAZINES ::");
		
		for (int i = 0; i < magazines.size(); i++) {
			System.out.println((i + 1) + ". " + magazines.get(i).getTitle());
		}
	}
	
	public static void makeReport() throws IOException {
		Report report = new Report();
		report.setNameFile("reporte");
		report.setExtension("txt");
		report.setTitle(":: VISTOS ::");
		String contentReport = report.getTitle();;
		
		for (Movie movie : movies) {
			if (movie.getIsViewed()) {
				contentReport += "\n\n" + movie;
			}
		}
		
		for (Serie serie : series) {
			ArrayList<Chapter> chapters = serie.getChapters();
			for (Chapter chapter : chapters) {
				if (chapter.getIsViewed()) {
					contentReport += "\n\nTitle Serie: " + serie.getTitle();
					contentReport += "\n" + chapter;
				}
			}
		}
		
		for (Book book : books) {
			if (book.isRead()) {
				contentReport += "\n" + book;
			}
		}
		
		report.setContent(contentReport);
		report.makeReport();
		System.out.println("Archivo " + report.getNameFile() + "." + report.getExtension() + " generado!");
	}
	
	public static void makeReport(Date date) throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-m-ss-S");
		String dateString = df.format(date);
		
		Report report = new Report();
		report.setNameFile("reporte-" + dateString);
		report.setExtension("txt");
		report.setTitle("\n\n\t:: VISTOS ::");
		
		SimpleDateFormat dfNameDays = new SimpleDateFormat("E, W MMM Y, hh:mm:ss a");
		dateString = dfNameDays.format(date);
		String contentReport = "Date: " + dateString + report.getTitle();
		
		for (Movie movie : movies) {
			if (movie.getIsViewed()) {
				contentReport += "\n\n" + movie;
			}
		}
		
		for (Serie serie : series) {
			ArrayList<Chapter> chapters = serie.getChapters();
			for (Chapter chapter : chapters) {
				if (chapter.getIsViewed()) {
					contentReport += "\n\nTitle Serie: " + serie.getTitle();
					contentReport += "\n" + chapter;
				}
			}
		}
		
		for (Book book : books) {
			if (book.isRead()) {
				contentReport += "\n" + book;
			}
		}
		
		report.setContent(contentReport);
		report.makeReport();
		System.out.println("Archivo " + report.getNameFile() + "." + report.getExtension() + " generado!");
	}
	
	public static void seenThread() {
		try {
			byte min = 1, max = 5;
			long num_random = (long) Math.floor(Math.random() * (max - min + 1)) + min;
			Thread.sleep(num_random * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}