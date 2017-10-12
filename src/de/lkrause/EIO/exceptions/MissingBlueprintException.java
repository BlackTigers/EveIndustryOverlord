package de.lkrause.EIO.exceptions;

public class MissingBlueprintException extends Exception {

	private static final long serialVersionUID = -8220083623892054811L;

	public MissingBlueprintException() {
		super();
	}
	
	public MissingBlueprintException(String pMessage) {
		super(pMessage);
	}
}
