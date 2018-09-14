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

public class Testm extends ApplicationKeyword{

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

			extent = new ExtentReports(folderPath+"/Special.html", true);
			extent.addSystemInfo("User Name", "QA");
			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

		} catch (Exception e) 
		{
			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
		}
	}
	@Test
	public void Tc_Shop_001() {
		testStarts("Tc_Shop_001", "SHOP - Generate PO");
		Loginpage.OpenBrowserAndLoginnew();
		Itemcatalog.NavigatetoItemcatalog();
		Itemcatalog.addNewItem();
		ManageInventory.NavigateManageInvetory();
		ManageInventory.addItemInInventory();
		waitUntilPageReady();
		JavascriptExecutor je = (JavascriptExecutor) driver;		
		WebElement element = driver.findElement(By.xpath("//*[@href='#/shop']"));
		je.executeScript("arguments[0].scrollIntoView(true);",element);
		clickOn(_OR.Dashboard_Submenu_shop);
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitTime(5);
		waitUntilPageReady();
		if(isElementDisplayed(OR.Shop_SHopfor_Search_Addtocart_First))
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
		}
		waitUntilPageReady();
		if(driver.findElements(By.xpath("//*[@class='modal-title']")).size()!=0)
		{
		String one = getTextchild("//*[@class='modal-title']") ;
		if(one.contains("Price check"))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		
		waitUntilPageReady();
		}
		waitUntilPageReady();
		
		if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
		{
		String one3 = getTextchild("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']") ;
		if(one3.contains("This item is not under contract or a preference item from your distributor, It may cost more."))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		waitUntilPageReady();
		}
		waitUntilPageReady();
		if(driver.findElements(By.xpath("//*[@class='modal-title']")).size()!=0)
		{
		String one1 = getTextchild("//*[@class='modal-title']") ;
		if(one1.contains("Item reorder warning"))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		}
		waitUntilPageReady();
		clickOn(OR.MyCart);
		waitUntilPageReady();
		clickOn(OR.MyCart_GeneratePo);
		waitTime(5);

