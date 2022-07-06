package test.java.com.ofs.testcases;


import main.java.generics.BaseTest;
import main.java.generics.CommonMethods;
import main.java.generics.ObjectRepSheet;
import main.java.generics.Utility;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class DemoRegressionTest extends BaseTest {
    private static CommonMethods method = new CommonMethods ();

   @Test(groups = {"Regression"})
    public void testVerifyHomePage() {
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        ObjectRepSheet links = Utility.getCellData_OBR("HomePage", "Links");

        //Verify HomePage is Displayed
        method.verifyTitleContain(homePageTitle);
        method.getCurrentUrl();

        //Print Links Present in the Home Page
        method.printContent(links, "HomePage");
    }

     @Test
    public void testHandleCheckBox() {
         String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String checkBoxPageURL = Utility.getCellData_TestData("Regression Test", "Check Box Page Url");
        ObjectRepSheet checkBox = Utility.getCellData_OBR("HomePage", "Checkboxes");
        ObjectRepSheet clickCheckBox = Utility.getCellData_OBR("Actions", "clickCheckBox");

        method.verifyTitleContain(homePageTitle);
        method.click(checkBox, "CheckBox link");
        method.verifyURL(checkBoxPageURL);
        List<WebElement> boxes = method.findElements(clickCheckBox);
         for (WebElement box : boxes) {
             if (box.getAttribute ("checked") == null)
                 box.click ();
         }
    }

    @Test
    public void dragAndDrop(){
         String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
         String dragAndDropPageURL = Utility.getCellData_TestData("Regression Test", "dragAndDrop Page URL");
        ObjectRepSheet dragAndDrop = Utility.getCellData_OBR("HomePage", "dragAndDrop");
        ObjectRepSheet ele1 = Utility.getCellData_OBR("Actions", "dragEle1");
        ObjectRepSheet ele2 = Utility.getCellData_OBR("Actions", "dragEle2");
        method.verifyTitleContain(homePageTitle);
        method.click(dragAndDrop,"Drag and Drop Link");
        method.verifyURL(dragAndDropPageURL);
        method.dragAndDrop(ele2,ele1);
    }

    @Test
    public void testSendKeys() {
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String inputsPageURL = Utility.getCellData_TestData("Regression Test", "Inputs Page URL");
        ObjectRepSheet textBox = Utility.getCellData_OBR("HomePage", "TextBox");
        ObjectRepSheet inputTB = Utility.getCellData_OBR("Actions", "InputTB");

        method.verifyTitleContain(homePageTitle);
        method.click(textBox, "Input link");
        method.verifyURL(inputsPageURL);
        method.sendKeys(inputTB, "123456", "TextBox");
    }

    @Test
    public void testKeyPress() throws AWTException {
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String keyPressPageURL = Utility.getCellData_TestData("Regression Test", "KeyPress Page URL");
        ObjectRepSheet keyPresses = Utility.getCellData_OBR("HomePage", "KeyPresses");
        ObjectRepSheet data = Utility.getCellData_OBR("Actions", "KeyPressed");

        method.verifyTitleContain(homePageTitle);
        method.click(keyPresses, "KeyPresses link");
        method.verifyURL(keyPressPageURL);
        Robot rb = new Robot();

        rb.keyPress(KeyEvent.VK_ENTER);
        method.getText(data, "Key Pressed");

        rb.keyPress(KeyEvent.VK_TAB);
        method.getText(data, "Key Pressed");
    }

    @Test
    public void testHandleAlerts() {
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String alertsPageURL = Utility.getCellData_TestData("Regression Test", "Alerts Page URL");
        ObjectRepSheet alerts = Utility.getCellData_OBR("HomePage", "Alerts");
        ObjectRepSheet acceptAlert = Utility.getCellData_OBR("Alerts", "AcceptAlert");
        ObjectRepSheet conformAlert = Utility.getCellData_OBR("Alerts", "ConformAlert");
        ObjectRepSheet alertResult = Utility.getCellData_OBR("Actions", "alert Result");

        method.verifyTitleContain(homePageTitle);
        method.click(alerts, "Alerts Links");
        method.verifyURL(alertsPageURL);

        method.click(acceptAlert, "AcceptAlert BTN");
        method.handleAlert("accept", "Alert Accepted");
        String result = method.getText(alertResult, "Result");
        method.compareText(result, "You successfuly clicked an alert");
       // addScreenShot();

        method.click(conformAlert, "AcceptAlert BTN");
        method.handleAlert("accept", "Alert Accepted");
        String resultConform = method.getText(alertResult, "Result");
        method.compareText(resultConform, "You clicked: Ok");
       // addScreenShot();

        method.click(conformAlert, "AcceptAlert BTN");
        method.handleAlert("dismiss", "Alert Accepted");
        String resultDismiss = method.getText(alertResult, "Result");
        method.compareText(resultDismiss, "You clicked: Cancel");
    }

    @Test
    public void testScrollAndClick() {
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String scrollPageURL = Utility.getCellData_TestData("Regression Test", "Scroll Page URL");
        ObjectRepSheet typosElement = Utility.getCellData_OBR("HomePage", "Typos");
        method.verifyTitleContain(homePageTitle);
        method.clickElementByScrollPage(typosElement,"Typos Link");
        method.verifyURL(scrollPageURL);
    }

    @Test
    public void testScreenShot(){
        //addScreenShot();
    }

    @Test
    public void testAddAndRemoveElement() throws InterruptedException{
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String addElementURL = Utility.getCellData_TestData("Regression Test", "AddAndRemoveElement Page URL");
        ObjectRepSheet addElementLink = Utility.getCellData_OBR("HomePage", "Add/Remove Elements");
        ObjectRepSheet addElement = Utility.getCellData_OBR("Actions", "addElement");
        ObjectRepSheet deleteElement = Utility.getCellData_OBR("Actions", "deleteElement");

        method.verifyTitleContain(homePageTitle);
        method.click(addElementLink, "Add and Remove Element Links");
        method.verifyURL(addElementURL);
        method.click(addElement, "Add Element BTN");
        method.sleep(2);
        //addScreenShot();
        method.click(deleteElement, "Delete Element BTN");
    }


    @Test
    public void testMouseHovers(){
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String hoverPageURL = Utility.getCellData_TestData("Regression Test", "MouseHovers Page URL");
        ObjectRepSheet mouseHoverLink = Utility.getCellData_OBR("HomePage", "Mouse Hover");
        ObjectRepSheet mouseHover = Utility.getCellData_OBR("Actions", "mouseHover");

        method.verifyTitleContain(homePageTitle);
        method.click(mouseHoverLink, "Hover Links");
        method.verifyURL(hoverPageURL);
        method.mouseHover(mouseHover);
    }


    @Test
    public void testFrames(){
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String framesPageURL = Utility.getCellData_TestData("Regression Test", "Frames Page URL");
        String nestedFramesPageURL = Utility.getCellData_TestData("Regression Test", "nestedFrames Page URL");
        ObjectRepSheet framesLink = Utility.getCellData_OBR("HomePage", "Frames");
        ObjectRepSheet frames = Utility.getCellData_OBR("Actions", "framesCount");
        ObjectRepSheet nestedFramesLink = Utility.getCellData_OBR("Actions", "nestedFrameLink");

        method.verifyTitleContain(homePageTitle);
        method.click(framesLink, "Frames Links");
        method.verifyURL(framesPageURL);
        method.click(nestedFramesLink,"Nested Frame Link");
        method.verifyURL(nestedFramesPageURL);
        method.frameByIndex(frames,1,"Frames");
    }


    @Test
    public void testFileUpload(){
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String fileUploadPageURL = Utility.getCellData_TestData("Regression Test", "FileUpload Page URL");
        String filePath = Utility.getCellData_TestData("Regression Test", "fileUpload Path");
        ObjectRepSheet fileUploadLink = Utility.getCellData_OBR("HomePage", "File Upload");
        ObjectRepSheet fileUpload = Utility.getCellData_OBR("Actions", "fileUpload");

        method.verifyTitleContain(homePageTitle);
        method.click(fileUploadLink, "File Upload Links");
        method.verifyURL(fileUploadPageURL);
        method.fileUPLoad(fileUpload,filePath);
    }

    @Test
    public void testFileDownload(){
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String fileDownloadPageURL = Utility.getCellData_TestData("Regression Test", "FileDownload Page URL");
        ObjectRepSheet fileDownloadLink = Utility.getCellData_OBR("HomePage", "File Download");
        ObjectRepSheet fileDownload = Utility.getCellData_OBR("Actions", "fileDownload");

        method.verifyTitleContain(homePageTitle);
        method.click(fileDownloadLink, "File DownLoad Link");
        method.verifyURL(fileDownloadPageURL);
        //method.fileDownload(fileDownload);

    }

    @Test
    public void testDropDown(){
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String dropDownPageURL = Utility.getCellData_TestData("Regression Test", "DropDown Page URL");
        ObjectRepSheet dropDownLink = Utility.getCellData_OBR("HomePage", "Drop Down");
        ObjectRepSheet dropDown = Utility.getCellData_OBR("Actions", "dropDown");

        method.verifyTitleContain(homePageTitle);
        method.click(dropDownLink, "Drop Down Link");
        method.verifyURL(dropDownPageURL);
        method.selectByIndex(dropDown,1,"DropDown List");
    }


    @Test
    public void testDisappearingElement() throws InterruptedException{
        String homePageTitle = Utility.getCellData_TestData("Regression Test", "HomePage Title");
        String pageURL = Utility.getCellData_TestData("Regression Test", "DisappearingElement Page URL");
        ObjectRepSheet disappearingElement = Utility.getCellData_OBR("HomePage", "Disappearing Element");

        method.verifyTitleContain(homePageTitle);
        method.click(disappearingElement, "Disappearing Element Link");
        method.verifyURL(pageURL);
        method.sleep(5);
        method.refresh();
    }
}
