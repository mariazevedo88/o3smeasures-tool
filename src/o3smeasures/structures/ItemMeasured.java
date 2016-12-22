package o3smeasures.structures;

import java.util.ArrayList;
import java.util.List;

import org.jsefa.xml.annotation.XmlDataType;
import org.jsefa.xml.annotation.XmlElement;

/**
 * Class that represents the an item measured by the plug-in, that could be a package, 
 * a compilation unit or a method. It also records the measurement value, the mean, 
 * the parent item of the measure and all the children items.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@XmlDataType(defaultElementName = "measure")
public class ItemMeasured {

	@XmlElement(pos = 1)
	private String name;
	@XmlElement(pos = 2)
	private Double value;
	@XmlElement(pos = 3)
	private Double mean;
	@XmlElement(pos = 4)
	private Double max;
	@XmlElement(pos = 5)
	private String classWithMaxValue;
	private ItemMeasured parent;
	private List<ItemMeasured> children;
	private Measure measure;
	
	public ItemMeasured(){}
	
	public ItemMeasured(String name, ItemMeasured parent) {
		this.name     = name;
		this.parent   = parent;
		this.children = new ArrayList<ItemMeasured>();
		this.value    = 0d;
		this.mean     = 0d;
		this.max      = 0d;
		this.classWithMaxValue = "";
	}

	/**
	 * Method to get the name of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param
	 * @return String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Method to get the parent of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param
	 * @return ItemMeasured
	 */
	public ItemMeasured getParent(){
		return parent;
	}
	
	/**
	 * Method to set the parent of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param item
	 */
	public void setParent(ItemMeasured item){
		parent = item;
	}
	
	/**
	 * Method to get the children of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return List<ItemMeasured>
	 */
	public List<ItemMeasured> getChildren(){
		return children;
	}
	
	/**
	 * Method to add a child in a list of item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param item
	 */
	public void addChild(ItemMeasured item){
		children.add(item);
	}
	
	/**
	 * Method to get the value of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public double getValue(){
		return value;
	}
	
	/**
	 * Method to set the value of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param value
	 */
	public void setValue(double value){
		this.value = value;
	}
	
	/**
	 * Method to add a value in the list of item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param value
	 */
	public void addValue(double value){
		this.value += value;
	}
	
	/**
	 * Method to get the mean of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public double getMean(){
		return mean;
	}
	
	/**
	 * Method to set the mean of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param mean
	 */
	public void setMean(double mean){
		this.mean = mean;
	}
	
	/**
	 * Method to add the mean in the list of item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param mean
	 */
	public void addMean(double mean){
		this.mean += mean;
	}
	
	/**
	 * Method to get the max value of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public double getMax(){
		return max;
	}
	
	/**
	 * Method to set the max value of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param max
	 */
	public void setMax(double max){
		this.max = max;
	}
	
	/**
	 * Method to get the class with the max value of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public String getClassWithMax(){
		return classWithMaxValue;
	}
	
	/**
	 * Method to set the class with the max value of the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param max
	 */
	public void setClassWithMax(String classWithMaxValue){
		this.classWithMaxValue = classWithMaxValue;
	}
	
	/**
	 * Method to get the measure represented by the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Measure
	 */
	public Measure getMeasure() {
		return measure;
	}

	/**
	 * Method to set the measure represented by the item measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param measure
	 */
	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	/**
	 * @see String#toString()
	 */
	public String toString() {
		return name;
	}
}
