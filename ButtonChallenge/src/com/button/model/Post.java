package com.button.model;

public class Post {
    public String id;
    public String message;
    public String story;
    public String created_time;
    public User from;

    public Post(String id, String message, String story,  String created_time, User from){
        this.id = id;
        this.message = message;
        this.story = story;
        this.created_time = created_time;
        this.from = from;
    }
    public Post(){
        this.id = null;
        this.message = null;
        this.story = null;
        this.created_time = null;
        this.from = null;
    }

    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}



	@Override
	public String toString() {
		return "Post [id=" + id + ", message=" + message + ", created_time=" + created_time + ", from=" + from + "]";
	}
    
    
}