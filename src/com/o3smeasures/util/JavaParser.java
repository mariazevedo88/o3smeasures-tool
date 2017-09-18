package com.o3smeasures.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.o3smeasures.main.ASTSession;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;

/**
 * Class that implements a parser with AST generation and visitor support. 
 * The AST records the source code structure, javadoc and comments. 
 * It is also possible to change the AST nodes or create new ones to modify 
 * the source code.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class JavaParser {
	
	 static Logger logger = Logger.getLogger(JavaParser.class);	
	
	 private static ASTParser astParser;
	 private static boolean cacheParser = true;
	 
	 private JavaParser(){}
	 
	 /**
	  * Method to check if a parser is already instantiated.
	  * @author Mariana Azevedo
	  * @since 13/07/2014
	  * @param value
	  */
	 public static void setCacheParser(boolean value) {
        cacheParser = value;
        if (!value) {
            astParser = null;
        }
     }
	 
	 /**
	  * Method that parses a compilation unit and creates the AST DOM to manipulate
	  * the source code on an file.
	  * @author Mariana Azevedo
	  * @since 13/07/2014
	  * @param fileInputStream
	  * @return CompilationUnit
	  * @throws ParseException
	  */
	 public static CompilationUnit parseAST(FileInputStream fileInputStream){
		
		 CompilationUnit cUnit = null; 
		 ASTSession.getInstance().reset();
		 
		 if (cacheParser) {
		   astParser = ASTParser.newParser(AST.JLS8);
           astParser.setKind(ASTParser.K_COMPILATION_UNIT);
   		   astParser.setResolveBindings(true);
           char[] source = getFileContent(fileInputStream);
           
	       astParser.setSource(source);
	       cUnit = (CompilationUnit) astParser.createAST(null);
        }
		astParser = null; 
        
        return cUnit;
	 }
	 
	 /**
	  * Method that parses a file and creates the JDT DOM to manipulate
	  * the source code on an file.
	  * @author Mariana Azevedo
	  * @since 13/07/2014
	  * @param file
	  * @return ICompilationUnit
	  * @throws ParseException
	  */
	 public static ICompilationUnit parseJDT(File file){
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath path = Path.fromOSString(file.getAbsolutePath());
		IFile source = workspace.getRoot().getFileForLocation(path);
		return (ICompilationUnit) JavaCore.create(source);
	 }

	 /**
	  * Method to get the content of java file.
	  * @author Mariana Azevedo
	  * @since 13/07/2014
	  * @param fileInputStream
	  * @return char[]
	  */
	public static char[] getFileContent(FileInputStream fileInputStream) {
	    StringBuilder strBuilder = new StringBuilder();
	    Reader reader;
		try {
			reader = new InputStreamReader(fileInputStream, "UTF-8");
			char[] buffer = new char[1024];
			int charAmount = reader.read(buffer);
			while(charAmount > 0) {
				strBuilder.append(buffer, 0, charAmount);
				charAmount = reader.read(buffer);
			} 
		} catch (IOException exception) {
			logger.error(exception);
	    }
		
	    return strBuilder.toString().toCharArray();
	}
	
	/**
	 * Method to get the java file to parse.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param javaFile
	 * @param className
	 * @return CompilationUnit
	 * @throws ParseException
	 * @throws IOException
	 */
	public static CompilationUnit getJavaFile(File javaFile, String className) throws IOException{
		
		CompilationUnit cUnit;
        FileInputStream inputStream = new FileInputStream(javaFile);
        try {
        	logger.info("The class '"+className+"' has: ");
        	cUnit = parseAST(inputStream);
        } finally {
            inputStream.close();
        }
		return cUnit;
	}

}
