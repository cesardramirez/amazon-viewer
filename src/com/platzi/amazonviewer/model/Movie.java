package com.platzi.amazonviewer.model;

import java.util.ArrayList;
import java.util.Date;

import com.platzi.util.AmazonUtil;

/**
 * Hereda de {@link Film} e
 * Implementa de {@link IVisualizable}
 * 
 * @author Cesar Ramírez
 **/
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

	/**
	 * {@inheritDoc}
	 **/
	@Override
	public Date startToSee(Date dateI) {
		return dateI;
	}

	/**
	 * {@inheritDoc}
	 **/
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

	/**
	 * <p>Método que maneja un comportamiento de una visualización en n segundos.</p>
	 * {@inheritDoc}
	 **/
	@Override
	public void view() {
		this.setViewed(true);
		Date dateI = this.startToSee(new Date());
		
		AmazonUtil.seenThread();

		this.stopToSee(dateI, new Date());
		System.out.println("Viste \"" + this.getTitle() + "\" en " + this.getTimeViewed() + " segundos !! ");		
	}	
}