package com.platzi.amazonviewer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.platzi.amazonviewer.model.Book;
import com.platzi.amazonviewer.model.Chapter;
import com.platzi.amazonviewer.model.Magazine;
import com.platzi.amazonviewer.model.Movie;
import com.platzi.amazonviewer.model.Serie;
import com.platzi.makereport.Report;

public class Main {
	static ArrayList<Movie> movies;
	static ArrayList<Serie> series;
	
	public static void main(String[] args) throws IOException {
		Movie movie = new Movie("Coco", "Animation", "Lee Unkrich", 120, (short) 2017);
		movie.setTitle("Rambo");
		System.out.println(movie);  // Llama por defecto al método toString().
		
		// Comparar si dos objetos son iguales según sus atributos y no su dirección en memoria.
		Magazine magaz1 = new Magazine("Enter.co", new Date(1220227200L), "El Tiempo", new String[]{"El propio"});
		Magazine magaz2 = new Magazine("Enter.co", new Date(1220227200L), "El Tiempo", new String[]{"El propio"});
		System.out.println("\n" + (magaz1.equals(magaz2) ? "Same objects" : "Different objects"));
		
		// Crea un objeto Book enviando el formato de fecha espececífico.
		try {
			Book book = new Book("The Little Prince", new SimpleDateFormat("dd/MM/yyyy").parse("06/04/1943"), "Éditions Gallimard", true);
			book.setAuthors(new String[]{"Antoine de Saint-Exupéry"});
			System.out.println(book);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
		showMenu();
	}
	
	public static void showMenu() throws IOException {
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		movies = Movie.makeMoviesList();
		series = Serie.makeMoviesList();
		
		do {
			System.out.println("*** BIENVENIDOS AMAZON VIEWER ***\n");
			System.out.print("1. Movies\n2. Series\n3. Books\n4. Magazines\n5. Report\n6. Report Today\n0. Exit\nSelecciona el número de la opción deseada: ");
			
			try {
				int opcion = sc.nextInt();

				if (opcion == 0) {
					System.out.println("Saliendo...");
					exit = true;
				} else if (opcion == 1) {
					showMovies(movies);
				} else if (opcion == 2) {
					showSeries(series);
				} else if (opcion == 3) {
					showBooks();
				} else if (opcion == 4) {
					showMagazines();
				} else if (opcion == 5) {
					makeReport();
				} else if (opcion == 6) {
					makeReport(new Date());
				} else {
					System.out.println("OPCIÓN INVÁLIDA!");
				}
			} catch (InputMismatchException e) {
				System.out.println("No es un número... ");
				exit = true;
			}
			
			System.out.println();
		} while (!exit);
		sc.close();
	}
	
	public static void showMovies(ArrayList<Movie> movies) {
		System.out.println("\n:: MOVIES ::");
		
		for (int i = 0; i < movies.size(); i++) {
			System.out.println((i+1) + ". " + movies.get(i).getTitle() + "\tViewed: " + movies.get(i).isViewed() + "\tTime Viewed: " + movies.get(i).getTimeViewed() + " seg");
		}
		
		// Elegir película.
		System.out.print("Elige la película que deseas ver: ");
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
		
		if (opcion > 0) {
			// Ver película.
			Movie movieSelected = movies.get(opcion - 1);
			movieSelected.setViewed(true);
			Date dateI = movieSelected.startToSee(new Date());
			
			seenThread();
			
			movieSelected.stopToSee(dateI, new Date());
			System.out.println("Viste \"" + movieSelected.getTitle() + "\" en " + movieSelected.getTimeViewed() + " segundos !! ");
		} else {
			System.out.println("Opción no válida");
			sc.close();
		}
	}
	
	public static void showSeries(ArrayList<Serie> series) {
		System.out.println("\n:: SERIES ::");

		for (int i = 0; i < series.size(); i++) {
			System.out.println((i+1) + ". " + series.get(i).getTitle() + "\tViewed: " + series.get(i).isViewed());
		}
		
		// Elegir una serie para visualizar los capítulos.
		System.out.print("Elige la serie que deseas ver: ");
		Scanner sc = new Scanner(System.in);
		int serieSelected = sc.nextInt();
		
		if (serieSelected > 0) {
			showChapters(series.get(serieSelected-1).getChapters());
		} else {
			System.out.println("Opción no válida");
			sc.close();
		}
	}

	public static void showChapters(ArrayList<Chapter> chaptersOfSerieSelected) {
		System.out.println("\n:: CHAPTERS ::");

		for (int i = 0; i < chaptersOfSerieSelected.size(); i++) {
			System.out.println((i+1) + ". " + chaptersOfSerieSelected.get(i).getTitle() + "\tViewed: " + chaptersOfSerieSelected.get(i).isViewed());
		}

		// Elegir un capítulo de una serie.
		System.out.print("Elige el capítulo que deseas ver: ");
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
		
		if (opcion > 0) {
			// Ver capítulo de una serie.
			Chapter chapterSelected = chaptersOfSerieSelected.get(opcion - 1);
			chapterSelected.setViewed(true);
			Date dateI = chapterSelected.startToSee(new Date());
			
			seenThread();
			
			chapterSelected.stopToSee(dateI, new Date());
			System.out.println("Viste \"" + chapterSelected.getTitle() + "\" en " + chapterSelected.getTimeViewed() + " segundos !! ");
		} else {
			System.out.println("Opción no válida");
			sc.close();
		}
	}
	
	public static void showBooks() {
		System.out.println("\n:: BOOKS ::");
	}
	public static void showMagazines() {
		System.out.println("\n:: MAGAZINES ::");
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
		
		report.setContent(contentReport);
		report.makeReport();
		System.out.println("Archivo " + report.getNameFile() + "." + report.getExtension() + " generado!");
	}
	
	public static void makeReport(Date date) throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		
		Report report = new Report();
		report.setNameFile("reporte-" + dateString);
		report.setExtension("txt");
		report.setTitle(":: VISTOS ::");
		String contentReport = report.getTitle();;
		
		for (Movie movie : movies) {
			if (movie.getIsViewed()) {
				contentReport += "\n\n" + movie;
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