package com.learner.util;

public class ExceptionHandler {

	public static String printExceptionDetails(Exception e) {
		String message = null;
		int numberOfLines = 20;
		for (int i = 0; i < numberOfLines; i++) {
			message += e.getStackTrace()[i].toString() + "\n" + "<br/>";
		}
		return message;
	}

}
