package com.company.CompanyTask.model;

public class PostModel {

	private long id;
	private String title;
	private long views;
	private long reviews;
	private String author;
	
	//Getter Setter Methods
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getViews() {
		return views;
	}
	public void setViews(long views) {
		this.views = views;
	}
	public long getReviews() {
		return reviews;
	}
	public void setReviews(long reviews) {
		this.reviews = reviews;
	}
	
}
