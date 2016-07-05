package com.testhouse.Functions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import com.testhouse.ObjectRepository.CustomerBillingObjects;


public class CustomerBillingFunctions extends CustomerBillingObjects 
{
	public static String testName;
	public static String billTerms = null, optimis = null;

	public void fetchDetailsCB(WebDriver driver)
	{
		try
		{
			TimeUnit.SECONDS.sleep(5);
			element(driver, homeLink).click();
			element(driver, customerBillingLink).click();
			TimeUnit.SECONDS.sleep(10);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
			element(driver, customerDetailsLink).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, customerRefSearch).sendKeys(orderRef);
			TimeUnit.SECONDS.sleep(2);
			new Actions(driver).moveToElement(element(driver, viewCustomersButton)).perform();
			element(driver, viewCustomersButton).click();
			TimeUnit.SECONDS.sleep(5);
			new Actions(driver).moveToElement(element(driver, viewCustomer(orderRef))).perform();
			element(driver, viewCustomer(orderRef)).click();
			TimeUnit.SECONDS.sleep(3);
		}
		catch(Exception e)
		{
			ATUReports.add("Unable to fetch details from CS screen",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	public void verifyNewSubscriptionCBType1(WebDriver driver) throws Exception
	{
		testName = "verifyNewSubscriptionCBType1";
		try
		{
			fetchDetailsCB(driver);
			try
			{
				Assert.assertTrue(element(driver, verifyLedger).isDisplayed());
				element(driver, accSubDet).click();
				//billTerms = element(driver, billingTerms).getText();
				Assert.assertTrue(element(driver, billingTerms).getText().contains("ISSUES"));
				optimis = element(driver, optimistic).getText();
				Assert.assertEquals(optimis, "true");
				ATUReports.add(" Order:"+orderRef+" has been successfully verified in Customer Billing screen with Billing Terms is issue based", orderRef,"Optimistic = true"," Optimistic =" + optimis,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				TimeUnit.SECONDS.sleep(5); 
			}
			catch(AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription in Customer Billing Screen","Billing Terms: "+ billTerms,"Optimistic = " + optimis, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch(Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}

	public void verifyNewSubscriptionCBType2(WebDriver driver) throws Exception
	{
		testName = "verifyNewSubscriptionCBType1";
		try
		{
			TimeUnit.SECONDS.sleep(3);
			try 
			{
				Assert.assertTrue(element(driver, verifyLedger).isDisplayed());
				element(driver, accSubDet).click();
				billTerms = element(driver, billingTerms).getText();

				//assertTrue(element(driver, billingTerms).getText().matches("ISSUES"));
				Assert.assertTrue(element(driver, billingTerms).getText().contains("ISSUES"));
				//Assert.assertEquals(billTerms, "1,ISSUES,GBP,0,1");

				optimis = element(driver, optimistic).getText();
				Assert.assertEquals(optimis, "true");
				ATUReports.add(" Order:"+orderRef+" has been successfully verified in Customer Billing screen with Billing Terms is issue based", orderRef,"Optimistic = true"," Optimistic =" + optimis,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				TimeUnit.SECONDS.sleep(5); 
			}
			catch(AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription in Customer Billing Screen","Billing Terms: "+ billTerms,"Optimistic = " + optimis, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch(Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}

	public void verifyNewSubscriptionCBType3(WebDriver driver) throws Exception
	{
		testName = "verifyNewSubscriptionCBType1";
		try
		{
			fetchDetailsCB(driver);
			TimeUnit.SECONDS.sleep(3);
			try 
			{
				Assert.assertTrue(element(driver, verifyLedger).isDisplayed());
				element(driver, accSubDet).click();
				billTerms = element(driver, billingTerms).getText();

				//assertTrue(element(driver, billingTerms).getText().matches("ISSUES"));
				Assert.assertTrue(element(driver, billingTerms).getText().contains("ISSUES"));
				//Assert.assertEquals(billTerms, "1,ISSUES,GBP,0,1");

				optimis = element(driver, optimistic).getText();
				Assert.assertEquals(optimis, "true");
				ATUReports.add(" Order:"+orderRef+" has been successfully verified in Customer Billing screen with Billing Terms is issue based", orderRef,"Optimistic = true"," Optimistic =" + optimis,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				TimeUnit.SECONDS.sleep(5); 
			}
			catch(AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription in Customer Billing Screen","Billing Terms: "+ billTerms,"Optimistic = " + optimis, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch(Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}
	public void verifyNewSubscriptionCBType4(WebDriver driver) throws Exception
	{
		testName = "verifyNewSubscriptionCBType1";
		try
		{
			fetchDetailsCB(driver);
			TimeUnit.SECONDS.sleep(3);
			try 
			{
				Assert.assertTrue(element(driver, verifyLedger).isDisplayed());
				element(driver, accSubDet).click();
				billTerms = element(driver, billingTerms).getText();

				//assertTrue(element(driver, billingTerms).getText().matches("ISSUES"));
				Assert.assertTrue(element(driver, billingTerms).getText().contains("PRODUCT_ONLY"));
				//Assert.assertEquals(billTerms, "1,ISSUES,GBP,0,1");

				optimis = element(driver, optimistic).getText();
				Assert.assertEquals(optimis, "false");
				ATUReports.add(" Order:"+orderRef+" has been successfully verified in Customer Billing screen with Billing Terms is issue based", orderRef,"Optimistic = false"," Optimistic =" + optimis,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				TimeUnit.SECONDS.sleep(5); 
			}
			catch(AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription in Customer Billing Screen","Billing Terms: "+ billTerms,"Optimistic = " + optimis, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch(Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}

	public void verifyNewSubscriptionCBType5(WebDriver driver) throws Exception
	{
		testName = "verifyNewSubscriptionCBType1";
		try
		{
			TimeUnit.SECONDS.sleep(3);
			try
			{
				Assert.assertTrue(element(driver, verifyLedger).isDisplayed());
				element(driver, accSubDet).click();
				billTerms = element(driver, billingTerms).getText();

				//assertTrue(element(driver, billingTerms).getText().matches("ISSUES"));
				Assert.assertTrue(element(driver, billingTerms).getText().contains("ISSUES"));
				//Assert.assertEquals(billTerms, "1,ISSUES,GBP,0,1");

				optimis = element(driver, optimistic).getText();
				Assert.assertEquals(optimis, "true");
				ATUReports.add(" Order:"+orderRef+" has been successfully verified in Customer Billing screen with Billing Terms is issue based", orderRef,"Optimistic = true"," Optimistic =" + optimis,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				TimeUnit.SECONDS.sleep(5); 
			}
			catch(AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription in Customer Billing Screen","Billing Terms: "+ billTerms,"Optimistic = " + optimis, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch(Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}
	public void cBCheckDetails(WebDriver driver,  String referenceNumb) throws Exception
	{
		try
		{
			TimeUnit.SECONDS.sleep(5);
			element(driver, homeLink).click();
			element(driver, customerBillingLink).click();
			TimeUnit.SECONDS.sleep(10);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
			element(driver, customerDetailsLink).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, customerRefSearch).sendKeys(referenceNumb);
			TimeUnit.SECONDS.sleep(2);
			new Actions(driver).moveToElement(element(driver, viewCustomersButton)).perform();
			element(driver, viewCustomersButton).click();
			TimeUnit.SECONDS.sleep(5);
			new Actions(driver).moveToElement(element(driver, viewCustomer(referenceNumb))).perform();
			element(driver, viewCustomer(referenceNumb)).click();
			TimeUnit.SECONDS.sleep(3);
			Assert.assertTrue(element(driver, verifyLedger).isDisplayed());

			TimeUnit.SECONDS.sleep(3);
			element(driver, listView).click();
			Assert.assertTrue(element(driver, verifyLView).isDisplayed());
			TimeUnit.SECONDS.sleep(3);
			element(driver, bankInst).click();
			Assert.assertTrue(element(driver, verifyBankInst).isDisplayed());
			TimeUnit.SECONDS.sleep(3);
			element(driver, accSubDet).click();
			Assert.assertTrue(element(driver, verifyAccSub).isDisplayed());
			TimeUnit.SECONDS.sleep(3);
			element(driver, billschedule).click();
			//Assert.assertTrue(element(driver, verifyBankInst).isDisplayed());
			TimeUnit.SECONDS.sleep(3);
			element(driver, payDetails).click();
			Assert.assertTrue(element(driver, verifyPayDetails).isDisplayed());
			TimeUnit.SECONDS.sleep(3);
			element(driver, custDetails).click();
			Assert.assertTrue(element(driver, verifyCusDetails1).isDisplayed());
			TimeUnit.SECONDS.sleep(3);
			element(driver, billDetails).click();
			Assert.assertTrue(element(driver, verifyCusDetails2).isDisplayed());
			element(driver, deliveryAdd).click();
			Assert.assertTrue(element(driver, verifyCusDetails3).isDisplayed());

			ATUReports.add(" Order:"+referenceNumb+" has been successfully verified in Customer Billing screen ", referenceNumb,"List view, Bank Instructions, Account Sub Details, Payment Details, Customer Details, Billing Details, Delivery Address "," List view, Bank Instructions, Account Sub Details, Payment Details, Customer Details, Billing Details, Delivery Address " ,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}
		catch(Exception e)
		{
			ATUReports.add("Unable to fetch details from CS screen",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

}
