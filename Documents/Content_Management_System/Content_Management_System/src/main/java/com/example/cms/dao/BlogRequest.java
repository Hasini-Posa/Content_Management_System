package com.example.cms.dao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.experimental.PackagePrivate;

public class BlogRequest {

	@NotNull
	@Pattern(regexp = "^(?=.[A-Z])(?=.[a-z]){200,}$",message="Title must be alphabets only")
	private String title;
	private String[] topics;
	private String about;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getTopics() {
		return topics;
	}
	public void setTopics(String[] topics) {
		this.topics = topics;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
}
