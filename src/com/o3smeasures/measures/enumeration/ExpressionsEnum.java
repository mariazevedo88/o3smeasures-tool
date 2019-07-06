package com.o3smeasures.measures.enumeration;

public enum ExpressionsEnum {

	SETTER_METHOD("set"),
	GETTER_METHOD("get");
	
	private String expression;
	
	private ExpressionsEnum(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}
}
