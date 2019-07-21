package io.github.mariazevedo88.o3smeasures.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.jsefa.xml.XmlIOFactory;
import org.jsefa.xml.XmlSerializer;
import org.jsefa.xml.config.XmlConfiguration;
import org.jsefa.xml.namespace.QName;

import io.github.mariazevedo88.o3smeasures.plugin.Activator;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;

/**
 * Class that implements functions to export the measurement file into CSV or XML. The CSV format 
 * is stand for comma-separated values, CSV is a delimited data format that has fields/columns 
 * separated by the comma character and records/rows separated by newlines. The XML format is is 
 * used to describe data. The XML standard is a flexible way to create information formats and 
 * electronically share structured data via the public Internet, as well as via corporate networks.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class FileExport {

	private static final List<String[]> headerItems = new ArrayList<>();
	private static String tempFolderPath = "";
	private static String pathDelimiter = "\\";
	private static String desktopPath = "\\Desktop\\";
	
	static Logger logger = Logger.getLogger(FileExport.class);
	
	/**
	 * Method to set the path of the file (csv or xml) created.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private static void setFolderPath(){
		
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		String preferenceValue = prefStore.getString("PATH");
		
		if (preferenceValue.equals("")){
			//Treatment to export the file in Unix and Windows OS.
			if (System.getProperty("os.name").toLowerCase().contains("windows")){
				tempFolderPath = System.getProperty("user.home").concat(desktopPath);
			}else{
				tempFolderPath = System.getProperty("user.home");
			}
		}else{
			tempFolderPath = preferenceValue.concat(pathDelimiter);
		}
		
	}
	
	/**
	 * Method to build the csv file header.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private void populateHeader(){
		headerItems.clear();
		headerItems.add(new String[] {"Item", "Value", "Mean Value per Class", "Max Value", "Resource with Max Value", "Description"});
	}
	
	/**
	 * Method to populate the csv value with the measurement results. Which item 
	 * is a measure defined in the application.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param itemMeasured
	 * @return List
	 */
	private List<String[]> populateItems(ItemMeasured itemMeasured){
		
		List<String[]> recordItems = new ArrayList<>();
		for (ItemMeasured item : itemMeasured.getChildren()){
			recordItems.add(new String[] {item.toString(), 
					String.valueOf(item.getValue()), 
					String.valueOf(item.getMean()), 
					String.valueOf(item.getMax()),
					item.getClassWithMax(),
					item.getMeasure().getDescription()});
    	}
		
		return recordItems;
	}
	
	/**
	 * Method to create a csv file with the measurement result.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param outputFile
	 * @param item
	 * @throws IOException 
	 */
	public void createCSVFile(String outputFile, ItemMeasured item) throws IOException{
	
		setFolderPath();		
		populateHeader();
		
		File file = new File(tempFolderPath + outputFile + ".csv");
		if (!file.exists()){
			boolean isFileCreated = file.createNewFile();
			logger.info("File created " + isFileCreated);
		}
		
	    try (FileWriter fileWriter = new FileWriter(file, true);
	    	 CSVPrinter csvOutput = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
	    	
    		csvOutput.printRecords(headerItems);
    		List<String[]> it = populateItems(item);
    		csvOutput.printRecords(it);
    		
    		JOptionPane.showMessageDialog(null, "CSV file was created successfully!");
	        
	    }catch (IOException exception) {
	    	JOptionPane.showMessageDialog(null, "Error in CsvFileWriter!");
	    	logger.error(exception);
		} 
	}
	
	/**
	 * Method to create a xml file with the measurement result.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param outputFile
	 * @param itemMeasured
	 * @throws IOException 
	 */
	public void createXMLFile (String outputFile, ItemMeasured itemMeasured) throws IOException{
		
		setFolderPath();

    	File file = new File(tempFolderPath + outputFile + ".xml");
		if (!file.exists()){
			boolean isFileCreated = file.createNewFile();
    		logger.info("File created " + isFileCreated);
		}
		try (FileWriter fileWriter = new FileWriter(file, true)) {
			
			XmlConfiguration config = new XmlConfiguration();
			config.getSimpleTypeConverterProvider().registerConverterType(Double.class, JSefaConverter.class);
			XmlSerializer serializer = XmlIOFactory.createFactory(config, ItemMeasured.class).createSerializer();
			serializer.open(fileWriter);
			
			serializer.getLowLevelSerializer().writeXmlDeclaration("1.0", System.getProperty("file.encoding"));
			serializer.getLowLevelSerializer().writeStartElement(QName.create("measures")); 
			//calling serializer.write for every measure to serialize  
			for (ItemMeasured item : itemMeasured.getChildren()){
				serializer.write(item);
			}
			serializer.getLowLevelSerializer().writeEndElement();
			serializer.close(true);
			
			JOptionPane.showMessageDialog(null, "XML file was created successfully!");
			
		}catch (IOException exception) {
			logger.error(exception);
		}
	}
	
}
