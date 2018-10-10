package testcases_functionality;

import java.io.File;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import AutomationFramework.ApplicationKeyword;
import AutomationFramework.Generickeywords;
import AutomationFramework._OR;
import funcation_PageObject.*;
import pageObject.Changepasswordpage;

public class TC_Changepassword extends ApplicationKeyword{

//	@Parameters({"siteName", "siteUrl"})
//	@BeforeTest
//	public void startReport(String siteName, String siteUrl) {
//		try {
//			Loginpage.URL=siteUrl + "#/login/";
//			Generickeywords.SITENAME=siteName;
//			Generickeywords.DashBoardURL=siteUrl + "#/dashboard";
//			String folderPath=OutputDirectory + "/" + siteName;
//
//			File directory = new File(folderPath);
//			if (! directory.exists()){
//				directory.mkdir();
//			}
//
//			extent = new ExtentReports(folderPath+"/ChangePassword.html", true);
//			extent.addSystemInfo("User Name", "QA");
//			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
//
//		} catch (Exception e) 
//		{
//			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
//		}
//	}

	
	@Test
	public void Tc_changepassword_001() {
		testStarts("Tc_changepassword_001", "Verify that \"Change Password\" pop-up appear when user clicks on \"Change Password\".");
		Loginpage.OpenBrowserAndLoginnew();
		clickOn(_OR.Dashboard_User);
		clickOn(_OR.Dashboard_User_ChangePassword);
		Changepasswordpage.VerifyChangePassword();
		waitForElement(_OR.Dashboard_User);
		clickOn(_OR.Dashboard_User);
		clickOn(_OR.Dashboard_User_ChangePassword);
		Changepasswordpage.changePassword();
	}
	
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


