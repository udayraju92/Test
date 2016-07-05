package com.testhouse.Functions;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import com.testhouse.ObjectRepository.CustomerServiceObjects;

/**
 * Class file to store functions which are generic and is not dependent on the application
 * @version 1.0
 * @author Testhouse
 *
 */

public class GeneralFunctions extends CustomerServiceObjects
{

	/**
	 * Method used for logging into the application
	 * @param driver Object for webdriver
	 * @param Username User which needs to be logged in
	 * @param Password Password of the respective user
	 * @param testName To fetch the current method name, used for taking screenshot
	 * 
	 * @throws Exception
	 */
	public void login(WebDriver driver, String Username, String Password, String testName) throws Exception
	{
		element(driver, username).sendKeys(Username);
		element(driver, password).sendKeys(Password);
		element(driver, logIn).click();
		TimeUnit.SECONDS.sleep(7);

		try
		{	
			element(driver, customerServiceLink).isDisplayed();
			ATUReports.add(Username+" logged in successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch (Exception e)
		{
			ATUReports.add("Username or password is incorrect", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}

	/**
	 * Method used to fetch the details from configuration file
	 * @return props Will return all the system properties
	 * 
	 */	
	static Properties props = new Properties();
	public Properties loadProperties()
	{
		String propsFilePath = System.getProperty("user.dir").concat("\\configuration\\config.properties");		
		try 
		{
			props.load(new FileInputStream(propsFilePath));
		} 
		catch (Exception e)
		{
			String error = e.getMessage();
			Reporter.log(error);
		}
		return props;
	}

	/**
	 * Method used to generalize the element identification
	 * @param driver Object for webdriver
	 * @param object Element which needs to be identified
	 * @return Will Return the particular element
	 * 
	 */
	public WebElement element(WebDriver driver, By object)
	{
		WebElement element = driver.findElement(object);
		return element;
	}	

	/**
	 * Wait for the element to be present in the DOM, and displayed on the page. 
	 * And returns the first WebElement using the given method.
	 * @param WebDriver	The driver object to be used 
	 * @param By	selector to find the element
	 * @param int	The time in seconds to wait until returning a failure
	 *
	 * @return WebElement	the first WebElement using the given method, or null (if the timeout is reached)
	 */
	public WebElement waitForElement(WebDriver driver, final By by, long timeOutInSeconds) 
	{
		WebElement element; 
		try
		{	
			// To nullify implicitlyWait()
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  

			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds); 
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //reset implicitlyWait
			return element; //return the element
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to select a value from the dropdown
	 * @param findElement Element which needs to be selected
	 * @return Will return the particular dropdown selection
	 * 
	 */
	public Select Select(WebElement findElement) 
	{
		Select Select = new Select(findElement);
		return Select;
	}

	/**
	 * Method to download a file from web
	 * @return Will return the profile value
	 * @throws Exception
	 * 
	 */
	public static FirefoxProfile FirefoxDriverProfile() throws Exception 
	{
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", "C:\\Users\\Testhouse\\Desktop");
		profile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
		return profile;
	}

	/**
	 * Method to fetch the latest downloaded file
	 * @param dirPath Location from where the file needs to be identified
	 * @return Will return the last modified file 
	 * @throws InterruptedException
	 * 
	 */
	public static File getLatestFilefromDir(String dirPath) throws InterruptedException
	{
		File dir = new File(dirPath);
		File[] files = dir.listFiles();

		if (files == null || files.length == 0) 
		{
			return null;
		}
		File lastModifiedFile = files[0];

		for (int i = 1; i < files.length; i++) 
		{
			if (lastModifiedFile.lastModified() < files[i].lastModified()) 
			{
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	/**
	 * Method to verify a text within the PDF
	 * @param fileName Location of the file which needs to be checked
	 * @param textToVerify Text content which needs to be verified
	 * @return Will return a true flag for text presence and a false flag will indicate text not found
	 * @throws Exception
	 * 
	 */	
	public boolean verifyPDFContent(String fileName,String textToVerify) throws Exception 
	{
		boolean flag=false;
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;
		File file2 = new File(System.getProperty("user.home") + "\\Downloads\\"+fileName);
		PDFParser parser = new PDFParser(new FileInputStream(file2));
		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdfStripper.setStartPage(1);
		pdfStripper.setEndPage(1);
		pdDoc = new PDDocument(cosDoc);
		parsedText = pdfStripper.getText(pdDoc);

		if(parsedText.contains(textToVerify)){
			flag=true;
		}
		else
		{
			flag=false;  
		}
		return flag;
	}

	/**
	 * Method to fetch the current system time
	 * @return Will return the system time in the format mentioned on the method
	 * 
	 */
	public String getCurrentTime()
	{
		int second, minute, hour;
		GregorianCalendar date = new GregorianCalendar();
		second = date.get(Calendar.SECOND);
		minute = date.get(Calendar.MINUTE);
		hour = date.get(Calendar.HOUR);
		String currentTime = "_"+hour+minute+second;
		return currentTime;
	}

	/**
	 * Method to switch between frames
	 * @param driver Object for webdriver
	 * @param frame Name of the frame to switch over
	 * 
	 */
	public void switchToFrame(WebDriver driver, String frame)
	{
		driver.switchTo().frame(frame); 
	}

	/**
	 * Method to take screenshot whenever a test is failed
	 * 
	 * @param driver Object for webdriver
	 * @param testName Used for naming the screenshot file 
	 * @throws Exception 
	 * 
	 */
	public void takeScreenShotOnFailure(WebDriver driver, String testName) throws Exception 
	{ 
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		DateTime dt = DateTime.now();
		String dateFolder = dt.toLocalDate().toString();
		Properties props = loadProperties();	// Retrieve properties from config file
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir").concat(props.getProperty("screenshot.path"))+"\\" + dateFolder +"\\" + testName + "_" + dt.toString().replaceAll(":", "-")+".jpg"));
		scrFile.mkdir();
	}

	/** Waits for the completion of Ajax jQuery processing by checking "return jQuery.active == 0" condition.  
	 *
	 * @param WebDriver - The driver object to be used to wait and find the element
	 * @param int - The time in seconds to wait until returning a failure
	 * @return boolean true or false(condition fail, or if the timeout is reached)
	 *  
	 */
	public boolean waitForJQueryProcessing(WebDriver driver, long timeOutInSeconds)
	{
		boolean jQcondition = false; 
		try
		{	
			// To nullify implicitlyWait()
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);  
			new WebDriverWait(driver, timeOutInSeconds) 
			{}.until(new ExpectedCondition<Boolean>() 
					{
				@Override
				public Boolean apply(WebDriver driverObject) 
				{
					return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return jQuery.active == 0");
				}
					});

			jQcondition = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
			TimeUnit.SECONDS.sleep(3);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //reset implicitlyWait
			return jQcondition; 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		return jQcondition; 
	}
}
