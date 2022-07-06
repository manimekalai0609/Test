package main.java.generics;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listeners implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        Reporter.log(testName + " : has Started Execution",true);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        Reporter.log( testName + " : has Successfully Executed",true);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        Reporter.log( testName + " : has Failed to Execute Because of : " + result.getThrowable(),true);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        Reporter.log( testName + " : has got Skipped from the Execution Because of : " + result.getThrowable(),true);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Do nothing
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        // Do nothing
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        // Do nothing
    }
}
