package com.testhouse.ObjectRepository;

import org.openqa.selenium.By;

/**
 * Class file to store all the objects which are relavent for the application
 * @version 1.0
 * @author Testhouse
 *
 */
public class CustomerServiceObjects
{
	/* Login & Logout Objects */
	public By username = By.id("id_loginForm:id_username");
	public By password = By.id("id_loginForm:id_password");
	public By logIn = By.id("id_loginForm:id_loginButton");
	public By logOut = By.linkText("Logout");

	/* Customer Service Objects */
	public By customerServiceLink = By.linkText("Customer Services");
	public By clientSelect = By.id("brandSearchForm:client");

	public By brandSelect(String brand)
	{
		By brandSelect = By.linkText(brand);
		return brandSelect;
	}

	public By fastFoward = By.xpath("//div/table/tbody/tr/td[10]");
	public By newSubscription = By.id("newSubscriptionLink");	
	public By promotionName = By.id("form:id_promoNameInputText");
	public By findPromotion = By.xpath("//*[contains(@value,'Find Promotion')]");

	public By selectPromotion(String promotion)
	{
		By selectPromotion = By.xpath("//tr/td[@class='rich-table-cell center' and text()='"+promotion+"']/following-sibling::td/input");
		return selectPromotion;
	}
	public By promotionNextBtn = By.id("form:id_selectPromotionNext");

	public By offerCard(String card)
	{
		By offerCard = By.xpath("//*[contains(text(),'"+card+"')]/parent::td/following::td/input");
		return offerCard;
	}

	public By title = By.id("id_newCustomerDetailsForm:id_customerTitleDecorator:id_customerTitle");
	public By firstName = By.id("id_newCustomerDetailsForm:id_customerForenameDecorator:id_customerForename");
	public By surName = By.id("id_newCustomerDetailsForm:id_customerSurnameDecorator:id_customerSurname");
	public By postCode = By.id("id_newCustomerDetailsForm:id_custInputDetailsPostCodeDecorator:id_custInputDetailsPostCode");
	public By lookupAddress = By.id("id_newCustomerDetailsForm:id_customerBillingAddressLookupButton");
	public By selectAddress = By.name("id_newCustomerDetailsForm:j_id147");
	public By custNextBtn = By.id("id_newCustomerDetailsForm:id_nextButton");
	public By custAssociationNextBtn = By.id("id_btnNext");
	public By issueCalenderNextBtn = By.id("issueCalendarForm:id_btnOrderNext");
	public By customerName = By.id("id_checkoutForm:id_decorateCreditCardCustomerName:id_creditCardCustomerName");
	public By cardNumber = By.id("id_checkoutForm:id_decorateCreditCardNumber:id_creditCardNumber");
	public By expiryDate = By.name("id_checkoutForm:j_id180:j_id188");
	public By expiryYear = By.xpath("//div[@id='id_checkoutForm:id_decorateCreditCardExpiryYear:j_id202']/select");
	public By checkoutNextBtn = By.id("id_checkoutForm:id_nextButton");
	public By orderRefNumber = By.id("id_orderRef");

	// Verify the newly created order
	public By homeLink = By.linkText("Main Menu");
	public By serviceExistingSubscriptionLink = By.id("existingSubscriptionLink");
	public By customerRefSearch = By.id("searchCriteria:id_custrefinputtext");
	public By viewCustomersButton = By.id("searchCriteria:id_advancedCustomerSearchButton");

	/*public By viewCustomer(String orderNum)
	{
		By viewCustomer = By.id("searchCriteria:customerTable:"+orderNum+":selectCustomerButton");
		return viewCustomer;
	}*/
	public By viewCustomer(String orderNum)
	{
		By viewCustomer = By.xpath("//*[contains(text(),'"+orderNum+"')]/following::td/input");
		return viewCustomer;
	}

	public By verifyCustomerRef = By.id("summaryform:id_textCustOptionRef");
	public By verifyContractStatus = By.id("summaryform:id_currContractStatustext");
	public By verifySubType = By.id("summaryform:id_textCustOptionSubcriptionType");
	public By paymentMethod = By.xpath("//*[contains(text(),'Payment Method')]/parent::td/following::td/span");
	public By paymentStatus = By.id("summaryform:id_currcontractpaymentStatustext");
	public By renewalStatus = By.id("summaryform:id_currcontractrenewalStatustext");
	public By subscriberRole = By.id("summaryform:id_textCustOptionSubcriberType");

