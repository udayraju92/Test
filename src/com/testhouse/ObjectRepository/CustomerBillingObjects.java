package com.testhouse.ObjectRepository;

import org.openqa.selenium.By;

import com.testhouse.Functions.CSFunctions;

public class CustomerBillingObjects extends CSFunctions 
{
	/* Customer Billing Objects */
	public By customerBillingLink = By.linkText("Customer Billing");
	public By customerDetailsLink = By.id("iconj_id55");
	public By verifyLedger = By.id("mainFrom:id_accountHeaderPanel_header");
	public By accSubDet = By.id("mainFrom:id_acsTabDetailsView_lbl");
	public By billingTerms = By.id("mainFrom:id_bTerm");
	public By optimistic = By.id("mainFrom:id_opt");
	
	//Verification of CB screen
	public By custDetails = By.id("mainFrom:id_customerDetailsTab_lbl");
	public By verifyCusDetails1 = By.id("mainFrom:j_id212");
	public By billDetails = By.id("mainFrom:j_id223_lbl");
	public By verifyCusDetails2 = By.id("mainFrom:j_id224_body");
	public By deliveryAdd = By.id("mainFrom:j_id245_lbl");
	public By verifyCusDetails3 = By.id("mainFrom:j_id246_body");
	public By listView = By.id("mainFrom:id_acsTabListView_lbl");
	public By verifyLView = By.id("mainFrom:id_ledgerListView:id_colDateheader:sortDiv");
	public By bankInst = By.id("mainFrom:id_acsTabInstructionsView_lbl");
	public By verifyBankInst = By.id("mainFrom:id_instructions:id_colTypeheader:sortDiv");
	public By verifyAccSub = By.id("mainFrom:id_bTermLbl");
	public By billschedule = By.id("mainFrom:id_acsTabBillingEventsView_lbl");
	public By payDetails = By.id("mainFrom:id_acsTabPaymentView_lbl");
	public By verifyPayDetails = By.id("mainFrom:id_typeLbl");
}
