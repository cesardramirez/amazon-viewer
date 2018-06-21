package com.platzi.amazonviewer.model;

import java.util.ArrayList;
import java.util.Date;

public class Movie extends Film implements IVisualizable {
	public int id;
	private int timeViewed;
	
	
	public Movie(String title, String genre, String creator, int duration, short year) {
		super(title, genre, creator, duration); // Hereda del constructor padre 'Film'
		this.setYear(year);
	}

	public int getId() {
		return id;
	}
	
	public int getTimeViewed() {
		return timeViewed;
	}
	public void setTimeViewed(int timeViewed) {
		this.timeViewed = timeViewed;
	}
	
	@Override
	public String toString() {
		return "Title: " + this.getTitle() +
				"\nGenre: " + this.getGenre() +
				"\nYear: " + this.getYear() +
				"\nCreator: " + this.getCreator() +
				"\nDuration: " + this.getDuration();
	}

	@Override
	public Date startToSee(Date dateI) {
		return dateI;
	}

	@Override
	public void stopToSee(Date dateI, Date dateF) {
	  int result = dateF.getTime() > dateI.getTime() ? (int) (dateF.getTime() - dateI.getTime()) / 1000 : 0;
	  this.setTimeViewed(result);
	}
	
	public static ArrayList<Movie> makeMoviesList() {
		ArrayList<Movie> movies = new ArrayList<>();
		
		for (int i = 1; i <= 5; i++) {
			movies.add(new Movie("Movie " + i, "Genre " + i, "Creator " + i, 120 + i, (short) (2017 + i)));
		}
		
		return movies;
	}
}