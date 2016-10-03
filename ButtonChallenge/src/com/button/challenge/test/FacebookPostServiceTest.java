package com.button.challenge.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacebookPostServiceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try{
			
			String date = "20160930";
			
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
			else
				System.out.println("in between");

    	}catch(ParseException ex){
    		ex.printStackTrace();
    	}
		
		
	}

}
