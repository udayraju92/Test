package com.testhouse.Functions;

import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.openqa.selenium.WebDriver;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import com.testhouse.ObjectRepository.AccountAdminObjects;

public class AAFunctions extends AccountAdminObjects
{
	public static String testName;

	/**
	 * Method to create a new standard schedule
	 * @param driver Object for webdriver
	 * @param client Object for selecting a client from the list
	 * @param brand Object for selecting a brand from the list
	 * @param opt Object to check / uncheck the optimistic check box
	 * @param cal Object to check / uncheck the Calendar check box
	 * @param act Object to check / uncheck the Active check box
	 * @param name Object for naming the schedule
	 * @param description Object defined for the schedule description
	 * @param type Object to select whether the schedule is an Issue / Product
	 * @param when Object for choosing when the event should be scheduled
	 * @param delType Object for selecting the event delivery type
	 * @param dest Object for selecting the destination option from the list
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 */
	public void standardSchedule(WebDriver driver, String client, String brand, String opt, String cal, String act,String name, String description, String type, String when, String delType, String dest) throws Exception
	{
		try
		{
			element(driver, accountAdminLink).click();	
			TimeUnit.SECONDS.sleep(10);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
			element(driver, manageSchedule).click();
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, clientSelect)).selectByVisibleText(client);

			for (int i = 1; i <= 100; i++)
			{
				try
				{
					element(driver, brandSelect(brand)).click();
					TimeUnit.SECONDS.sleep(7);
					break;
				}

				catch (Exception e)
				{
					element(driver, fastFoward).click();
				}
			}	
			TimeUnit.SECONDS.sleep(8);
			
			try
			{
				element(driver, addSchedule).isEnabled();
				element(driver, addSchedule).click();
			}
			catch (Exception e)
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, addSchedule).click();
			}

			TimeUnit.SECONDS.sleep(3);
			DateTime dt = DateTime.now();
			String dateFolder = dt.toLocalDate().toString();

			element(driver, scheduleName).click();
			element(driver, scheduleName).clear();
			element(driver, scheduleName).sendKeys(""+name+""+" "+dateFolder+"");
			TimeUnit.SECONDS.sleep(3);
			element(driver, scheduleDesc).sendKeys(description);
			if(opt.equals("Yes")||opt.equals("yes"))
			{
				element(driver, optimisticCheck).click();
			}
			if(cal.equals("Yes")||cal.equals("yes"))
			{
				element(driver, calenderBasedCheck).click();
			}
			if(act.equals("Yes")||act.equals("yes"))
			{
				element(driver, activeCheck).click();
			}
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, subscriptionType)).selectByVisibleText(type);
			TimeUnit.SECONDS.sleep(3);
			element(driver, scheduleEventsBtn).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, newEventBtn).click();
			TimeUnit.SECONDS.sleep(3);
			Select(element(driver, eventWhen)).selectByVisibleText(when);
			TimeUnit.SECONDS.sleep(3);
			Select(element(driver, eventDeliveryType)).selectByVisibleText(delType);
			TimeUnit.SECONDS.sleep(2);
			element(driver, eventNextBtn).click();
			TimeUnit.SECONDS.sleep(15);
			Select(element(driver, eventDest)).selectByVisibleText(dest);
			TimeUnit.SECONDS.sleep(5);
			element(driver, eventFinishBtn).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, eventSaveBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, eventBackBtn).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, saveScheduleBtn).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, schedulesList).click();
			TimeUnit.SECONDS.sleep(3);
			
			if(element(driver, findSchedule(""+name+""+" "+dateFolder+"")).isDisplayed())
			{
				ATUReports.add("Created and verified a new standard schedule: "+""+name+""+" "+dateFolder+"", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
			else
			{
				ATUReports.add("Unable to verify the newly created subscription", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
			}
		}

		catch (Exception e)
		{
			ATUReports.add("Unable to create a new schedule", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}
}

