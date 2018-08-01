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

public class TC_ApprovedPo extends ApplicationKeyword{

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

			extent = new ExtentReports(folderPath+"/ApprovedPo.html", true);
			extent.addSystemInfo("User Name", "QA");
			extent.loadConfig(new File(System.getProperty("user.dir") + "/extent-config.xml"));

		} catch (Exception e) 
		{
			System.out.println("--Start REPORT-Cases-Error---" + e.toString());
		}
	}

	
	@Test
	public void Tc_ApprovedPo_001() {
		testStarts("Tc_ApprovedPo_001", "Verify that 'ORDERS TO APPROVE' label text appear left of the page.");
		Loginpage.OpenBrowserAndLoginnew();
		ApprovePO.NavigateApprovedPO();
		ApprovePO.Menu();
		ApprovePO.Verifydetails();
		clickOn(OR.ApprovePO_Menu_ShopApp);
		Shopcart.Tour();
	}
	
	@Test
	public void Tc_ApprovedPo_002()
	{
		testStarts("Tc_ApprovedPo_002", "Verify that results appears when user performs search on the basis of filters.");
		//Loginpage.OpenBrowserAndLoginnew();
		ApprovePO.NavigateApprovedPO();
		ApprovePO.VendorUnappove();
		ApprovePO.AddItem();
		ManageInventory.NavigateManageInvetory();
		ManageInventory.addItemInInventory1(getProperty("ApprovePONumber"));
	}
	
	@Test
	public void Tc_ApprovedPo_003()
	{
		testStarts("Tc_ApprovedPo_003", "Verify that results appears when user performs search on the basis of filters.");
		//Loginpage.OpenBrowserAndLoginnew();
		ApprovePO.NavigateApprovedPO();
		ApprovePO.SearchNavigation();
		ApprovePO.DrillDowntext();
		ApprovePO.Apprvalicon();
	}
	
	@Test
	public void Tc_ApprovedPo_004()
	{
		testStarts("Tc_ApprovedPo_004", "Verify that User can approve/reject po as per  Level and Rules added in Approval flow module.");
		//Loginpage.OpenBrowserAndLoginnew();
		ApprovePO.NavigateApprovedPO();
		ApprovePO.SearchNavigation();
		ApprovePO.ApproveFlow();
	}
	
	@Test
	public void Tc_ApprovedPo_005()
	{
		testStarts("Tc_ApprovedPo_005", "Verify that on click Reject button Warning popup appear \"Are you sure?Do you want to reject this order?\" ");
		//Loginpage.OpenBrowserAndLoginnew();
		ApprovePO.NavigateApprovedPO();
		ApprovePO.RejectFlow();
	}
	
	
	@Test
	public void Tc_ApprovedPo_006()
	{
		testStarts("Tc_ApprovedPo_006", "Verify that when user haver on Print Items then following o");
		//Loginpage.OpenBrowserAndLoginnew();
		ApprovePO.NavigateApprovedPO();
		ApprovePO.AddItemForVerify();
		ApprovePO.PrintItem();
		ApprovePO.Barcode();
		ApprovePO.QRcode();
	}
	
	@Test
	public void Tc_ApprovedPo_007()
	{
		testStarts("Tc_ApprovedPo_007", "Verify that on clicking on PO Log link, PO logs popup opens for that PO");
	//	Loginpage.OpenBrowserAndLoginnew();
		ApprovePO.SearchNavigation();
		if(driver.findElements(By.xpath("//*[text()='"+getProperty("ApprovePONumber")+"']")).size()!=0)
		{
			driver.findElement(By.xpath("//*[text()='"+getProperty("ApprovePONumber")+"']")).click();
		}
//		OrderDetailsPage.PrintLog();
//		OrderDetailsPage.AddNotes();
		OrderDetailsPage.viewDocumentForPO();
	}
	
	@AfterTest
	public void endReport()
	{
		closeBrowser();
		extent.flush();
	}
}


