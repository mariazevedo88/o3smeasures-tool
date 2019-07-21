package io.github.mariazevedo88.o3smeasures.measures.enumeration;

/**
 * Enum to describe internal quality factors. See reference here: http://repositorio.ufla.br/handle/1/10561
 * @author Mariana Azevedo
 * @since 16/04/2017
 * 
 */
public enum FactorsEnum {
	
	INCREASE_MODULARITY("Increase of Modularity"),
	HIGH_COHESION_METHODS("High Cohesion of Methods"),
	TIGHT_LOOSE_COHESION("Tight and Loose Class Cohesion"),
	COMPLEXITY_REDUCTION("Complexity Reduction"),
	NONE("None");
	
	private String name;
	
	private FactorsEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
