package io.github.mariazevedo88.o3smeasures.main;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Class that inits an abstract syntax trees (ASTs), save items, and get items
 * from AST.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class ASTSession {

	private static ASTSession ast;
	private MutableMap<ITypeRoot, CompilationUnit> units;
	
	private ASTSession() {
		units = Maps.mutable.empty();
	}
	
	/**
	 * Method that creates a ASTSession instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return ASTSession
	 */
	public static ASTSession getInstance(){
		if (ast == null) {
			ast = new ASTSession();
		}
		return ast;
	} 
	
	/**
	 * Method that insert an compilation unit on the map
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param unit
	 */
	public void save(CompilationUnit unit){
		units.put(unit.getTypeRoot(), unit);
	}

	/**
	 * Method that gets a specific compilation unit on the map
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param unit
	 * @return CompilationUnit
	 */
	public CompilationUnit get(ITypeRoot unit){
		return units.get(unit);
	}
	
	/**
	 * Method that verifies if an unit it is contained within the units' map
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param unit
	 * @return boolean
	 */
	public boolean contains(ITypeRoot unit){
		return units.containsKey(unit);
	}
	
	/**
	 * Method that cleans the units map
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void reset(){
		units.clear();
	}
}
