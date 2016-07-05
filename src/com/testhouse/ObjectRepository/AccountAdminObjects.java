package com.testhouse.ObjectRepository;

import org.openqa.selenium.By;

import com.testhouse.Functions.GeneralFunctions;

public class AccountAdminObjects extends GeneralFunctions
{
	/* Schedule Objects */
	public By accountAdminLink = By.linkText("Account Admin");
	public By manageSchedule = By.id("iconid_manageSchedules");
	public By clientSelect = By.id("id_scheduleListform:client");
	
	public By brandSelect(String brand)
	{
		By brandSelect = By.linkText(brand);
		return brandSelect;
	}
	public By fastFoward = By.xpath("//div/table/tbody/tr/td[10]");
	public By addSchedule = By.id("id_scheduleListform:id_addScheduleButton");
	
	/* New Schedule */
	public By scheduleName = By.id("id_scheduleForm:id_nameDecorator:name");
	public By scheduleDesc = By.id("id_scheduleForm:id_descriptionDecorator:desc");
	public By optimisticCheck = By.id("id_scheduleForm:opt");
	public By calenderBasedCheck = By.id("id_scheduleForm:cal");
	public By activeCheck = By.id("id_scheduleForm:act");	
	public By subscriptionType = By.id("id_scheduleForm:id_typeOfSubscriptionDecorator:charge");
	public By saveScheduleBtn = By.id("id_scheduleForm:id_saveButton");
	public By schedulesList = By.id("id_scheduleForm:id_schedulesButton");
	
	public By findSchedule(String name)
	{
		By brandSelect = By.xpath("//*[contains(text(),'"+name+"')]");
		return brandSelect;
	}
	
	/* New Event */
	public By scheduleEventsBtn = By.id("id_scheduleForm:id_eventsButton");
	public By newEventBtn = By.id("form:j_id93");
	public By eventWhen = By.id("j_id99:eventWhen");
	public By eventDeliveryType = By.id("j_id99:deliverableType");
	public By eventNextBtn = By.id("j_id99:j_id149");
	public By eventDest = By.id("j_id99:destinationType");
	public By eventFinishBtn = By.id("j_id99:j_id150");
	public By eventSaveBtn = By.id("form:j_id94");
	public By eventBackBtn = By.id("form:j_id95");
}
