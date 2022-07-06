package test.java.com.ofs.testcases;

import main.java.generics.BaseTest;
import main.java.generics.CommonMethods;
import main.java.generics.ObjectRepSheet;
import main.java.generics.Utility;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DemoFeaturedTest extends BaseTest {

    @Test(groups = {"Featured"})
    public void testFeatured()throws AWTException{

        String homePageTitle = Utility.getCellData_TestData("Featured Test", "HomePage Title");
        String keyPressPageURL = Utility.getCellData_TestData("Featured Test", "KeyPress Page URL");
        ObjectRepSheet keyPressed = Utility.getCellData_OBR("HomePage", "KeyPresses");
        ObjectRepSheet data = Utility.getCellData_OBR("Actions", "KeyPressed");

        CommonMethods method=new CommonMethods ();

        method.verifyTitleContain(homePageTitle);
        method.click(keyPressed, "KeyPresses link");
        method.verifyURL(keyPressPageURL);
        Robot rb = new Robot();

        rb.keyPress(KeyEvent.VK_ENTER);
        method.getText(data, "Key Pressed");

        rb.keyPress(KeyEvent.VK_TAB);
        method.getText(data, "Key Pressed");

    }
}
