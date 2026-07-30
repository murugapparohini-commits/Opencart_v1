package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
//public  WebDriver driver;
private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
public Logger logger; //Log4j
public Properties p;

	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException {

	    // Loading config.properties
	    FileReader file = new FileReader("./src/test/resources/config.properties");
	    p = new Properties();
	    p.load(file);

	    logger = LogManager.getLogger(this.getClass());

	    // Remote Execution (Selenium Grid)
	    if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

	        MutableCapabilities capabilities = null;

	        // Browser
	        switch (br.toLowerCase()) {

	            case "chrome":
	                ChromeOptions chromeOptions = new ChromeOptions();
	                
	                if (os.equalsIgnoreCase("windows"))
	                    chromeOptions.setPlatformName("windows");

	                else if (os.equalsIgnoreCase("mac"))
	                    chromeOptions.setPlatformName("mac");

	                else if (os.equalsIgnoreCase("linux"))
	                    chromeOptions.setPlatformName("linux");

	                else {
	                    System.out.println("Invalid OS");
	                    return;
	                }

	                capabilities = chromeOptions;

	                break;

	            case "edge":
	                EdgeOptions edgeOptions = new EdgeOptions();

	                if (os.equalsIgnoreCase("windows"))
	                    edgeOptions.setPlatformName("Windows");
	                else if (os.equalsIgnoreCase("mac"))
	                    edgeOptions.setPlatformName("macOS");
	                else if (os.equalsIgnoreCase("linux"))
	                	edgeOptions.setPlatformName("linux");
	                else {
	                    System.out.println("Invalid OS");
	                    return;
	                }

	                capabilities = edgeOptions;
	                break;
	                
	            case "firefox":
	                FirefoxOptions firefoxOptions = new FirefoxOptions();

	                if (os.equalsIgnoreCase("windows"))
	                    firefoxOptions.setPlatformName("Windows");
	                else if (os.equalsIgnoreCase("mac"))
	                    firefoxOptions.setPlatformName("macOS");
	                else if (os.equalsIgnoreCase("linux"))
	                    firefoxOptions.setPlatformName("linux");
	                else {
	                    System.out.println("Invalid OS");
	                    return;
	                }

	                capabilities = firefoxOptions;
	                break;

	            default:
	                System.out.println("Invalid Browser");
	                return;
	        }

	       /* driver = new RemoteWebDriver(
	                new URL("http://localhost:4444"),
	                capabilities); */
	        tlDriver.set(new RemoteWebDriver(
	                URI.create("http://127.0.0.1:4444/").toURL(),
	                capabilities));
	    }

	    // Local Execution
	    else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

	        switch (br.toLowerCase()) {

	            case "chrome":
	                tlDriver.set(new ChromeDriver());
	                break;

	            case "firefox":
	                tlDriver.set(new FirefoxDriver());
	                break;

	            case "edge":
	                tlDriver.set(new EdgeDriver());
	                break;

	            default:
	                System.out.println("Invalid Browser");
	                return;
	        }
	    
	    }

	    getDriver().manage().deleteAllCookies();
	    getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    getDriver().manage().window().maximize();
	    getDriver().get(p.getProperty("appURL"));
	}
	
	
	


	public static WebDriver getDriver() {
	    return tlDriver.get();
	}

	


	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown() 
	{
		getDriver().quit();
		tlDriver.remove();
		
	}

	public String randomeString()
	{
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
		
	}
	
	public String randomeNumber() {
		String generatedNumber=RandomStringUtils.randomNumeric(10);
		return generatedNumber;		
	}
	
	public String randomeAlphaNumeric() {
		String generatedString=RandomStringUtils.randomAlphabetic(3);
		String generatedNumber=RandomStringUtils.randomNumeric(3);
		return(generatedString+"@"+generatedNumber);
		
	}

	public String captureScreen(String tname) throws IOException {

	    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss")
	            .format(new Date());

	    TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
	    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

	    String targetFilePath = System.getProperty("user.dir")
	            + "\\screenshots\\"
	            + tname + "_" + timeStamp + ".png";

	    File targetFile = new File(targetFilePath);

	    sourceFile.renameTo(targetFile);

	    return targetFilePath;
	}


	public static ThreadLocal<WebDriver> getTlDriver() {
		return tlDriver;
	}


	public static void setTlDriver(ThreadLocal<WebDriver> tlDriver) {
		BaseClass.tlDriver = tlDriver;
	}
}
