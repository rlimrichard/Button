package com.button.challenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



@Path("/fbapi")
public class FacebookPostApi {

	private static String 	TOKEN_MISSING_MESSAGE 		= "{'error': 'Parameter access_token missing in the query'}";
	private static String 	ID_MISSING_MESSAGE 			= "{'error': 'Parameter id missing in the query'}";
	private static String 	DATE_MISSING_MESSAGE 		= "{'error': 'Parameter date missing in the query.'}";
	private static String 	DATE_WRONG_FORMAT_MESSAGE 	= "{'error': 'the date has to be in format YYYYMMDD'}";
	private static String 	KEYWORD_MISSING_MESSAGE 	= "{'error': 'Parameter keyword missing in the query.'}";
	private static String 	ERROR 						= "{'error': 'ERROR'}";


	private static String	FB_API_VERSION			= "v2.7";
	private static String 	GRAPH_API_URL			= "https://graph.facebook.com/" + FB_API_VERSION ;

	//to test the API
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public Response pingFacebookPostService(InputStream incomingData) {
		String result = "Facebook Service Successfully started.. Ping!";

		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}

	//Get All posts to be displayed
	@GET
	@Path("/allposts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPosts(@QueryParam("access_token") String access_token) 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(TOKEN_MISSING_MESSAGE).build();

		String parameters = "?";
		parameters += "access_token=" + access_token;
		parameters += "&fields=id,message,story,created_time,from" ;

		String output = facebookRequest(GRAPH_API_URL + "/me/feed" + parameters);

		if (ERROR.equals(output))
			return Response.status(500).entity(output).build();

		// return HTTP response 200 in case of success
		return Response.status(200).entity(output).build();
	}


	//get the post id
	@GET
	@Path("/post")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyId(	@QueryParam("access_token") String access_token, 
			@QueryParam("id") String id) 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(TOKEN_MISSING_MESSAGE).build();

		if (id == null || id == "")
			return Response.status(500).entity(ID_MISSING_MESSAGE).build();

		String parameters = "?";
		parameters += "access_token=" + access_token;
		parameters += "&fields=id,message,story,created_time,from" ;

		String output = "{ \"data\": ["; 
		output += facebookRequest(GRAPH_API_URL + "/" + id + parameters);
		output += "]}";

