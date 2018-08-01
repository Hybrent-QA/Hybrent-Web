package funcation_PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.reporters.jq.TestPanel;

import AutomationFramework.ApplicationKeyword;
import AutomationFramework.OR;

public class Shopcart extends ApplicationKeyword{
	
    public static void VerifyPage()
    {
    	waitUntilPageReady();
    	verifyElement(OR.MyCart);
    	clickOn(OR.MyCart);
    	verifyElement(OR.MyCart_Sku);
    	verifyElement(OR.MyCart_Qty);
    	verifyElement(OR.MyCart_UOM);
    	verifyElement(OR.MyCart_status);
    	verifyElement(OR.MyCart_Price);
    	verifyElement(OR.MyCart_TotalPrice);
		String beforeadd = getText(OR.MyCart_count);
		int countitem =Integer.parseInt(beforeadd);
    	if(countitem==0)
    	{
    		Shopcart.Additem();
    	}
    	if(getText(OR.MyCart_cartFor_Details).contains(getProperty("ItemDesc")))
    	{
    		testLogPass("Facility are matching");
    	}
    }
    
    public static void matchFac()
	{	
	String facName =getProperty("UserAddfailityName");
	clickOn(OR.Shop_SHopfor_ShopfaclitySelect);
	waitForElementToDisplayWithoutFail(OR.Shop_SHopfor_Shopfaclity, 10);
	verifyElementText(OR.Shop_SHopfor_Shopfaclity, "Select Facility");
	waitForElementToDisplayWithoutFail(OR.Shop_countoffacilities, 10);
	int one = driver.findElements(By.xpath("(//*[@class='table table-responsive table-striped table-bordered']//tbody//td[1])")).size();
	boolean facFound=false;
	String xpath;
	String selectedFacility;
	WebElement btn;
	for(int i=1; i<=one; i++)
	{
		
		selectedFacility=driver.findElement(By.xpath("(//*[@class='table table-responsive table-striped table-bordered']//tbody//td[1])["+i+"]")).getText();
		//System.out.println(selectedFacility);
		if(selectedFacility.equals(facName))
		{  
			facFound=true;
			btn= driver.findElement(By.xpath("(//*[starts-with(@class, 'table table-responsive')]//tbody//td[1])/following-sibling::td//button"));
			if(btn.getAttribute("disabled")!=null)
			{
				testLogPass("Go the text '"+selectedFacility+ "' Matches with selected Facility" );
			}
			else
			{
				testLogFail("'" + facName + "' is not selected in popup." );
			}
			break;
		}
	}
	if(!facFound)
	{
		testLogFail("Could not Got the text '"+facName+ "' Matches with selected Facility" );
	}
	clickOn(OR.Shop_SHopfor_cancelButtonPopup);
	}

    public static void Additem() 
    {
    	waitUntilPageReady();
    	String beforeadd, AfterAdd;
    	beforeadd = getText(OR.MyCart_count);
    	int countitem =Integer.parseInt(beforeadd);
		testLogPass("before adding the item to cart is "+countitem);
		SearchItem(getProperty("ItemDesc"));
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
		
		
		if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
		{
		String one3 = getTextchild("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']") ;
		if(one3.contains("This item is not under contract or a preference item from your distributor, It may cost more."))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		waitUntilPageReady();
		}
		
		if(driver.findElements(By.xpath("//*[@class='modal-title']")).size()!=0)
		{
		String one1 = getTextchild("//*[@class='modal-title']") ;
		if(one1.contains("Item reorder warning"))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		}
		AfterAdd = getText(OR.MyCart_count);
		int AfterAdd1 =Integer.parseInt(AfterAdd);
		testLogPass("After adding the item to cart is "+AfterAdd1);
    }
    
