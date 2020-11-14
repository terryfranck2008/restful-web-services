package com.francktepi.restfulwebservices.post;

public class Post {
	private long id;
	private String message;
	private long userId;
	
	public Post(long id, String message, long userId) {
		super();
		this.id = id;
		this.message = message;
		this.userId = userId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	
}
