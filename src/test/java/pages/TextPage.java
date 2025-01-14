package pages;

import org.openqa.selenium.By;

public class TextPage extends BasePage{
    public final String expectedlinkyfyHeaderText="Text/Linkify";
    public By LOGTEXTBOX = By.xpath("//android.widget.TextView[@content-desc=\"LogTextBox\"]");
    public By ADD = By.xpath("//android.widget.Button[@content-desc=\"Add\"]");
    public By LINKIFY = By.xpath("//android.widget.TextView[@content-desc=\"Linkify\"]");
    public By LINKYFY_HEADER = By.xpath("//android.widget.TextView[@text=\"Text/Linkify\"]");

    HomePage homePage = new HomePage();
    public void navigateToLogTextBox(){
        clickOnElement(homePage.TEXT);
        clickOnElement(LOGTEXTBOX);
    }
    public void navigateToLinkify(){
        clickOnElement(homePage.TEXT);
        clickOnElement(LINKIFY);
    }
}
