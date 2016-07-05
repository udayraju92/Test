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

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;
import automation_home.ddf.constants.ExcelConstants;
import automation_home.ddf.wrapper.Wrapper;
import automation_home.ddf.wrapperimpl.ExcelWrapper;

import com.testhouse.Functions.CSFunctions;
import com.testhouse.Functions.CustomerBillingFunctions;

/**
 * Class file to write the test scripts for the Customer Service scenarios
 * @version 1.0
 * @author Testhouse
 *
 */
@Listeners({ ATUReportsListener.class, ConfigurationListener.class,	MethodListener.class })
public class CustomerServices extends CSFunctions
{
	/* Defining object for driver */
	WebDriver driver;

	public static  List<WebDriver> drivers;

	/* Defining object for excel data read */
	Wrapper wrapper = new ExcelWrapper();	

	/* Constructor to fetch the drivers */
	public CustomerServices()
	{
		CustomerServices.drivers = new ArrayList<WebDriver>();
	}

	/* Setting ATU reports configuration file path */
	Properties props = loadProperties();
	{		
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("atu.reporter.config", System.getProperty("user.dir").concat(props.getProperty("atuPropertiesFilePath")));	
	}
CustomerBillingFunctions cbf = new CustomerBillingFunctions();
	/**
	 * Method to fetch the browser which needs to be launched during parallel execution 
	 * @param browser Name of the browser which needs to be executed
	 * @throws Exception
	 * 
	 */	
	@BeforeMethod(alwaysRun=true)
	@Parameters("browser")	
	public void setUp(String browser) throws Exception 
	{ 
		if(browser.equalsIgnoreCase("Firefox"))
		{
			driver = new FirefoxDriver();
			CustomerServices.drivers.add(driver);
		}

		else if (browser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir").concat(props.getProperty("chromeDriverPath")));
			driver = new ChromeDriver();
			CustomerServices.drivers.add(driver);
		}

		else if (browser.equalsIgnoreCase("IE"))
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir").concat(props.getProperty("ieDriverPath")));
			driver = new InternetExplorerDriver();
			CustomerServices.drivers.add(driver);
		}		

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   
		ATUReports.setWebDriver(driver);
		ATUReports.indexPageDescription = "Dovetail Automation";
		ATUReports.currentRunDescription ="Customer Service";
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
		String sheetname = context.getCurrentXmlTest().getParameter("sheetname");
		wrapper.setParameter(ExcelConstants.FILE_PATH, System.getProperty("user.dir").concat(props.getProperty("testDataFilePath")));
		wrapper.setParameter(ExcelConstants.SHEET_NAME, sheetname);
		wrapper.setParameter(ExcelConstants.TESTCASE_NAME, testName);
		wrapper.setParameter(ExcelConstants.TESTCASE_START_ELEMENT, "_START");
		wrapper.setParameter(ExcelConstants.TESTCASE_END_ELEMENT, "_END");
		wrapper.setParameter(ExcelConstants.INCLUDE_TESTDATA_HEADER_NAME, "Execution");
		wrapper.setParameter(ExcelConstants.INCLUDE_TESTDATA_YES, "Run");
		wrapper.setParameter(ExcelConstants.INCLUDE_TESTDATA_NO, "No-Run");
		return wrapper.retrieveTestData();
	}

	/**
	 * Test to perform a new subscription via Customer Service screen and to verify it in CS and Customer Billing screens
	 * @param Type1:Customer service_UK Personal Subscription Issuebased_Optimistic_Single stage_CC;  Renewal Strategy : Single stage;  Schedule:Direct & Optimistic; Offer: Issue based; Payment method: Credit Card
	 * @throws Exception 
	 * @param newSubscription function to create a new subscription
	 * @param verifyNewSubscription function verify newly created subscription in CS screen
	 * @param cbf.verifyNewSubscriptionCB function to verify newly created subscription in Customer Billing screen
	 */
	//@Test(priority=0, dataProvider="databinding")
	public void customerServiceType1(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
	     newSubscriptionCCType1(driver, h.get("Client"), h.get("Brand"), h.get("PromotionName"), h.get("Card"), h.get("Title"), h.get("Firstname"), h.get("Surname"), h.get("Postcode"), h.get("Address"), h.get("CustomerName"), h.get("Card1"), h.get("ExpiryDate"), h.get("ExpiryYear"));
	
		/* Verify the newly created subscription in Customer Service Screen*/
		verifyNewSubscriptionCSType1(driver, h.get("PromotionName"), h.get("Client"), h.get("Brand"));
		
		/* Verify the newly created subscription in Customer Billing Screen*/
		cbf.verifyNewSubscriptionCBType1(driver);
		TimeUnit.SECONDS.sleep(3);
		
	}
	/**
	 * Test to perform a new subscription via Customer Service screen and to verify it in CS and Customer Billing screens
	 * @param Type2: Customer service_UK Personal Subscription Issuebased_Optimistic_Single stage_DD; Renewal Strategy : Single stage;  Schedule:Direct & Optimistic; Offer: Issue based; Payment method: Direct Debit
	 * @throws Exception 
	 * @param newSubscription function to create a new subscription
	 * @param verifyNewSubscription function verify newly created subscription in CS screen
	 * @param cbf.verifyNewSubscriptionCB function to verify newly created subscription in Customer Billing screen
	 */
	@Test(priority=1, dataProvider="databinding")
	public void customerServiceType2(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
	    newSubscriptionDD(driver, h.get("Client"), h.get("Brand"), h.get("PromotionName"), h.get("Card"), h.get("Title"), h.get("Firstname"), h.get("Surname"), h.get("Postcode"), h.get("Address"), h.get("AccHolderName"), h.get("AccNumber"), h.get("SortCode"));
	
		/* Verify the newly created subscription in Customer Service Screen*/
		verifyNewSubscriptionCSType2(driver, h.get("PromotionName"), h.get("Client"), h.get("Brand"));
		
		/* Verify the newly created subscription in Customer Billing Screen*/
		cbf.verifyNewSubscriptionCBType2(driver);
		TimeUnit.SECONDS.sleep(3);
		
	}
	/**
	 * Test to perform a new subscription via Customer Service screen and to verify it in CS and Customer Billing screens
	 * @param Type3: Customer service_UK Gift Subscription Issuebased_Optimistic_Single stage_CC; Renewal Strategy : Single stage;  Schedule:Direct & Optimistic; Offer: Issue based; Payment method: Credit Card
	 * @throws Exception 
	 * @param newSubscription function to create a new subscription
	 * @param verifyNewSubscription function verify newly created subscription in CS screen
	 * @param cbf.verifyNewSubscriptionCB function to verify newly created subscription in Customer Billing screen
	 */
    @Test(priority=2, dataProvider="databinding")
	public void customerServiceType3(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
	     newSubscriptionCCGiftType3(driver, h.get("Client"), h.get("Brand"), h.get("PromotionName"), h.get("Card"), h.get("Title"), h.get("Firstname"), h.get("Surname"), h.get("Postcode"), h.get("Address"), h.get("GTitle"), h.get("GFirstname"), h.get("GSurname"), h.get("GPostcode"), h.get("GAddress"),h.get("CustomerName"), h.get("Card1"), h.get("ExpiryDate"), h.get("ExpiryYear"));

		/* Verify the newly created subscription in Customer Service Screen*/
		verifyNewSubscriptionCSType5(driver, h.get("PromotionName"), h.get("Client"), h.get("Brand"));
		
		/* Verify the newly created subscription in Customer Billing Screen*/
		cbf.verifyNewSubscriptionCBType5(driver);
		TimeUnit.SECONDS.sleep(3);
		
	}
	
	/**
	 * Test to perform a new subscription via Customer Service screen and to verify it in CS and Customer Billing screens
	 * @param Type3:Customer Service_UK Personal subscription_Not Optimistic_Singlestage_CC; Renewal strategy: Singlestage; Offer:Product only; Payment method: Credit Card; Schedule: Direct & Not Optimistic
	 * @throws Exception 
	 * @param newSubscription function to create a new subscription
	 * @param verifyNewSubscription function verify newly created subscription in CS screen
	 * @param cbf.verifyNewSubscriptionCB function to verify newly created subscription in Customer Billing screen
	 */
	@Test(priority=3, dataProvider="databinding")
	public void customerServiceType4(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
		newSubscriptionCCType4(driver, h.get("Client"), h.get("Brand"), h.get("PromotionName"), h.get("Card"), h.get("Title"), h.get("Firstname"), h.get("Surname"), h.get("Postcode"), h.get("Address"), h.get("CustomerName"), h.get("Card1"), h.get("ExpiryDate"), h.get("ExpiryYear"));
	
		/* Verify the newly created subscription in Customer Service Screen*/
		verifyNewSubscriptionCSType4(driver, h.get("PromotionName"), h.get("Client"), h.get("Brand"));
		
		/* Verify the newly created subscription in Customer Billing Screen*/
		cbf.verifyNewSubscriptionCBType4(driver);
		TimeUnit.SECONDS.sleep(3);
		
	
	}
	
	/**
	 * Test to perform a new subscription via Customer Service screen and to verify it in CS and Customer Billing screens
	 * @throws Exception 
	 * @param newSubscription function to create a new subscription
	 * @param verifyNewSubscription function verify newly created subscription in CS screen
	 * @param cbf.verifyNewSubscriptionCB function to verify newly created subscription in Customer Billing screen
	 */
	//@Test(priority=4, dataProvider="databinding")
	public void customerServiceTypeSearch(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Searching new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
		searchCs(driver, h.get("Client"), h.get("Brand"), h.get("CustomerReference"), h.get("AddressLine"), h.get("PostCode"), h.get("CompanyName"), h.get("LastName"), h.get("FirstName"), h.get("Email"), h.get("AccountNumber"), h.get("SortCode"), h.get("CCNumber"), h.get("Country"));
        TimeUnit.SECONDS.sleep(3);
		
	
	}
	/**
	 * Test to perform a new subscription via Customer Service screen and to verify it in CS and Customer Billing screens
	 * @param Type5: Customer service_UK Gift Subscription Issuebased_Optimistic_Single stage_DD; Renewal Strategy : Single stage;  Schedule:Direct & Optimistic; Offer: Issue based; Payment method: Direct Debit
	 * @throws Exception 
	 * @param newSubscription function to create a new subscription
	 * @param verifyNewSubscription function verify newly created subscription in CS screen
	 * @param cbf.verifyNewSubscriptionCB function to verify newly created subscription in Customer Billing screen
	 */
	//@Test(priority=5, dataProvider="databinding")
	public void customerServiceType5(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
	     newSubscriptionCCGiftType5(driver, h.get("Client"), h.get("Brand"), h.get("PromotionName"), h.get("Card"), h.get("Title"), h.get("Firstname"), h.get("Surname"), h.get("Postcode"), h.get("Address"), h.get("GTitle"), h.get("GFirstname"), h.get("GSurname"), h.get("GPostcode"), h.get("GAddress"),h.get("AccHolderName"), h.get("AccNumber"), h.get("SortCode"));

		/* Verify the newly created subscription in Customer Service Screen*/
		verifyNewSubscriptionCSType5(driver, h.get("PromotionName"), h.get("Client"), h.get("Brand"));
		
		/* Verify the newly created subscription in Customer Billing Screen*/
		cbf.verifyNewSubscriptionCBType3(driver);
		TimeUnit.SECONDS.sleep(3);}

	/**
	 * Test to perform a new subscription via Customer Service screen and to verify it in CS and Customer Billing screens
	 * @param Type6: Customer service_UK Personal Subscription Issuebased_Optimistic_Single stage_Free;  Renewal Strategy : Single stage;  Schedule:Direct & Optimistic; Offer: Issue based; Payment method: Free
	 * @throws Exception 
	 * @param newSubscription function to create a new subscription
	 * @param verifyNewSubscription function verify newly created subscription in CS screen
	 * @param cbf.verifyNewSubscriptionCB function to verify newly created subscription in Customer Billing screen
	 */
	//@Test(priority=6, dataProvider="databinding")
	public void customerServiceType6(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
	     newSubscriptionFreeType6(driver, h.get("Client"), h.get("Brand"), h.get("PromotionName"), h.get("Card"), h.get("Title"), h.get("Firstname"), h.get("Surname"), h.get("Postcode"), h.get("Address"));
	
		/* Verify the newly created subscription in Customer Service Screen*/
	     verifyNewSubscriptionFreeType6(driver, h.get("PromotionName"), h.get("Client"), h.get("Brand"));
		
		/* Verify the newly created subscription in Customer Billing Screen*/
		cbf.verifyNewSubscriptionCBType1(driver);
		TimeUnit.SECONDS.sleep(3);
		
	}
	
	
	/*
	public void customerServiceType7(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */ /*
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Creating a new subscription */		
	   //newSubscriptionDDType7(driver, h.get("Client"), h.get("Brand"), h.get("Country"),  h.get("PromotionName"), h.get("Card"), h.get("Title"), h.get("Firstname"), h.get("Surname"),h.get("OverAddress"), h.get("AccHolderName"), h.get("AccNumber"), h.get("SortCode"));
	
		/* Verify the newly created subscription in Customer Service Screen*/
		//verifyNewSubscriptionCSType2(driver, h.get("PromotionName"), h.get("Client"), h.get("Brand"));
		
		/* Verify the newly created subscription in Customer Billing Screen*/
		//cbf.verifyNewSubscriptionCBType2(driver);
		//TimeUnit.SECONDS.sleep(3);}

	/**
	 * Method which is used to quit all the browser instances after execution
	 * @throws Exception
	 * 
	 */
	

	/**
	 * Test to check whether user can amend details in CS screen
	 * @throws Exception 
	 * @param check whether user can amend details in CS screen
	 */
	//@Test(priority=7, dataProvider="databinding")
	public void cSServiceAmend(HashMap<String, String> h) throws Exception
	{
		ATUReports.setTestCaseReqCoverage("Creating a new subscription through Customer service screen");
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");	

		/* Login Section */
		driver.get(props.getProperty("baseUrl"));
		driver.manage().window().maximize();
		TimeUnit.SECONDS.sleep(3);
		login(driver, h.get("Username"), h.get("Password"), testName);

		/* Amend details in CS Screen */		
	     serviceAmendDetails(driver, h.get("Client"), h.get("Brand"), h.get("ReferenceNumber"), h.get("NewName"), h.get("NewAddress"));
		
	}
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