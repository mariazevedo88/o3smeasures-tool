package o3smeasures.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import o3smeasures.plugin.Activator;
import o3smeasures.structures.ItemMeasured;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.jsefa.xml.XmlIOFactory;
import org.jsefa.xml.XmlSerializer;
import org.jsefa.xml.config.XmlConfiguration;
import org.jsefa.xml.namespace.QName;

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

	private static final List<String[]> headerItems = new ArrayList<String[]>();
	public String TEMP_FOLDER_PATH = "";
	
	/**
	 * Method to set the path of the file (csv or xml) created.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private void setFolderPath(){
		
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		String preferenceValue = prefStore.getString("PATH") + "\\";
		
		if (preferenceValue.equals("")){
			//Treatment to export the file in Unix and Windows OS.
			if (!System.getProperty("os.name").toLowerCase().contains("windows")){
				TEMP_FOLDER_PATH = System.getProperty("user.home");
			}else{
				TEMP_FOLDER_PATH = System.getProperty("user.home") + "\\Desktop\\";
			}
		}else{
			TEMP_FOLDER_PATH = preferenceValue;
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
		
		List<String[]> recordItems = new ArrayList<String[]>();
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
	 */
	public void createCSVFile(String outputFile, ItemMeasured item){
	
		setFolderPath();		
		populateHeader();
		
		FileWriter fileWriter = null;
		CSVPrinter csvOutput = null;
	    try {
	    	
	    	File file = new File(TEMP_FOLDER_PATH + outputFile + ".csv");
	    	if (!file.exists()){
	    		file.createNewFile();
	    	}
	    	
	    	fileWriter = new FileWriter(file, true);
	    	
	    	csvOutput = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
	    	csvOutput.printRecords(headerItems);
	    	List<String[]> it = populateItems(item);
	    	csvOutput.printRecords(it);
	    	
	        JOptionPane.showMessageDialog(null, "CSV file was created successfully!");
	        
	    } catch (IOException e) {
	    	JOptionPane.showMessageDialog(null, "Error in CsvFileWriter!");
	        e.printStackTrace();
	    } finally {
	    	try {
				fileWriter.flush();
				fileWriter.close();
				csvOutput.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error while flushing/closing fileWriter/csvPrinter !");
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Method to create a xml file with the measurement result.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param outputFile
	 * @param itemMeasured
	 */
	public void createXMLFile (String outputFile, ItemMeasured itemMeasured){
		
		setFolderPath();
		
		File file = null;
    	FileWriter fileWriter = null;
		
    	try {
    		file = new File(TEMP_FOLDER_PATH + outputFile + ".xml");
    		if (!file.exists()){
    			file.createNewFile();
    		}
    		
			fileWriter = new FileWriter(file, true);
			
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
