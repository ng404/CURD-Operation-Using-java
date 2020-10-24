

package com.company.CompanyTask.model;

import java.util.ArrayList;
import java.util.List;

public class PostAuthorModel {
	
	ArrayList<PostModel> posts;
	ArrayList<AuthorModel> authors;
	
	
	public ArrayList<PostModel> getPosts() {
		return posts;
	}
	public void setPosts(ArrayList<PostModel> posts) {
		this.posts = posts;
	}
	public ArrayList<AuthorModel> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<AuthorModel> authors) {
		this.authors = authors;
	}
	
	
	

}
