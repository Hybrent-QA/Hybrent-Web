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
import pageObject.Changepasswordpage;

public class TC_Patterns extends ApplicationKeyword{

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

			extent = new ExtentReports(folderPath+"/Pattern.html", true);
			extent.addSystemInfo("User Name", "QA");
			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

		} catch (Exception e) 
		{
			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
		}
	}

	
	@Test
	public void Tc_Pattern_001() {
		testStarts("Tc_Pattern_001", "Verify that heading “Manage Patterns” appear on the page.");
		Loginpage.OpenBrowserAndLoginnew();
		Pattern.NavigateToPattern();
		Pattern.VerifyPage();
		Pattern.CreatePatten();
	}
	
	
	
	@Test
	public void Tc_Pattern_002() {
		testStarts("Tc_Pattern_002", "Verify that heading “Manage Patterns” appear on the page.");
		Pattern.NavigateToPONUmber();
		verifyElement(OR.PoNumber_Default);
		verifyElement(OR.PoNumber_Prefix);
		verifyElement(OR.PoNumber_Pattern);
		verifyElement(OR.Pattern_Add_Close);
		clickOn(OR.PoNumber_Prefix);
		verifyElement(OR.PoNumber_PrefixNumber);
		typeIn(OR.PoNumber_PrefixNumber, "1234567890123456789012345678901234567890");
		String value = getAttributeValue(OR.PoNumber_PrefixNumber, "value");
		getAttributeValue(OR.PoNumber_PrefixNumber, "maxlength").contains("20");
		clickOn(OR.PoNumber_Pattern);
		verifyElement(OR.PoNumber_pattern_id);
		clickOn(OR.Profile_Btn_Update);
		ToastmesssageSucess();
	
	}
	
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


