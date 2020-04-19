package io.github.mariazevedo88.o3smeasures.util.filehandlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.jsefa.xml.XmlIOFactory;
import org.jsefa.xml.XmlSerializer;
import org.jsefa.xml.config.XmlConfiguration;
import org.jsefa.xml.namespace.QName;

import io.github.mariazevedo88.o3smeasures.plugin.Activator;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.util.parsers.JSefaConverter;

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

	private static final MutableList<String[]> headerItems = Lists.mutable.empty();
	private static final String PATH_DELIMITER = "\\";
	private static final String DESKTOP_PATH = "\\Desktop\\";
	
	private static final Logger logger = Logger.getLogger(FileExport.class);
	
	private String tempPath;
	
	/**
	 * Method to set the path of the file (csv or xml) created.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return String
	 */
	public String setFolderPath(){
		
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		String preferenceValue = prefStore.getString("PATH");
		
		if (preferenceValue.equals("")){
			//Treatment to export the file in Unix and Windows OS.
			if (System.getProperty("os.name").toLowerCase().contains("windows")){
				tempPath = System.getProperty("user.home").concat(DESKTOP_PATH);
			}else{
				tempPath = System.getProperty("user.home");
			}
		}else{
			tempPath = preferenceValue.concat(PATH_DELIMITER);
		}
		
		return tempPath;
	}
	
	/**
	 * Method to build the csv file header.
	 * 
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
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param itemMeasured
	 * 
	 * @return List
	 */
	private MutableList<String[]> populateItems(ItemMeasured itemMeasured){
		
		MutableList<String[]> recordItems = Lists.mutable.empty();
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
	 * Method that creates a CSV file
	 * 
	 * @author Mariana Azevedo
	 * @since 25/01/2019
	 * 
	 * @param outputFileName
	 * @return File
	 * @throws IOException
	 */
	private File createCSVFile(String outputFileName) throws IOException {
		
		File file = new File(tempPath + outputFileName + ".csv");
		if (!file.exists()){
			boolean isFileCreated = file.createNewFile();
			logger.info("File " + file.getName() + " created => " + isFileCreated);
		}
		
		return file;
	}
	
	/**
	 * Method that fill the csv file created
	 * 
	 * @author Mariana Azevedo
	 * @since 25/01/2019
	 * 
	 * @param item
	 * @param csvOutput
	 * @throws IOException
	 */
	private void createCSVFileContent(ItemMeasured item, CSVPrinter csvOutput) throws IOException {
		
		csvOutput.printRecords(headerItems);
		MutableList<String[]> it = populateItems(item);
		csvOutput.printRecords(it);
		
	}
	
	/**
	 * Method that creates a XML file
	 * 
	 * @author Mariana Azevedo
	 * @since 27/01/2019
	 * 
	 * @param outputFileName
	 * @return File
	 * @throws IOException
	 */
	private File createXMLFile(String outputFile) throws IOException {
		
		File file = new File(tempPath + outputFile + ".xml");
		if (!file.exists()){
			boolean isFileCreated = file.createNewFile();
    		logger.info("File " + file.getName() + " created => " + isFileCreated);
		}
		
		return file;
	}
	
	/**
	 * Method that fill the xml file created
	 * 
	 * @author Mariana Azevedo
	 * @since 27/01/2019
	 * 
	 * @param itemMeasured
	 * @param fileWriter
	 */
	private void createXMLFileContent(ItemMeasured itemMeasured, FileWriter fileWriter) {
		
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
	}

	/**
	 * Method to create a csv file with the measurement result.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param outputFileName
	 * @param item
	 * 
	 * @throws IOException 
	 */
	public void createCSVFile(String outputFileName, ItemMeasured item) throws IOException{
	
		setFolderPath();		
		populateHeader();
		
		File file = createCSVFile(outputFileName);
		
	    try (FileWriter fileWriter = new FileWriter(file, true);
	    	CSVPrinter csvOutput = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
	    	
    		createCSVFileContent(item, csvOutput);
    		JOptionPane.showMessageDialog(null, "CSV file was created successfully!");
	        
	    }catch (IOException exception) {
	    	JOptionPane.showMessageDialog(null, "Error in CsvFileWriter!");
	    	logger.error(exception);
		} 
	}

	/**
	 * Method to create a csv file with the measurement result to upload to cloud storage.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/01/2020
	 * 
	 * @param outputFileName
	 * @param item
	 * 
	 * @throws IOException 
	 */
	public void createCSVFileToUpload(String outputFileName, ItemMeasured item) throws IOException{
	
		setFolderPath();		
		populateHeader();
		
		File file = createCSVFile(outputFileName);
		
	    try (FileWriter fileWriter = new FileWriter(file, true);
	    	CSVPrinter csvOutput = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
    		createCSVFileContent(item, csvOutput);
    		
	    }catch (IOException exception) {
	    	logger.error(exception);
		} 
	}
	
	/**
	 * Method to create a xml file with the measurement result.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param outputFile
	 * @param itemMeasured
	 * 
	 * @throws IOException 
	 */
	public void createXMLFile(String outputFile, ItemMeasured itemMeasured) throws IOException{
		
		setFolderPath();

    	File file = createXMLFile(outputFile);
		
		try (FileWriter fileWriter = new FileWriter(file, true)) {
			
			createXMLFileContent(itemMeasured, fileWriter);
			JOptionPane.showMessageDialog(null, "XML file was created successfully!");
			
		}catch (IOException exception) {
			logger.error(exception);
		}
	}
	
	/**
	 * Method to create a xml file with the measurement result to upload to cloud storage.
	 * 
	 * @author Mariana Azevedo
	 * @since 27/01/2020
	 * 
	 * @param outputFile
	 * @param itemMeasured
	 * 
	 * @throws IOException 
	 */
	public void createXMLFileToUpload(String outputFile, ItemMeasured itemMeasured) throws IOException{
		
		setFolderPath();

    	File file = createXMLFile(outputFile);
		
		try (FileWriter fileWriter = new FileWriter(file, true)) {
			createXMLFileContent(itemMeasured, fileWriter);
		}catch (IOException exception) {
			logger.error(exception);
		}
	}
	
}
