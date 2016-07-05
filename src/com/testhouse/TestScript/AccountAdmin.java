package com.testhouse.TestScript;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.testhouse.Functions.AAFunctions;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;
import automation_home.ddf.constants.ExcelConstants;
import automation_home.ddf.wrapper.Wrapper;
import automation_home.ddf.wrapperimpl.ExcelWrapper;


/**
 * Class file to write the test scripts for the Account Admin scenarios
 * @author Testhouse
 *
 */
@Listeners({ ATUReportsListener.class, ConfigurationListener.class,	MethodListener.class })
public class AccountAdmin extends AAFunctions
{	
	/* Defining object for driver */
	WebDriver driver;

	public static  List<WebDriver> drivers;
	
	/* Defining object for excel data read */
	Wrapper wrapper = new ExcelWrapper();	

	/* Constructor to fetch the drivers */
	public AccountAdmin()
	{
		AccountAdmin.drivers = new ArrayList<WebDriver>();
	}

	/* Setting ATU reports configuration file path */
	Properties props = loadProperties();
	{		
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("atu.reporter.config", System.getProperty("user.dir").concat(props.getProperty("atuPropertiesFilePath")));	
	}

	/**
	 * Method to fetch the browser which needs to be launched during parallel execution
	 * @param browser Name of the browser which needs to be executed
	 * @throws Exception
	 */
	@BeforeMethod(alwaysRun=true)
	@Parameters("browser")	
	public void setUp(String browser) throws Exception 
	{ 
		if(browser.equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
			AccountAdmin.drivers.add(driver);
		}

		else if (browser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir").concat(props.getProperty("chromeDriverPath")));
			driver = new ChromeDriver();
			AccountAdmin.drivers.add(driver);
		}

		else if (browser.equalsIgnoreCase("IE"))
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir").concat(props.getProperty("ieDriverPath")));
			driver = new InternetExplorerDriver();
			AccountAdmin.drivers.add(driver);
		}		

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   
		ATUReports.setWebDriver(driver);
		ATUReports.indexPageDescription = "Dovetail Automation";
		ATUReports.currentRunDescription ="Account Admin";
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");
	}
	
	/**
	 * Method which is used to fetch the data from Excel
	 * @param method Object to identify the method which is currently using the Data provider
	 * @param context Object which is used to fetch the sheet name from Excel
	 * @return Will return the data values which has been identified from Excel
	 * @throws Exception
	 * 
	 */	
	@DataProvider
	public Object[][] databinding(Method method, ITestContext context) throws Exception 
	{ 				
		testName = method.getName();
		wrapper.setParameter(ExcelConstants.FILE_PATH, System.getProperty("user.dir").concat(props.getProperty("testDataFilePath")));
		wrapper.setParameter(ExcelConstants.SHEET_NAME, "Account Admin");
		wrapper.setParameter(ExcelConstants.TESTCASE_NAME, testName);
		wrapper.setParameter(ExcelConstants.TESTCASE_START_ELEMENT, "_START");
		wrapper.setParameter(ExcelConstants.TESTCASE_END_ELEMENT, "_END");
		wrapper.setParameter(ExcelConstants.INCLUDE_TESTDATA_HEADER_NAME, "Execution");
		wrapper.setParameter(ExcelConstants.INCLUDE_TESTDATA_YES, "Run");
		wrapper.setParameter(ExcelConstants.INCLUDE_TESTDATA_NO, "No-Run");
		return wrapper.retrieveTestData();
	}
	
	/**
	 * Test to create a new standard optimistic schedule
	 * @throws Exception 
	 * 
	 */
	@Test(priority=0, dataProvider="databinding")
	public void optimisticStandardSchedule(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new standard optimistic Schedule");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);
		
		/* Creating a new Standard Schedule */
		standardSchedule(driver, h.get("Client"), h.get("Brand"), h.get("Optimistic"), h.get("Calender"), h.get("Active"), h.get("Name"), h.get("Description"), h.get("Type"), h.get("EventWhen"), h.get("EventDeliveryType"), h.get("EventDestination"));
	}
	
	/**
	 * Test to create a new standard non optimistic schedule
	 * @throws Exception 
	 * 
	 */
	@Test(priority=1, dataProvider="databinding")
	public void nonOptimisticStandardSchedule(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new standard non optimistic Schedule");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);
		
		/* Creating a new Standard Schedule */
		standardSchedule(driver, h.get("Client"), h.get("Brand"), h.get("Optimistic"), h.get("Calender"), h.get("Active"), h.get("Name"), h.get("Description"), h.get("Type"), h.get("EventWhen"), h.get("EventDeliveryType"), h.get("EventDestination"));
	}
		
	/**
	 * Method which is used to quit all the browser instances after execution
	 * @throws Exception
	 * 
	 */
	@AfterMethod(alwaysRun=true)
	public void tearDown() throws Exception 
	{
		element(driver, logOut).click();	
		TimeUnit.SECONDS.sleep(2);

		for(WebDriver d : drivers)
		{
			d.quit();
		}
	}
}