    public static void AdditemDecrease() 
    {
    	waitUntilPageReady();
    	typeIn(OR.MyCart_searchInCart, getProperty("ItemDesc"));
    	waitUntilPageReady();
    	if(driver.findElements(By.xpath("//*[@id='btnAdd' and contains(text(),'Add')]")).size()!=0)
    	{
    		clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
    	}
    	else
    	if(driver.findElements(By.xpath("//*[@id='dcr']/following-sibling::big")).size()!=0)
    	{
    		String si = driver.findElement(By.xpath("//*[@id='dcr']/following-sibling::big")).getText();
    		int s = Integer.parseInt(si);
    		for(int i=1;i<=s;i++)
    		{
    			clickOn(OR.MyCart_InputQty_InputQtyDecrease);
    		}
    		waitUntilPageReady();
        	if(driver.findElements(By.xpath("//*[@id='btnAdd' and contains(text(),'Add')]")).size()!=0)
        	{
        		clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
        	}
    	}
    	
		if(driver.findElements(By.xpath("//*[@class='modal-title']")).size()!=0)
		{
		String one = getTextchild("//*[@class='modal-title']") ;
		if(one.contains("Price check"))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		
		waitUntilPageReady();
		}
		
		
		if(driver.findElements(By.xpath("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']")).size()!=0)
		{
		String one3 = getTextchild("//*[text()='This item is not under contract or a preference item from your distributor, It may cost more.']") ;
		if(one3.contains("This item is not under contract or a preference item from your distributor, It may cost more."))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		waitUntilPageReady();
		}
		
		if(driver.findElements(By.xpath("//*[@class='modal-title']")).size()!=0)
		{
		String one1 = getTextchild("//*[@class='modal-title']") ;
		if(one1.contains("Item reorder warning"))
		{
			clickOn(OR.MyCart_warningPopup);
		}
		}
		
    }
    public static void CartMore()
    {
    	Select select = new Select(driver.findElement(By.xpath("//*[@id='shippingLocationDepartment']")));
    	WebElement option = select.getFirstSelectedOption();
    	String defaultItem = option.getText();
    	testLogPass("Default selected dropdown value is "+defaultItem);
    	waitForElement(OR.MyCart_cartFor_CartMore);
    	clickOn(OR.MyCart_cartFor_CartMore);
    	Tour();
    	waitForElement(OR.MyCart_cartFor_CartMore);
    	clickOn(OR.MyCart_cartFor_CartMore);
    	waitUntilPageReady();
    	int Size = driver.findElements(By.xpath("//*[@tour-step='cart-more']//li/a")).size();
    	for(int i=1;i<=Size;i++)
    	{
    		String one = driver.findElement(By.xpath("(//*[@tour-step='cart-more']//li/a)["+i+"]")).getText();
    		testLogPass("Dropd Down value are "+one);
    	}
    	waitUntilPageReady();
    	for(int j=1;j<=Size;j++)
    	{
    		WebElement one = driver.findElement(By.xpath("(//*[@tour-step='cart-more']//li/a)["+j+"]"));
    		if(one.getText().contains("Clear Current Cart"))
    		{
    			one.click();
    			verifyElementText(OR.OrderDetails_DeleteConfirmion, "Are you sure you want to clear cart?");
    			clickOn(OR.Dep_Delete_Yes);
    			verifyElementText(OR.OrderDetails_DeleteSucessConfirmion, "Cart cleared successfully.");
    			clickOn(OR.Template_Warningok);
    			String s = driver.findElement(By.xpath("//*[text()='Your cart is Empty']")).getText();
    			
    			if(s.contains("Your cart is Empty"))
    			{
    				testLogPass("Card is empty");
    			}
    			break;
    		}
    		
    	}
    }
    
    public static void Tour()
    {
    	waitForElement(OR.Shop_showTour1);
    	clickOn(OR.Shop_showTour1);
    	waitForElement(OR.Shop_showTour_Message);
		testLogPass("Tour message is "+ getText(OR.Shop_showTour_Message));
		waitForElement(OR.Shop_showTour_Next);
		verifyElement(OR.Shop_showTour_Next);
		verifyElement(OR.Shop_showTour_EndTour);
		clickOn(OR.Shop_showTour_Next);
		waitForElement(OR.Shop_showTour_Message);
		testLogPass("Tour message is "+ getText(OR.Shop_showTour_Message));
		waitUntilPageReady();
		clickOn(OR.Shop_showTour_EndTour);
    }
    
