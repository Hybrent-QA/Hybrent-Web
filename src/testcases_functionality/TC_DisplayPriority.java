package testcases_functionality;

import java.io.File;

import org.openqa.selenium.By;
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

public class TC_DisplayPriority extends ApplicationKeyword{

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

			extent = new ExtentReports(folderPath+"/DisplayPriority.html", true);
			extent.addSystemInfo("User Name", "QA");
			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

		} catch (Exception e) 
		{
			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
		}
	}

	
	@Test
	public void Tc_DisplayPriority_001() {
		testStarts("Tc_DisplayPriority_001", "Verify that \"DISPLAY PRIORITY\" module appears in menu bar only when \"Is Vendor display Order\" option is set as Active in Admin part.");
		Loginpage.OpenBrowserAndLoginnew();
		Displaypriority.NavigateDisplayPriority();
		Displaypriority.VerifyPage();
		
	}
	
	
	@Test
	public void Tc_DisplayPriority_002() {
		testStarts("Tc_DisplayPriority_002", "Verify that user is able to drag & drop vendors.");
		Loginpage.OpenBrowserAndLoginnew();
		Displaypriority.NavigateDisplayPriority();
		Displaypriority.VerifyPage();
		
	}
	
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


