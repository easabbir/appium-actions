package tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.TextPage;
import utilities.DriverSetup;

public class LogTextBoxTests extends DriverSetup {
    TextPage textPage= new TextPage();

    @Test(priority = 0)
    @Description("This test verifies LogTextBox")
    public void openAppWithInstallation() throws InterruptedException {
        textPage.navigateToLogTextBox();
        textPage.clickOnElement(textPage.ADD);


    }
}