    public static void SearchItem(String Search)
    {
    	waitUntilPageReady();
    	typeIn(OR.MyCart_searchInCart, Search);
    	if(driver.findElements(By.xpath("//*[@id='btnAdd' and contains(text(),'Add')]")).size()!=0)
    	{
    		clickOn(OR.Shop_SHopfor_Search_Addtocart_First);
    	}
    	//clickOn(OR.Shop_GeneratePo);
    }
    
    public static void vendor()
    {
    	waitUntilPageReady();
    	verifyElementText(OR.Mycard_GenderFor, "Generate for");
    	verifyElement(OR.Mycard_GenderFor_vendor);
    	waitUntilPageReady();
    	clickOn(OR.MyCart_drillDownVendor);
    	verifyElement(OR.MyCart_accountSetUp);
    	verifyElement(OR.MyCart_removeVendor);
    	verifyElement(OR.MyCart_Checkaccount);
    	clickOn(OR.MyCart_removeVendor);
    	verifyElementText(OR.MyCart_removeVendor_validation, "Are you sure you want to remove this vendor's items from cart?");
    	clickOn(OR.Dep_Delete_Cancel);
    	clickOn(OR.MyCart_drillDownVendor);
    	waitForElement(OR.MyCart_removeVendor);
    	clickOn(OR.MyCart_removeVendor);
    	verifyElementText(OR.MyCart_removeVendor_validation, "Are you sure you want to remove this vendor's items from cart?");
    	clickOn(OR.Template_Yes);
    	if(isElementDisplayed(OR.Template_Warningok))
    	{
    	waitForElement(OR.Template_Warningok);
    	clickOn(OR.Template_Warningok);
    	}
    }
    
    public static void AccountSetup()
    {
    	    	clickOn(OR.MyCart_drillDownVendor);
    	    	verifyElement(OR.MyCart_accountSetUp);
    	    	clickOn(OR.MyCart_accountSetUp);
    	    	getTextchild("//*[@class='pagehead']");
    	    	for(int i=1; i<=2; i++ )
    	    	{
    	    		driver.findElement(By.xpath("(//input[starts-with(@id,'facility_account_text')])["+i+"]")).sendKeys("123");
    	    		
    	    	}
    	    	WebElement element = driver.findElement(By.xpath("//button[text()='Close']"));
    	    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    	    	executor.executeScript("arguments[0].click();", element);
    	    	waitUntilPageReady();
    	    	waitForElement(OR.MyCart_drillDownVendor);
    	    	WebElement element1 = driver.findElement(By.xpath("//span[@id='optionMenu']"));
    	    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
    	    	executor.executeScript("arguments[0].click();", element1);
    	    	//clickOn(OR.MyCart_drillDownVendor);
    	    	verifyElement(OR.MyCart_accountSetUp);
    	    	waitUntilPageReady();
    	    	clickOn(OR.MyCart_accountSetUp);
    	    	waitUntilPageReady();
    	    	int size1 = driver.findElements(By.xpath("//input[starts-with(@id,'facility_account_text')]")).size();
    	    	for(int j=1; j<=size1; j++ )
    	    	{
    	    		driver.findElement(By.xpath("(//input[starts-with(@id,'facility_account_text')])["+j+"]")).clear();
    	    		driver.findElement(By.xpath("(//input[starts-with(@id,'facility_account_text')])["+j+"]")).sendKeys("123");
    	    	}
    	    	
    	    	WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
    	    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", save);
    	    	executor.executeScript("arguments[0].click();", save);
    	    	//ToastmesssageSucess();
    }
    
