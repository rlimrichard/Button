package com.button.model.facebook;

import com.button.model.Post;
import com.button.model.User;

public class PostFacebook extends Post
{
	private UserFacebook from;
	
	public PostFacebook(String id, String message, String story, String created_time, User from) {
		super(id, message, story, created_time, (UserFacebook)from);
		
	}
	
	public PostFacebook() {
		super();
	}

	@Override
	public String toString() {
		return "PostFacebook [id=" + id + ", message=" + message + ", story=" + story + ", created_time=" + created_time
				+ ", from=" + from + "]";
	}

}
