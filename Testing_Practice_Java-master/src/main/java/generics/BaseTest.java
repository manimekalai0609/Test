package main.java.generics;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterSuite;

import java.io.File;
import java.lang.reflect.Method;

public abstract class BaseTest  {

    static  WebDriver driver=null;
    private static ExtentReports report=null;
    static  ExtentTest reporter=null;
    static String objectRepository="";
    static String testData;

    private long ito = (new Double(Utility.getPropertyValue(AutomationConstants.PLATFORM_CONFIG, "ImplicitTimeOut")).intValue());
    private String environment = Utility.getPropertyValue(AutomationConstants.PLATFORM_CONFIG, "ReportsEnvironment");
    private String url = Utility.getPropertyValue(AutomationConstants.PLATFORM_CONFIG, "url");

    @Parameters({"targetDriver"})
    @BeforeSuite(alwaysRun = true)
    public synchronized void configReports(String targetDriver) {
        report = new ExtentReports(System.getProperty("user.dir") + Utility.getPropertyValue(AutomationConstants.AUTOMATION_CONSTANTS,"reportsPath"));
        report.addSystemInfo("Environment", environment)
                .addSystemInfo("Browser", targetDriver)
                .addSystemInfo("URL", url)
                .addSystemInfo("User Name", System.getProperty("user.name"));
        report.loadConfig(new File(System.getProperty("user.dir") + Utility.getPropertyValue(AutomationConstants.AUTOMATION_CONSTANTS,"reportsConfig")));
    }

    @Parameters({"targetDriver"})
    @BeforeTest(alwaysRun = true)
    public synchronized WebDriver openBrowser(String targetDriver) {

        if (targetDriver.equalsIgnoreCase("Chrome")) {
            ChromeDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--ignore-certificate-errors");
            driver = new ChromeDriver(options);
        } else if (targetDriver.equalsIgnoreCase("FireFox")) {
            ChromeDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (targetDriver.equalsIgnoreCase("IE")) {
            ChromeDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        return driver;
    }

    @BeforeClass(alwaysRun = true)
    public synchronized void getRepo() {
        try {
            objectRepository = Utility.getPropertyValue(AutomationConstants.AUTOMATION_CONSTANTS,"ExcelObjectRepo");
            testData = Utility.getPropertyValue(AutomationConstants.AUTOMATION_CONSTANTS,"ExcelTestData");
        } catch (Exception e) {
            Reporter.log("TheFile path specified are not the valid path", true);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void startTest(Method method) {
        String className = method.getName();
        reporter = report.startTest(className);
        CommonMethods.implicitWait(ito);
        CommonMethods.enterURL(url);
    }

    @AfterMethod(alwaysRun = true)
    public void endTest(ITestResult result, Method method) {
        String className = method.getName();
        if (result.getStatus() == ITestResult.SUCCESS) {
            CommonMethods.addScreenShot();
            reporter.log(LogStatus.PASS, className + " : Has Executed Successfully");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            reporter.log(LogStatus.FAIL, className + " : Has Failed to Execute");
        } else if (result.getStatus() == ITestResult.SKIP) {
            reporter.log(LogStatus.SKIP, className + " : Has got Skipped from Execution");
        }
        report.endTest(reporter);
    }

    @AfterTest(alwaysRun = true)
    public void tearDownBrowser() {
        driver.close();
    }

    @AfterSuite(alwaysRun = true)
    public void closeReport() {
        report.flush();
    }
}