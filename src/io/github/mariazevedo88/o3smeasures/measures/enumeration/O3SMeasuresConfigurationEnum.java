package io.github.mariazevedo88.o3smeasures.measures.enumeration;

import io.github.mariazevedo88.o3smeasures.util.exception.FactorNotFoundException;

/**
 * Enum to describe measure's configurations. See reference here: http://repositorio.ufla.br/handle/1/10561
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 */
public enum O3SMeasuresConfigurationEnum {
	
	//The weights reference here: http://repositorio.ufla.br/handle/1/10561
	NUMBER_CLASSES("Number of Classes", "None", 0.0),
	NUMBER_METHODS("Number of Methods", 
			FactorsEnum.COMPLEXITY_REDUCTION.getName(), 0.788),
	NUMBER_ATTRIBUTES("Number of Attributes", 
			FactorsEnum.COMPLEXITY_REDUCTION.getName(), 0.818),
	LINES_CODE("Lines of Code", 
			FactorsEnum.COMPLEXITY_REDUCTION.getName(), 0.937),
	WEIGHT_METHODS_CLASS("Weight Methods per Class", 
			FactorsEnum.COMPLEXITY_REDUCTION.getName(), 0.750),
	CYCLOMATIC_COMPLEXITY("Cyclomatic Complexity", 
			FactorsEnum.COMPLEXITY_REDUCTION.getName(), 0.913),
	DEPTH_INHERITANCE_TREE("Depth of Inheritance Tree", "None", 0.0),
	NUMBER_CHILDREN("Number of Children", "None", 0.0),
	COUPLING_BETWEEN_OBJECTS("Coupling between Objects", "None", 0.0),
	RESPONSE_FOR_CLASS("Response for Class", 
			FactorsEnum.COMPLEXITY_REDUCTION.getName(), 0.744),
	FAN_OUT("Fan-out", FactorsEnum.INCREASE_MODULARITY.getName(), 0.899),
	LACK_COHESION_METHODS("Lack of Cohesion of Methods", 
			FactorsEnum.HIGH_COHESION_METHODS.getName(), 0.983),
	LACK_COHESION_METHODS2("Lack of Cohesion of Methods 2", 
			FactorsEnum.INCREASE_MODULARITY.getName(), 0.849),
	LACK_COHESION_METHODS4("Lack of Cohesion of Methods 4", 
			FactorsEnum.HIGH_COHESION_METHODS.getName(), 0.981),
	TIGHT_CLASS_COHESION("Tight Class Cohesion", 
			FactorsEnum.TIGHT_LOOSE_COHESION.getName(), 0.950),
	LOOSE_CLASS_COHESION("Loose Class Cohesion", 
			FactorsEnum.TIGHT_LOOSE_COHESION.getName(), 0.949);
	
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

	public Double getWeight() {
		return weight;
	}

	public static O3SMeasuresConfigurationEnum searchByValue (String value) throws FactorNotFoundException {
		for (O3SMeasuresConfigurationEnum measureConf : values()) {
			if (measureConf.getMeasure().equals(value)) {
				return measureConf;
			}
		}
		throw new FactorNotFoundException("Invalid factor: " + value);
	}
}
