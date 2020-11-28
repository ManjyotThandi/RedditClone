package com.testapplication.reddit.exceptions;

public class SpringRedditException extends RuntimeException {

	// creating this so we can have our own custom exception thrown to user rather
	// than the internal details of runtime exception
	// essentially just calling super method with our own custom message
	
	public SpringRedditException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
