package com.button.network;

import com.button.model.Posts;

public interface NetworkInterface {

	Posts getAllPosts() ;
	Posts getPost(String id) ;
}
