package com.platzi.amazonviewer.model;

import java.util.ArrayList;

public class Chapter extends Movie {
	private int id;
	private int seasonNumber;
	private Serie serie;
	
	public Chapter(String title, String genre, String creator, int duration, short year, int seasonNumber) {
		super(title, genre, creator, duration, year);
		this.seasonNumber = seasonNumber;
	}
	
	public Chapter(String title, String genre, String creator, int duration, short year, int seasonNumber,
			Serie serie) {
		super(title, genre, creator, duration, year);
		this.seasonNumber = seasonNumber;
		this.serie = serie;
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
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}
	
	@Override
	public String toString() {
		return "Title: " + this.getTitle() +
				"\nGenre: " + this.getGenre() +
				"\nYear: " + this.getYear() +
				"\nCreator: " + this.getCreator() +
				"\nDuration: " + this.getDuration();
	}

	public static ArrayList<Chapter> makeChaptersList(Serie serie) {
		ArrayList<Chapter> chapters = new ArrayList<>();
		
		for (int i = 1; i <= 5; i++) {
			chapters.add(new Chapter("Chapter " + i, "Genre " + i, "Creator " + i, 45 + i, (short) (2017 + i), i, serie));
		}
		
		return chapters;
	}
	
	@Override
	public void view() {
		super.view();
		ArrayList<Chapter> chapters = this.getSerie().getChapters();
		int chapterViewedCounter = 0;
		
		for (Chapter c : chapters) {
			if (c.getIsViewed()) {
				chapterViewedCounter++;
			}
		}
		
		if (chapterViewedCounter == chapters.size()) {
			this.getSerie().setViewed(true);
		}
	}
}