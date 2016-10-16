package com.button.challenge.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacebookPostServiceTest {
	
	public static String access_token = "EAAPBkRlr8AwBAFTs0f0yYJICBTUym1nZA7jTZApJ6cgtA2Hpkg6xBJqBRhcXJZAnZAqMLiW44ryoekJKoLVZAuMLPKpYi6mebLyGhJyx6Tb9pHqvWgOePIJLgZByxM8ExDCsHX7THJZCJQpdJalqOuHNad2CJBSg276XRcVvBd5hgZDZD";


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
//		FacebookApi fbapi = new FacebookApi(access_token);
	//	PostsFacebook posts = (PostsFacebook) fbapi.getAllPosts();
			
		//for (int i=0 ; i<posts.getData().size() ; i++)
		//	System.out.println(posts.getData().toString());
		
		testDate();


	}


	public static void  testDate(){

		try{

			String date = "20160919";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ssZ");
			Date dateStart = sdf.parse(date + "T00:00:00+0000");
			Date dateEnd = sdf.parse(date + "T23:59:59+0000");

			SimpleDateFormat sdfpost = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			Date postDate = sdfpost.parse("2016-09-18T09:21:58+0000");
			//"yyyy-MM-dd'T'HH:mm:ss.SSSZ"	2001-07-04T12:08:56.235-0700
			System.out.println("postDate.compareTo(dateStart) = " + postDate.compareTo(dateStart));
			System.out.println("postDate.compareTo(dateEnd) = " + postDate.compareTo(dateEnd));
			if(postDate.compareTo(dateStart)<=0 || postDate.compareTo(dateEnd)>=0)
				System.out.println("not in between");
			else if(postDate.compareTo(dateStart)>=0 && postDate.compareTo(dateEnd)<=0)
				System.out.println("in between");
			else
				System.out.println("idk");

		}catch(ParseException ex){
			ex.printStackTrace();
		}


	}


}
