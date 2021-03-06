package io.github.mariazevedo88.o3smeasures.util.parsers;

import org.jsefa.common.converter.SimpleTypeConverter;

/**
 * Class that implements a converter to be used by JSefa. Some types such as Double 
 * type is not supported by JSefa. For example, the DoubleConverter class should be provided, 
 * implementing a JSefa Interface SimpleTypeConverter.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class JSefaConverter implements SimpleTypeConverter {
	private static final JSefaConverter INSTANCE = new JSefaConverter();
	
	public static JSefaConverter create() {
		return INSTANCE;
	}
	private JSefaConverter() {}
	
	@Override
	public Object fromString(String str) {
		return Double.valueOf(str);
	}
	@Override
	public String toString(Object doubleObj) {
		return doubleObj.toString();
	}
}

 

