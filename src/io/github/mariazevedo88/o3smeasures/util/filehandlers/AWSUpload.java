package io.github.mariazevedo88.o3smeasures.util.filehandlers;

import java.io.File;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;

import io.github.mariazevedo88.o3smeasures.plugin.Activator;
import io.github.mariazevedo88.o3smeasures.util.FileUpload;

/**
 * Class that implements file upload to AWS S3 bucket
 * 
 * @author Mariana Azevedo
 * @since 25/01/2020
 */
public class AWSUpload implements FileUpload {

	private static final String BUCKET_NAME = Activator.getDefault().getPreferenceStore().getString("BUCKET");
	private static final String ACCESS_KEY =  Activator.getDefault().getPreferenceStore().getString("ACCESSKEY");
	private static final String SECRET_KEY = Activator.getDefault().getPreferenceStore().getString("SECRETKEY");
	private static final String REGION = Activator.getDefault().getPreferenceStore().getString("REGION");
	private static final BasicAWSCredentials CREDENTIALS = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	
	private static final Logger logger = Logger.getLogger(AWSUpload.class);

	/**
	 * @see FileUpload#upload(String)
	 */
	@Override
	public void upload(String fileName) {
		
		if(BUCKET_NAME == "" || ACCESS_KEY == "" || SECRET_KEY == "" || REGION == "") {
			JOptionPane.showMessageDialog(null, "AWS credentials to access S3 bucket aren't defined. "
				+ "Go to preference's page to set the values correctly.");
			throw new AmazonS3Exception("AWS credentials to access S3 bucket aren't defined. Go to preference's page "
				+ "to set the values correctly." + "Credentials: {Bucket name: " + BUCKET_NAME + ", Access Key ID: " 
				+ ACCESS_KEY + ", Secret Key: " + SECRET_KEY + ", Region: " + REGION + "}");
		}

		Regions clientRegion = Regions.fromName(REGION);
        String path = FileExport.setFolderPath();
        String fileAbsolutePath = path + fileName;

        try {
           
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            		.withCredentials(new AWSStaticCredentialsProvider(CREDENTIALS))
            		.withRegion(clientRegion)
                    .build();

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, fileName, new File(fileAbsolutePath));
            s3Client.putObject(request);
            
            logger.info("File " + fileName + " was upload successfully to bucket => " + request.getBucketName());
            JOptionPane.showMessageDialog(null, "File was uploaded to AWS S3 successfully!");
            
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
        	JOptionPane.showMessageDialog(null, "Error on upload file to AWS S3!");
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
        	JOptionPane.showMessageDialog(null, "Error on upload file to AWS S3!");
            e.printStackTrace();
        }
	}

	/**
	 * Method to get the S3 bucket name
	 * 
	 * @author Mariana Azevedo
	 * @since 26/01/2020
	 * 
	 * @return String
	 */
	public static String getBucketName() {
		return BUCKET_NAME;
	}

	/**
	 * Method to get the access key 
	 * 
	 * @author Mariana Azevedo
	 * @since 26/01/2020
	 * 
	 * @return String
	 */
	public static String getAccessKey() {
		return ACCESS_KEY;
	}

	/**
	 * Method to get the secret key
	 * 
	 * @author Mariana Azevedo
	 * @since 26/01/2020
	 * 
	 * @return String
	 */
	public static String getSecretKey() {
		return SECRET_KEY;
	}

	/**
	 * Method to get the region of the AWS bucket
	 * 
	 * @author Mariana Azevedo
	 * @since 26/01/2020
	 * 
	 * @return String
	 */
	public static String getRegion() {
		return REGION;
	}

	/**
	 * Method to get basic credentials to access AWS
	 * 
	 * @author Mariana Azevedo
	 * @since 26/01/2020
	 * 
	 * @return BasicAWSCredentials
	 */
	public static BasicAWSCredentials getCredentials() {
		return CREDENTIALS;
	}

}
