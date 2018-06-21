package com.platzi.amazonviewer.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Book extends Publication implements IVisualizable {
	private int id;
	private String isbn;
	private boolean read;
	private int timeRead;
	
	public Book(String title, Date editionDate, String editorial, boolean read) {
		super(title, editionDate, editorial);
		this.read = read;
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
	public boolean isRead() {
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
}