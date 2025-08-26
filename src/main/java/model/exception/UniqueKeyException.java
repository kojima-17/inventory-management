package model.exception;

public class UniqueKeyException extends RuntimeException{
	public UniqueKeyException(String message) {
		super(message);
	}
}