if(driver.findElements(By.xpath("//button[@class='confirm']")).size()!=0)
{
	clickOn(OR.Cpwd_okay);
}
waitTime(5);
if(driver.findElements(By.xpath("//button[@class='confirm']")).size()!=0)
{
	clickOn(OR.Cpwd_okay);
}
waitTime(5);
if(driver.findElements(By.xpath("//button[@class='confirm']")).size()!=0)
{
	clickOn(OR.Cpwd_okay);
}
ToastmesssageSucess();
waitUntilPageReady();
waitForElement(OR.Order_Myorder_txt);
verifyElement(OR.Order_Myorder_txt);
clickOn(OR.Orders_Link);
waitUntilPageReady();
driver.findElement(By.xpath("//a[starts-with(@ng-href,'#/orders/view')]")).click();
waitUntilPageReady();
testLogPass(driver.getTitle());
}
	
	@Test
	public void TC_ShopCart_001()
	{
		testStarts("TC_ShopCart_001", "Shop Cart - generate PO");
		waitUntilPageReady();
		clickOn(OR.MyCart);
		String beforeadd = getText(OR.MyCart_count);
		int countitem =Integer.parseInt(beforeadd);
    	if(countitem==0)
    	{
    		Shopcart.Additem();
    	}
    	verifyElement(OR.MyCart_SpecialInstruction);
    	waitUntilPageReady();
    	String BeforeCount = driver.findElement(By.xpath("//*[@class='item-qty-editable-label ng-binding']")).getText();
    	int before = Integer.parseInt(BeforeCount);
    	
    	testLogPass("Before increasing the item "+before);
    	clickOn(OR.InvLoc_Add_PlusBtn);
    	waitForAngularLoad();
    	String AfterIncreaseCount = driver.findElement(By.xpath("//*[@class='item-qty-editable-label ng-binding']")).getText();
    	int After = Integer.parseInt(AfterIncreaseCount);
    	if(before!=After)
    	{
    		testLogPass("Price is increased");
    	}
    	waitUntilPageReady();
    	clickOn(OR.InvLoc_Add_MinusBtn);
    	waitForAngularLoad();
    	String BeforeCount1 = driver.findElement(By.xpath("//*[@class='item-qty-editable-label ng-binding']")).getText();
    	int before1 = Integer.parseInt(BeforeCount1);
    	if(before1==before)
    	{
    		testLogPass("Price is decreasment");
    	}
    	waitUntilPageReady();
    	waitForElement(OR.MyCart_editItemQty);
		clickOn(OR.MyCart_editItemQty);
		typeIn(OR.MyCart_editItemQtyTextBox, "0");
		waitForElement(OR.MyCart_cancelQty);
		clickOn(OR.MyCart_cancelQty);
		waitUntilPageReady();
		clickOn(OR.MyCart_editItemQty);
		waitUntilPageReady();
		typeIn(OR.MyCart_editItemQtyTextBox, "2");
		waitUntilPageReady();
		clickOn(OR.MyCart_submitQty);
		waitUntilPageReady();
		clickOn(OR.MyCart_editItemQty);
		typeIn(OR.MyCart_editItemQtyTextBox, "1");
		waitUntilPageReady();
		clickOn(OR.MyCart_submitQty);
    	String SpecialInstr = randomAlphaNumeric(8)+"sp1"; 
		setProperty("SpecialGenerateNumber", SpecialInstr);
    	typeIn(OR.MyCart_SpecialInstruction,SpecialInstr);
    	 String PoNumber = randomAlphaNumeric(9)+"-002"; 
		 setProperty("SpecialGeneratePoNumber", PoNumber);
		 
		 String getAttr = getAttributeValue(OR.MyCart_usePo, "class");
		 if(getAttr.contains("ng-empty"))
		 {
			 clickOn(OR.MyCart_usePo);
			 typeIn(OR.MyCart_GeneratePoNumber, PoNumber);
		 }
		if(getAttr.contains("ng-not-empty"))
		{
			typeIn(OR.MyCart_GeneratePoNumber, PoNumber);
		}
		waitUntilPageReady();
typeIn(OR.MyCart_GeneratePoNumber, PoNumber);
clickOn(OR.MyCart_GeneratePo);
waitTime(5);
if(driver.findElements(By.xpath("//button[@class='confirm']")).size()!=0)
{
	clickOn(OR.Cpwd_okay);
}
waitTime(5);
if(driver.findElements(By.xpath("//button[@class='confirm']")).size()!=0)
{
	clickOn(OR.Cpwd_okay);
}
waitTime(5);
if(driver.findElements(By.xpath("//button[@class='confirm']")).size()!=0)
{
	clickOn(OR.Cpwd_okay);
}
waitUntilPageReady();
//verifyElement(OR.MyCart_YourcartEmpty);
//waitForElement(OR.Order_Myorder_txt);
verifyElement(OR.Order_Myorder_txt);
clickOn(OR.Orders_Link);
typeIn(OR.Receive_SearchTextBox, PoNumber);
clickOn(OR.PrintBarcodes_searchButton);
waitUntilPageReady();
driver.findElement(By.xpath("//*[text()='"+PoNumber+"']")).click();
typeIn(OR.MyCart_SpecialInstruction,"");
clickOn(OR.MyCart_Save);
ToastmesssageSucess();
	}
	@Test
	public void TC_OrderDetails_001() {
		testStarts("TC_OrderDetails_001", "Verify that PO number appears on top.");
			waitUntilPageReady();
		clickOn(OR.Order);
		waitUntilPageReady();
		String one =driver.findElement(By.xpath("//a/i/sup")).getText();
		int COunt = Integer.parseInt(one);
		if(COunt!=0)
		{
			waitUntilPageReady();
			driver.findElement(By.xpath("//a/i/sup")).click();
			waitUntilPageReady();
			clickOn(OR.OrderDetails_Btn_GeneratePo);
			clickOn(OR.OrderDetails_Btn_ClearAllCart);
			clickOn(OR.Template_confirmButton);
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//*[@class='confirm' and text()='OK']")).size()!=0)
			{
				clickOn(OR.Confirmionok);
			}
		}
		clickOn(OR.Order);
		OrderPage.SearchItem(getProperty("SpecialGeneratePoNumber"));
		waitUntilPageReady();
		waitTime(3);
		if(driver.findElements(By.xpath("//*[text()='"+getProperty("SpecialGeneratePoNumber")+"']")).size()!=0)
		{
			driver.findElement(By.xpath("//*[text()='"+getProperty("SpecialGeneratePoNumber")+"']")).click();
			waitForElement(OR.OrderDetails_PO_Dropdown);
			getTextchild("//*[@class='pagehead ng-binding']");
			verifyElement(OR.OrderDetails_Close);
			clickOn(OR.OrderDetails_Close);
			verifyElement(OR.Order_Search_Input);
		}	
	}
	@Test
	public void Tc_Invoice_001() {
		testStarts("Tc_Invoice_001", "Verify that “Manage Inventory Locations” heading appears on top left side of page.");
		waitUntilPageReady();
		Receive.NavigateToApprovalflow();
		ReceiveOrderDetails.Search(getProperty("SpecialGeneratePoNumber"));
		clickOn(OR.OrderDetails_PO_Dropdown);
		mouseOver(OR.Order_PO_first_invoice);
		waitForElement(OR.OrderDetails_AddInvoice);
		clickOn(OR.OrderDetails_AddInvoice);
		selectFromDropdown(OR.Invoice_AddItemInInvoice, "All");
		clickOn(OR.Invoice_AddItemButton);
		String InvoiceNumber= "InvoiceUpdateNo"+generateRandomInt(5);
		setProperty("InvoiceUpdateNumber", InvoiceNumber);
		typeIn(OR.OrderDetails_InvoiceInput, InvoiceNumber);
		clickOn(OR.SI_SAveButton);
		waitUntilPageReady();
		waitTime(5);
		if(isElementDisplayed(OR.OrderDetails_Invoice_validation))
		{	
			clickOn(OR.Dep_confirmButton);
			ToastmesssageSucess();
		}else
		if(isElementDisplayed(OR.ReceiveOrderDetails_InvoiceBlank))
		{
			verifyElementText(OR.ReceiveOrderDetails_InvoiceBlank, "Invoice # is blank.");
			clickOn(OR.SI_ConfirmButton);
		}
		Invoices.NavigateToInvoice();
		waitUntilPageReady();
		int size = driver.findElements(By.xpath("//table/thead/tr/th//a")).size();
		for(int i=1; i<=size;i++)
		{
			String one = driver.findElement(By.xpath("(//table/thead/tr/th//a)["+i+"]")).getText();
			if(one.isEmpty())
			{
				
			}
			else
			{
			testLogPass("Header is "+one);
			}
		}
	
		
	}
	
	
