package testcases_functionality;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class TC_Shop extends ApplicationKeyword{

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

			extent = new ExtentReports(folderPath+"/shop.html", true);
			extent.addSystemInfo("User Name", "QA");
			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

		} catch (Exception e) 
		{
			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
		}
	}

	
	@Test
	public void Tc_Shop_001() {
		testStarts("Tc_Shop_001", "Verify that \"Shopping For Facility Name\" label appears on top left of page.");
		Loginpage.OpenBrowserAndLoginnew();
		ManageInventory.NavigateManageInvetory();
		ManageInventory.addItemInInventory();
		WebElement element = driver.findElement(By.xpath("//*[@href='#/shop']"));
		executor.executeScript("arguments[0].scrollIntoView(true);",element);
		clickOn(_OR.Dashboard_Submenu_shop);
		Shop.verifyShop();
		//Shop.autosearch();
		//Shop.verifyselectedFacdisable();
		//Shop.Search("tes");
	//	verifyElement(OR.Shop_VendorSelect);
		Shop.Vendorlisverification();
		clickOn(OR.Shop_Mostordered_Itemdescription);
		Sorting("//hyb-highlight[@value='name']");
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitForElementToDisplayWithoutFail(OR.Shop_wait, 10);
waitUntilPageReady();
		if(isElementDisplayed(OR.Shop_SHopfor_Search_Addtocart_First))
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
		}
		//clickOn(OR.HeaderClose);
	}
	
	@Test
	public void Tc_Shop_002() {
		testStarts("Tc_Shop_002", "Verify that \"Shopping For Facility Name\" label appears on top left of page.");
		//Loginpage.OpenBrowserAndLoginnew();
		Shop.Organiationchange();
		Shop.Grid();
		Shop.MenuList();
		clickOn(OR.Shop_SHopfor_MostOrderedradiobutton);
		Shop.MenuList();
		clickOn(OR.Shop_SHopfor_RecentlyOrderedradiobutton);
		Shop.MenuList();
		clickOn(OR.Shop_SHopfor_FavOrderedradiobutton);
		Shop.MenuList();
	}
	
	@Test
	public void Tc_Shop_003() {
		testStarts("Tc_Shop_003", "Verify that drop down appears with following options when user clicks the “down arrow” button on left of item name: ");
		clickOn(_OR.Dashboard_Submenu_shop);
		waitUntilPageReady();
		  WebElement element = driver.findElement(By.xpath("//*[text()='"+getProperty("ItemDesc")+"']"));
		   element.click();
		String one = driver.findElement(By.xpath("(//*[@class='popover ng-scope ng-isolate-scope top fade in']//tbody/tr/td/strong)[1]")).getText();
				testLogPass(one);
	}
	
	
	@Test
	public void Tc_Shop_005() {
		testStarts("Tc_Shop_005", "Verify that if user clicks on Star icon then color of icon changes into blue.");
	clickOn(_OR.Dashboard_Submenu_shop);
	clickOn(OR.Shop_SHopfor_MyInventoryradiobutton);
	String itemDsc=getProperty("ItemDesc");
	typeIn(OR.Shop_SHopfor_SearchBox,itemDsc);
	waitUntilPageReady();
		String Fav = getAttributeValue(OR.Shop_SHopfor_favtab, "ng-if");
		
		if (Fav.equals("isFavorite")) {
			testLogPass("item is isFavorite");
			String one = getProperty("ItemDesc");
			
			if(driver.findElements(By.xpath("//*[text()='"+one+"']")).size()!=0)
			{
				clickOn(OR.Shop_SHopfor_FavOrderedradiobutton);
				if(driver.findElements(By.xpath("//*[text()='"+one+"']")).size()!=0)
				{
					testLogPass("item in Favorite then it isdisplay under the Favourite Item radio button.");
				}
			}
			
			
		} else

			if (Fav.equals("!isFavorite")) {
				testLogPass("item is !isFavorite");
			}
		clickOn(OR.Shop_SHopfor_favtab);
		String Fav1 = getAttributeValue(OR.Shop_SHopfor_favtab, "ng-if");
		if (Fav1.equals("!isFavorite")) {
			testLogPass("item is !isFavorite");
		} else if (Fav1.equals("isFavorite")) {
			testLogPass("item is isFavorite");

			String one = getProperty("ItemDesc");
			
			if(driver.findElements(By.xpath("//*[text()='"+one+"']")).size()!=0)
			{
				clickOn(OR.Shop_SHopfor_FavOrderedradiobutton);
				if(driver.findElements(By.xpath("//*[text()='"+one+"']")).size()!=0)
				{
					testLogPass("item in Favorite then it isdisplay under the Favourite Item radio button.");
				}
			}
			
		}
		
		 WebElement Fav11 = driver.findElement(By.xpath("//*[text()='"+getProperty("ItemDesc")+"']"));
		    Actions toolAct = new Actions(driver);
		    toolAct.moveToElement(Fav11).build().perform();
		    String one = driver.findElement(By.xpath("//*[@class='tooltip ng-scope ng-isolate-scope right fade in']")).getText();
		    if(one.contains("Favourite"))
		    {
				testLogPass("Toolip for is presnet");
		    }
		
	}
	
	@Test
	public void Tc_Shop_006() {
		testStarts("Tc_Shop_006", "Verify that item gets added to cart on clicking Add button.");
		//Loginpage.OpenBrowserAndLoginnew();
		clickOn(_OR.Dashboard_Submenu_shop);
		waitUntilPageReady();
		clickOn(OR.Shop_SHopfor_MyInventoryradiobutton);
		String one = getText(OR.MyCart_count);
		int S = Integer.parseInt(one);
		
		testLogPass("Before adding the item to card the count is "+S);
		waitUntilPageReady();
		String itemDsc=getProperty("ItemDesc");
		typeIn(OR.Shop_SHopfor_SearchBox,itemDsc);
		waitUntilPageReady();
		if(driver.findElements(By.xpath("(//*[@id='btnAdd' and contains(text(),'Add')])[1]")).size()!=0)
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//button[contains(text(),'Update Price')]")).size()!=0)
			{
				clickOn(OR.MyCart_warningPopup);
			}
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
			{
				verifyElementText(OR.Shop_Alertvalidation, "This item is not under contract or a preference item from your distributor, It may cost more.");
				clickOn(OR.MyCart_warningPopup);
			}
		}
		waitForElement(OR.MyCart_count);
		String one1 = getText(OR.MyCart_count);
		int S1 = Integer.parseInt(one1);	
		testLogPass("After adding the item to card the count is "+S1);
		typeIn(OR.Shop_SHopfor_SearchBox,itemDsc);
	waitUntilPageReady();
		if(isElementDisplayed(OR.Shop_SHopfor_Search_Addtocart_First))
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//button[contains(text(),'Update Price')]")).size()!=0)
			{
				clickOn(OR.MyCart_warningPopup);
			}
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
			{
				verifyElementText(OR.Shop_Alertvalidation, "This item is not under contract or a preference item from your distributor, It may cost more.");
				clickOn(OR.MyCart_warningPopup);
			}

			
		}
		
			String BeforeIncrease = getText(OR.Shop_Item_Qty_First);
			clickOn(OR.Shop_Item_Qty_Increase_First);
			String AfterIncrease = getText(OR.Shop_Item_Qty_First);
			if (AfterIncrease != BeforeIncrease) {
				testLogPass(BeforeIncrease + " valuse  Before Increase" + AfterIncrease + " After Increase...");
			} else {
				testLogPass(BeforeIncrease + " valuse  Before Increase" + AfterIncrease + " After Increase...");
			}
			clickOn(OR.Shop_Item_Qty_Derease_First);
			String BeforeIncrease1 = getText(OR.Shop_Item_Qty_First);
			if (AfterIncrease != BeforeIncrease1) {
				testLogPass(BeforeIncrease + " valuse  Before Decrease' " + AfterIncrease + " After Increase...");
			} else {
				testLogPass(BeforeIncrease + " valuse  Before Decrease' " + AfterIncrease + " After Increase...");
			}	
			clickOn(OR.Shop_SHopfor_Search_Input_First1);
			clickOn(OR.Shop_Qty_Close);
			clickOn(OR.Shop_SHopfor_Search_Input_First1);
			typeIn(OR.Shop_SHopfor_Search_Addtocart_InputQty, "-1");
			clickOn(OR.Invoice_SearchButton);
			verifyElementText(OR.Shop_Qty_Validation, "Cart quantity can not be less then zero.");
			clickOn(OR.Shop_Qty_ValidationOK);
			clickOn(OR.Shop_SHopfor_Search_Input_First1);
			typeIn(OR.Shop_SHopfor_Search_Addtocart_InputQty, "0");
			clickOn(OR.Invoice_SearchButton);
	}
	
	@Test
	public void Tc_Shop_007() {
		testStarts("Tc_Shop_007", "Verify that the popup does not appear again, if user selects the checkbox and tries to add item in cart.");
		//Loginpage.OpenBrowserAndLoginnew();
		clickOn(_OR.Dashboard_Submenu_shop);
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitUntilPageReady();
		if(driver.findElements(By.xpath("(//button[@id='btnAdd'])[1]")).size()!=0)
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
			{
				verifyElementText(OR.Shop_Alertvalidation, "This item is not under contract or a preference item from your distributor, It may cost more.");
				if(isElementDisplayed(OR.Shop_Alert_Dontnotadd_checkbox))
				{
					clickOn(OR.Shop_Alert_Dontnotadd_checkbox);
				}
				clickOn(OR.MyCart_warningPopup);
			}
			
			if(driver.findElements(By.xpath("//button[text()='Update Price']")).size()!=0)
			{
				String one1 = driver.findElement(By.xpath("//*[@class='modal-body']//h4")).getText();
				testLogPass("validation message is displays for vendor price is greater than contract price "+one1);
				clickOn(OR.Shop_UpdatePrice);
				waitUntilPageReady();
				clickOn(OR.Shop_UpdatePrice1);
			}
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//button[text()='Continue']")).size()!=0)
			{
				clickOn(OR.MyCart_warningPopup);
			}
		}		
		waitUntilPageReady();
		if(driver.findElements(By.xpath("(//*[@editable-text='item.qty']/nobr/span)[1]")).size()!=0)
		{	String BeforeIncrease = getText(OR.Shop_Item_Qty_First);
			int qtyysize = Integer.parseInt(BeforeIncrease);
			for(int i = 1;i<=qtyysize;i++)
			{
				clickOn(OR.Shop_Item_Qty_Derease_First);
			}
		}
		waitUntilPageReady();
		if(driver.findElements(By.xpath("(//*[@ng-click='$ctrl.addItemToCart(item)'])[1]")).size()!=0)
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
				if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
				{
					verifyElementText(OR.Shop_Alertvalidation, "This item is not under contract or a preference item from your distributor, It may cost more.");
					if(isElementDisplayed(OR.Shop_Alert_Dontnotadd_checkbox))
					{
						clickOn(OR.Shop_Alert_Dontnotadd_checkbox);
					}
					clickOn(OR.MyCart_warningPopup);
					clickOn(OR.User);
					clickOn(OR.User_alert);
					if(driver.findElement(By.xpath("//*[text()='Show Preferred Item Warning:']/following-sibling::div/div")).getAttribute("class").contains("bootstrap-switch-off"))
					{
						testLogPass("toggle button is changed to “OFF”");
					}
				}
				else
				{
					waitForElement(OR.User);
					clickOn(OR.User);
					clickOn(OR.User_alert);
					if(driver.findElement(By.xpath("//*[text()='Show Preferred Item Warning:']/following-sibling::div/div")).getAttribute("class").contains("bootstrap-switch-off"))
					{
						testLogPass("toggle button is changed to “OFF”");
						driver.findElement(By.xpath("//*[text()='Show Preferred Item Warning:']/following-sibling::div/div")).click();
						clickOn(OR.Profile_Btn_Update);
						ToastmesssageSucess();
					}
					clickOn(_OR.Dashboard_Submenu_shop);
					typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
					if(driver.findElements(By.xpath("(//*[@editable-text='item.qty']/nobr/span)[1]")).size()!=0)
					{	String BeforeIncrease = getText(OR.Shop_Item_Qty_First);
						int qtyysize = Integer.parseInt(BeforeIncrease);
						for(int i = 1;i<=qtyysize;i++)
						{
							clickOn(OR.Shop_Item_Qty_Derease_First);
						}
					}
if(driver.findElements(By.xpath("(//*[@ng-click='$ctrl.addItemToCart(item)'])[1]")).size()!=0)
					{
						clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
							if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
							{
								verifyElementText(OR.Shop_Alertvalidation, "This item is not under contract or a preference item from your distributor, It may cost more.");
								if(isElementDisplayed(OR.Shop_Alert_Dontnotadd_checkbox))
								{
									clickOn(OR.Shop_Alert_Dontnotadd_checkbox);
									clickOn(OR.MyCart_warningPopup);
								}
							}
						
					}
				}
			}
		
	}
	
	@Test
	public void Tc_Shop_008() {
		testStarts("Tc_Shop_008", "Verify that “Item reorder warning” popup appears when user tries to add corresponding item to cart which is not received completely within another PO created by current user.");
		clickOn(OR.MyCart);
		waitForElement(OR.Shop_SHopfor_SearchBox);
		waitUntilPageReady();
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitUntilPageReady();
		if(isElementDisplayed(OR.Shop_SHopfor_Search_Addtocart_First))
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
		}
		String one = driver.findElement(By.xpath("//*[@ng-show=\"$ctrl.editQty\"]//big")).getText();
		waitUntilPageReady();
		if(one!= null && !one.isEmpty())
		{	String BeforeIncrease = getText(OR.MyCart_InputQty);
			int qtyysize = Integer.parseInt(BeforeIncrease);
			for(int i = 1;i<=qtyysize;i++)
			{
				clickOn(OR.MyCart_InputQty_InputQtyDecrease);
				waitUntilPageReady();
			}
		}
		if(driver.findElements(By.xpath("(//button[@id='btnAdd'])[1]")).size()!=0)
		{
			clickOn(OR.MyCart_addItemInCart);
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//button[contains(text(),'Update Price')]")).size()!=0)
			{
				clickOn(OR.MyCart_warningPopup);
			}
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//button[text()='Continue']")).size()!=0)
			{
				clickOn(OR.MyCart_warningPopup);
			}
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
				{
					verifyElementText(OR.Shop_Alertvalidation, "This item is not under contract or a preference item from your distributor, It may cost more.");
					if(isElementDisplayed(OR.Shop_Alert_Dontnotadd_checkbox))
					{
						clickOn(OR.Shop_Alert_Dontnotadd_checkbox);
					}
					clickOn(OR.MyCart_warningPopup);
					clickOn(OR.User);
					clickOn(OR.User_alert);
					if(driver.findElement(By.xpath("//*[text()='Show Preferred Item Warning:']/following-sibling::div/div")).getAttribute("class").contains("bootstrap-switch-off"))
					{
						testLogPass("toggle button is changed to “OFF”");
					}
				}
				else
				{
					waitForElement(OR.User);
					clickOn(OR.User);
					clickOn(OR.User_alert);
					if(driver.findElement(By.xpath("//*[text()='Show Preferred Item Warning:']/following-sibling::div/div")).getAttribute("class").contains("bootstrap-switch-off"))
					{
						testLogPass("toggle button is changed to “OFF”");
						driver.findElement(By.xpath("//*[text()='Show Item Already in PO Warning:']/following-sibling::div/div")).click();
						clickOn(OR.Profile_Btn_Update);
						ToastmesssageSucess();
					}
					clickOn(OR.MyCart);
					typeIn(OR.MyCart_searchInCart,getProperty("ItemDesc"));
					String one4g = driver.findElement(By.xpath("//*[@ng-show=\"$ctrl.editQty\"]//big")).getText();
					if(one4g!= null && !one4g.isEmpty())
					{	String BeforeIncrease = getText(OR.MyCart_InputQty);
						int qtyysize = Integer.parseInt(BeforeIncrease);
						for(int i = 1;i<=qtyysize;i++)
						{
							clickOn(OR.MyCart_InputQty_InputQtyDecrease);
							waitUntilPageReady();
						}
					}
					if(driver.findElements(By.xpath("(//button[@id='btnAdd'])[1]")).size()!=0)
					{
						clickOn(OR.MyCart_addItemInCart);
						waitUntilPageReady();
						if(driver.findElements(By.xpath("//button[contains(text(),'Update Price')]")).size()!=0)
						{
							clickOn(OR.MyCart_warningPopup);
						}
						waitUntilPageReady();
						if(driver.findElements(By.xpath("//button[text()='Continue']")).size()!=0)
						{
							clickOn(OR.MyCart_warningPopup);
						}
						waitUntilPageReady();
						if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
							{
								verifyElementText(OR.Shop_Alertvalidation, "This item is not under contract or a preference item from your distributor, It may cost more.");
								if(isElementDisplayed(OR.Shop_Alert_Dontnotadd_checkbox))
								{
									clickOn(OR.Shop_Alert_Dontnotadd_checkbox);
								}
								clickOn(OR.MyCart_warningPopup);
							}
					}
					
			
			}
			
		}
		
	}
	
	@Test
	public void Tc_Shop_009() {
		testStarts("Tc_Shop_009", "Verify that popup message appears if vendor price is greater than contract price, and Contract Price Check toggle button is Active in Admin settings module.");
		clickOn(_OR.Dashboard_Submenu_shop);
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitUntilPageReady();
		if(isElementDisplayed(OR.Shop_SHopfor_Search_Addtocart_First))
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
		}
		Shop.pricevalidation();
	}
	
	@Test
	public void Tc_Shop_010() {
		testStarts("Tc_Shop_010", "Verify that confirmation popup appears with text “Item availability status is discontinued. Are you sure you want to order this item ?”, when user clicks “Add” button for item with Status as “Discontinued”.");
		clickOn(_OR.Dashboard_Submenu_shop);
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitUntilPageReady();
		if(isElementDisplayed(OR.Shop_SHopfor_Search_Addtocart_First))
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
		}
		Shop.Discontinued();
	}
	
	@Test
	public void Tc_Shop_011() {
		testStarts("Tc_Shop_011", "Verify that “Show Item Backorder Warning” toggle button is changed to “OFF”/”Inactive” on Alerts page and in User's module at Admin end.");
		clickOn(_OR.Dashboard_Submenu_shop);
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitUntilPageReady();
		if(isElementDisplayed(OR.Shop_SHopfor_Search_Addtocart_First))
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
		}
		Shop.backorder();
	}
	
	@Test
	public void Tc_Shop_012() {
		testStarts("Tc_Shop_012", "Down arrow icon>>Verify that following columns appear under the down arrow icon");
		clickOn(_OR.Dashboard_Submenu_shop);
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitUntilPageReady();
		if(driver.findElements(By.xpath("(//button[@id='btnAdd'])[1]")).size()!=0)
		{
			clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
			waitUntilPageReady();
			if(driver.findElements(By.xpath("//button[contains(text(),'Update Price')]")).size()!=0)
			{
				clickOn(OR.MyCart_warningPopup);
			}
			waitUntilPageReady();
		}
		verifyElement(OR.Shop_Checkoutbtn);
		clickOn(OR.Descending);
		waitUntilPageReady();
		int s = driver.findElements(By.xpath("(//*[@ng-if='showAppTourBtn && !isResellerCustomer()']//li/a)")).size();
		for(int i=1; i<=s;i++)
		{
			String on = driver.findElement(By.xpath("(//*[@ng-if='showAppTourBtn && !isResellerCustomer()']//li/a)["+i+"]")).getText();
			if(on.isEmpty())
			{
				
			}
			if(on.contains("Show Tour"))
			{
				testLogPass(on+" is in dropdown");
			}
			else if(on.contains("View Type"))
			{
				testLogPass(on+" is in dropdown");
			}
			else if(on.contains("Change Current Layout"))
			{
				testLogPass(on+" is in dropdown");
			}
			else if(on.contains("Create New Layout"))
			{
				testLogPass(on+" is in dropdown");
			}
			else if(on.contains("Layouts"))
			{
				testLogPass(on+" is in dropdown");
			}
			else if(on.contains("Set As Default Layout"))
			{
				testLogPass(on+" is in dropdown");
			}
		}
		clickOn(OR.Shop_showTour1);
		testLogPass("Tour message is "+ getText(OR.Shop_showTour_Message));
		verifyElement(OR.Shop_showTour_Next);
		verifyElement(OR.Shop_showTour_EndTour);
		clickOn(OR.Shop_showTour_Next);
		testLogPass("Tour message is "+ getText(OR.Shop_showTour_Message));
		clickOn(OR.Shop_showTour_EndTour);
	}
	
	@Test
	public void Tc_Shop_013() 
	{
		testStarts("Tc_Shop_013", "Verify that \"Similar Item\" hyperlink appear under item name, if same item with different vendors are not appearing on same page.");
		//		Shop.SimilarItems();
		clickOn(_OR.Dashboard_Submenu_shop);
		typeIn(OR.Shop_SHopfor_SearchBox,getProperty("ItemDesc"));
		waitUntilPageReady();
		if(driver.findElements(By.xpath("(//*[text()='similar item'])[1]")).size()!=0)
		{
			verifyElement(OR.ItemCatalog_Similaritem);
		}
//		  WebElement element = driver.findElement(By.xpath("(//*[text()='TestItemDesNYAG'])[1]"));
//		    Actions toolAct = new Actions(driver);
//		    toolAct.moveToElement(element).build().perform();
//		  int size1 = driver.findElements(By.xpath("(//*[@class='popover ng-scope ng-isolate-scope top fade in']//tbody/tr/td/strong)")).size();  
//		 for(int j = 1; j<=size1; j++)
//		 {
//			 String one = driver.findElement(By.xpath("(//*[@class='popover ng-scope ng-isolate-scope top fade in']//tbody/tr/td/strong)["+j+"]")).getText();
//			 testLogPass("Mouse over elements are "+one);
//				
//		 }
		waitUntilPageReady();
		 String attributevalue = driver.findElement(By.xpath("(//*[@class='item-info-icon-padd']/i)[1]")).getAttribute("class").replaceAll("ng-scope ", "");
		 testLogPass("color of the item is "+attributevalue);
		 clickOn(OR.Shop_ItemNameDropDown_First);
			waitUntilPageReady();
			clickOn(OR.Shop_ItemNameDropDownEdit_First);
				testLogPass("header is "+getTextchild("//*[@class='headtext']"));
				clickOn(OR.ItemCatalog_Close);
				waitForElement(OR.Shop_ItemNameDropDown_First);
				clickOn(OR.Shop_ItemNameDropDown_First);
				waitUntilPageReady();
				clickOn(OR.DD_PriceChangeHistory);
				waitUntilPageReady();
				clickOn(OR.ItemCatalog_Close);	
				waitForElement(OR.Shop_ItemNameDropDown_First);
				clickOn(OR.Shop_ItemNameDropDown_First);
				waitUntilPageReady();
				clickOn(OR.DD_PurchaseHistory);
				clickOn(OR.ItemCatalog_Close);	
			if(driver.findElements(By.xpath("(//*[text()='similar item'])[1]")).size()!=0)
			{
		 clickOn(OR.ItemCatalog_Similaritem);
		 
		getTextchild("//*[@class='modal-header']");
		String one = driver.findElement(By.xpath("(//*[@ng-repeat='item in items']/td)[4]")).getText();
		testLogPass("Sku id is "+one);
		int size = driver.findElements(By.xpath("//*[@class='table-responsive ng-scope']//th/a")).size();
		for(int i =1;i<=size;i++)
		{
			String u = driver.findElement(By.xpath("(//*[@class='table-responsive ng-scope']//th/a)["+i+"]")).getText();
			testLogPass("colom is '"+u);
		}
		if(driver.findElements(By.xpath("//*[@class='fa fa-shopping-cart']/following-sibling::text()[contains(., 'Replace')]")).size()!=0)
		{
			verifyElement(OR.ItemCatalog_Replace_Btn);
		}

		clickOn(OR.csManufacture_Close);
			}	
		
	}
	
	@Test
	public void Tc_Shop_014() 
	{
		testStarts("Tc_Shop_014", "Shop Grid>>Layout Name>>Verify that it should accept alphanummeric values.");
	//	Loginpage.OpenBrowserAndLoginnew();
		clickOn(_OR.Dashboard_Submenu_shop);
		
		clickOn(OR.Shop_SHopfor_drilldownicon);
		clickOn(OR.Shop_drilldownCreateLayout);
		String layout="AAA00"+ApplicationKeyword.randomAlphaNumeric(2);
		typeIn(OR.Shop_drilldownLayoutNameText, layout);
		String NameofLayout="Configure Shop Layout "+layout;
		waitForElementToDisplayWithoutFail(OR.Shop_drilldownCreateLayoutSave, 10);
		clickOn(OR.Shop_drilldownCreateLayoutSave);
		//Configure layout save button
		waitForElementToDisplayWithoutFail(OR.Shop_drilldownCreateLayoutSave, 10);
		clickOn(OR.Shop_drilldownCreateLayoutSave);
		clickOn(OR.Shop_SHopfor_drilldownicon);
		waitForElementToDisplayWithoutFail(OR.Shop_drilldownChangeLayout, 10);
		clickOn(OR.Shop_drilldownChangeLayout);
		//waitForElementToDisplay(OR.Shop_drilldownChangeLayoutPopup, 30);
		String actualName=getText(OR.Shop_changeLayoutTextPopUP);
		if(NameofLayout.equalsIgnoreCase(actualName))
		{
			testLogPass("User has opened Change layout Pop up with name "+ actualName);

		}
		clickOn(OR.Shop_drilldownChangeLayoutClose);
		clickOn(OR.Shop_SHopfor_drilldownicon);
		waitForElementToDisplayWithoutFail(OR.Shop_drilldownLayouts, 10);
		mouseOver(OR.Shop_drilldownLayouts);
		//clickOn(OR.Shop_drilldownRemoveLayout);
		WebElement elem=driver.findElement(By.xpath("//*[contains(text(),'"+layout+"')]/following-sibling::i"));
		elem.click();
		waitForElementToDisplayWithoutFail(OR.Shop_drilldownRemoveLayoutYes, 10);
		clickOn(OR.Shop_drilldownRemoveLayoutYes);
		ToastmesssageSucess();
		clickOn(OR.Shop_Menu);
		waitForElementToDisplayWithoutFail(OR.Shop_SHopfor_drilldownicon, 10);
		clickOn(OR.Shop_SHopfor_drilldownicon);	
		waitForElementToDisplayWithoutFail(OR.Shop_drilldownCreateLayout, 10);
		clickOn(OR.Shop_drilldownCreateLayout);	
		waitForElementToDisplayWithoutFail(OR.Shop_SHopfor_Layoutpopup, 10);
		verifyElementText(OR.Shop_SHopfor_Layoutpopup, "Layout");
		waitForElementToDisplayWithoutFail(OR.Shop_SHopfor_Layoutpoupclose, 10);
		clickOn(OR.Shop_SHopfor_Layoutpoupclose);	
		waitForElementToDisplayWithoutFail(OR.Shop_SHopfor_drilldownicon, 10);
		clickOn(OR.Shop_SHopfor_drilldownicon);
		waitForElementToDisplayWithoutFail(OR.Shop_SHopfor_copyLayoutpoup, 10);
		clickOn(OR.Shop_SHopfor_copyLayoutpoup);
		waitForElementToDisplayWithoutFail(OR.Shop_SHopfor_Layoutpopup, 10);
		verifyElementText(OR.Shop_SHopfor_Layoutpopup, "Layout");
		clickOn(OR.Shop_SHopfor_Layoutpoupclose);		
	}
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


