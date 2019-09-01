package de.lkrause.eio.database;

public class DataAccessException extends RuntimeException {

	/** Serialization ID */
	private static final long serialVersionUID = -4847935091799941777L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String pArg0, Throwable pArg1, boolean pArg2, boolean pArg3) {
		super(pArg0, pArg1, pArg2, pArg3);
	}

	public DataAccessException(String pArg0, Throwable pArg1) {
		super(pArg0, pArg1);
	}

	public DataAccessException(String pArg0) {
		super(pArg0);
	}

	public DataAccessException(Throwable pArg0) {
		super(pArg0);
	}

}
