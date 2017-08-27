package com.jpm.openart.core.model;

import java.util.Map;

public class Song {
	private String title;
	private String genre;
	private String artist;
	private String fileName;
	private String format;
	private double duration;
	
	public Song(String title, String genre, String artist, String fileName, String format, double duration) {
		super();
		this.title = title;
		this.genre = genre;
		this.artist = artist;
		this.fileName = fileName;
		this.format = format;
		this.duration = duration;
	}
	
	   public Song(Map<String,String> values){
	        title = values.get("title");
	        genre = values.get("genre");
	        artist = values.get("artist");
	        fileName = values.get("fileName");
	        format = values.get("format");
	        duration = Double.parseDouble(values.get("duration"));
	    }
	
	public static class Builder{
		
		private String title;
		private String genre;
		private String artist;
		private String fileName;
		private String format;
		private double duration;
		
	
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder genre(String genre) {
			this.genre= genre;
			return this;
		}
		

		public Builder artist(String artist) {
			this.artist = artist;
			return this;
		}
		
		public Builder fileName(String fileName) {
			this.fileName = fileName;
			return this;
		}
		
		public Builder format(String format) {
			this.format = format;
			return this;
		}
		
		public Builder duration(double duration) {
			this.duration = duration;
			return this;
		}
		public Song build() {
			return new Song(title, genre, artist, fileName, format, duration);
		}
			
	}
}
