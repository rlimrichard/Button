package com.button.constants;

public abstract class Message {
	
	public static String 	TOKEN_MISSING_MESSAGE 		= "{'error': 'Parameter access_token missing in the query'}";
	public static String 	NETWORK_MISSING_MESSAGE 	= "{'error': 'Parameter network missing in the query'}";
	public static String 	ID_MISSING_MESSAGE 			= "{'error': 'Parameter id missing in the query'}";
	public static String 	DATE_MISSING_MESSAGE 		= "{'error': 'Parameter date missing in the query.'}";
	public static String 	DATE_WRONG_FORMAT_MESSAGE 	= "{'error': 'the date has to be in format YYYYMMDD'}";
	public static String 	KEYWORD_MISSING_MESSAGE 	= "{'error': 'Parameter keyword missing in the query.'}";
	public static String 	ERROR 						= "{'error': 'ERROR'}";

}