	//For DD subscription from CS screen
	public By accountHolderName = By.id("id_checkoutForm:id_accountHolderDecorator:id_accountHolder");
	public By accountNumber = By.id("id_checkoutForm:id_accountNumberDecorator:id_accountNumber");
	public By sCode = By.id("id_checkoutForm:id_sortCodeDecorator:id_sortCode");
	public By lookupBankButton = By.id("id_checkoutForm:id_lookupBankAddressButton");

	//For Gift subscription from CS screen
	public By giftSubs = By.id("id_newCustomerDetailsForm:id_custDetailsGiftSubscriptionButton");
	public By gTitle = By.id("id_newCustomerDetailsForm:id_recipientTitleDecorator:id_recipientTitle");
	public By gFirstName = By.id("id_newCustomerDetailsForm:id_recipientFirstNameDecorator:id_recipientFirstName");
	public By gSurName = By.id("id_newCustomerDetailsForm:id_recipientSurnameDecorator:id_recipientSurName");
	public By gPostCode = By.id("id_newCustomerDetailsForm:id_recipientPostCodeDecorator:id_recipientPostCode");
	public By gLookupAddress = By.id("id_newCustomerDetailsForm:id_customerBillingAddressLookupButton");
	public By gSelectAddress = By.name("id_newCustomerDetailsForm:j_id147");
	public By gSaveButton = By.name("id_newCustomerDetailsForm:id_saveRecipientCustomerButton");

	//For product only subscription
	public By proRadioButton = By.id("form:id_subscriptionOption:1");
	public By listProButton = By.id("j_id53:id_textListProducts");
	public By quantityNum = By.id("j_id53:id_poPurchaseList:0:id_poPurchaseQuantity");
	public By quantitySel = By.id("j_id53:id_poPurchaseList:0:id_selectedOffer");
	public By quantityNext = By.id("j_id53:id_selectOfferNext");
	public By proSubStatus = By.id("summaryform:id_productCurrContractStatustext");
	public By proAccountId = By.id("summaryform:id_textProductCustOptionRef");
	public By proSubType = By.id("summaryform:id_textProductCustOptionSubcriptionType");
	public By proPaystatus = By.id("summaryform:id_pProductCurrcontractpaymentStatustext");

	//Search CS screen
	public By verifySearch = By.xpath("//input[contains(@id, 'selectCustomerButton')]");
	public By addressLine = By.id("searchCriteria:id_line1inputtext");
	public By postcodeSearch = By.id("searchCriteria:id_postcodeinputtext");
	public By companyName = By.id("searchCriteria:id_companynameinputtext");
	public By lastName = By.id("searchCriteria:id_line1inputtext");
	public By firstNameSearch = By.id("searchCriteria:id_forenameinputtext");
	public By email = By.id("searchCriteria:id_Emailinputtext");
	public By accNumberSearch = By.id("searchCriteria:id_accountnumberinputtext");
	public By sortCodeSearch = By.id("searchCriteria:id_sortcodeinputtext");
	public By creditCardNum = By.id("searchCriteria:id_cccardnumberinputtext");
	public By country = By.id("searchCriteria:id_countrylistmenu");

	//Overseas
	public By selectCountry = By.id("form:id_countryMenu");
	public By overAddress = By.id("id_newCustomerDetailsForm:id_customerBillingAddressLine1Decorator:id_customerBillingAddressLine1");

	//Service existing subs
	public By amendDetails = By.linkText("Amend Details");
	public By changeName = By.id("amendDetailsform:id_inputTextAmendDetailsNameDecorator:id_inputTextAmendDetailsName");
	public By changeAddress = By.id("amendDetailsform:id_inputTextAmendDetailsLine1Decorator:id_inputTextAmendDetailsLine1");
	public By changeDetails = By.id("amendDetailsform:id_inputTextAmendDetailsAlter");
	public By fName = By.id("summaryform:id_textCustOptionForeName");
	public By summary = By.linkText("Summary");	

}
