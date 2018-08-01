package pageObject;

import org.apache.poi.openxml4j.opc.internal.marshallers.TestZipPackagePropertiesMarshaller;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import AutomationFramework.ApplicationKeyword;

import AutomationFramework.OR;


public class Reportspage extends ApplicationKeyword {

	 public static void VerifyViewReport()
     {
         waitForElementToDisplay(OR.ViewMoreReport, 60);
         waitforclick(OR.ViewMoreReport);
         clickOn(OR.ViewMoreReport);
         int sClass = driver.findElements(By.xpath("//li[@tooltip-trigger='focus']/a[@role='menuitem']")).size();

         for(int j =1;j <=sClass; j++)
         {
        	 waitUntilPageReady();
             WebElement one = driver.findElement(By.xpath("(//li[@tooltip-trigger='focus']/a[@role='menuitem'])[" + j + "]"));
             
             
             testLogPass("Report is "+one.getText());
             if(one.getText()!=null)
             {
            	 one.click();
                 waitUntilPageReady();
                 testLogPass("selected report item  page is" + driver.getTitle());
             }
             clickOn(OR.ViewMoreReport);
             
         }

     }
    
}
