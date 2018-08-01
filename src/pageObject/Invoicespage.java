package pageObject;

import AutomationFramework.ApplicationKeyword;
import AutomationFramework.OR;

public class Invoicespage extends ApplicationKeyword{

	public static void org_SettingPage()
	{
		clickOn(OR.DashBoard_AdminDropdown);
		clickOn(OR.Shop_orgpage);
		clickOn(OR.Shop_orgFeatures2Page);

	}

	public static boolean IntactAndQucikBook_Toggle()
	{
		org_SettingPage(); 
		String className=getAttributeValue(OR.orgsetting_IntactSetting, "class");
		System.out.println(className);
		String className2=getAttributeValue(OR.orgsetting_QuickBookIntegrationSetting, "class");
		System.out.println(className2);
		if(className.contains("ng-untouched ng-valid ng-empty")&&className2.contains("ng-untouched ng-valid ng-empty"))
		{
			return false;
		}
		return true;		 
	}
	
	public static void typeAndSearchInvoice()
	{	
		clickOn(OR.Request_InvoicePageLink);
		waitForElementToDisplayWithoutFail(OR.Receive_selectedFacInDropDown, 10);
		ReceivePageObject.InvoiceshopFacility();
		waitTime(2);
		String invoiceName=getProperty("invoiceName");
		//ReceivePageObject.selectByDefaultUser();	
		typeIn(OR.Invoice_SearchInInvoiceTextBox, invoiceName);
		clickOn(OR.Invoice_SearchButton);
	}

	public static void getUserFacility()
	{	
		clickOn(OR.Shop_Menu);
		//String facility_Name=getText(OR.Shop_SHopfor_getfacilityName);
	}
}