//	
//	@Test
//	public void Tc_Test_001() {
//		testStarts("Tc_Test_001", "Verify that user gets redirected to \"User Alert\" page when clicks on \" Alert\" button.");
//		openBrowser(Loginpage.URL);
//		Loginpage.login(Loginpage.UserName, Loginpage.Password);
//		User.adminAndUserPage();
//		typeIn(OR.Facilty_SearchBox, "13 Feb");
//		clickOn(OR.News_searchButton);
//		waitForElement(OR.ItemcaCategory_Edit);
//		clickOn(OR.ItemcaCategory_Edit);
//		waitForElement(_OR.user_addUser_FacilityAccess);
//		 clickOn(_OR.user_addUser_FacilityAccess);
//		 
//		 clickOn(OR.Pagination_Last);
//		 String GetTextLAST = driver.findElement(By.xpath("(//*[@class='pagination-page ng-scope active']/a)[1]")).getText();
//		 int ConvertLAstText =Integer.parseInt(GetTextLAST);
//		 
//		 
//		 for(int j =1 ;j<=ConvertLAstText;j++)
//		 {
//			 clickOn(OR.Pagination_First);
//			 int sizeOfEdit = driver.findElements(By.xpath("//label[text()='Edit PO']/following-sibling::div/input")).size();
//			 for(int i=1 ;i<=sizeOfEdit;i++)
//			 {
//				 driver.findElement(By.xpath("(//label[text()='Edit PO']/following-sibling::div/input)["+i+"]")).click();
//			 }
//			 
//			 
//			 if(driver.findElements(By.xpath("//*[@class=\"pagination-next ng-scope disabled\"]")).size()!=0)
//			 {
//				 
//			 }else if(driver.findElements(By.xpath("(//*[@ class='pagination-page ng-scope']/a)["+j+"]")).size()!=0)
//			 {
//				 driver.findElement(By.xpath("(//*[@ class='pagination-page ng-scope']/a)["+j+"]")).click();
//			 }
//				 
//		 }
//	}
//	
//	@Test
//	public void Tc_Test_002() {
//		testStarts("Tc_Test_001", "Verify that user gets redirected to \"User Alert\" page when clicks on \" Alert\" button.");
//		openBrowser(Loginpage.URL);
//		Loginpage.login(Loginpage.UserName, Loginpage.Password);
////		
////		
////		selectFromDropdown(OR.VendorAccSetup_VendorDropdown, "Cardinal Pharma");
////		int sie = driver.findElements(By.xpath("//*[starts-with(@id,'facility_account_text')]")).size();
////		for(int i=1;i<=sie;i++)
////		{driver.findElement(By.xpath("(//*[starts-with(@id,'facility_account_text')])["+i+"]")).clear();
////		driver.findElement(By.xpath("(//*[starts-with(@id,'facility_account_text')])["+i+"]")).sendKeys("123");
////		}
////		System.out.println("123");
//	}
	
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


