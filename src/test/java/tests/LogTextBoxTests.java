package tests;

import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.TextPage;
import utilities.DriverSetup;

public class LogTextBoxTests extends DriverSetup {
    TextPage textPage= new TextPage();

    @BeforeMethod
    public void takesScreenshotBeforeTest(){
        textPage.addScreenshot("LogTextBoxTests");
    }

    @Test(priority = 0)
    @Description("This test verifies LogTextBox")
    public void testsLogTextBox() throws InterruptedException {
        textPage.navigateToLogTextBox();
        textPage.clickOnElement(textPage.ADD);


    }

    @AfterMethod
    public void takesScreenshotAfterTest(){
        textPage.addScreenshot("LogTextBoxTests");
    }
}
