package com.button.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.button.constants.Message;
import com.button.model.Posts;
import com.button.model.facebook.PostsFacebook;

public class FacebookApi extends NetworkApi implements NetworkInterface
{
	private static String	FB_API_VERSION			= "v2.7";
	private static String 	GRAPH_API_URL			= "https://graph.facebook.com/" + FB_API_VERSION ;

	public FacebookApi(){
		super();
	}

	public FacebookApi(String access_token) {
		super(access_token);
	}

	/////////////////////////////
	// API Calls
	/////////////////////////////
	@Override
	public Posts getAllPosts() 
	{
		String parameters = "?";
		parameters += "access_token=" + access_token;
		parameters += "&fields=id,message,story,created_time,from" ;

		String output = facebookRequest(GRAPH_API_URL + "/me/feed" + parameters);

		PostsFacebook posts = null;
		try {
			posts = mapper.readValue(output, PostsFacebook.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return posts;
	}

	@Override
	public Posts getPost(String id) 
	{
		String parameters = "?";
		parameters += "access_token=" + access_token;
		parameters += "&fields=id,message,story,created_time,from" ;

		String output = "{ \"data\": ["; 
		output += facebookRequest(GRAPH_API_URL + "/" + id + parameters);
		output += "]}";


		PostsFacebook posts = null;
		try {
			posts = mapper.readValue(output, PostsFacebook.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return posts;
	}
	
	
	//Call the Facebook Graph API and return the JSON 
	private String facebookRequest(String sUrl){
		String output = Message.ERROR; 
		System.out.println("[Sending] - To Facebook: " + sUrl);
		try {
			//URL url = new URL(GRAPH_API_URL  + "/me/feed");
			URL url = new URL(sUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			output = br.readLine();
			conn.disconnect();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[Receiving] From Facebook: " + output);
		return output;
	}
}
