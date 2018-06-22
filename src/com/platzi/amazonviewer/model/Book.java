package com.platzi.amazonviewer.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.platzi.util.AmazonUtil;

public class Book extends Publication implements IVisualizable {
	private int id;
	private String isbn;
	private boolean read;
	private int timeRead;
	
	public Book(String title, Date editionDate, String editorial, boolean read) {
		super(title, editionDate, editorial);
		this.read = read;
	}
	
	public Book(String title, Date editionDate, String editorial, String[] authors) {
		super(title, editionDate, editorial);
		this.setAuthors(authors);
	}

	public int getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String isRead() {
		return read ? "Yes" : "No";
	}
	public boolean getIsRead() {
		return read;
	}	
	public void setRead(boolean read) {
		this.read = read;
	}
	public int getTimeRead() {
		return timeRead;
	}
	public void setTimeRead(int timeRead) {
		this.timeRead = timeRead;
	}
	
	@Override
	public String toString() {
		String detailBook = 
				"\nTitle: " + this.getTitle() + 
				"\nEditorial: " + this.getEditorial() + 
				"\nEdition Date: " + new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(this.getEditionDate()) + 
				"\nAuthors: ";
		for (String author : this.getAuthors()) {
			detailBook += author + "\t";
		}
		return detailBook;
	}

	@Override
	public Date startToSee(Date dateI) {
		return dateI;
	}

	@Override
	public void stopToSee(Date dateI, Date dateF) {
		int result = dateF.getTime() > dateI.getTime() ? (int) (dateF.getTime() - dateI.getTime()) / 1000 : 0;
		this.setTimeRead(result);
	}
	
	public static ArrayList<Book> makeBookList() {
		ArrayList<Book> books = new ArrayList<>();
		String[] authors = new String[3];
		
		for (int i = 0; i < 3; i++) {
			authors[i] = "Author " + i;
		}
		for (int i = 1; i <= 5; i++) {
			books.add(new Book("Book " + i, new Date(), "Editorial " + i, authors));
		}
		
		return books;
	}
	
	public void view() {
		this.setRead(true);
		Date dateI = this.startToSee(new Date());
		
		AmazonUtil.seenThread();
		
		this.stopToSee(dateI, new Date());
		System.out.println("Viste \"" + this.getTitle() + "\" en " + this.getTimeRead() + " segundos !! ");
	}
}