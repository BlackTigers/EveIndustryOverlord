package de.lkrause.eio.exceptions;

public class InvalidTypeException extends RuntimeException {

	/** Serialization ID */
	private static final long serialVersionUID = 1L;

	public InvalidTypeException(String pTypeName) {
		super(pTypeName + " not found.");
	}
}
