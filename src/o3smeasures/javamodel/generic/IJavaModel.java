package o3smeasures.javamodel.generic;

/**
 * Interface to calculate measures based on the java model.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public interface IJavaModel<T> {

	/**
	 * Method that calculate the value of a measure using Java Model
	 * structure
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param unit
	 */
	public void calculateValue(T unit); 
	
}
