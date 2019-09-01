package de.lkrause.eio.exceptions;

public class UnknownElementException extends RuntimeException {
	
	/** Serialization ID */
	private static final long serialVersionUID = 6412406463227448010L;

	public UnknownElementException(String pMessage) {
		super(pMessage);
	}

}
