package com.button.challenge;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import com.button.constants.Message;
import com.button.constants.Network;
import com.button.model.Post;
import com.button.model.Posts;
import com.button.model.User;
import com.button.network.FacebookApi;
import com.button.network.NetworkApi;

@Path("/api")
public class Routing {

	private ObjectMapper mapper = new ObjectMapper();


	//to test the API
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public Response pingApi(InputStream incomingData) {
		String result = "API reached... Ping!";

		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}



	//Get All posts to be displayed
	@GET
	@Path("/allposts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPosts(@QueryParam("access_token") String access_token, @QueryParam("network") String network) 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(Message.TOKEN_MISSING_MESSAGE).build();

		if (network == null || network == "")
			return Response.status(500).entity(Message.NETWORK_MISSING_MESSAGE).build();

		NetworkApi currentNetwork = this.callApi(access_token, network);
		Posts allPosts = currentNetwork.getAllPosts();
		String output = Message.ERROR;
		if (allPosts != null)
		{
			try {
				output = mapper.writeValueAsString(allPosts);
				return Response.status(200).entity(output).build();
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.status(500).entity(Message.ERROR).build();	
	}


	//get the post id
	@GET
	@Path("/post")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyId(	@QueryParam("access_token") String access_token,
			@QueryParam("network") String network, 
			@QueryParam("id") String id) 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(Message.TOKEN_MISSING_MESSAGE).build();

		if (network == null || network == "")
			return Response.status(500).entity(Message.NETWORK_MISSING_MESSAGE).build();

		if (id == null || id == "")
			return Response.status(500).entity(Message.ID_MISSING_MESSAGE).build();

		NetworkApi currentNetwork = this.callApi(access_token, network);
		Posts allPosts = currentNetwork.getPost(id);
		String output = Message.ERROR;
		if (allPosts != null)
		{
			try {
				output = mapper.writeValueAsString(allPosts);
				return Response.status(200).entity(output).build();
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.status(500).entity(Message.ERROR).build();	
	}


	//get the post by Date
	@GET
	@Path("/postdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyDate(	@QueryParam("access_token") String access_token, 
			@QueryParam("network") String network, 
			@QueryParam("date") String date) throws JSONException 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(Message.TOKEN_MISSING_MESSAGE).build();

		if (network == null || network == "")
			return Response.status(500).entity(Message.NETWORK_MISSING_MESSAGE).build();

		if (date == null || date == "")
			return Response.status(500).entity(Message.DATE_MISSING_MESSAGE).build();

		if (!isDateFormat(date))
			return Response.status(500).entity(Message.DATE_WRONG_FORMAT_MESSAGE).build();

		NetworkApi currentNetwork = this.callApi(access_token, network);
		Posts allPosts = currentNetwork.getAllPosts();
		String output = Message.ERROR;
		if (allPosts != null)
		{
			allPosts = SearchPostsForDate(allPosts, date);

			try {
				output = mapper.writeValueAsString(allPosts);
				return Response.status(200).entity(output).build();
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.status(500).entity(Message.ERROR).build();	
	}


	//get the posts having the following keyword
	@GET
	@Path("/postkeyword")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyKeyword(	@QueryParam("access_token") String access_token, 
			@QueryParam("network") String network, 
			@QueryParam("keyword") String keyword) throws JSONException 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(Message.TOKEN_MISSING_MESSAGE).build();

		if (network == null || network == "")
			return Response.status(500).entity(Message.NETWORK_MISSING_MESSAGE).build();

		if (keyword == null || keyword == "")
			return Response.status(500).entity(Message.KEYWORD_MISSING_MESSAGE).build();


		NetworkApi currentNetwork = this.callApi(access_token, network);
		Posts allPosts = currentNetwork.getAllPosts();
		String output = Message.ERROR;
		if (allPosts != null)
		{
			allPosts = SearchPostsForKeyword(allPosts, keyword);

			try {
				output = mapper.writeValueAsString(allPosts);
				return Response.status(200).entity(output).build();
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.status(500).entity(Message.ERROR).build();	
	}

	

	//get the posts having the following keyword
	@GET
	@Path("/postfriend")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPostbyFriend(	@QueryParam("access_token") String access_token, 
			@QueryParam("network") String network, 
			@QueryParam("friend") String friend) throws JSONException 
	{
		if (access_token == null || access_token == "")
			return Response.status(500).entity(Message.TOKEN_MISSING_MESSAGE).build();
		
		if (network == null || network == "")
			return Response.status(500).entity(Message.NETWORK_MISSING_MESSAGE).build();

		if (friend == null || friend == "")
			return Response.status(500).entity(Message.KEYWORD_MISSING_MESSAGE).build();

		NetworkApi currentNetwork = this.callApi(access_token, network);
		Posts allPosts = currentNetwork.getAllPosts();
		String output = Message.ERROR;
		if (allPosts != null)
		{
			allPosts = SearchPostsForFriend(allPosts, friend);

			try {
				output = mapper.writeValueAsString(allPosts);
				return Response.status(200).entity(output).build();
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.status(500).entity(Message.ERROR).build();	
	}
	


	//Search all the posts from the date passed in parameters
	private Posts SearchPostsForDate(Posts allPosts, String date)
	{
		//Posts resultPosts = allPosts;	
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ssZ");
			Date dateStart = sdf.parse(date + "T00:00:00+0000");
			Date dateEnd = sdf.parse(date + "T23:59:59+0000");

			Iterator<Post> it = allPosts.getData().iterator();
			ArrayList<Post> postList = new ArrayList<Post>();
			while (it.hasNext())
			{
				Post currentPost = it.next();
				String sPostDate = currentPost.getCreated_time();

				if (sPostDate != null)
				{
					SimpleDateFormat sdfpost = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
					Date postDate = sdfpost.parse(sPostDate);
					if(postDate.compareTo(dateStart)>=0 && postDate.compareTo(dateEnd)<=0)
						postList.add(currentPost);
				}
			}
			allPosts.setData(postList);
		}
		catch(ParseException ex){
			ex.printStackTrace();
		}
		return allPosts;
	}


	//check that date format to be YYYYMMDD
	//very basic check just on the length, we can add more check on the numbers, ...
	private boolean isDateFormat(String date)
	{
		if (date.length() != 8)
			return false;

		return true;
	}


	//Search all the posts from the keyword passed in parameters
	private Posts SearchPostsForKeyword(Posts allPosts, String keyword)
	{
		keyword = (keyword != null) ? keyword.toUpperCase() : "";
		
		Iterator<Post> it = allPosts.getData().iterator();
		ArrayList<Post> postList = new ArrayList<Post>();
		while (it.hasNext())
		{
			Post currentPost = it.next();
			String message = currentPost.getMessage();
			String story = currentPost.getStory();
			
			message = (message != null) ? message.toUpperCase() : "";
			story 	= (story != null) 	? story.toUpperCase() 	: "";

			if (message.contains(keyword) || story.contains(keyword))
				postList.add(currentPost);			
		}
		allPosts.setData(postList);
		
		return allPosts;
	}

	
	//Search all the posts from friend name passed in parameters
	private Posts SearchPostsForFriend(Posts allPosts, String friend)
	{
		friend = (friend != null) ? friend.toUpperCase() : "";
		
		Iterator<Post> it = allPosts.getData().iterator();
		ArrayList<Post> postList = new ArrayList<Post>();
		while (it.hasNext())
		{
			Post currentPost = it.next();
			User currentUser = currentPost.getFrom();
			String id  = currentUser.getId();
			String name  = currentUser.getName();
			
			id 		= (id != null) 		? id.toUpperCase() 		: "";
			name 	= (name != null) 	? name.toUpperCase() 	: "";
			
			if (id.contains(friend) || name.contains(friend))
				postList.add(currentPost);			
		}
		allPosts.setData(postList);
		
		return allPosts;
	}
	

	//Convert the network to the right Network API
	private NetworkApi callApi(String access_token, String network)
	{
		if (Network.FACEBOOK.equals(network))
			return new FacebookApi(access_token);
		//else if (Network.TWITTER.equals(network))
		//	return new TwitterApi(access_token);
		//else if (Network.LINKEDIN.equals(network))
		//	return new LinkedinApi(access_token);


		return new NetworkApi(access_token);
	}

}
