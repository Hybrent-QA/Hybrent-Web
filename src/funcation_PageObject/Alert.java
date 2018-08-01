package funcation_PageObject;

import AutomationFramework.ApplicationKeyword;
import AutomationFramework.OR;
import AutomationFramework._OR;

public class Alert extends ApplicationKeyword{
    public static void VerifyAlert()
    {
    	waitForElementToDisplay(OR.Alert_txt_userAlert, 60);
    	verifyElementText(OR.Alert_txt_userAlert, "USER ALERT");
        verifyElement(OR.Alert_Btn_cancel);
        verifyElement(OR.Alert_Btn_Update);
    }
    
    public static void NavigateToAlert()
    {
    	waitForElement(_OR.Dashboard_User);
    	clickOn(_OR.Dashboard_User);
    	waitForElement(_OR.Dashboard_User_alert);
		clickOn(_OR.Dashboard_User_alert);
    }

}
