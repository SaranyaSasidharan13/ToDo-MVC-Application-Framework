package FileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import Enum.DriverType;

public class ConfigfileReader {

	private Properties properties;
	 private final String propertyFilePath= System.getProperty("user.dir") + "\\src\\main\\java\\Config\\Configuration.properties";
	 
	 
	 	public ConfigfileReader()
	 {
	 BufferedReader reader;
	 try {
	 reader = new BufferedReader(new FileReader(propertyFilePath));
	 properties = new Properties();
	 try {
	 properties.load(reader);
	 reader.close();
	 } catch (IOException e) {
	 e.printStackTrace();
	 }
	 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		 } 
		 }
		
	 	/**
	 	 * Get driver path from properties file
	 	 * @return
	 	 */
	 	public String getDriverPath(){
		 String driverPath = properties.getProperty("driverpath");
		 if(driverPath!= null) return driverPath;
		 else throw new RuntimeException("driverpath not specified in the Configuration.properties file."); 
		 }
		 /**
		  * Get implicit wait time from properties file
		  * @return
		  */
		 public long getImplicitlyWait() { 
		 String implicitlyWait = properties.getProperty("implicitwaittime");
		 if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
		 else throw new RuntimeException("implicitwaittime not specified in the Configuration.properties file."); 
		 }
		 
		 /**\
		  * Get application url from properties file
		  * @return
		  */
		 public String getApplicationUrl() {
		 String url = properties.getProperty("url");
		 if(url != null) return url;
		 else throw new RuntimeException("url not specified in the Configuration.properties file.");
		 }
		 
		 /**
		  * Get drivertype from properties file
		  * @return
		  */
		 public DriverType getBrowser() {
			 String browserName = properties.getProperty("browser");
			 if(browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
			 else if(browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
			 else if(browserName.equals("iexplorer")) return DriverType.INTERNETEXPLORER;
			 else throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
			 }
}
