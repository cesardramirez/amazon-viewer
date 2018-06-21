package com.platzi.amazonviewer.model;

import java.util.ArrayList;

public class Chapter extends Movie {
	private int id;
	private int seasonNumber;
	
	public Chapter(String title, String genre, String creator, int duration, short year, int seasonNumber) {
		super(title, genre, creator, duration, year);
		this.seasonNumber = seasonNumber;
	}
	
	@Override
	public int getId() { // Método getId() proveniente de Movie.
		return this.id;
	}
	
	public int getSeasonNumber() {
		return seasonNumber;
	}
	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	
	@Override
	public String toString() {
		return "Title: " + this.getTitle() +
				"\nGenre: " + this.getGenre() +
				"\nYear: " + this.getYear() +
				"\nCreator: " + this.getCreator() +
				"\nDuration: " + this.getDuration();
	}

	public static ArrayList<Chapter> makeChaptersList() {
		ArrayList<Chapter> chapters = new ArrayList<>();
		
		for (int i = 1; i <= 5; i++) {
			chapters.add(new Chapter("Chapter " + i, "Genre " + i, "Creator " + i, 45 + i, (short) (2017 + i), i));
		}
		
		return chapters;
	}
}