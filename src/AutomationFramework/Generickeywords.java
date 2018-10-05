package AutomationFramework;
import Utilities.Common;
import funcation_PageObject.Loginpage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.google.common.base.Function;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Generickeywords extends Common
{
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static WebDriver driver = null;
	public static NgWebDriver ngdriver;
	public static String identifier;
	public static String locator;
	 public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<RemoteWebDriver>();
	public static String locatorDescription;
	public static String outputDirectory;
	public static String currentExcelBook;
	public static String mainWindow;
	public static String currentBrowser = "";
	public static Common.identifierType idType;
	public static WebElement webElement;
	public static boolean testFailure = false;
	public static boolean loadFailure = false;
	public static int temp = 1;
	public static String testStatus = "";
	public static int testCaseDataRow;
	public static int testLoadWaitTime = 40;
	public static int elementLoadWaitTime = 10;
	public static int implicitlyWaitTime = 15;
	public static int pageLoadWaitTime = 40;
	public static boolean windowreadyStateStatus = true;
	public static Properties prop;
	public static InputStream input = null;
	public static boolean pageLinkExists = true;
	public static String testCaseDesc="";

	//	public static String propsFileName =System.getProperty("user.dir")+"/projectconfiguration.properties";
	//	public static String OutputDirectory =System.getProperty("user.dir")+"/test-output";
	public static String propsFileName =System.getProperty("user.dir")+"/projectconfiguration.properties";
	public static String OutputDirectory =System.getProperty("user.dir")+"/test-output";
	//	public static String firefoxPath=System.getProperty("user.dir")+"/geckodriver.exe";
	//	public static String chromePath=System.getProperty("user.dir")+"/chromedriver.exe";
	public static String firefoxPath=System.getProperty("user.dir")+"/geckodriver.exe";
	public static String chromePath=System.getProperty("user.dir")+"/chromedriver.exe";
	public static  JavascriptExecutor executor;
	public static WebDriverWait wait ;
	public static String DashBoardURL = "https://qa4.test.hybrent.com/b/#/dashboard";
	public static String timeStamp = "";
	public static boolean refresh=true;
	public String URL, Node;
	public static String SITENAME = "";

	protected ThreadLocal<RemoteWebDriver> threadDriver = null;

	//@BeforeSuite	
	//	public void startReport() {
	//
	//		try {
	//
	//			extent = new ExtentReports(OutputDirectory+"/StartBrowser.html", true);
	//			// extent.addSystemInfo("Environment","Environment Name")
	//			extent.addSystemInfo("User Name", "Ravneet");
	//			extent.loadConfig(new File(System.getProperty("user.dir") + "//extent-config.xml"));
	//		
	//			logger = extent.startTest("Setting Browser");
	//		} catch (Exception e) {
	//			testLogFail("Unable to generate the pass report " + e.toString());
	//		}
	//	}

	private String setPropFile(String siteName) throws IOException
	{
		String filePath=System.getProperty("user.dir")+"/assets/";
		String fileName=siteName + "_projectconfiguration.properties";
		String destFullPath=filePath + fileName;
		String origFilePath=filePath + "projectconfiguration.properties";

		try
		{
			File sourceFile=new File(origFilePath);
			File destFile=new File(destFullPath);
			if(!destFile.exists())
			{
				Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			
			return destFullPath;
		}
		catch (Exception ex)
		{
			System.out.println("Error occurred in 'SetProFile() '" + ex.toString());
		}

		return origFilePath;
	}

	@Parameters({"browser", "nodeUrl", "siteName"})
	@BeforeClass
	public void setBrowser(String browser, String nodeUrl, String siteName)
	{	
		try
		{
			propsFileName = setPropFile(siteName);

			if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", firefoxPath);
				System.out.println(" Executing on FireFox");
				DesiredCapabilities cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setCapability("marionette", true);
				cap.setPlatform(Platform.ANY);
				System.out.println(nodeUrl);
				//driver = new RemoteWebDriver(new URL(nodeUrl), cap);
				driver = new FirefoxDriver(cap);
			}
			else 
				if (browser.equalsIgnoreCase("Chrome")) {
					System.setProperty("webdriver.chrome.driver", chromePath);

					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("profile.default_content_setting_values.notifications", 2);
					ChromeOptions options = new ChromeOptions();
					options.setExperimentalOption("prefs", prefs);
					//options.addArguments("--headless");
					DesiredCapabilities cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.ANY);
					cap.setCapability(ChromeOptions.CAPABILITY, options);
					System.out.println(nodeUrl);
					driver = new RemoteWebDriver(new URL(nodeUrl), cap);
						
				}
				else {	
					throw new IllegalArgumentException("Invalid browser value!!");
				}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	
	 

	public static void openBrowser(String URL)
	{
		refresh=false;
		try
		{
			//			
			//			//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
			//			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
			//			Map<String, Object> prefs = new HashMap<String, Object>();
			//			prefs.put("profile.default_content_setting_values.notifications", 2);
			//			ChromeOptions options = new ChromeOptions();
			//			options.setExperimentalOption("prefs", prefs);
			//			//options.addArguments("--headless");
			//			driver = new ChromeDriver(options);
			NavigateUrl(URL);
			System.out.println("Opened Browser");
			testLogPass("Open the url "+URL);
			executor = (JavascriptExecutor)driver;
			((WebDriver) driver).manage().timeouts().implicitlyWait(implicitlyWaitTime,TimeUnit.SECONDS) ;
			((WebDriver) driver).manage().timeouts().pageLoadTimeout(elementLoadWaitTime, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, testLoadWaitTime);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			ngdriver = new NgWebDriver((JavascriptExecutor) driver);
			System.out.println("WIndow Maximized");
		}
		catch(Exception e)
		{ 
			System.out.println("---ERROR In Opening Browser---" + e.toString());
			testLogFail("Exception " + e.toString());
		}
		finally
		{
			refresh=true;
		}
	}

	public static void NavigateUrl(String URL)
	{
		try
		{
			driver.navigate().to(URL);
			waitUntilPageReady();
			if(refresh)
			{
				driver.navigate().refresh();
				waitForElementToDisplayWithoutFail(OR.Dashboard_Order_Table, 20);
			}
		}		
		catch(Exception e)
		{ 
			System.out.println("---ERROR ON NavigateUrl--- " + e.toString());
			//testLogFail("navigate to URL Exception " + e.toString());
		}
	}

	public static void NavigateToUrl(String URL)
	{
		try
		{
			driver.navigate().to(URL);
			testLogPass("Open the url "+URL);
			driver.navigate().refresh();
			waitForElementToDisplay(OR.Shop_Menu, 60);
		}		
		catch(Exception e)
		{ 
			testLogFail("navigate to URL Exception " + e.toString());
		}
	}


	public static void identifyBy(String identifier) {
		Common.identifierType i = Common.identifierType.valueOf(identifier);
		switch (i) {
		case classname:
			webElement = driver.findElement(By.className(locator));
			break;
		case id:
			webElement = driver.findElement(By.id(locator));
			break;
		case cssselector:
			webElement = driver.findElement(By.cssSelector(locator));
			break;
		case lnktext:
			webElement = driver.findElement(By.linkText(locator));
			break;
		case name:
			webElement = driver.findElement(By.name(locator));
			break;
		case partiallinktext:
			webElement = driver.findElement(By.partialLinkText(locator));
			break;
		case tagname:
			webElement = driver.findElement(By.tagName(locator));
			break;
		case xpath:
			webElement = driver.findElement(By.xpath(locator));
			break;
		case angular_model:
			webElement = driver.findElement(ByAngular.model(locator));
			break;
		case Angular_ButtonText:
			webElement = driver.findElement(ByAngular.buttonText(locator));
			break;
		default:

		}
	}

	public static void waitForElement(String objName)
	{
		waitUntilPageReady();
		waitForElement(objName, elementLoadWaitTime);
	}

	public static void waitForElement(String objectName, int timeout) {
		try {
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
			waitJQueryAngular();
			
			for (int i = 1; i <= timeout; i++)
			{
				try
				{
					findWebElement(objectName);
				}
				catch (InvalidSelectorException e)
				{
					waitTime(1L);
				}
				catch (StaleElementReferenceException e)
				{
					waitTime(1L);
				}
				catch (NoSuchElementException e)
				{
					waitTime(1L);
				}
				catch (ElementNotVisibleException e) {
					waitTime(1L);
				}
				catch (UnreachableBrowserException e)
				{
					waitTime(1L);
				}
				catch (WebDriverException e) {
					waitTime(1L);
				}

			}
		}
		catch (Exception e)
		{
			testLogFail("Exception " + e.toString());
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
		}
	}

	public static void verifyElementText(String objectLocator, String expectedText)
	{

		waitForElementToDisplay(objectLocator, elementLoadWaitTime);
		waitUntilPageReady();
		try 
		{
			if (webElement.getText().trim().contains((expectedText.trim())))
			{
				ApplicationKeyword.testLogPass("verify if the " + locatorDescription + " element contains text '" + expectedText);
			}
			else
			{
				ApplicationKeyword.testLogFail("verify if the " + webElement.getText() + " element contains text '" + expectedText);
			}
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
		}
	}

	public static void findWebElement(String objectLocator)
	{
		parseidentifyByAndlocator(objectLocator);
		identifyBy(identifier);
	}


	public static void typeIn(String objectLocator, String inputValue)
	{
		waitForElementToDisplay(objectLocator, elementLoadWaitTime);

		{
			try
			{
				webElement.clear();
				webElement.sendKeys(inputValue);
				ApplicationKeyword.testLogPass("Type '" + inputValue + "' in : " + locatorDescription);
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
		}
		}

	public static void sendkeys(String Locator, String inputValue)
	{
		try
		{
			driver.findElement(By.xpath(Locator)).clear();
			driver.findElement(By.xpath(Locator)).sendKeys(new CharSequence[] { inputValue });

			ApplicationKeyword.testLogPass(  "Type '" + inputValue + "' in : " + locatorDescription);
		}
		catch (Exception e)
		{

			ApplicationKeyword.testLogFail("Element is not in editable state '" + locatorDescription);
		}
	}

	public static void refreshPage()
	{
		try
		{
			driver.navigate().refresh();
			waitJQueryAngular();
			ApplicationKeyword.testLogPass("Sucessfully Refreshed browser");
		}
		catch (InvalidSelectorException e)
		{
			waitTime(1L);
		}
		catch (StaleElementReferenceException e)
		{
			waitTime(1L);
		}
		catch (ElementNotVisibleException e)
		{
			waitTime(1L);
		}
		catch (UnreachableBrowserException e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		catch (UnhandledAlertException e)
		{
			waitTime(1L);
		}
		catch (WebDriverException e)
		{
			waitTime(1L);
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
		}

	}

	public static void clickOnBackButton()
	{
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				driver.navigate().back();
				waitJQueryAngular();
				ApplicationKeyword.testLogPass("Sucessfully moved to 'Back' page");
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail("Error moving to 'Back' page");
			}
		}
	}

	public static void clickOnForwardButton()
	{
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				driver.navigate().forward();
				waitJQueryAngular();
				ApplicationKeyword.testLogPass("Sucessfully moved to 'Forward' page");
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail("Error moving to 'Forward' page");
			}
		}
	}

	public static void alertOk()
	{
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				Alert alert = driver.switchTo().alert();
				alert.accept();
				ApplicationKeyword.testLogPass(  "Click on Alert OK button");
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail("Click on Alert OK button");
			}
		}
	}

	public static void alertCancel()
	{
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				Alert alert = driver.switchTo().alert();
				alert.dismiss();

				ApplicationKeyword.testLogPass(  "Click on Alert Cancel button");
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail("Click on Alert Cancel button");
			}
		}
	}

	public static boolean isAlertWindowPresent()
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
		catch (Exception E)
		{
		}
		return false;
	}

	public static void verifyElement(String objLocator)
	{waitUntilPageReady();
		waitForElementToDisplay(objLocator, elementLoadWaitTime);

		try
		{

			ApplicationKeyword.testLogPass(  "Verify Element : '" + locatorDescription+"' is present as expected");
		}

		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Verify Element : " + locatorDescription+ "' is not present as expected");
			ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
		}


	}

	public static void mouseOver(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				waitUntilPageReady();
				Actions builder = new Actions(driver);
				builder.moveToElement(webElement).build().perform();

				ApplicationKeyword.testLogPass(  "Move the mouse over '" + locatorDescription + "'");
				break;
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail("Move the mouse over '" + locatorDescription + "'");
			}
		}
	}

	public static void waitTime(long waittime)
	{
		try {
			Thread.sleep(waittime * 1000L);
		}
		catch (InterruptedException e)
		{
			ApplicationKeyword.testLogFail( "Thread.sleep operation failed, during waitTime function call");
		}
	}

	public static  void getallFacilities() throws Exception 
	{
		List<String> list = new ArrayList<String>();
		list.add(getText(OR.Shop_SHopfor_allfacilityArray));
	}

	public static void selectFromDropdown(String objLocator, String valueToSelect)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		try {
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);

			try
			{
				waitJQueryAngular();
				//webElement.click();
				Select select = new Select(webElement);
				select.selectByVisibleText(valueToSelect);

				ApplicationKeyword.testLogPass(  "Select '" + valueToSelect + "' from : " + locatorDescription);
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
		}
	}

	public static void selectFromDropdown(String objLocator, int indexNumber)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);

		if(webElement==null)
		{
			//Fail Test
			testLogFail("Element Not Found");
		}

		try {
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);

			try
			{
				webElement.click();
				Select select = new Select(webElement);
				select.selectByIndex(indexNumber);					
				ApplicationKeyword.testLogPass(  "Select '" + indexNumber + "' option from : " + locatorDescription);
			}

			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
		}

		catch (Exception e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
		}
	}

	public static String getDropDownText(String objLocator, int indexNumber)
	{
		String elemText = null;
		waitForElementToDisplay(objLocator, elementLoadWaitTime);

		if(webElement==null)
		{
			//Fail Text
			return elemText;
		}

		try {
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);

			try
			{
				webElement.click();
				Select select = new Select(webElement);
				select.selectByIndex(indexNumber);
				elemText=select.getFirstSelectedOption().getText();
				ApplicationKeyword.testLogPass(  "Select '" + indexNumber + "' option from : " + locatorDescription);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
		}

		return elemText;
	}

	public static void verifyPageTitle(String partialTitle)
	{

		try {
			waitJQueryAngular();
			if (driver.getTitle().contains(partialTitle))
			{

				ApplicationKeyword.testLogPass(  "Verify if the page title contains text '" + partialTitle + "'");
			}
			else
			{

				ApplicationKeyword.testLogFail("Verify if the page title contains text '" + partialTitle + "'");
			}

		}
		catch (InvalidSelectorException e)
		{
			waitTime(1L);
		}
		catch (StaleElementReferenceException e)
		{
			waitTime(1L);
		}
		catch (ElementNotVisibleException e)
		{
			waitTime(1L);
		}
		catch (UnreachableBrowserException e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		catch (UnhandledAlertException e)
		{
			waitTime(1L);
		}
		catch (WebDriverException e)
		{
			waitTime(1L);
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
		}

	}

	public static void verifyLinkText(String txt)
	{
		try
		{
			waitJQueryAngular();
			driver.findElement(By.linkText(txt));

			ApplicationKeyword.testLogPass(  "Verify if link '" + txt + "' is present");
		}
		catch (Exception e)
		{

			ApplicationKeyword.testLogFail("Verify if link '" + txt + "' is present");
		}
	}

	public static void verifyAttribute(String objLocator, String attributeType, String expectedAttributeValue)
	{
		waitForElement(objLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				String attribute = "";
				attribute = webElement.getAttribute(attributeType);

				if (!attribute.trim().equalsIgnoreCase(expectedAttributeValue.trim()))
				{

					ApplicationKeyword.testLogFail("Verify attribute of '" + attribute + "' for value '" + expectedAttributeValue + "'");
				}
				else
				{		
					ApplicationKeyword.testLogPass(  "Verify attribute of '" + attribute + "' for value '" + expectedAttributeValue + "'");
					break;
				}
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	public static void waitForText(String txt)
	{waitJQueryAngular();
		waitForText(txt, testLoadWaitTime);
	}

	public static void waitForText(String txt, int timeout)
	{
		for (int second = 0; second < timeout; second++)
		{
			try {
				waitJQueryAngular();
				driver.getCurrentUrl();
			} catch (Exception e) {
				ApplicationKeyword.testLogFail("WebDriver is not found");
			}if (second == timeout - 1) {
				ApplicationKeyword.testLogFail("Text is not found ' " + txt + "'");
				break;
			}
			try
			{
				if (driver.getPageSource().contains(txt))
					ApplicationKeyword.testLogPass("Text: '" + txt + "' is present");
			}
			catch (Exception localException1)
			{
				try
				{
					Thread.sleep(1000L);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void clickOn(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);

		try
		{	waitJQueryAngular();
			webElement.click();
			ApplicationKeyword.testLogPass(  "Click on :" + locatorDescription);
		}
		catch (InvalidSelectorException e)
		{
			waitTime(1L);
		}
		catch (StaleElementReferenceException e)
		{
			waitTime(1L);
		}
		catch (ElementNotVisibleException e)
		{
			waitTime(1L);
		}
		catch (UnreachableBrowserException e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		catch (UnhandledAlertException e)
		{
			waitTime(1L);
		}
		catch (WebDriverException e)
		{
			waitTime(1L);
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
		}
	}

	//	@SuppressWarnings("unused")
	//	public static WebElement fluentWait(final WebDriver driver, By locator, final int timeoutSeconds) {
	//	    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	//	            .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
	//	            .pollingEvery(500, TimeUnit.MILLISECONDS)
	//	            .ignoring(NoSuchElementException.class);
	//
	//	    return wait.until(new Function<WebDriver, WebElement>() {
	//	        public WebElement apply(WebDriver webDriver) {
	//	            return driver.findElement(locator);
	//	        }
	//	    });
	//	}

	public WebElement getWhenVisible(By objLocator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		return element;
	}

	public void clickWhenReady(By objLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(objLocator));
		element.click();
	}

	public static void dragAndDrop(String sourceObjLocator, String destinationObjLocator)
	{
		String sourceDesc = ""; String destinationDesc = "";
		try
		{
			findWebElement(sourceObjLocator);
			WebElement source = webElement;
			sourceDesc = locatorDescription;
			findWebElement(destinationObjLocator);
			WebElement target = webElement;
			destinationDesc = locatorDescription;
			new Actions(driver).dragAndDrop(source, target).perform();
			ApplicationKeyword.testLogPass(  "Drag '" + sourceDesc + "' and drop on '" + destinationDesc + "'");
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Drag '" + sourceDesc + "' and drop on '" + destinationDesc + "'");
		}
	}

	public void dragAndDrop2(WebElement sourceElement, WebElement destinationElement) {
		try {
			if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) {
				Actions action = new Actions(driver);
				action.dragAndDrop(sourceElement, destinationElement).build().perform();
			} else {
				System.out.println("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element with " + sourceElement + "or" + destinationElement + "is not attached to the page document "
					+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element " + sourceElement + "or" + destinationElement + " was not found in DOM "+ e.getStackTrace());
		} catch (Exception e) {
			System.out.println("Error occurred while performing drag and drop operation "+ e.getStackTrace());
		}
	}


	public static void switchFrame(String fr)
	{
		waitTime(10);
		try
		{
			driver.switchTo().frame(fr);
			ApplicationKeyword.testLogPass(  "Switch to frame :" + fr);
		}
		catch (InvalidSelectorException e)
		{
			waitTime(1L);
		}
		catch (StaleElementReferenceException e)
		{
			waitTime(1L);
		}
		catch (ElementNotVisibleException e)
		{
			waitTime(1L);
		}
		catch (UnreachableBrowserException e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		catch (UnhandledAlertException e)
		{
			waitTime(1L);
		}
		catch (WebDriverException e)
		{
			waitTime(1L);
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
		}
	}

	public static void clearEditBox(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		try
		{
			webElement.click();
			webElement.clear();
			ApplicationKeyword.testLogPass("Clear Text Box '' in : " + locatorDescription);
		}
		catch (InvalidSelectorException e)
		{
			waitTime(1L);
		}
		catch (StaleElementReferenceException e)
		{
			waitTime(1L);
		}
		catch (ElementNotVisibleException e)
		{
			waitTime(1L);
		}
		catch (UnreachableBrowserException e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}
		catch (UnhandledAlertException e)
		{
			waitTime(1L);
		}
		catch (WebDriverException e)
		{
			waitTime(1L);
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
		}
	}

	public static void rightClick(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				Actions builder = new Actions(driver);
				builder.contextClick(webElement).build().perform();
				ApplicationKeyword.testLogPass(  "Right Clicked '" + locatorDescription + "'");
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	public static void doubleClick(String objLocator) {
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				Actions builder = new Actions(driver);
				builder.doubleClick(webElement).build().perform();
				ApplicationKeyword.testLogPass(  "Double Clicked '" + locatorDescription + "'");
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	protected boolean isElementPresent(By By){
		try{
			driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			driver.findElement(By);
			return true;
		}
		catch(NoSuchElementException e){

		}
		finally
		{
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime,TimeUnit.SECONDS);
		}

		return false;
	}

	public static boolean elementPresent(String objectLocator)
	{
		try
		{
			findWebElement(objectLocator);
			return true;
		}
		catch (NoSuchElementException e)
		{
			return false;
		}
		catch (Exception e)
		{
			ApplicationKeyword.testLogFail(e.toString());
		}return false;
	}

	public static void verifyPageShouldContainText(String text)
	{
		if (driver.getPageSource().contains(text))
		{
			ApplicationKeyword.testLogPass(  "Verify if page '" + text + "' text is present");
		}
		else
		{
			ApplicationKeyword.testLogFail("Verify if page '" + text + "' text is present");
		}
	}

	public static void verifyPageShouldNotContainText(String text)
	{
		if (driver.getPageSource().contains(text))
		{
			ApplicationKeyword.testLogFail("Verify if page '" + text + "' text is not present");
		}
		else
		{
			ApplicationKeyword.testLogPass(  "Verify if page '" + text + "' text is not present");
		}
	}
	public static String getTextSelectedOption(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		String SelectText = "";
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				Select select = new Select(webElement);
				return select.getFirstSelectedOption().getText().toString();
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}

		return SelectText;
	}

	public static void verifyTextFieldCount(String objLocator, int CountNumber)
	{
		waitForElement(objLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				String text = getAttributeValue(objLocator, "value");
				if (text.length() > CountNumber)
				{
					ApplicationKeyword.testLogFail(locatorDescription + "textfield is getting more than '" + CountNumber + "' value ");
				}
				else
				{
					ApplicationKeyword.testLogPass(  "Verify if " + locatorDescription + " textfield is not getting more than '" + CountNumber + "' value");
				}

			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	public static void verifyTextValueNotCharacter(String objLocator)
	{
		waitForElement(objLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				String text = getAttributeValue(objLocator, "value");
				if (text.matches("[a-zA-z]+"))
				{
					ApplicationKeyword.testLogFail(locatorDescription + "textfield is getting character value");
				}
				else
				{
					ApplicationKeyword.testLogPass(  "Verify:" + locatorDescription + " textfield is  getting only number value");
				}

			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	public static void verifyTextValueNotNumber(String objLocator)
	{
		waitForElement(objLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				String text = getAttributeValue(objLocator, "value");
				if (text.matches("[0-9]+"))
				{
					ApplicationKeyword.testLogFail(locatorDescription + "textfield is getting Number value ");
				}
				else
				{
					ApplicationKeyword.testLogPass(  "Verify if " + locatorDescription + " textfield is  getting only character value");
				}

			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	public static void verifyAlertTextShouldContain(String expectedAlertText)
	{
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		if (alertText.contains(expectedAlertText))
		{
			ApplicationKeyword.testLogPass(  "Verify if '" + expectedAlertText + "' alert text is present ");
		}
		else
		{
			ApplicationKeyword.testLogFail("'" + expectedAlertText + "' alert text is not present");
		}
	}

	public static void verifyTextFieldShouldContain(String objectLocator, String expectedValue)
	{
		waitForElement(objectLocator, elementLoadWaitTime);

		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				String actualValue = getAttributeValue(objectLocator, "value");
				if (actualValue.contains(expectedValue))
				{
					ApplicationKeyword.testLogPass("Verify if '" + expectedValue + "' is present in " + locatorDescription + " textfield");
				}
				else
				{
					ApplicationKeyword.testLogFail("'" + expectedValue + "' is not present in " + locatorDescription + " textfield");
				}

			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				ApplicationKeyword.testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				ApplicationKeyword.testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	public static void verifyTextFieldShouldNotContain(String objectLocator, String expectedValue)
	{
		waitForElement(objectLocator, elementLoadWaitTime);
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				String textValue = getAttributeValue(objectLocator, "value");
				if (!textValue.contains(expectedValue))
				{
					ApplicationKeyword.testLogPass(  "Verify if '" + expectedValue + "' is not present in " + locatorDescription + " textfield");
				}
				else
				{
					ApplicationKeyword.testLogFail("'" + expectedValue + "' is present in " + locatorDescription + " textfield");
				}

			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}
	}

	/*public static void closeAllBrowser()
  {
    deleteAllCookies();
    Set windowhandles = driver.getWindowHandles();
    for (String handle : windowhandles)
    {
      driver.switchTo().window(handle);
      driver.close();
    }
  }*/

	public static void closeChildBrowser(String windowTitle)
	{
		try {
			for (String winHandle : driver.getWindowHandles())
			{
				driver.switchTo().window(winHandle);
				if (driver.getTitle().equalsIgnoreCase(windowTitle))
				{
					driver.close();
					testLogPass(  "Close Browser");
					break;
				}
			}
		}
		catch (Exception e)
		{
			testLogFail("Close Browser");
		}
	}

	public static String getText(String objLocator)
	{
		String getText = null;
		waitUntilPageReady();
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				//Since for loop is already present for required waitTime,
				//waitForElement does not have to wait for another full wait cycle
				waitForElement(objLocator, 1);
				getText = webElement.getText();
				//Following condition is added to make sure that it waits for element
				//to load completely.
				if(getText==null || getText.trim().equals(""))
				{	
					waitTime(1L);
					continue;
				}

				testLogPass("Sucessfully got the text '" + getText + "'");
				waitUntilPageReady();
				break;
			}
			catch (InvalidSelectorException e)
			{	
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{	
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{	
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{	
				waitTime(1L);
			}
			catch (UnhandledAlertException e)
			{	
				waitTime(1L);
			}
			catch (WebDriverException e)
			{	
				waitTime(1L);
			}
			catch (Exception e)
			{	
				testLogFail("Exception Error '" + e.toString() + "'");
			}
		}

		return getText;
	}

	public static String getAttributeValue(String objLocator, String attributeName)
	{
		String getAttributeValue = null;
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try {
				waitForElement(objLocator, elementLoadWaitTime);
				getAttributeValue = webElement.getAttribute(attributeName);
				testLogPass("Sucessfully got the attribute value '" + getAttributeValue + "'");
				break;
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				ApplicationKeyword.testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				testLogFail("Exception Error '" + e.toString() + "'");
			}


		}

		return getAttributeValue;
	}

	public static int getMatchingXpathCount(String objLocator)
	{
		List xpathCount = null;
		for (int i = 1; i <= elementLoadWaitTime; i++)
		{
			try
			{
				xpathCount = driver.findElements(By.xpath(objLocator));
				testLogPass("Sucessfully got the matchingxPath Count'" + xpathCount + "'");
			}
			catch (InvalidSelectorException e)
			{
				waitTime(1L);
			}
			catch (StaleElementReferenceException e)
			{
				waitTime(1L);
			}
			catch (ElementNotVisibleException e)
			{
				waitTime(1L);
			}
			catch (UnreachableBrowserException e)
			{
				testLogFail(e.toString());
			}
			catch (UnhandledAlertException e)
			{
				waitTime(1L);
			}
			catch (WebDriverException e)
			{
				waitTime(1L);
			}
			catch (Exception e)
			{
				testLogFail("Exception Error '" + e.toString() + "'");
			}

			if (i == elementLoadWaitTime)
			{
				testLogFail(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
			}
		}

		return xpathCount.size();
	}


	public static void UnSelectFrame()
	{
		try {
			driver.switchTo().defaultContent();
		}
		catch (Exception e)
		{
			testLogFail("Error in swiching to default content frame");
		}
	}

	public static void waitForAlertWindow(int timeout)
	{
		for (int i = 0; i <= timeout; i++)
		{
			if (isAlertWindowPresent())
			{
				break;
			}

			waitTime(1L);

			if (i == timeout)
			{
				testLogFail("Alert Window is not present within '" + timeout + "' timeout");
			}
		}
	}

	public static void waitForAlertWindow(String alertTitle, int timeout)
	{
		for (int i = 0; i <= timeout; i++)
		{
			if (isAlertWindowPresent())
			{
				break;
			}

			waitTime(1L);

			if (i == timeout)
			{
				testLogFail(alertTitle + " alert Window is not present within '" + timeout + "' timeout");
			}
		}
	}	

	public static void waitForElementToDisplay(String objLocator, int timeout)
	{
		boolean webElementStatus = false;
		try {
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);

			for (int i = 1; i <= timeout; i++)
			{
				try
				{
					if (!webElementStatus)
					{		
						waitJQueryAngular();
						findWebElement(objLocator);
						webElementStatus = true;
				}
					if (webElement.isDisplayed())
					{
						break;
					}
					waitTime(1L);			
				}
				catch (InvalidSelectorException e)
				{
					waitTime(1L);
				}
				catch (StaleElementReferenceException e)
				{
					waitTime(1L);
				}
				catch (NoSuchElementException e)
				{
					waitTime(1L);
				}
				catch (ElementNotVisibleException e) 
				{
					waitTime(1L);
				}
				catch (UnreachableBrowserException e)
				{
					waitTime(1L);
				}
				catch (WebDriverException e)
				{
					waitTime(1L);
				}			
				if (i == timeout)
				{
					if (webElementStatus)
					{
						testLogPass(locatorDescription + " element is present but its not in clickable/editable state within '" + timeout + "' timeout");
					}
					else
					{
						testLogFail(locatorDescription + " element not found in '" + timeout + "' seconds timeout ");
					}
				}
			}
		}
		catch (Exception e)
		{
			testLogFail("Exception error '" + e.toString() + "'");

		}
		finally
		{
			webElementStatus = false;
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
		}
	}

	public static void waitForElementToDisplayWithoutFail(String objLocator, int timeout)
	{
		boolean webElementStatus = false;
		try {
			waitUntilPageReady();
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);

			for (int i = 1; i <= timeout; i++)
			{
				try
				{
					if (!webElementStatus)
					{		
						//new WebDriverWait(driver, 40).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
						findWebElement(objLocator);
						webElementStatus = true;
					}
					if (webElement.isDisplayed())
					{
						break;
					}
					waitTime(1L);			
				}
				catch (InvalidSelectorException e)
				{
					waitTime(1L);
				}
				catch (StaleElementReferenceException e)
				{
					waitTime(1L);
				}
				catch (NoSuchElementException e)
				{
					waitTime(1L);
				}
				catch (ElementNotVisibleException e) 
				{
					waitTime(1L);
				}
				catch (UnreachableBrowserException e)
				{
					waitTime(1L);
				}
				catch (WebDriverException e)
				{
					waitTime(1L);
				}			
				if (i == timeout)
				{
					if (webElementStatus)
					{
						//testLogFail(locatorDescription + " element is present but its not in clickable/editable state within '" + timeout + "' timeout");
						waitTime(3);
					}
					else
					{
						waitTime(3);
					}
				}
			}
		}
		catch (Exception e)
		{
			testLogFail("Exception error '" + e.toString() + "'");

		}
		finally
		{
			webElementStatus = false;
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
		}
	}


	//Had to create overload because 'checkPageIsReady' is important in some scnearios
	//Existing method neither waits nor calls this methos
	//waitForElement method also does not call this method
	public static boolean isElementDisplayed(String objLocator, int timeout)
	{
		boolean webElementStatus = false;
		try {
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);

			for (int i = 1; i <= timeout; i++)
			{
				try
				{
					if (!webElementStatus)
					{
						findWebElement(objLocator);
						webElementStatus = true;
					}
					if (webElement.isDisplayed())
					{
						//System.out.println(locatorDescription + " is displayed");
						break;
					}

					waitTime(1L);
				}
				catch (InvalidSelectorException e)
				{
					waitTime(1L);
				}
				catch (StaleElementReferenceException e)
				{
					waitTime(1L);
				}
				catch (NoSuchElementException e)
				{
					waitTime(1L);
				}
				catch (ElementNotVisibleException e) {
					waitTime(1L);
				}
				catch (UnreachableBrowserException e)
				{
					testLogFail(e.toString());
				}
				catch (WebDriverException e) {
					waitTime(1L);
				}

				if (i == timeout)
				{
					return false;
				}
			}
		}
		catch (Exception e)
		{
			testLogFail("Exception error '" + e.toString() + "'");
		}
		finally
		{
			webElementStatus = false;

			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
		}

		return true;
	}
	
	
	public static boolean isElementDisplayedwithoutFail(String objLocator, int timeout)
	{
		boolean webElementStatus = false;
		try {
			driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);

			for (int i = 1; i <= timeout; i++)
			{
				try
				{
					if (!webElementStatus)
					{
						findWebElement(objLocator);
						webElementStatus = true;
					}
					if (webElement.isDisplayed())
					{
						//System.out.println(locatorDescription + " is displayed");
						break;
					}

					waitTime(1L);
				}
				catch (InvalidSelectorException e)
				{
					waitTime(1L);
				}
				catch (StaleElementReferenceException e)
				{
					waitTime(1L);
				}
				catch (NoSuchElementException e)
				{
					waitTime(1L);
				}
				catch (ElementNotVisibleException e) {
					waitTime(1L);
				}
				catch (UnreachableBrowserException e)
				{
					testLogFail(e.toString());
				}
				catch (WebDriverException e) {
					waitTime(1L);
				}

				if (i == timeout)
				{
					return false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			webElementStatus = false;
			driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
			//driver.findWebElement(objLocator);
		}
		return true;
	}



	public static void clickOnSpecialElement(String objectLocator)
	{
		try
		{
			waitForElement(objectLocator, elementLoadWaitTime);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", new Object[] { webElement });
			testLogPass(  "Click on :" + locatorDescription);
		}
		catch (Exception e)
		{
			testLogFail("Click on :" + locatorDescription);
		}
	}

	public static boolean isElementDisplayed(String objectLocator)
	{
		waitUntilPageReady();
		findWebElement(objectLocator);

		if (webElement.isDisplayed())
		{
			return true;
		}

		return false;
	}

	public static boolean isTextPresent(String expectedText)
	{
		if (driver.getPageSource().contains(expectedText))
		{
			return true;
		}

		return false;
	}

	public static void deleteAllCookies()
	{
		try
		{
			driver.manage().deleteAllCookies();

		}
		catch (Exception e)
		{
			windowreadyStateStatus = false;
			testLogFail("Delete All cookies keyword exception error" + e.toString());
		}
	}

	public static void maximiseWindow()
	{
		try
		{
			driver.manage().window().maximize();
			testLogPass("Successfully Maximised Browser Window");
		}
		catch (Exception e)
		{
			windowreadyStateStatus = false;
			testLogFail("Maximise window keyword exception error" + e.toString());
		}
	}

	public static void selectCheckBox(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		try
		{
			if (!webElement.isSelected())
			{
				webElement.click();
			}
			testLogPass("Checked on the " + locatorDescription + " checkbox");
		}
		catch (Exception e)
		{
			testLogFail(e.toString());
		}
	}
	
	public static void selectRadioBtn(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		try
		{
			if (!webElement.isSelected())
			{
				webElement.click();
			}
			testLogPass("Checked on the " + locatorDescription + " RadioBtn");
		}
		catch (Exception e)
		{
			testLogFail(e.toString());
		}
	}

	public static void unSelectCheckBox(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		try
		{
			if (webElement.isSelected())
			{
				webElement.click();
			}
			testLogPass("Unchecked the " + locatorDescription + " checkbox");
		}
		catch (Exception e)
		{
			testLogFail(e.toString());
		}
	}

	public static void verifyCheckBoxIsChecked(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		try
		{
			if (webElement.isSelected())
			{
				testLogPass("Verified that " + locatorDescription + " is checked");
			}
			else
			{
				testLogFail(locatorDescription + " is not checked");
			}

		}
		catch (Exception e)
		{
			testLogFail(e.toString());
		}
	}

	public static void verifyCheckBoxIsUnChecked(String objLocator)
	{
		waitForElementToDisplay(objLocator, elementLoadWaitTime);
		try
		{
			if (!webElement.isSelected())
			{
				testLogPass("Verified that " + locatorDescription + " is Unchecked");
			}
			else
			{
				testLogFail(locatorDescription + " is  checked");
			}

		}
		catch (Exception e)
		{
			testLogFail(e.toString());
		}
	}


	public static void testStarts(String TestCaseName, String Description)
	{
		try
		{
			logger = extent.startTest(TestCaseName, Description);
			Assert.assertTrue(true);
		}
		catch(Exception e)
		{
			testLogFail("unable to generate the pass report "+e.toString());
		}
	}

	public static void testLogPass(String Description)
	{
		try
		{
			logger.log(LogStatus.PASS, Description);
		}
		catch(Exception e)
		{
			testLogFail("unable to generate the Error report "+e.toString());
		}
	}
	public static void testLogError(String Description)
	{
		try
		{
			String screenshotPath = getScreenshot();
			logger.log(LogStatus.ERROR, Description, logger.addScreenCapture(screenshotPath));
		}
		catch(Exception e)
		{
			testLogFail("unable to generate the Error report "+e.toString());
		}
	}

	public static void testLogFail(String Description)
	{
		try
		{
			if(logger==null)
			{
				System.out.println("---ERROR---");
			}

			if(logger!=null)
			{
				logger.log(LogStatus.FAIL, Description);
				String screenshotPath = getScreenshot();
				//To add it in the extent report 
				logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
				//Assert.fail();
			}

		}
		catch(Exception e)
		{
			//testLogFail("unable to generate the fail report "+e.toString());
			System.out.println("---EXCEPTION---" + e.toString());
		}
	}


	public static void testcaseDescription(String Description)
	{
		try
		{
			logger.log(LogStatus.WARNING, Description);
		}
		catch(Exception e)
		{
			testLogFail("unable to generate the Skip report "+e.toString());
		}
	}

	public static void setProperty(String propName, String value)
	{
		prop= new Properties();
		try 
		{
			//load the file
			input = new FileInputStream(propsFileName);
			prop.load(input);
			input.close();
			//change the value
			prop.setProperty(propName, value);				
			//save modified property file
			FileOutputStream output = new FileOutputStream(propsFileName);
			prop.store(output, "Data is updated recently");
			output.close();

		} 
		catch (IOException ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (input != null)
			{
				try 
				{
					input.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static String getProperty(String propName)
	{
		String value=null;
		prop= new Properties();
		try 
		{
			//load the file
			input = new FileInputStream(propsFileName);
			prop.load(input);
			input.close();
			//change the value
			value=prop.getProperty(propName);			
			//save modified property file
			FileOutputStream output = new FileOutputStream(propsFileName);
			prop.store(output, "Data is reterived recently");
			output.close();


		} 
		catch (IOException ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (input != null)
			{
				try 
				{
					input.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static void typeAndSearch(String objLocator, String typeValue, String searchLocator)
	{
		typeIn(objLocator, typeValue);
		clickOn(searchLocator);
	}


	public void getscreenshot() throws Exception 
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"/test-output/screenshot.png"));
	}

	public static String getScreenshot() throws Exception 
	{
		String folderPath = null;
		try
		{
			 folderPath="/test-output/screenshots/" + SITENAME;
			File directory = new File(folderPath);
			if (! directory.exists()){
				directory.mkdir();
				// If you require it to make the entire directory path including parents,
				// use directory.mkdirs(); here instead.
			}

			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			//
			//String destination = System.getProperty("user.dir") + "/test-output/screenshots/Automation"+dateName+".png";
			String destination = folderPath + "/Automation"+dateName+".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);

			return destination;
		}
		catch(Exception e)
		{
			System.out.println("---EXCEPTION1111---" + e.toString());
			return System.getProperty(folderPath) + "/test-output/Automation20180313113543.png";
		}
	}


	public static enum keys
	{
		ENTER, 
		SPACE, 
		ESCAPE, 
		CONTROL, 
		ALT, 
		BACKSPACE, 
		CANCEL, 
		DELETE, 
		PAGEDOWN, 
		PAGEUP, 
		TAB;
	}

	public boolean isFileDownloaded(String downloadPath, String fileName) {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().equals(fileName)) {
				// File has been found, it can now be deleted:
				dirContents[i].delete();
				return true;
			}
		}
		return false;
	}

	public static void compareExactText(String objLocator, String passText, String failText, String expectedText)
	{
		try
		{
			if(getText(objLocator).trim().equals(expectedText))
			{	
				testLogPass(passText);
			}
			else
			{
				testLogFail(failText);
			}
		}
		catch(Exception e)
		{
			testLogFail("Error occurred in compareExactText " + e.toString());
		}
	}

	public static void compareText(String objLocator, String passText, String failText, String expectedText)
	{
		try
		{
			if(getText(objLocator).contains(expectedText))
			{
				testLogPass(passText);			
			}
			else
			{
				testLogFail(failText);
			}
		}
		catch(Exception e)
		{
			testLogFail("Error occurred in compareText " + e.toString());
		}
	}	


	public static void closeBrowser()
	{
		try
		{   
			driver.quit();
			testLogPass("Closed browser sucessfully");
		}
		catch (Exception e)
		{

			testLogFail("unable to Close browser "+e.toString());
		}

	}


	public static void waitforclick(String objLocator) 
	{
		try
		{
			WebElement element = null;
			element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		}
		catch(Exception e)
		{
			testLogFail("Error occurred on clickWhenReady " + e.toString());
		}
	}

	public static void waitvisible(String objLocator) 
	{
		try
		{
			WebElement element = null;
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		}
		catch(Exception e)
		{
			testLogFail("Error occurred on clickWhenReady " + e.toString());
		}
	}	
	
	
    
    private static WebDriverWait jsWait;
    private static JavascriptExecutor jsExec;
 
    public static void setDriver (WebDriver driver) {
        jsWait = new WebDriverWait(driver, 10);
        jsExec = (JavascriptExecutor) driver;
    }
 
    public static void waitForJQueryLoad() {
       sleep(20);
    }
 
 
    public static void waitForAngularLoad() {
       sleep(20);
    }
 
    public static void waitUntilJSReady() {
       sleep(20);
    }
 
    public static void waitUntilJQueryReady() {
        sleep(20);
    }
 
    public static void waitUntilPageReady() {
        sleep(20);
    }
 
    public static void waitJQueryAngular() {
    	waitUntilPageReady();
    }
 
    public static void sleep (Integer seconds) {
        long secondsLong = (long) seconds;
        try {
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
   
}