package testcases_functionality;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import AutomationFramework.ApplicationKeyword;
import AutomationFramework.Generickeywords;
import AutomationFramework._OR;
import AutomationFramework.OR;
import funcation_PageObject.*;
import pageObject.Alertpage;
import pageObject.MycartPage;

public class TC_RequestService extends ApplicationKeyword{

	@Parameters({"siteName", "siteUrl"})
	@BeforeTest
	public void startReport(String siteName, String siteUrl) {
		try {
			Loginpage.URL=siteUrl + "#/login/";
			Generickeywords.SITENAME=siteName;
			Generickeywords.DashBoardURL=siteUrl + "#/dashboard";
			String folderPath=OutputDirectory + "/" + siteName;
			File directory = new File(folderPath);
			if (! directory.exists()){
				directory.mkdir();
			}
			extent = new ExtentReports(folderPath+"/RequestService.html", true);
			extent.addSystemInfo("User Name", "QA");
			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

		} catch (Exception e) 
		{
			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
		}
	}

	
	@Test
	public void TC_RequestService_001() {
		testStarts("TC_RequestService_001", "Request Service > Verify that \"Create Service Po\" heading appears on top left side of the page ");
		Loginpage.OpenBrowserAndLoginnew();
		String ReqServDesc = "ReqServDesc"+ApplicationKeyword.randomAlphaNumeric(6);
		String ReqServ_Mfr = "ReqServMfr"+ApplicationKeyword.randomAlphaNumeric(6);
		setProperty("ReqServ_Mfr", ReqServ_Mfr);
		String ReqServ_ID = "ReqServID"+ApplicationKeyword.randomAlphaNumeric(6);
		String ReqServ_Sku = "ReqServsku"+ApplicationKeyword.randomAlphaNumeric(6);
		clickOn(OR.DashBoard_AdminDropdown);
		clickOn(OR.Admin_ItemCatalog);
		waitForElementToDisplayWithoutFail(OR.ItemCatalog_AddItem, 60);
		clickOn(OR.ItemCatalog_AddItem);
		waitForElement(OR.ItemCatalog_AddItem_ItemDetails_Description, 20);
		typeIn(OR.ItemCatalog_AddItem_ItemDetails_Description, ReqServDesc);
		MycartPage.chkMan_level("ReqServ_Mfr");
		if(isElementDisplayed(OR.ItemCatalog_AddItme_TestId, 10))
		{
			typeIn(OR.ItemCatalog_AddItme_TestId, ReqServ_ID);
		}
		waitUntilPageReady();
		clickOn(OR.Request_isServicePOToggle);
		waitUntilPageReady();
		clickOn(OR.ItemCatalog_VendorsTab);
		waitUntilPageReady();
		clickOn(OR.ItemCatalog_AddVendors);
		waitUntilPageReady();
		clickOn(OR.ItemCatalog_Add_Vendorsname);		
		waitUntilPageReady();
		int size = driver.findElements(By.xpath("//*[@class='ui-select-choices-row-inner']")).size();
		for(int i=1;i<=size;i++)
		{
			WebElement e = driver.findElement(By.xpath("(//*[@class='ui-select-choices-row-inner'])["+i+"]"));
			waitTime(1);
			 setProperty("Vendor", "medline");
			if(e.getText().toLowerCase().contains(getProperty("Vendor")))
			{
				e.click();
			}
		}		
		typeIn(OR.ItemCatalog_Add_VendorsSkuName, ReqServ_Sku);
		typeIn(OR.ItemCatalog_Add_VendorsMinOrderQty, "3");
		selectFromDropdown(OR.Request_Duration, "For One Time");
		vendor_Name=getText(OR.ItemCatalog_firstvendor);
		setProperty("Req_vendorName", vendor_Name);
		MycartPage.chkMan_level("ReqServ_Mfr");
		clickOn(OR.ItemCatalog_AddItem_Man_Save);
		waitForElementToDisplayWithoutFail(OR.ItemCatalog_AddItem_MapValidation, 60);
		verifyElementText(OR.ItemCatalog_AddItem_MapValidation, "Do you want to map this item to your various facilities?");
		clickOn(OR.ItemCatalog_AddItem_MapValidation_yes);
		getText(OR.ItemCatalog_AddItem_Map_Header);
		verifyElementText(OR.ItemCatalog_AddItem_Map_SearchFacility_Text, "Search Facility");
		clickOn(OR.ItemCatalog_mapallbuttontopright);
		clickOn(OR.ItemCatalog_mapwithalltopright);
		waitForElementToDisplayWithoutFail(OR.ItemCatalog_verifytextpopup, 60);
		verifyElementText(OR.ItemCatalog_verifytextpopup, "Map with all facilities");
		typeIn(OR.ItemCatalog_purchaseprice, "60");
		clickOn(OR.ItemCatalog_mapallbutton);
		waitForElementToDisplayWithoutFail(OR.ItemCatalog_AddItem_Map_Closepage, 60);	
		clickOn(OR.ItemCatalog_AddItem_Map_Closepage);	
		setProperty("ItemDesc1", ReqServDesc);
		ManageInventory.NavigateManageInvetory();
		ManageInventory.addItemInInventory();
		
		JavascriptExecutor je = (JavascriptExecutor) driver;		
		WebElement element = driver.findElement(By.xpath("//a[@href='#/service-request/create']"));
		je.executeScript("arguments[0].scrollIntoView(true);",element);
		
		clickOn(OR.Request_MenuLink);
		
		clickOn(OR.Request_vendorDropDown);
		waitUntilPageReady();
		int size1 = driver.findElements(By.xpath("//*[starts-with(@id,'ui-select-choices')]//*[starts-with(@ng-bind,'vendor.name')]")).size();
		for(int i=1;i<=size1;i++)
		{
		String so = "(//*[starts-with(@id,'ui-select-choices')]//*[starts-with(@ng-bind,'vendor.name')])["+i+"]";
		WebElement eled = driver.findElement(By.xpath(so));
		if(eled.getText().toLowerCase().contains(getProperty("Vendor")))
		{
			eled.click();
		}
		}
		waitUntilPageReady();
		typeIn(OR.Request_searchBox, ReqServDesc);
		waitForElement(OR.Request_getItemName, 20);
		String ItemDescAfterSearch=getText(OR.Request_getItemName);
		System.out.println(ItemDescAfterSearch);
		if(ItemDescAfterSearch.equalsIgnoreCase(ReqServDesc))
		{
			testLogPass("Item is searched with Item Description");
		}
		else
		{
			testLogFail("Item is not searched with Item Description");
		}
		clearEditBox(OR.Request_searchBox);
		waitForElementToDisplayWithoutFail(OR.Request_searchBox, 20);
		typeIn(OR.Request_searchBox, ReqServ_Sku);
		waitForElementToDisplayWithoutFail(OR.Request_getSkuName, 20);
		String skuAfterSearch=getText(OR.Request_getSkuName);
		if(skuAfterSearch.equalsIgnoreCase(ReqServ_Sku))
		{
			testLogPass("Item is searched with sku");
		}
		else
		{
			testLogFail("Item is not searched with sku");
		}
		clearEditBox(OR.Request_searchBox);
		waitForElementToDisplayWithoutFail(OR.Request_searchBox, 20);
				
	}
	
