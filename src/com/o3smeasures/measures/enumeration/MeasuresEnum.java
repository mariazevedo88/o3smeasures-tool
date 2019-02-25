package com.o3smeasures.measures.enumeration;

public enum MeasuresEnum {
	
	CBO("CBO", "Coupling between Objects"),
	CC("CC", "Cyclomatic Complexity"),
	DIT("DIT", "Depth of Inheritance Tree"),
	FAN_OUT("FOUT", "Fan-out"),
	LCOM("LCOM", "Lack of Cohesion of Methods"),
	LCOM2("LCOM2", "Lack of Cohesion of Methods 2"),
	LCOM4("LCOM4", "Lack of Cohesion of Methods 4"),
	LOC("LOC", "Lines of Code"),
	LCC("LCC", "Loose Class Cohesion"),
	NOA("NOA", "Number of Attributes"),
	NOC("NOC", "Number of Children"),
	NC("NC", "Number of Classes"),
	NOM("NOM", "Number of Methods"),
	RFC("RFC", "Response for Class"),
	TCC("TCC", "Tight and Loose Class Cohesion"),
	WMC("WMC", "Weight Methods per Class");
	
	private String acronym;
	private String name;
	
	private MeasuresEnum(String acronym, String name) {
		this.acronym = acronym;
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public String getName() {
		return name;
	}

}
