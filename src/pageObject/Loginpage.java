package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import AutomationFramework.ApplicationKeyword;
import AutomationFramework.OR;

public class Loginpage  extends ApplicationKeyword{
	public static String UserName = "admin";
	public static String Password = "goouser";
	public static String URL = "https://qa4.test.hybrent.com/b/";

	public static void login(String Username, String Password)
	{	

		if(isElementDisplayed(OR.Login_link, 10))
		{
			//waitForElementToDisplay(OR.Login_link, 10);
			clickOn(OR.Login_link);
		}
		else if(isElementDisplayed(OR.Adfs_link, 10))
		{
			clickOn(OR.Adfs_link);
		}
		else
		{
			waitForElementToDisplay(OR.Label_username,20);
			//verifyElementText(OR.Label_password, "Password:");

		}
		//         waitForElementToDisplay(OR.Label_username, 20);
		//         verifyPageTitle("Login");
		//         verifyElement(OR.HeaderLogo);
		//         verifyElementText(OR.Label_username, "User Name:");
		//         verifyElementText(OR.Label_password, "Password:");
		typeIn(OR.Login_Username, Username);
		typeIn(OR.Login_Password, Password);
		clickOn(OR.Login_Submit);
	}

	public static void OpenBrowserAndLogin()
	{
		openBrowser(URL);		 
		Loginpage.login(UserName, Password);
	//	waitForJQueryLoad();
		waitForAngularLoad();
		if(driver.findElements(By.xpath("//button[text()='Ok']")).size()!=0)
		{
			WebElement elem = driver.findElement(By.xpath("//button[text()='Ok']"));
			executor.executeScript("arguments[0].scrollIntoView(true);",elem);
			executor.executeScript("arguments[0].click();", elem);
		}
	

	}
	public static void allPermissions()
	{
		clickOn(OR.User);
		waitForElementToDisplay(OR.User_Profile, 60);
		clickOn(OR.User_Profile);
		String facNAme=getText(OR.Profile_defaultFacility); 
		String userEmail=getAttributeValue(OR.Profile_userEmail, "value");
		clickOn(OR.DashBoard_AdminDropdown);
		clickOn(OR.Users_page);
		typeIn(OR.Users_SearchByText, userEmail);
		clickOn(OR.Users_SearchButton);
		waitForElementToDisplay(OR.Users_waitforResult, 10);
		clickOn(OR.Users_EditUserButton);
		waitForElementToDisplay(OR.Users_EditUserPopupText, 10);
		clickOn(OR.Users_facilityTab);
		typeIn(OR.Users_searchTextBoxFacTab, facNAme);
		clickOn(OR.Users_AllPermissionsCheckBox);
		clickOn(OR.Users_AllPermissionsCheckBox);
		clickOn(OR.Users_saveButton);
		//handling Logout Popup



	}



}
