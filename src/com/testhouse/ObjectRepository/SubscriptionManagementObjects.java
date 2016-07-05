package com.testhouse.ObjectRepository;

import com.testhouse.Functions.GeneralFunctions;
import org.openqa.selenium.By;

public class SubscriptionManagementObjects extends GeneralFunctions
{
	public By subManageLink = By.linkText("Subscription Management");
	public By newSubscription = By.linkText("New Subscription");
	public By clientName = By.id("newsubscription_form:client_meh"); 
	public By brandName = By.id("newsubscription_form:brandList");
	public By subscribeType = By.id("newsubscription_form:deFunction");
}
