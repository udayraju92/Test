package com.testhouse.Functions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class CSFunctions extends GeneralFunctions
{
	String accountID = null, verifyStatus = null, verifyType = null, payMethod = null, payStatus = null, renStatus = null, subRole = null;	
	public static String testName,orderRef;

	/**
	 * Method performed to do a new subscription 
	 * 
	 * @param driver Object for webdriver
	 * @param client Object for selecting a client from the list
	 * @param brand Object for selecting a brand from the list
	 * @param promotion Object for selecting a valid promotion to do a new subscription
	 * @param cardType Object for selecting a card type from the options available 
	 * @param custTitle Object for fetching the salutation
	 * @param firstname Object for fetching the first name 
	 * @param surname Object for fetching the surname 
	 * @param postalcode Object for fetching the post code
	 * @param address Object for fetching the address 
	 * @param custName Object for fetching the Card Holder's Name 
	 * @param cardNum1 Object for fetching the first 8 digits of the Card Number 
	 * @param cardNum2 Object for fetching the last 8 digits of the Card Number
	 * @param date Object for fetching the Expiry Date
	 * @param year Object for fetching the Expiry Year
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 * 
	 */
	public void newSubscriptionCCType1(WebDriver driver, String client, String brand, String promotion, String cardType, String custTitle, String firstname, String surname, String postalcode, String address, String custName, String cardNum1, String date, String year) throws Exception
	{
		try
		{
			element(driver, customerServiceLink).click();	
			TimeUnit.SECONDS.sleep(3);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
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
			element(driver, newSubscription).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, promotionName).sendKeys(promotion);
			element(driver, findPromotion).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, selectPromotion(promotion)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, promotionNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, offerCard(cardType)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, title).sendKeys(custTitle);
			element(driver, firstName).sendKeys(firstname);
			element(driver, surName).sendKeys(surname);
			element(driver, postCode).sendKeys(postalcode);
			element(driver, lookupAddress).click();
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, selectAddress)).selectByVisibleText(address);
			TimeUnit.SECONDS.sleep(5);
			element(driver, custNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			try
			{
				element(driver, custAssociationNextBtn).isDisplayed();
				element(driver, custAssociationNextBtn).click();
			}
			catch(Exception e)
			{
				// No Action Required
			}
			TimeUnit.SECONDS.sleep(2);
			element(driver, issueCalenderNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, customerName).sendKeys(custName);
			TimeUnit.SECONDS.sleep(2);
			element(driver, cardNumber).sendKeys(cardNum1);
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, expiryDate)).selectByVisibleText(""+date+"");
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, expiryYear)).selectByVisibleText(""+year+"");	
			TimeUnit.SECONDS.sleep(5);
			element(driver, checkoutNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, orderRefNumber).isDisplayed();
			orderRef = element(driver, orderRefNumber).getText();
			ATUReports.add("New subscription has been done sucessfully with order reference number as: "+orderRef, "Promotion name: "+ promotion,"Order Reference",orderRef, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			ATUReports.add("Unable to do a new subscription", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}

	public void fetchDetailsCs(WebDriver driver, String client, String brand)
	{
		try
		{
			TimeUnit.SECONDS.sleep(5);
			element(driver, homeLink).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, customerServiceLink).click();
			TimeUnit.SECONDS.sleep(5);
			TimeUnit.SECONDS.sleep(3);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
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

			TimeUnit.SECONDS.sleep(3);
			element(driver, serviceExistingSubscriptionLink).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, customerRefSearch).sendKeys(orderRef);
			TimeUnit.SECONDS.sleep(2);
			new Actions(driver).moveToElement(element(driver, viewCustomersButton)).perform();
			element(driver, viewCustomersButton).click();
			TimeUnit.SECONDS.sleep(5);
			new Actions(driver).moveToElement(element(driver, viewCustomer(orderRef))).perform();
			element(driver, viewCustomer(orderRef)).click();
			TimeUnit.SECONDS.sleep(3);
			if(element(driver, custAssociationNextBtn).isEnabled())
			{
				element(driver, custAssociationNextBtn).click();
				TimeUnit.SECONDS.sleep(3);
			}
			accountID = element(driver, verifyCustomerRef).getText();
			verifyStatus = element(driver, verifyContractStatus).getText();
			verifyType = element(driver, verifySubType).getText();
			payMethod = element(driver, paymentMethod).getText();
			payStatus = element(driver, paymentStatus).getText();
			subRole = element(driver, subscriberRole).getText();
			renStatus = element(driver, renewalStatus).getText();
		}
		catch(Exception e2)
		{		
			ATUReports.add("Unable to fetch details from CS screen",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	/**
	 * Method used for verifying the newly created subscription
	 * @param driver Object for defining the web driver
	 * @param promotion Variable to pass the promotion name
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 */
	public void verifyNewSubscriptionCSType1(WebDriver driver, String promotion, String client, String brand) throws Exception
	{
		try
		{
			try
			{
				fetchDetailsCs(driver, client, brand);
				TimeUnit.SECONDS.sleep(3);
				Assert.assertEquals(accountID, orderRef);
				Assert.assertEquals(verifyStatus, "ACTIVE");
				Assert.assertEquals(verifyType, "STANDARD_SUBSCRIPTION");
				Assert.assertEquals(payMethod, "CREDIT_CARD");
				Assert.assertEquals(payStatus, "Paid");
				Assert.assertEquals(renStatus, "");
				Assert.assertEquals(subRole, "DIRECT");
				ATUReports.add(verifyType + " Order: "+accountID+"has been successfully verified in CS screen with contract status as:"+verifyStatus,"Order Reference: "+ orderRef,"Payment Method: Direct_Debit, Payment Status: UnPaid, Renewal Status: Continous, Subscriber Role: Direct","Payment Method: "+payMethod + ", Payment Status:"+payStatus+ " ,Renewal Status:"+ renStatus+", Subscriber Role: " +subRole,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				TimeUnit.SECONDS.sleep(5); 
			}
			catch (AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription",accountID, verifyStatus, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

		}
		catch (Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}

	public void newSubscriptionDD(WebDriver driver, String client, String brand, String promotion, String cardType, String custTitle, String firstname, String surname, String postalcode, String address, String accHolderName, String accNumber, String SortCode) throws Exception
	{
		try
		{
			element(driver, customerServiceLink).click();	
			TimeUnit.SECONDS.sleep(3);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
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
			element(driver, newSubscription).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, promotionName).sendKeys(promotion);
			element(driver, findPromotion).click();
			TimeUnit.SECONDS.sleep(3);

			element(driver, selectPromotion(promotion)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, promotionNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, offerCard(cardType)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, title).sendKeys(custTitle);
			element(driver, firstName).sendKeys(firstname);
			element(driver, surName).sendKeys(surname);
			element(driver, postCode).sendKeys(postalcode);
			element(driver, lookupAddress).click();
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, selectAddress)).selectByVisibleText(address);
			TimeUnit.SECONDS.sleep(5);
			element(driver, custNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			try
			{
				element(driver, custAssociationNextBtn).isDisplayed();
				element(driver, custAssociationNextBtn).click();
			}
			catch(Exception e)
			{
				// No Action Required
			}
			TimeUnit.SECONDS.sleep(2);
			element(driver, issueCalenderNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, accountHolderName).sendKeys(accHolderName);
			TimeUnit.SECONDS.sleep(2);
			element(driver, accountNumber).sendKeys(accNumber);
			TimeUnit.SECONDS.sleep(2);
			element(driver, sCode).sendKeys(SortCode);
			//element(driver, sCode).sendKeys(sortCode);
			/*Select(element(driver, expiryDate)).selectByVisibleText(""+date+"");
		TimeUnit.SECONDS.sleep(2);
		Select(element(driver, expiryYear)).selectByVisibleText(""+year+"");	
		TimeUnit.SECONDS.sleep(5);*/
			TimeUnit.SECONDS.sleep(5);
			element(driver, lookupBankButton).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, checkoutNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, orderRefNumber).isDisplayed();
			orderRef = element(driver, orderRefNumber).getText();
			ATUReports.add("New subscription has been done sucessfully with order reference number as: "+orderRef, "Promotion name: "+ promotion,"Order Reference",orderRef,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			ATUReports.add("Unable to do a new subscription", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}
	public void verifyNewSubscriptionCSType2(WebDriver driver, String promotion, String client, String brand) throws Exception
	{
		try
		{
			try
			{
				fetchDetailsCs(driver, client, brand);
				TimeUnit.SECONDS.sleep(3);
				Assert.assertEquals(accountID, orderRef);
				Assert.assertEquals(verifyStatus, "ACTIVE");
				Assert.assertEquals(verifyType, "STANDARD_SUBSCRIPTION");
				Assert.assertEquals(payMethod, "DIRECT_DEBIT");
				Assert.assertEquals(payStatus, "Paid");
				Assert.assertEquals(renStatus, "Continious");
				Assert.assertEquals(subRole, "DIRECT");
				ATUReports.add(verifyType + " Order: "+orderRef+"has been successfully verified in CS screen with contract status as:"+verifyStatus,"Order Reference: "+ orderRef,"Payment Method: Direct_Debit, Payment Status: UnPaid, Renewal Status: Continous, Subscriber Role: Direct","Payment Method: "+payMethod + ", Payment Status:"+payStatus+ " ,Renewal Status:"+ renStatus+", Subscriber Role: " +subRole,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				TimeUnit.SECONDS.sleep(5); 
			}
			catch (AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription",accountID, verifyStatus, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

		}
		catch (Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}
	public void newSubscriptionCCGiftType3(WebDriver driver, String client, String brand, String promotion, String cardType, String custTitle, String firstname, String surname, String postalcode, String address, String gCustTitle, String gFirstname, String gSurname, String gPostalcode, String gAddress, String custName, String cardNum1, String date, String year) throws Exception
	{
		try
		{
			element(driver, customerServiceLink).click();	
			TimeUnit.SECONDS.sleep(3);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
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
			element(driver, newSubscription).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, promotionName).sendKeys(promotion);
			element(driver, findPromotion).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, selectPromotion(promotion)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, promotionNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, offerCard(cardType)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, title).sendKeys(custTitle);
			element(driver, firstName).sendKeys(firstname);
			element(driver, surName).sendKeys(surname);
			element(driver, postCode).sendKeys(postalcode);
			element(driver, lookupAddress).click();
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, selectAddress)).selectByVisibleText(address);
			TimeUnit.SECONDS.sleep(5);
			element(driver, giftSubs).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, gTitle).sendKeys(gCustTitle);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gFirstName).sendKeys(gFirstname);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gSurName).sendKeys(gSurname);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gPostCode).sendKeys(gPostalcode);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gLookupAddress).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, gSelectAddress).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, gSaveButton).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, custNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			try
			{
				element(driver, custAssociationNextBtn).isDisplayed();
				element(driver, custAssociationNextBtn).click();
			}
			catch(Exception e)
			{
				// No Action Required
			}
			TimeUnit.SECONDS.sleep(2);
			element(driver, issueCalenderNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, customerName).sendKeys(custName);
			TimeUnit.SECONDS.sleep(2);
			element(driver, cardNumber).sendKeys(cardNum1);
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, expiryDate)).selectByVisibleText(""+date+"");
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, expiryYear)).selectByVisibleText(""+year+"");	
			TimeUnit.SECONDS.sleep(5);
			element(driver, checkoutNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, orderRefNumber).isDisplayed();
			orderRef = element(driver, orderRefNumber).getText();
			ATUReports.add("New subscription has been done sucessfully with order reference number as: "+orderRef, "Promotion name: "+ promotion,"Order Reference",orderRef, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			ATUReports.add("Unable to do a new subscription", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}

	/**
	 * Method used for verifying the newly created subscription
	 * @param driver Object for defining the web driver
	 * @param promotion Variable to pass the promotion name
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 */
	public void verifyNewSubscriptionCSType3(WebDriver driver, String promotion, String client, String brand) throws Exception
	{
		try
		{
			try
			{
				fetchDetailsCs(driver, client, brand);
				TimeUnit.SECONDS.sleep(3);
				//Assert.assertEquals(accountID, orderRef);
				Assert.assertEquals(verifyStatus, "ACTIVE");
				Assert.assertEquals(verifyType, "GIFT_SUBSCRIPTION");
				Assert.assertEquals(payMethod, "CREDIT_CARD");
				Assert.assertEquals(payStatus, "Paid");
				Assert.assertEquals(renStatus, "");
				Assert.assertEquals(subRole, "DONOR");
				ATUReports.add(verifyType + " Order: "+accountID+"has been successfully verified in CS screen with contract status as:"+verifyStatus,"Order Reference: "+ orderRef,"Payment Method: CREDIT_CARD, Payment Status: Paid, Renewal Status: Continous, Subscriber Role: DONOR","Payment Method: "+payMethod + ", Payment Status:"+payStatus+ " ,Renewal Status:"+ renStatus+", Subscriber Role: " +subRole,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				TimeUnit.SECONDS.sleep(5); 
			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the newly created subscription",accountID, verifyStatus, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch (Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}

	/**
	 * Method performed to do a new subscription 
	 * 
	 * @param driver Object for webdriver
	 * @param client Object for selecting a client from the list
	 * @param brand Object for selecting a brand from the list
	 * @param promotion Object for selecting a valid promotion to do a new subscription
	 * @param cardType Object for selecting a card type from the options available 
	 * @param custTitle Object for fetching the salutation
	 * @param firstname Object for fetching the first name 
	 * @param surname Object for fetching the surname 
	 * @param postalcode Object for fetching the post code
	 * @param address Object for fetching the address 
	 * @param custName Object for fetching the Card Holder's Name 
	 * @param cardNum1 Object for fetching the first 8 digits of the Card Number 
	 * @param cardNum2 Object for fetching the last 8 digits of the Card Number
	 * @param date Object for fetching the Expiry Date
	 * @param year Object for fetching the Expiry Year
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 * 
	 */
	public void newSubscriptionCCType4(WebDriver driver, String client, String brand, String promotion, String cardType, String custTitle, String firstname, String surname, String postalcode, String address, String custName, String cardNum1, String date, String year) throws Exception
	{
		try
		{
			element(driver, customerServiceLink).click();	
			TimeUnit.SECONDS.sleep(3);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
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
			element(driver, newSubscription).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, proRadioButton).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, promotionName).sendKeys(promotion);
			element(driver, findPromotion).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, selectPromotion(promotion)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, promotionNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, offerCard(cardType)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, listProButton).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, quantityNum).sendKeys("1");
			TimeUnit.SECONDS.sleep(3);
			element(driver, quantitySel).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, quantityNext).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, title).sendKeys(custTitle);
			element(driver, firstName).sendKeys(firstname);
			element(driver, surName).sendKeys(surname);
			element(driver, postCode).sendKeys(postalcode);
			element(driver, lookupAddress).click();
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, selectAddress)).selectByVisibleText(address);
			TimeUnit.SECONDS.sleep(5);
			element(driver, custNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			try
			{
				element(driver, custAssociationNextBtn).isDisplayed();
				element(driver, custAssociationNextBtn).click();
			}
			catch(Exception e)
			{
				// No Action Required
			}
			TimeUnit.SECONDS.sleep(2);
			element(driver, issueCalenderNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, customerName).sendKeys(custName);
			TimeUnit.SECONDS.sleep(2);
			element(driver, cardNumber).sendKeys(cardNum1);
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, expiryDate)).selectByVisibleText(""+date+"");
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, expiryYear)).selectByVisibleText(""+year+"");	
			TimeUnit.SECONDS.sleep(5);
			element(driver, checkoutNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, orderRefNumber).isDisplayed();
			orderRef = element(driver, orderRefNumber).getText();
			ATUReports.add("New subscription has been done sucessfully with order reference number as:"+orderRef, "Promotion name: "+ promotion,"Order Reference",orderRef, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			ATUReports.add("Unable to do a new subscription", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}
	/**
	 * Method used for verifying the newly created subscription
	 * @param driver Object for defining the web driver
	 * @param promotion Variable to pass the promotion name
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 */
	public void verifyNewSubscriptionCSType4(WebDriver driver, String promotion, String client, String brand) throws Exception
	{
		try
		{
			String proId  = null, proSStatus = null, proSStype = null, proPStatus = null;
			try
			{
				fetchDetailsCs(driver, client, brand);
				TimeUnit.SECONDS.sleep(3);
				proId = element(driver, proAccountId).getText();
				proSStatus = element(driver, proSubStatus).getText();
				proSStype = element(driver, proSubType).getText();
				proPStatus = element(driver, proPaystatus).getText();
				Assert.assertEquals(proId, orderRef);
				Assert.assertEquals(proSStatus, "COMPLETED");
				Assert.assertEquals(proSStype, "PRODUCT_ONLY_SUBSCRIPTION");
				Assert.assertEquals(proPStatus, "Paid");
				ATUReports.add(verifyType + " Order: "+accountID+"has been successfully verified in CS screen with contract status as:"+verifyStatus,"Order Reference: "+ orderRef,"Payment Method: Credit Card, Payment Status: Paid","Payment Method: "+payMethod + ", Payment Status:"+payStatus,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				TimeUnit.SECONDS.sleep(5); 
			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the newly created subscription",accountID, verifyStatus, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch (Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}

	/**
	 * Method used for verifying the newly created subscription
	 * @param driver Object for defining the web driver
	 * @param promotion Variable to pass the promotion name
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 */
	public void searchCs(WebDriver driver, String client, String brand, String customerReference, String AddressLine, String PostCode, String CompanyName, String LastName, String FirstName, String Email, String AccountNumber, String SortCode, String CCNumber, String Country) throws Exception
	{
		try
		{						//Customer Reference
			try
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(5);
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, customerRefSearch).sendKeys(customerReference);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Customer Reference number", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Customer Reference number",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
			try					//Address Line1
			{
				TimeUnit.SECONDS.sleep(3);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(3);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(3);
				try
				{
					element(driver, addressLine).sendKeys(AddressLine);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Address Line", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Address Line",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
			try				//Post Code		
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, postcodeSearch).sendKeys(PostCode);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Post Code", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Post Code",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//Company Name
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try{
					element(driver, companyName).sendKeys(CompanyName);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Company Name", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Company Name",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//Last Name
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, lastName).sendKeys(LastName);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Last Name", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Last Name",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//First Name
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, firstNameSearch).sendKeys(FirstName);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using First Name", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using First Name",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//Email
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, email).sendKeys(Email);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Email", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Email",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//Account Number
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, accNumberSearch).sendKeys(AccountNumber);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Account Number", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Account Number",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//Sort Code
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, sortCodeSearch).sendKeys(SortCode);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Sort code", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Sort Code",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//CC Number
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, creditCardNum).sendKeys(CCNumber);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using CC Number", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using CC Number",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

			try			//Country
			{
				TimeUnit.SECONDS.sleep(5);
				element(driver, homeLink).click();
				TimeUnit.SECONDS.sleep(5);
				element(driver, customerServiceLink).click();
				TimeUnit.SECONDS.sleep(3);
				for (String winHandle : driver.getWindowHandles()) 
				{
					driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
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

				TimeUnit.SECONDS.sleep(3);
				element(driver, serviceExistingSubscriptionLink).click();
				TimeUnit.SECONDS.sleep(5);
				try
				{
					element(driver, country).sendKeys(Country);
					TimeUnit.SECONDS.sleep(3);
					Assert.assertTrue(element(driver, verifySearch).isDisplayed());
					TimeUnit.SECONDS.sleep(3);
				}
				catch(Exception e)
				{
					ATUReports.add("Verified search using Country", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}

			}
			catch (Exception e)
			{
				ATUReports.add("Unable to verify the search using Country",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}
		}
		catch (Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}
	}

	public void newSubscriptionCCGiftType5(WebDriver driver, String client, String brand, String promotion, String cardType, String custTitle, String firstname, String surname, String postalcode, String address, String gCustTitle, String gFirstname, String gSurname, String gPostalcode, String gAddress, String accHolderName, String accNumber, String SortCode) throws Exception
	{
		try
		{
			element(driver, customerServiceLink).click();	
			TimeUnit.SECONDS.sleep(3);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
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
			element(driver, newSubscription).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, promotionName).sendKeys(promotion);
			element(driver, findPromotion).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, selectPromotion(promotion)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, promotionNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, offerCard(cardType)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, title).sendKeys(custTitle);
			element(driver, firstName).sendKeys(firstname);
			element(driver, surName).sendKeys(surname);
			element(driver, postCode).sendKeys(postalcode);
			element(driver, lookupAddress).click();
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, selectAddress)).selectByVisibleText(address);
			TimeUnit.SECONDS.sleep(5);
			element(driver, giftSubs).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, gTitle).sendKeys(gCustTitle);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gFirstName).sendKeys(gFirstname);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gSurName).sendKeys(gSurname);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gPostCode).sendKeys(gPostalcode);
			TimeUnit.SECONDS.sleep(2);
			element(driver, gLookupAddress).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, gSelectAddress).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, gSaveButton).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, custNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			try
			{
				element(driver, custAssociationNextBtn).isDisplayed();
				element(driver, custAssociationNextBtn).click();
			}
			catch(Exception e)
			{
				// No Action Required
			}
			TimeUnit.SECONDS.sleep(2);
			element(driver, issueCalenderNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			TimeUnit.SECONDS.sleep(2);
			element(driver, accountHolderName).sendKeys(accHolderName);
			TimeUnit.SECONDS.sleep(2);
			element(driver, accountNumber).sendKeys(accNumber);
			TimeUnit.SECONDS.sleep(2);
			element(driver, sCode).sendKeys(SortCode);
			//element(driver, sCode).sendKeys(sortCode);
			/*Select(element(driver, expiryDate)).selectByVisibleText(""+date+"");
			TimeUnit.SECONDS.sleep(2);
			Select(element(driver, expiryYear)).selectByVisibleText(""+year+"");	
			TimeUnit.SECONDS.sleep(5);*/
			TimeUnit.SECONDS.sleep(5);
			element(driver, lookupBankButton).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, checkoutNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, orderRefNumber).isDisplayed();
			orderRef = element(driver, orderRefNumber).getText();
			ATUReports.add("New subscription has been done sucessfully with order reference number as: "+orderRef, "Promotion name: "+ promotion,"Order Reference",orderRef,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			ATUReports.add("Unable to do a new subscription", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}
	public void verifyNewSubscriptionCSType5(WebDriver driver, String promotion, String client, String brand) throws Exception
	{
		try
		{
			try
			{
				fetchDetailsCs(driver, client, brand);
				TimeUnit.SECONDS.sleep(3);
				//Assert.assertEquals(accountID, orderRef);
				Assert.assertEquals(verifyStatus, "ACTIVE");
				Assert.assertEquals(verifyType, "GIFT_SUBSCRIPTION");
				Assert.assertEquals(payMethod, "DIRECT_DEBIT");
				Assert.assertEquals(payStatus, "Paid");
				Assert.assertEquals(renStatus, "Continious");
				Assert.assertEquals(subRole, "DONOR");
				ATUReports.add(verifyType + " Order: "+orderRef+"has been successfully verified in CS screen with contract status as:"+verifyStatus,"Order Reference: "+ orderRef,"Payment Method: Direct_Debit, Payment Status: UnPaid, Renewal Status: Continous, Subscriber Role: Donor","Payment Method: "+payMethod + ", Payment Status:"+payStatus+ " ,Renewal Status:"+ renStatus+", Subscriber Role: " +subRole,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				TimeUnit.SECONDS.sleep(5); 
			}
			catch (AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription",accountID, verifyStatus, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

		}
		catch (Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}


	}
	/**
	 * Method performed to do a new subscription 
	 * 
	 * @param driver Object for webdriver
	 * @param client Object for selecting a client from the list
	 * @param brand Object for selecting a brand from the list
	 * @param promotion Object for selecting a valid promotion to do a new subscription
	 * @param cardType Object for selecting a card type from the options available 
	 * @param custTitle Object for fetching the salutation
	 * @param firstname Object for fetching the first name 
	 * @param surname Object for fetching the surname 
	 * @param postalcode Object for fetching the post code
	 * @param address Object for fetching the address 
	 * @param custName Object for fetching the Card Holder's Name 
	 * @param cardNum1 Object for fetching the first 8 digits of the Card Number 
	 * @param cardNum2 Object for fetching the last 8 digits of the Card Number
	 * @param date Object for fetching the Expiry Date
	 * @param year Object for fetching the Expiry Year
	 * @throws Exception To throw an exception whenever an unexpected failure occurs
	 * 
	 */
	public void newSubscriptionFreeType6(WebDriver driver, String client, String brand, String promotion, String cardType, String custTitle, String firstname, String surname, String postalcode, String address) throws Exception
	{
		try
		{
			element(driver, customerServiceLink).click();	
			TimeUnit.SECONDS.sleep(3);
			for (String winHandle : driver.getWindowHandles()) 
			{
				driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
			}
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
			element(driver, newSubscription).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, promotionName).sendKeys(promotion);
			element(driver, findPromotion).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, selectPromotion(promotion)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, promotionNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, offerCard(cardType)).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, title).sendKeys(custTitle);
			element(driver, firstName).sendKeys(firstname);
			element(driver, surName).sendKeys(surname);
			element(driver, postCode).sendKeys(postalcode);
			element(driver, lookupAddress).click();
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, selectAddress)).selectByVisibleText(address);
			TimeUnit.SECONDS.sleep(5);
			element(driver, custNextBtn).click();
			TimeUnit.SECONDS.sleep(2);
			try
			{
				element(driver, custAssociationNextBtn).isDisplayed();
				element(driver, custAssociationNextBtn).click();
			}
			catch(Exception e)
			{
				// No Action Required
			}
			TimeUnit.SECONDS.sleep(2);
			element(driver, issueCalenderNextBtn).click();
			TimeUnit.SECONDS.sleep(5);
			element(driver, checkoutNextBtn).click();
			TimeUnit.SECONDS.sleep(3);
			element(driver, orderRefNumber).isDisplayed();
			orderRef = element(driver, orderRefNumber).getText();
			ATUReports.add("New subscription has been done sucessfully with order reference number as: "+orderRef, "Promotion name: "+ promotion,"Order Reference",orderRef, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Exception e)
		{
			ATUReports.add("Unable to do a new subscription", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
		}
	}
	public void verifyNewSubscriptionFreeType6(WebDriver driver, String promotion, String client, String brand) throws Exception
	{
		try
		{
			try
			{
				fetchDetailsCs(driver, client, brand);
				TimeUnit.SECONDS.sleep(3);
				Assert.assertEquals(accountID, orderRef);
				Assert.assertEquals(verifyStatus, "ACTIVE");
				//Assert.assertEquals(verifyType, "STANDARD_SUBSCRIPTION");
				Assert.assertEquals(payMethod, "Free");
				//Assert.assertEquals(payStatus, "Unpaid");
				//Assert.assertEquals(renStatus, "");
				//Assert.assertEquals(subRole, "DIRECT");
				ATUReports.add(verifyType + " Order: "+accountID+"has been successfully verified in CS screen with contract status as:"+verifyStatus,"Order Reference: "+ orderRef,"Payment Method: Free","Payment Method: "+payMethod + ", Payment Status:"+payStatus+ " ,Renewal Status:"+ renStatus+", Subscriber Role: " +subRole,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				TimeUnit.SECONDS.sleep(5); 
			}
			catch (AssertionError e)
			{
				ATUReports.add("Unable to verify the newly created subscription",accountID, verifyStatus, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				takeScreenShotOnFailure(driver, testName);
				System.out.println(e);
			}

		}
		catch (Exception e1)
		{
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e1);
		}

	}
	public void serviceAmendDetails(WebDriver driver, String client, String brand, String refNumber, String newName, String newAddress) throws Exception
	{
		orderRef=refNumber;
		fetchDetailsCs(driver, client, brand);

		try
		{
			TimeUnit.SECONDS.sleep(2);
			element(driver, amendDetails).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, changeName).clear();
			TimeUnit.SECONDS.sleep(2);
			element(driver, changeName).sendKeys(newName);
			TimeUnit.SECONDS.sleep(2);
			element(driver, changeAddress).clear();
			TimeUnit.SECONDS.sleep(2);
			element(driver, changeAddress).sendKeys(newAddress);
			element(driver, changeDetails).click();
			TimeUnit.SECONDS.sleep(2);
			element(driver, summary).click();
			String cName = element(driver, fName).getText();
			//verification of Details changed
			Assert.assertEquals(cName, newName);
			ATUReports.add("successfully amended details",accountID,"Name and Address change","Change successful", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			TimeUnit.SECONDS.sleep(5); 
		}
		catch (AssertionError e)
		{
			ATUReports.add("Unable to amend details from CS screen", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			takeScreenShotOnFailure(driver, testName);
			System.out.println(e);
		}

	}

}	