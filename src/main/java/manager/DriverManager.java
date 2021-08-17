package manager;

	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.ie.InternetExplorerDriver;

import Enum.DriverType;
import FileReader.ConfigfileReader;


	public class DriverManager {

		private WebDriver driver;
		 private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
		 private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
		 private static final String IE_DRIVER_PROPERTY = "webdriver.ie.driver";
		 ConfigfileReader configreader;
		 private static DriverType driverType;
		
		 public DriverManager() {
			 configreader=new ConfigfileReader();
			 driverType = configreader.getBrowser();
			 }
		 public WebDriver getDriver() {
		 
			 switch (driverType) {     
		        case FIREFOX : driver = new FirefoxDriver();
		        System.setProperty(FIREFOX_DRIVER_PROPERTY, configreader.getDriverPath()+"geckodriver.exe");
		        driver = new FirefoxDriver();
		      break;
		        case CHROME : 
		         System.setProperty(CHROME_DRIVER_PROPERTY, System.getProperty("user.dir") + configreader.getDriverPath()+"chromedriver.exe");
		         driver = new ChromeDriver();
		     break;
		        case INTERNETEXPLORER : 
		        System.setProperty(IE_DRIVER_PROPERTY, configreader.getDriverPath()+"IEDriverServer.exe");
		        driver=new InternetExplorerDriver();
		     break;
		        }
			 driver.manage().window().maximize();
		       // driver.manage().timeouts().implicitlyWait(configreader.getImplicitlyWait(), TimeUnit.SECONDS);
		 return driver;
		 } 
			 
			 public void closeDriver() {
			 driver.close();
			 driver.quit();
			 }
	}

