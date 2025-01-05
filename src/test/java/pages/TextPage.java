package pages;

import org.openqa.selenium.By;

public class TextPage extends BasePage{
    public By LOGTEXTBOX = By.xpath("//android.widget.TextView[@content-desc=\"LogTextBox\"]");
    public By ADD = By.xpath("//android.widget.Button[@content-desc=\"Add\"]");

    HomePage homePage = new HomePage();
    public void navigateToLogTextBox(){
        clickOnElement(homePage.TEXT);
        clickOnElement(LOGTEXTBOX);
    }
}
