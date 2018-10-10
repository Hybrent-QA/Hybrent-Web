package testcases_functionality;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import AutomationFramework.ApplicationKeyword;
import AutomationFramework.Generickeywords;
import AutomationFramework.OR;
import AutomationFramework._OR;
import funcation_PageObject.*;
import pageObject.Alertpage;
import pageObject.ApprovePO_PageObject;
import pageObject.Changepasswordpage;
import pageObject.Dashboardpage;
import pageObject.MycartPage;
import pageObject.OrderPage;
import pageObject.ProfilePage;
import pageObject.Reportspage;
import pageObject.SurveyPage;

public class TC_News extends ApplicationKeyword{

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
//			extent = new ExtentReports(folderPath+"/news.html", true);
//			extent.addSystemInfo("User Name", "QA");
//			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));
//
//		} catch (Exception e) 
//		{
//			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
//		}
//	}


	@Test
	public void TC_News_001() {
		testStarts("TC_News_001", "Verify that heading \"Manage News\" appear on the page.");
		Loginpage.OpenBrowserAndLoginnew();
		News.NavigateToNews();
		News.Pageverify();
		News.AddActiveNews();
		
		}
	
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