    public static void Price()
    {
    	waitUntilPageReady();
    	if(driver.findElements(By.xpath("//a[@ng-click='setItemPrice()']")).size()!=0)
    	{
			testLogPass("Price contains the link");
			waitForElement(OR.MyCart_Price1);
			clickOn(OR.MyCart_Price1);
			verifyElementText(OR.MyCart_Price_updateprice, "Update Item Price");
			verifyElement(OR.Shop_UpdatePrice1);
			verifyElement(OR.ItemCatalog_FileUpload_CloseBtn);
			waitForElement(OR.HeaderClose1);
			clickOn(OR.HeaderClose1);
		}
    	String beforeprice = driver.findElement(By.xpath("//nobr/a")).getText().replaceAll("\\.?0*$", "").replace("$", "");
		int pri = Integer.parseInt(beforeprice);
		int priceupdate;
		if(pri==0)
		{
			priceupdate = generateRandomInt1(2);
		}
		else
		{
			priceupdate = generateRandomInt1(2);
		}
		clickOn(OR.MyCart_Price1);
		waitForAngularLoad();
		waitTime(2);
		String jprice = getAttributeValue(OR.MyCart_UpdatePrice,"value").replaceAll(".00", "");
		int piruceget = Integer.parseInt(jprice);
		if(piruceget==priceupdate)
		{
			priceupdate =generateRandomInt1(3);
		}
		else
		{
		String sprice ="10";
			typeIn(OR.MyCart_UpdatePrice, sprice);
			clickOn(OR.Shop_UpdatePrice1);
		}
		ToastmesssageSucess();
		String afterupdate = driver.findElement(By.xpath("//nobr")).getText();
		testLogPass("After updating the price is "+afterupdate);
    }
    
    public static void Dropdown()
    {
    	waitUntilPageReady();
		 clickOn(OR.Shop_ItemNameDropDown_two);
	
			WebElement Edit = driver.findElement(By.xpath("(//a[@ng-show='canBeEdit'])[1]"));
			Edit.click();
			testLogPass("header is "+getTextchild("//*[@class='headtext']"));
			clickOn(OR.ItemCatalog_Close);
			
			waitForElement(OR.Shop_ItemNameDropDown_two);
			clickOn(OR.Shop_ItemNameDropDown_two);
			WebElement dropdownValue1 = driver.findElement(By.xpath("(//a[text()='Price Change History'])[1]"));
			dropdownValue1.click();
			testLogPass("Dropdown value is '"+dropdownValue1.getText());
			testLogPass(getText(OR.MyCart_CurrentPrice));
			int si = driver.findElements(By.xpath("(//*[@ng-if='itemPriceDetail.priceChangeHistory.length']//th)")).size();
					for(int i=1 ; i<=si;i++)
					{
						testLogPass(driver.findElement(By.xpath("(//*[@ng-if='itemPriceDetail.priceChangeHistory.length']//th)["+i+"]")).getText());
					}
					clickOn(OR.Template_CloseBtn);
					
					
				waitForElement(OR.Shop_ItemNameDropDown_two);
					
			clickOn(OR.Shop_ItemNameDropDown_two);
			WebElement dropdownValue2 = driver.findElement(By.xpath("(//a[text()='Purchase History'])[1]"));
			dropdownValue2.click();
			testLogPass("header is "+getTextchild("//*[@class='modal-title']"));
		    String one = driver.getPageSource();
		    String one1 = getProperty("UserAddDeptName");		
		    if(one.contains(one1)) 
		    {
		    	testLogPass("Department assigned to item is displays in Purchase History");
		    }
			int si1 = driver.findElements(By.xpath("(//*[@class='table table-hover customtable']//th)")).size();
			for(int j=1 ; j<=si1;j++)
			{
				testLogPass(driver.findElement(By.xpath("(//*[@class='table table-hover customtable']//th)["+j+"]")).getText());
			}
			//verifyPagination();
			clickOn(OR.Template_CloseBtn);
    }
    
    public static void MouseOverDetails()
    {
    	String one = getProperty("ItemDesc");
		
		if(driver.findElements(By.xpath("//*[text()='"+one+"']")).size()!=0)
		{
    	 WebElement Fav11 = driver.findElement(By.xpath("//*[text()='"+one+"']"));
		    Actions toolAct = new Actions(driver);
		    toolAct.moveToElement(Fav11).build().perform();
		    String oned = driver.findElement(By.xpath("//*[@class='tooltip ng-scope ng-isolate-scope right fade in']")).getText();
		    if(oned.contains("Favourite"))
		    {
				testLogPass("Toolip for is presnet");
		    }
    
		}   
		    
		    
    }
}

