package com.button.network;

import org.codehaus.jackson.map.ObjectMapper;
import com.button.model.Posts;

public class NetworkApi implements NetworkInterface{
	
	protected String 			access_token 			= "";
	protected ObjectMapper 		mapper					= new ObjectMapper();

	public NetworkApi()
	{}
	
	public NetworkApi(String access_token)
	{
		this.access_token = access_token;
	}	
	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	@Override
	public Posts getAllPosts() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Posts getPost(String id) {
		return null;
	}
	
}
