package com.learner.util;

public class ExceptionHandler {

	public static String printExceptionDetails(Exception e) {
		String message = null;
		int numberOfLines = 30;
		for (int i = 0; i < numberOfLines; i++) {
			message += e.getStackTrace()[i].toString() + "\n";
		}
		return message;
	}

}
