package io.github.mariazevedo88.o3smeasures.util.exception;

/**
 * Class that implements FactorNotFoundException
 * 
 * @author Mariana Azevedo
 * @since 30/09/2017
 */
public class FactorNotFoundException extends Exception{

	private static final long serialVersionUID = -7460261477332564260L;
	
	public FactorNotFoundException(String msg){
		super(msg);
	}

	public FactorNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
}