	@Test
	public void TC_RequestService_002() 
	{
		testStarts("TC_RequestService_002", "Request Service > Verify that \"Create Service Po\" heading appears on top left side of the page ");
	//	Loginpage.OpenBrowserAndLoginnew();
		RequestServicepage.SelectVendor();
		RequestServicepage.navigateToRequestpage();
//		RequestServicepage.PageVerifyication();
		clickOn(OR.Order_Vendor_dropdown);
		int size2= driver.findElements(By.xpath("//*[starts-with(@id,'ui-select-choices-row')]")).size();
		for(int i=1;i<=size2;i++)
		{
			WebElement eled = driver.findElement(By.xpath("(//*[starts-with(@id,'ui-select-choices-row')])["+i+"]"));
			if(eled.getText().toLowerCase().contains(getProperty("Vendor")))
			{
				eled.click();
			}
		}
	
	
		typeIn(OR.Request_searchBox, getProperty("ReqServ_Mfr"));
		if(isElementDisplayed(OR.Request_Addbutton))
		{
			clickOn(OR.Request_Addbutton);
			String Addbtn ="Addbtn#xpath=//*[@id='btnAdd' and contains(text(),'Add')]/ancestor::div[3]";
			String one = getAttributeValue(Addbtn, "class");
			if(one.contains("ng-hide"))
			{
				testLogPass("Add button is not present");
			}
		}
		RequestServicepage.DropdownRequestService();
		RequestServicepage.SpecialInstruction();

	}
	
//	@Test
//	public void TC_RequestService_003() 
//	{
//		testStarts("TC_RequestService_003", "Request Service > Down arrow button > Verify that when any item is listed in grid, two more options appear ");
//		Loginpage.OpenBrowserAndLoginnew();
//		RequestServicepage.navigateToRequestpage();
//		
//		String s=getProperty("ReqServ_Mfr");
//		typeIn(OR.Request_searchBox, s);
//		waitForElementToDisplayWithoutFail(OR.Request_getSkuName, 20);
//		
//		
//		if(isElementDisplayed(OR.Request_Addbutton))
//		{
//			clickOn(OR.Request_Addbutton);
//			String one = driver.findElement(By.xpath("//*[@id='btnAdd' and contains(text(),'Add')]/ancestor::div[3]")).getAttribute("class");
//			if(one.contains("ng-hide"))
//			{
//				testLogPass("Add button is not present");
//			}
//		}
//		
//		RequestServicepage.PriceItesms();
//		RequestServicepage.clearCard();
//		
//	}
//	
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


