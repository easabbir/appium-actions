package tests;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.TextPage;
import utilities.DriverSetup;

public class LinkyfyTests extends DriverSetup {
    TextPage textPage = new TextPage();

    @BeforeMethod
    public void takesScreenshotBeforeTests(){
        textPage.addScreenshot("Linkyfy Tests Before");
    }


    @Test
    @Description("This test verifies Linkyfy")
    public void linkifyTests(){
        textPage.navigateToLinkify();
        Assert.assertEquals(textPage.expectedlinkyfyHeaderText, textPage.getElementText(textPage.LINKYFY_HEADER));


    }



    @AfterMethod
    public void takesScreenshotAfterTests(){
        textPage.addScreenshot("Linkyfy Tests After");
    }
}
