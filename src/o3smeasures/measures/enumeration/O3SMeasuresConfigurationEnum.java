package o3smeasures.measures.enumeration;

/**
 * Enum to describe measure's configurations. See reference here: http://repositorio.ufla.br/handle/1/10561
 * @author Mariana Azevedo
 * @since 16/04/2017
 * 
 */
public enum O3SMeasuresConfigurationEnum {
	
	//The weights reference here: http://repositorio.ufla.br/handle/1/10561
	NUMBER_CLASSES("Number of Classes", "None", 0.0),
	NUMBER_METHODS("Number of Methods", "Complexity Reduction", 0.788),
	NUMBER_ATTRIBUTES("Number of Attributes", "Complexity Reduction", 0.818),
	LINES_CODE("Lines of Code", "Complexity Reduction", 0.937),
	WEIGHT_METHODS_CLASS("Weight Methods per Class", "Complexity Reduction", 0.750),
	CYCLOMATIC_COMPLEXITY("Cyclomatic Complexity", "Complexity Reduction", 0.913),
	DEPTH_INHERITANCE_TREE("Depth of Inheritance Tree", "None", 0.0),
	NUMBER_CHILDREN("Number of Children", "None", 0.0),
	COUPLING_BETWEEN_OBJECTS("Coupling between Objects", "None", 0.0),
	RESPONSE_FOR_CLASS("Response for Class", "Complexity Reduction", 0.744),
	FAN_OUT("Fan-out", "Increase of Modularity", 0.899),
	LACK_COHESION_METHODS("Lack of Cohesion of Methods", "High Cohesion of Methods", 0.983),
	LACK_COHESION_METHODS2("Lack of Cohesion of Methods 2", "Increase of Modularity", 0.849),
	LACK_COHESION_METHODS4("Lack of Cohesion of Methods 4", "High Cohesion of Methods", 0.981),
	TIGHT_CLASS_COHESION("Tight Class Cohesion", "Tight and Loose Class Cohesion", 0.950),
	LOOSE_CLASS_COHESION("Loose Class Cohesion", "Tight and Loose Class Cohesion", 0.949);
	
	private String measure;
	private String factor;
	private Double weight;
	
	private O3SMeasuresConfigurationEnum(String measure, String factor, Double weight) {
		this.measure = measure;
		this.factor = factor;
		this.weight = weight;
	}

	public String getMeasure() {
		return measure;
	}

	public String getFactor() {
		return factor;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}
	
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public static O3SMeasuresConfigurationEnum searchByValue (String value) throws Exception {
		for (O3SMeasuresConfigurationEnum measureConf : values()) {
			if (measureConf.getMeasure().equals(value)) {
				return measureConf;
			}
		}
		throw new Exception("Invalid register: " + value);
	}

}
