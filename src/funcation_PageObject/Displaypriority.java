package funcation_PageObject;
import org.sikuli.script.Screen;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;


import AutomationFramework.ApplicationKeyword;
import AutomationFramework._OR;
import AutomationFramework.OR;

public class Displaypriority extends ApplicationKeyword{

	public static void NavigateDisplayPriority()
	{
		clickOn(_OR.DashBoard_Admin);
		clickOn(_OR.DashBoard_Admin_DisplayPriority);
		verifyElement(OR.DisplayPriority_Title);
	}
	
	public static void VerifyPage()
	{
		verifyElement(OR.DisplayPriority_SaveBtn);
		verifyElement(OR.DisplayPriority_DragInfo);
		//getTextchild("//strong");
		 Screen s=new Screen();
		 try {
			
			 String one = System.getProperty("user.dir")+"/Medline.png";
			 String one2 = System.getProperty("user.dir")+"/MCKESSON.png";
			s.find(one2);
		
		 System.out.println("Source image found");
		 s.find(one);
		 System.out.println("target image found");
		 s.dragDrop(one, one2);
		 } catch (FindFailed e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		WebElement Drag= driver.findElement(By.xpath("(//*[@dnd-draggable='item']/i)[1]"));
		WebElement Drop= driver.findElement(By.xpath("(//*[@dnd-draggable='item']/i)[3]"));
		 Actions act=new Actions(driver);
		 
		 

		 
		 
		 act.keyDown(Keys.CONTROL)
		   .click(Drag)
		   .dragAndDrop(Drag, Drop)
		   .keyDown(Keys.CONTROL);
//
//		// Then get the action:
//		 Action selectMultiple = act.build();
//
//		// And execute it:
//		selectMultiple.perform();   
//		 
//		 act.dragAndDrop(Drag, Drop).build().perform();
		 System.out.println("te  st");
		 System.out.println("te  st");
	}
	
}

