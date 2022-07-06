package test.java.com.ofs.testcases;

import main.java.generics.BaseTest;
import main.java.generics.CommonMethods;
import main.java.generics.ObjectRepSheet;
import main.java.generics.Utility;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.Screenshot;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class DemoSmokeTestCase extends BaseTest {

    @Test(groups = {"Smoke"})
    public void testSmokeTestCase(){
       // String homePageTitle = Utility.getCellData_TestData("Smoke Test", "HomePage Title");
       // ObjectRepSheet links = Utility.getCellData_OBR("HomePage", "Links");
        ObjectRepSheet checkBox = Utility.getCellData_OBR("HomePage", "Checkboxes");
        CommonMethods method=new CommonMethods ();

        //Verify HomePage is Displayed
        method.verifyTitleContain("The Internet");
        method.getCurrentUrl();
        method.highLightElement( checkBox,"Checkbox" );
        method.click( checkBox,"Checkbox" );
        System.out.println( "Smoke" );
        //Print Links Present in the Home Page
       // method.printContent(links, "HomePage");
    }

    @Test(groups = {"Regression"})
    public void testRegTestCase() throws IOException, InterruptedException {
        // String homePageTitle = Utility.getCellData_TestData("Smoke Test", "HomePage Title");
        // ObjectRepSheet links = Utility.getCellData_OBR("HomePage", "Links");
        ObjectRepSheet checkBox = Utility.getCellData_OBR("HomePage", "Checkboxes");
        CommonMethods method=new CommonMethods ();

        //Verify HomePage is Displayed
        // method.verifyTitleContain("The Internet");
        method.getCurrentUrl();
        // method.highLightElement( checkBox,"Checkbox" );
        Screenshot a = method.fullPageScreenCapture("test1.png");
        method.sleep( 2 );
        // Screenshot b = method.fullPageScreenCapture("test2.png");

        // Screenshot b = method.fullPageScreenCapture("test2");
        // method.click( checkBox,"Checkbox" );
        Screenshot b = method.fullPageScreenCapture("test2.png");
        BufferedImage c = method.imageCompare( a,b );
        //method.getScreenShot1(c);
        System.out.println( "Regression" );
        //Print Links Present in the Home Page
        // method.printContent(links, "HomePage");
    }
}
