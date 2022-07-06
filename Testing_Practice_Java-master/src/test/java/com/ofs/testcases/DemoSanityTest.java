package test.java.com.ofs.testcases;

import main.java.generics.BaseTest;
import main.java.generics.CommonMethods;
import main.java.generics.ObjectRepSheet;
import main.java.generics.Utility;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class DemoSanityTest extends BaseTest {

    @Test(groups = {"Sanity"})
    public void testSanity() {

       // String homePageTitle = Utility.getCellData_TestData ("Sanity Test", "HomePage Title");
        //String checkBoxPageURL = Utility.getCellData_TestData ("Sanity Test", "Check Box Page Url");

        ObjectRepSheet checkBox = Utility.getCellData_OBR ("HomePage", "Checkboxes");
        ObjectRepSheet clickCheckBox = Utility.getCellData_OBR ("Actions", "clickCheckBox");

        CommonMethods method = new CommonMethods ();

        //Verify HomePage is Displayed
        //method.verifyTitleContain (homePageTitle);

        // Click on the Check Box link in the Home Page
        method.click (checkBox, "CheckBox link");

        //Verify Check Box page is Displayed
        //method.verifyURL (checkBoxPageURL);

        //Click all the Check Boxes
        List<WebElement> boxes = method.findElements (clickCheckBox);
        for (WebElement box : boxes) {
            if (box.getAttribute ("checked") == null)
                box.click ();

        }
        System.out.println( "Sanity" );
    }
}



