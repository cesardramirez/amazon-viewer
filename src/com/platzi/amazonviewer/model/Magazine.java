package com.platzi.amazonviewer.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Magazine extends Publication {
	private int id;
	
	public Magazine(String title, Date editionDate, String editorial, String[] authors) {
		super(title, editionDate, editorial);
		this.setAuthors(authors);
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "\nTitle: " + this.getTitle() +
				"\nEditorial: " + this.getEditorial() +
				"\nEdition Date: " + new SimpleDateFormat("MMMM dd, yyyy", Locale.US).format(this.getEditionDate());
	}
}