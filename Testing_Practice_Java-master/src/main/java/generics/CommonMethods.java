package main.java.generics;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CommonMethods extends BaseTest {

    //------------------------------------------------1---------------------------------------------------------------//
    private static final long ETO = (new Double(Utility.getPropertyValue(AutomationConstants.PLATFORM_CONFIG, "ExplicitTimeOut")).intValue());
    private static JavascriptExecutor jse = (JavascriptExecutor) driver;
    //------------------------------------------------2---------------------------------------------------------------//

    /**
     * This method is used to initialize generic web page.
     */
    public void genericPage() {
        PageFactory.initElements(driver, this);
    }
    //------------------------------------------------3---------------------------------------------------------------//

    /**
     * This Method is used to get the root cause for the Exception caused during Execution.
     *
     * @param e is the object of the Exception,
     * @return error message text.
     */
    private static String getErrorMessage(Exception e) {
        String error;
        String[] message = e.getMessage().split(":");
        String screenshotPath = getScreenShot();
        error = message[0].trim() + " : " + message[1].trim() + " - Element info : " + message[message.length - 1].trim()
                + reporter.addScreenCapture(screenshotPath);
        return error;
    }
    //-------------------------------------------------4--------------------------------------------------------------//

    /**
     * This Method is used to get the current date and time in the Customized format.
     *
     * @return the current date and time in "dd_MM_yyyy_hh_mm_ss" format.
     */
    private static String getFormattedDateTime() {
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        return simpleDate.format(new Date());
    }
    //-------------------------------------------------5--------------------------------------------------------------//

    /**
     * This Method is used to find the Element by generic search taking the locator type and locator value
     * And give the WebElement to perform Action on that element at required time.
     *
     * @param object is the element LocatorType and LocatorValue
     * @return element;
     */
    private WebElement findElement(ObjectRepSheet object) {
        WebElement element = null;
        String locatorType = object.getLocatorType();
        String locatorValue = object.getLocatorValue();
        switch (Locator.valueOf(locatorType)) {
            case id:
                element = driver.findElement(By.id(locatorValue));
                break;
            case name:
                element = driver.findElement(By.name(locatorValue));
                break;
            case xpath:
                element = driver.findElement(By.xpath(locatorValue));
                break;
            case css:
                element = driver.findElement(By.cssSelector(locatorValue));
                break;
            case linkText:
                element = driver.findElement(By.linkText(locatorValue));
                break;
            case partialLinkText:
                element = driver.findElement(By.partialLinkText(locatorValue));
                break;
            case tagName:
                element = driver.findElement(By.tagName(locatorValue));
                break;
            case className:
                element = driver.findElement(By.className(locatorValue));
                break;
            default:
                reporter.log(LogStatus.WARNING, " The Locator Specified is not matching with any if the locator type please check the locator type");
                break;
        }
        return element;
    }
    //------------------------------------------------6---------------------------------------------------------------//

    /**
     * This Method is used to find the Element's by generic search taking the locator type and locator value
     * And give the list of WebElement to perform Action on the required element at required time.
     *
     * @param object is the element LocatorType and LocatorValue
     * @return element.
     */
    public List<WebElement> findElements(ObjectRepSheet object) {
        List<WebElement> element = null;
        String locatorType = object.getLocatorType();
        String locatorValue = object.getLocatorValue();
        switch (Locator.valueOf(locatorType)) {
            case id:
                element = driver.findElements(By.id(locatorValue));
                break;
            case name:
                element = driver.findElements(By.name(locatorValue));
                break;
            case xpath:
                element = driver.findElements(By.xpath(locatorValue));
                break;
            case css:
                element = driver.findElements(By.cssSelector(locatorValue));
                break;
            case linkText:
                element = driver.findElements(By.linkText(locatorValue));
                break;
            case partialLinkText:
                element = driver.findElements(By.partialLinkText(locatorValue));
                break;
            case tagName:
                element = driver.findElements(By.tagName(locatorValue));
                break;
            case className:
                element = driver.findElements(By.className(locatorValue));
                break;
            default:
                reporter.log(LogStatus.WARNING, " The Locator Specified is not matching with any if the locator type please check the locator type");
                break;
        }
        return element;
    }
    //------------------------------------------------7---------------------------------------------------------------//

    /**
     * This method is used to specify the Waiting Condition.
     *
     * @param time is the Time specified to wait before going to the next step
     */
    public void sleep(long time) throws InterruptedException {
        Thread.sleep(time * 1000);
    }
    //-------------------------------------------------8-------------------------------------------------------------//

    /**
     * This Method will compare the expected Title with the actual Title and.
     *
     * @param eTitle is expected title which we r going to pass and it is compared with the actual title.
     */
    public void verifyTitle(String eTitle) {
        String aTitle = driver.getTitle();
        try {
            Assert.assertEquals(aTitle, eTitle);
            reporter.log(LogStatus.PASS, " ActualTitle : " + aTitle + " is matching with the ExpectedTitle: " + eTitle);
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, " ActualTitle : " + aTitle + " is not matching with the ExpectedTitle : "
                    + eTitle + " and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //-------------------------------------------------9--------------------------------------------------------------//

    /**
     * This Method will wait till  the timeout (Number of seconds to wait to find the Title to appear.
     * and compare the expected Title is matching with the actual Title,
     *
     * @param eTitle is expected title which we r going to pass and it is compared with the actual title.
     */
    public void verifyTitleContain(String eTitle) {
        try {
            new WebDriverWait(driver, ETO).until(ExpectedConditions.titleContains(eTitle));
            String aTitle = driver.getTitle();
            Assert.assertEquals(aTitle, eTitle);
            reporter.log(LogStatus.PASS, " ActualTitle : " + aTitle + " is matching with the ExpectedTitle: " + eTitle);
        } catch (Exception e) {
            String aTitle = driver.getTitle();
            reporter.log(LogStatus.FAIL, " ActualTitle : " + aTitle + " is not matching with the ExpectedTitle : "
                    + eTitle + " and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------10--------------------------------------------------------------//

    /**
     * This Method is used to get the URL of the current Web page
     *
     * @return the Url of the page on which we are working on
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        reporter.log(LogStatus.INFO, "the URL of the web page is : " + url);
        return (url);
    }
    //------------------------------------------------11--------------------------------------------------------------//

    /**
     * This method is used to enter the url
     */
    static void enterURL(String url) {
        driver.get(url);
        reporter.log(LogStatus.INFO, "The Entered URL is : " + url);
    }
    //------------------------------------------------12--------------------------------------------------------------//

    /**
     * This Method is used to get OS On which we are Working
     *
     * @return value - platform.
     */
    static String platform() {
        String value = null;
        try {
            String platform = System.getProperty("os.name");
            platform = platform.toUpperCase();
            if (platform.contains("WINDOWS")) {
                value = "Windows";
            } else if (platform.contains("MAC")) {
                value = "Mac";
            } else if (platform.contains("Linux")) {
                value = "Linux";
            }

        } catch (NullPointerException e) {
            reporter.log(LogStatus.ERROR, e.getMessage());
        }
        return value;
    }
    //------------------------------------------------13--------------------------------------------------------------//

    /**
     * This Method is used to get screenshots and store the screenshot in the project directory
     *
     * @return finalImgPath - Returns the Screenshot path location.
     */
    private static String getScreenShot() {
        String encodedBase64 = null;
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try (FileInputStream fileInputStreamReader = new FileInputStream (sourceFile)){
            byte[] bytes = new byte[(int) sourceFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "An Error occurred while taking ScreenShot Because of : " + e.getMessage().split("\n")[0].trim());
            Assert.fail();
        }
        return "data:image/png;base64," + encodedBase64;
    }
    //------------------------------------------------14--------------------------------------------------------------//

    /**
     * This method is used synchronization of FindElement and FindElements
     *
     * @param time is the Implicit Waiting time to find the Element
     */
    static void implicitWait(long time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        reporter.log(LogStatus.INFO, "Implicit time is set to : " + time + " Seconds");
    }
    //------------------------------------------------15--------------------------------------------------------------//

    /**
     * This Method is used to perform click Operation
     *
     * @param object  is the element LocatorType and LocatorValue,
     * @param eleName is the Text message that will print in the report.
     */
    public void click(ObjectRepSheet object, String eleName) {
        try {
            WebElement ele = findElement(object);
            if (ele != null) {
                ele.click();
                reporter.log(LogStatus.PASS, "Clicked on: " + eleName);
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to perform Click operation on " + eleName + " and the Error is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------16--------------------------------------------------------------//

    /**
     * This Method is used to perform Composite Click Action on the Element
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName is the name of the element on which Click Action to be performed
     */
    public void clickByActions(ObjectRepSheet object, String eleName) {
        try {
            WebElement ele = findElement(object);
            Actions action = new Actions(driver);
            action.moveToElement(ele).click().perform();
            reporter.log(LogStatus.PASS, "Clicked on: " + eleName);
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to perform Click operation on " + eleName + " and the ERROR is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------17--------------------------------------------------------------//

    /**
     * This method is used to wait for the Element to by appear
     *
     * @param object is the element LocatorType and LocatorValue
     */
    public void visibilityOfElement(ObjectRepSheet object, String eleName) {
        WebDriverWait wait = new WebDriverWait(driver, ETO);
        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(object)));
            reporter.log(LogStatus.PASS, eleName + " is Visible");
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, eleName + " Element is not Visible even after the time" +
                    " out and the Error is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------18--------------------------------------------------------------//

    /**
     * This Method is used to enter data in to the text field
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param data    is the test data,
     * @param eleName is the Text message that will print in the report.
     */
    public void sendKeys(ObjectRepSheet object, String data, String eleName) {
        try {
            WebElement ele = findElement(object);
            if (ele != null) {
                ele.clear();
                ele.sendKeys(data);
            }
            if (eleName.equalsIgnoreCase("Password")) {
                data = "**********";
            }
            reporter.log(LogStatus.PASS, data + " : Entered in the " + eleName + " Text Field");
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to enter " + data + " in the " + eleName +
                    " Text Field and the Error is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------19--------------------------------------------------------------//

    /**
     * This Method is used to get the text present in the Element
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName is the Text message that will print in the report,
     */
    public String getText(ObjectRepSheet object, String eleName) {
        String text = "";
        try {
            WebElement ele = findElement(object);
            if (ele != null) {
                text = ele.getText();
                reporter.log(LogStatus.INFO, "The Text present in the " + eleName + " is " + text);
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to get Text present in the " + eleName + " and the Error is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
        return text;
    }
    //------------------------------------------------20--------------------------------------------------------------//

    /**
     * This Method is used to verify the URL of the WebPage
     *
     * @param expectedURL is the Expected URL that we are going to compare with the obtained URL(Actual URL)
     */
    public void verifyURL(String expectedURL) {
        new WebDriverWait(driver, ETO).until(ExpectedConditions.urlContains(expectedURL));
        String currentUrl = driver.getCurrentUrl();
        try {
            Assert.assertTrue(currentUrl.contains(expectedURL));
            reporter.log(LogStatus.PASS, "ActualURL : " + currentUrl + " is matching with the ExpectedURL : " + expectedURL);
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "ActualURL : " + currentUrl + " is not matching with the ExpectedURL : "
                    + expectedURL + " and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------21--------------------------------------------------------------//

    /**
     * This Method is used count the multiple matching Elements
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param webPage elements in the Web Page
     * @return count of all the Matching Elements.
     */
    public int countLinks(ObjectRepSheet object, String webPage) {
        int allLinks = 0;
        try {
            List<WebElement> ele = findElements(object);
            if (ele != null) {
                allLinks = ele.size();
                reporter.log(LogStatus.INFO, "Total no of links present in the " + webPage + " are : " + allLinks);
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Unable to count the total no of links present in the "
                    + webPage + " and the Error is : " + e.getMessage().split("\n")[0].trim());
        }
        return allLinks;
    }
    //------------------------------------------------22--------------------------------------------------------------//

    /**
     * This Method is used to Select any Element in the list Box by sending Index as input.
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName is the Text message that will print in the report,
     * @param index   Select Element based on Index.
     */
    public void selectByIndex(ObjectRepSheet object, int index, String eleName) {
        try {
            Select select = new Select(findElement(object));
            select.selectByIndex(index);
            reporter.log(LogStatus.PASS, eleName + " is selected from the Dropdown with the given Index");
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to select the " + eleName
                    + " from the Dropdown with the given Index and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------23--------------------------------------------------------------//

    /**
     * This Method is used to Select any Element in the list Box by sending expValue as input.
     *
     * @param object   is the element LocatorType and LocatorValue
     * @param eleName  is the Text message that will print in the report,
     * @param expValue Select Element based on Value.
     */
    public void selectByValue(ObjectRepSheet object, String expValue, String eleName) {
        try {
            Select select = new Select(findElement(object));
            select.selectByValue(expValue);
            reporter.log(LogStatus.PASS, eleName + " is selected from the Dropdown with the given Value");
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to select the " + eleName
                    + " from the Dropdown with the given Value and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------24--------------------------------------------------------------//

    /**
     * This Method is used to Select any Element in the list Box by sending Text as input.
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName is the Text message that will print in the report,
     * @param text    Select Element based on Text.
     */
    public void selectByText(ObjectRepSheet object, String text, String eleName) {
        try {
            Select select = new Select(findElement(object));
            select.selectByVisibleText(text);
            reporter.log(LogStatus.PASS, eleName + " is selected from the Dropdown with the given Text");
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to select the " + eleName
                    + " from the Dropdown with the given Text and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------25--------------------------------------------------------------//

    /**
     * This Method is used to Upload a file in to any WebElement or the Popup.
     *
     * @param object is the element LocatorType and LocatorValue
     */
    public void fileUPLoad(ObjectRepSheet object, String filePath) {
        try {
            WebElement ele = findElement(object);
            if (ele != null) {
                ele.click();
                Runtime.getRuntime().exec(filePath);
                reporter.log(LogStatus.PASS, "The fail got uploaded into the Up load path Successfully");
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to upLoad file in to the path and the ERROR is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------26--------------------------------------------------------------//

    /**
     * This Method is used to swap the Elements from one place to another.
     *
     * @param ele1object is the element LocatorType and LocatorValue
     * @param ele2object is the element LocatorType and LocatorValue
     */
    public void dragAndDrop(ObjectRepSheet ele1object, ObjectRepSheet ele2object) {
        try {
            WebElement ele1 = findElement(ele1object);
            WebElement ele2 = findElement(ele2object);
            Actions action = new Actions(driver);
            action.dragAndDrop(ele1, ele2).perform();
            action.build();
            reporter.log(LogStatus.PASS, "The two Elements got Swapped Successfully");
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to swap the Two Elements and the ERROR is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------27--------------------------------------------------------------//

    /**
     * This Method is used to print the text present in all the Matching Elements
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName is the Text message that will print in the report.
     */
    public void printContent(ObjectRepSheet object, String eleName) {
        String text;
        try {
            List<WebElement> ele = findElements(object);
            if (ele != null) {
                int allLinks = ele.size();
                reporter.log(LogStatus.INFO, "Total no of elements in the " + eleName + " is : " + allLinks);
                reporter.log(LogStatus.INFO, eleName + " names are as follows: ");
                for (int i = 0; i < allLinks; i++) {
                    WebElement link = ele.get(i);
                    text = link.getText();
                    reporter.log(LogStatus.INFO, i + 1 + ": " + text);
                }
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "No " + eleName + "'s are present in side the Element and the ERROR is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------28--------------------------------------------------------------//

    /**
     * This Method is used to Highlight any WebElement On Demand.
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName is the Text message that will print in the report.
     */
    public void highLightElement(ObjectRepSheet object, String eleName) {
        try {
            WebElement ele = findElement(object);
            jse.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
            String screenshotPath = getScreenShot();
            reporter.log(LogStatus.PASS, eleName + " : is Highlighted" + reporter.addScreenCapture(screenshotPath));
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to Highlight " + eleName + " and the ERROR is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------29--------------------------------------------------------------//

    /**
     * This Method is used is used to Count the total no of frames present inside the Web page.
     *
     * @param object is the element LocatorType and LocatorValue
     */
    public void countFrames(ObjectRepSheet object, String webPage) {
        try {
            List<WebElement> frames = findElements(object);
            if (frames != null) {
                int frameSize = frames.size();
                if (frameSize > 0) {
                    reporter.log(LogStatus.PASS, "Total no of frames in the " + webPage + " are : " + frameSize);
                } else {
                    reporter.log(LogStatus.PASS, "No frames present in the " + webPage);
                }
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to get the Frame Count in the "
                    + webPage + " and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------30--------------------------------------------------------------//

    /**
     * This Method is used is used to Count the total no of frames present inside the Web page and
     * Select the desired Frame by using the Index of the frame.
     *
     * @param frameName Element name that is print in the Report and to select the Frame.
     * @param object    is the element LocatorType and LocatorValue
     */
    public void frameByIndex(ObjectRepSheet object, int index, String frameName) {
        try {
            List<WebElement> ele = findElements(object);
            if (ele != null) {
                reporter.log(LogStatus.PASS, "Total no of frames in the Web Page are : " + ele.size());
                driver.switchTo().frame(index);
                reporter.log(LogStatus.PASS, frameName + " Frame is Selected");
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to select the " + frameName
                    + " Frame with the given Index and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------31--------------------------------------------------------------//

    /**
     * This Method is used is used to Count the total no of frames present inside the Web page and
     * Select the desired Frame by using the Value present inside the frame.
     *
     * @param name   Element name that is print in the Report and to select the Frame.
     * @param object is the element LocatorType and LocatorValue
     */
    public void frameByName(ObjectRepSheet object, String name) {
        try {
            List<WebElement> ele = findElements(object);
            if (ele != null) {
                reporter.log(LogStatus.PASS, "Total no of frames in the Web Page are : " + ele.size());
                driver.switchTo().frame(name);
                reporter.log(LogStatus.PASS, name + " Frame is Selected");
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to select the " + name
                    + " Frame with the given Name and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------32--------------------------------------------------------------//

    /**
     * This Method is used is used to Count the total no of frames present inside the Web page and
     * Select the desired Frame by using the text present inside the frame.
     *
     * @param frameName Element name that is print in the Report.
     * @param object    is the element LocatorType and LocatorValue
     */
    public void frameByID(ObjectRepSheet object, String id, String frameName) {
        try {
            List<WebElement> ele = findElements(object);
            if (ele != null) {
                reporter.log(LogStatus.PASS, "Total no of frames in the Web Page are : " + ele.size());
                driver.switchTo().frame(id);
                reporter.log(LogStatus.PASS, frameName + " Frame is Selected");
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to select the " + frameName
                    + " Frame with the given ID and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------33--------------------------------------------------------------//

    /**
     * This Method will check weather the Web Element is Enabled or Disabled
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName Element name that is print in the Report.
     */
    public void isEnabled(ObjectRepSheet object, String eleName) {
        try {
            WebElement ele = findElement(object);
            if (ele.isEnabled()) {
                reporter.log(LogStatus.PASS, eleName + ": Element is Enabled");
            } else {
                reporter.log(LogStatus.FAIL, eleName + ": Element is Disabled");
            }
        } catch (Exception e) {
            reporter.log(LogStatus.ERROR, "Unable to find the " + eleName
                    + " is enabled or disabled and the ERROR is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------34--------------------------------------------------------------//

    /**
     * This Method is used to establish Connection with the DataBase and Execute the Query
     * and gives out thr result of the Query in the results.
     *
     * @param dbUrl    url of the DataBase in which the Query has to be executed,
     * @param username DateBase login UserName,
     * @param password DataBase Login Password,
     * @param sqlQuery The Executable Query to Execute.
     * @throws ClassNotFoundException handling ClassNotFoundException
     */
    public void dbSQLQuery(String dbUrl, String username, String password, String sqlQuery) throws ClassNotFoundException {
        Class.forName(AutomationConstants.SQL_DRIVER);
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery)) {
            reporter.log(LogStatus.INFO, "Sql query " + sqlQuery + " output is : " + rs);
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Unable to execute the Query and the ERROR is :" + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------35--------------------------------------------------------------//

    /**
     * This Method is used to establish Connection with the DataBase and Execute the Query
     * and gives out thr result of the Query in the results.
     *
     * @param dbUrl    url of the DataBase in which the Query has to be executed,
     * @param username DateBase login UserName,
     * @param password DataBase Login Password,
     * @param query    The Executable Query to Execute.
     * @throws ClassNotFoundException handling ClassNotFoundException
     */
    public void dbOracleQuery(String dbUrl, String username, String password, String query) throws ClassNotFoundException {
        Class.forName(AutomationConstants.ORACLE_DRIVER);
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            reporter.log(LogStatus.INFO, "Oracle query " + query + " output is : " + rs);
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Unable to execute the Query and the ERROR is :" + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------36--------------------------------------------------------------//

    /**
     * This method is used Click on the Element by Scrolling the Web Page.
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName Element name that is print in the Report.
     */
    public void clickElementByScrollPage(ObjectRepSheet object, String eleName) {
        try {
            WebElement ele = findElement(object);
            jse.executeScript("arguments[0].scrollIntoView(true);", ele);
            reporter.log(LogStatus.INFO, eleName.trim() + " is available and is scrolled into view.");
            ele.click();
        } catch (Exception e) {
            reporter.log(LogStatus.ERROR, "Unable to perform Click operation on " + eleName.trim()
                    + "ERROR :" + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------37--------------------------------------------------------------//

    /**
     * This Method is used to Object of the Child Element.
     *
     * @param parentObject is the parentElement LocatorType and LocatorValue
     * @param childObject  is the childElement LocatorType and LocatorValue
     * @return getEle - Element of the Child.
     */
    public Object getChild(ObjectRepSheet parentObject, ObjectRepSheet childObject) {
        WebElement getEle = null;
        try {
            List<WebElement> parEle = findElements(parentObject);
            if (parEle != null) {
                int eleSize = parEle.size();
                for (int i = 0; i <= eleSize; i++) {
                    getEle = parEle.get(i);
                    String text = getEle.getText();
                    WebElement chiEle = findElement(childObject);
                    if (getEle.equals(chiEle)) {
                        break;
                    }
                    reporter.log(LogStatus.PASS, "Element " + text.trim() + " : is Selected");
                }
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Unable to Match the actualElement with the expectedElement" +
                    " and the ERROR :" + getErrorMessage(e));
            Assert.fail();
        }
        return getEle;
    }
    //------------------------------------------------38--------------------------------------------------------------//

    /**
     * This Method is used to handle Alerts of different Types.
     *
     * @param action can perform actions like "Switch to Alert","Accept the Alert","Dismiss the Alert",
     *               "SendData to Alert" and perform the action based on the action given
     * @param data   the Data that has to be entered in to the text field.
     */
    public void handleAlert(String action, String data) {
        try {
            switch (action) {
                case "switch":
                    driver.switchTo().alert();
                    reporter.log(LogStatus.PASS, "Switched to Alert");
                    break;
                case "accept":
                    driver.switchTo().alert().accept();
                    reporter.log(LogStatus.PASS, "Alert is Accepted");
                    break;
                case "dismiss":
                    driver.switchTo().alert().dismiss();
                    reporter.log(LogStatus.PASS, "Alert is Dismissed");
                    break;
                case "sendKeys":
                    driver.switchTo().alert().sendKeys(data);
                    reporter.log(LogStatus.PASS, data + " is Entered in the Alert");
                    break;
                default:
            }
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, "Failed to perform " + action + " on the Alert and the ERROR is : "
                    + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------39--------------------------------------------------------------//

    /**
     * This Method is used to perform composite Action.
     *
     * @param Object  is the element LocatorType and LocatorValue
     * @param eleName Element name that is print in the Report.
     *//*
    public void moveToElement(String by, String value, String eleName) {
        List<String> byList = new ArrayList<String>(Arrays.asList(by.split("\\|")));
        List<String> valueList = new ArrayList<String>(Arrays.asList(value.split("\\|")));
        if (byList.size() == valueList.size()) {
            for (int i = 0; i <= byList.size() - 1; i++) {
                String byData = byList.get(i);
                String valueData = valueList.get(i);
                try {
                    WebElement ele = findElement(byData, valueData);
                    action.moveToElement(ele).build().perform();
                    if (i == byList.size() - 1) {
                        action.moveToElement(ele).click().perform();
                        reporter.log(LogStatus.PASS, "moved to : " + eleName + " and clicked");
                        break;
                    }
                    sleep(2);
                } catch (Exception e) {
                    reporter.log(LogStatus.FAIL, "Failed to move to : " + eleName + " and the Error is "
                            + getErrorMessage(e));
                }
            }
        } else {
            reporter.log(LogStatus.ERROR, "The given locator type and locator values length are not matching" +
                    " please check the given parameters");
        }
    }*/
    //------------------------------------------------40--------------------------------------------------------------//

    /**
     * This method is used to wait for the Web Element to disappear
     *
     * @param object  is the element LocatorType and LocatorValue
     * @param eleName Element name that is print in the Report.
     */
    public void waitForElementToDisappear(ObjectRepSheet object, String eleName) {
        try {
            new WebDriverWait(driver, ETO).until(ExpectedConditions.invisibilityOfAllElements(findElements(object)));
            reporter.log(LogStatus.PASS, eleName + " Is disappeared");
        } catch (Exception e) {
            reporter.log(LogStatus.FAIL, eleName + " is not Disappeared and the Error is : " + getErrorMessage(e));
            Assert.fail();
        }
    }
    //------------------------------------------------41--------------------------------------------------------------//

    /**
     * This method is used to add Screen Shot to the report if required
     */
    static void addScreenShot() {
        String screenshotPath = getScreenShot();
        reporter.log(LogStatus.PASS, reporter.addBase64ScreenShot(screenshotPath));
    }
    //------------------------------------------------42--------------------------------------------------------------//

    /**
     * This method is used to compare the Actual Text with the Expected Text
     *
     * @param actualText   is the text from the Element
     * @param expectedText is the text to be compared with the Actual Text
     */
    public void compareText(String actualText, String expectedText) {
        try {
            Assert.assertEquals(actualText, expectedText);
        } catch (Exception e) {
            e.getMessage();
            reporter.log(LogStatus.FAIL, "Actual Text : " + actualText + " is not Matching with the Expected Text : " + expectedText);
        }
    }
    //------------------------------------------------43--------------------------------------------------------------//

    /**
     * This method is used to Download the file from the web source to the Desired Location in the Local
     *
     * @param object is the element LocatorType and LocatorValue
     */
    public void fileDownload(ObjectRepSheet object) {
        try {
            WebElement downloadButton = findElement(object);
            String sourceLocation = downloadButton.getAttribute("href");
            String wgetCommand = System.getProperty("user.dir");
            Reporter.log(wgetCommand, true);
            Process exec = Runtime.getRuntime().exec(wgetCommand);
            int exitVal = exec.waitFor();
            reporter.log(LogStatus.INFO, "Exit value: " + exitVal);
        } catch (Exception e) {
            e.getMessage();
            reporter.log(LogStatus.FAIL, "Failed to Download file and the Error is :" + getErrorMessage(e));
        }
    }
    //------------------------------------------------44--------------------------------------------------------------//

    /**
     * This Method is used to refresh the Current Web Page.
     */
    public void refresh() {
        driver.navigate().refresh();
    }
    //------------------------------------------------45--------------------------------------------------------------//

    /**
     * This Method is used to Perform Mouse hover Action using Actions Class
     *
     * @param object is the element LocatorType and LocatorValue.
     */
    public void mouseHover(ObjectRepSheet object) {
        try {
            WebElement element = findElement(object);
            Actions act = new Actions(driver);
            act.moveToElement(element).build().perform();
        } catch (Exception e) {
            e.getMessage();
            reporter.log(LogStatus.FAIL, "Failed to perform Mouse Hovering and the Error is : " + getErrorMessage(e));
        }
    }
    //------------------------------------------------46--------------------------------------------------------------//
    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Screenshot fullPageScreenCapture(String fileName) throws IOException, InterruptedException {
        // Here 100 is a scrollTimeOut in milliseconds, For every 100 ms the
        // screen will be scrolled and captured
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(100, 0, 0, 2))
                .takeScreenshot(driver);
        // To save the screenshot in desired location
        ImageIO.write(screenshot.getImage(), "PNG",
                new File("Reports/"+fileName));
        return screenshot;
    }
    //------------------------------------------------47--------------------------------------------------------------//

    /**
     *
     * @param myScreenshot
     * @param anotherScreenshot
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public BufferedImage imageCompare(Screenshot myScreenshot, Screenshot anotherScreenshot) throws IOException, InterruptedException {
        ImageDiff diff = new ImageDiffer().makeDiff( myScreenshot, anotherScreenshot );
        BufferedImage diffImage = diff.getMarkedImage();
        getScreenShot1(  diffImage );
        return diffImage;
    }
    //------------------------------------------------48--------------------------------------------------------------//

    /**
     *
     * @param imageValue
     * @throws IOException
     */
    private static void getScreenShot1(BufferedImage imageValue) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageValue, "png", baos);
        String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
        String screenshotPath =  "data:image/png;base64," + data;
        reporter.log(LogStatus.PASS, reporter.addBase64ScreenShot(screenshotPath));
    }
    //------------------------------------------------49--------------------------------------------------------------//

}

