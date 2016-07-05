package com.testhouse.Functions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.testhouse.ObjectRepository.SubscriptionManagementObjects;

public class SubscriptionManagementFunctions extends SubscriptionManagementObjects
{
	public void newSubscription(WebDriver driver, String client, String brand, String subType)
	{
		try
		{
			element(driver, subManageLink).click();
			TimeUnit.SECONDS.sleep(15);
			element(driver, newSubscription).click();
			TimeUnit.SECONDS.sleep(10);
			Select(element(driver, clientName)).selectByVisibleText(client);
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, brandName)).selectByVisibleText(brand);
			TimeUnit.SECONDS.sleep(5);
			Select(element(driver, subscribeType)).selectByVisibleText(subType);
			TimeUnit.SECONDS.sleep(5);
		}
		catch (Exception e)
		{
			
		}
	}
}