		System.out.println("[Sending] To Client: " + output);
		// return HTTP response 200 in case of success
		return Response.status(200).entity(output).build();
	}


	//get the post by Date
	@GET
	@Path("/postdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyDate(	@QueryParam("access_token") String access_token, 
			@QueryParam("date") String date) throws JSONException 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(TOKEN_MISSING_MESSAGE).build();

		if (date == null || date == "")
			return Response.status(500).entity(DATE_MISSING_MESSAGE).build();

		if (!isDateFormat(date))
			return Response.status(500).entity(DATE_WRONG_FORMAT_MESSAGE).build();


		String parameters = "?";
		parameters += "access_token=" + access_token;
		parameters += "&fields=id,message,story,created_time,from" ;

		String output = facebookRequest(GRAPH_API_URL + "/me/feed" + parameters);

		if (ERROR.equals(output))
			return Response.status(500).entity(output).build();

		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ssZ");
			Date dateStart = sdf.parse(date + "T00:00:00+0000");
			Date dateEnd = sdf.parse(date + "T23:59:59+0000");


			JSONObject obj = new JSONObject(output);
			JSONArray data = obj.getJSONArray("data");
			for (int i = data.length() - 1; i >= 0; i--) 
			{
				JSONObject post = data.getJSONObject(i);
				String sPostDate = post.getString("created_time");
				if (sPostDate != null)
				{
					SimpleDateFormat sdfpost = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
					Date postDate = sdfpost.parse(sPostDate);
					if(postDate.compareTo(dateStart)<=0 || postDate.compareTo(dateEnd)>=0)
						data.remove(i);
				}
			}
			//rebuild the json object
			JSONObject objResult = new JSONObject();
			objResult.put("data",data );

			// return HTTP response 200 in case of success
			System.out.println("[Sending] To Client: " + objResult);
			return Response.status(200).entity(objResult.toString()).build();

		}
		catch(ParseException ex){
			ex.printStackTrace();

		}
		// return HTTP response 200 in case of success
		return Response.status(500).entity(DATE_WRONG_FORMAT_MESSAGE).build();
	}


	//get the posts having the following keyword
	@GET
	@Path("/postkeyword")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyKeyword(	@QueryParam("access_token") String access_token, 
			@QueryParam("keyword") String keyword) throws JSONException 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(TOKEN_MISSING_MESSAGE).build();

		if (keyword == null || keyword == "")
			return Response.status(500).entity(KEYWORD_MISSING_MESSAGE).build();


		String parameters = "?";
		parameters += "access_token=" + access_token;
		parameters += "&fields=id,message,story,created_time,from" ;

		String output = facebookRequest(GRAPH_API_URL + "/me/feed" + parameters);

		if (ERROR.equals(output))
			return Response.status(500).entity(output).build();

		JSONObject obj = new JSONObject(output);
		JSONArray data = obj.getJSONArray("data");
		for (int i = data.length() - 1; i >= 0; i--) 
		{
			JSONObject post = data.getJSONObject(i);

			String message = "";
			String story = "";
			if (!post.isNull("message"))
				message = post.getString("message").toUpperCase();
			if (!post.isNull("story"))
				story = post.getString("story").toUpperCase();

			keyword = keyword.toUpperCase();
			if (!message.contains(keyword) && !story.contains(keyword))
				data.remove(i);
		}

		//rebuild the json object
		JSONObject objResult = new JSONObject();
		objResult.put("data",data );

		// return HTTP response 200 in case of success
		System.out.println("[Sending] To Client: " + objResult);
		return Response.status(200).entity(objResult.toString()).build();
	}


	//get the posts having the following keyword
	@GET
	@Path("/postfriend")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyFriend(	@QueryParam("access_token") String access_token, 
			@QueryParam("friend") String friend) throws JSONException 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(TOKEN_MISSING_MESSAGE).build();

		if (friend == null || friend == "")
			return Response.status(500).entity(KEYWORD_MISSING_MESSAGE).build();


		String parameters = "?";
		parameters += "access_token=" + access_token;
		parameters += "&fields=id,message,story,created_time,from" ;

		String output = facebookRequest(GRAPH_API_URL + "/me/feed" + parameters);

		if (ERROR.equals(output))
			return Response.status(500).entity(output).build();

		JSONObject obj = new JSONObject(output);
		JSONArray data = obj.getJSONArray("data");
		for (int i = data.length() - 1; i >= 0; i--) 
		{
			JSONObject post = data.getJSONObject(i);

			if (post.isNull("from"))
			{
				data.remove(i);
				continue;
			}

			JSONObject from = post.getJSONObject("from");

			String name = "";
			String id = "";
			if (!from.isNull("name"))
				name = from.getString("name").toUpperCase();
			if (!from.isNull("story"))
				id = from.getString("id").toUpperCase();

			friend = friend.toUpperCase();
			if (!id.contains(friend) && !name.contains(friend))
				data.remove(i);

		}

		//rebuild the json object
		JSONObject objResult = new JSONObject();
		objResult.put("data",data );

		// return HTTP response 200 in case of success
		System.out.println("[Sending] To Client: " + objResult);
		return Response.status(200).entity(objResult.toString()).build();
	}



	//Call the Facebook Graph API and return the JSON 
	private String facebookRequest(String sUrl){
		String output = ERROR; 
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


	//check that date format to be YYYYMMDD
	//very basic check just on the length, we can add more check on the numbers, ...
	private boolean isDateFormat(String date)
	{
		if (date.length() != 8)
			return false;

		return true;
	}




}
