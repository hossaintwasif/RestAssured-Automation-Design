package api.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtendReportManager implements ITestListener {

    public ExtentSparkReporter sparkReports;
    public ExtentReports extent;
    public ExtentTest test;
    
    String repname;
    
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repname = "Test-Report-" + timeStamp + ".html";
        
//        sparkReports = new ExtentSparkReporter("./reports/" + repname);
        String reportPath = System.getProperty("user.dir") + "/reports/" + repname;
        sparkReports = new ExtentSparkReporter(reportPath);
        sparkReports.config().setDocumentTitle("RestAssuredAutomationProject");
        sparkReports.config().setReportName("Pet Store User API");
        sparkReports.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReports);
        extent.setSystemInfo("Application", "Pet Store User API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("user", "Twasif");
    }
    
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
    }
    
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "Test Failed");
        test.fail(result.getThrowable().getMessage());
        
       /* String screenshotPath = ".\\screenshots\\" + result.getName() + ".png";
		// Assuming screenshot capture method is implemented elsewhere
		test.addScreenCaptureFromPath(screenshotPath);*/
    }
    
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test Skipped");
        test.skip(result.getThrowable().getMessage());
    }
    
    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
