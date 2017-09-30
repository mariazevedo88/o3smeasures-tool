package com.o3smeasures.util.exception;

public class FactorNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460261477332564260L;
	
	public FactorNotFoundException(String msg){
		super(msg);
	}

	public FactorNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
}
