package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


public class DriverSetup {
    public static String appName = System.getProperty("APP", "ApiDemos-debug.apk");
    private static AppiumDriverLocalService appiumService;

    File f = new File("src/test/resources");
    public static final ThreadLocal<AndroidDriver> LOCAL_DRIVER = new ThreadLocal<>();

    public static AndroidDriver getApp() {
        return LOCAL_DRIVER.get();
    }

    public static void setApp(AndroidDriver driver) {
        DriverSetup.LOCAL_DRIVER.set(driver);
    }

    // Start Appium server programmatically
    public static void startAppiumServer() {
        if (appiumService == null) {
            appiumService = new AppiumServiceBuilder()
                    .usingPort(4723)                                    // Set Appium server port
                    .withLogFile(new File("appium_server_logs.txt"))    // Set log file
                    .withArgument(() -> "--relaxed-security")           // Allow non-standard capabilities
                    .build();
        }
        appiumService.start();
        System.out.println("Appium server started...");
    }

    // Stop Appium server
    public static void stopAppiumServer() {
        if (appiumService != null && appiumService.isRunning()) {
            appiumService.stop();
            System.out.println("Appium server stopped...");
        }
    }

    public AndroidDriver getDriver(String appName) throws MalformedURLException {
        File apk = new File(f, appName);
        var options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:automationName", "UiAutomator2")
                .amend("appium:uiautomator2ServerInstallationTimeout",60000)
                .amend("appium:MobileCapabilityType.APP",System.getProperty("user.dir")+"/src/test/resources/" + appName)
                //.amend("appium:deviceName", "pixel9")
                //.amend("appium:udid", "emulator-5554")
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true)
                .amend("appium:app", System.getProperty("user.dir")+"/src/test/resources/" + appName);
//                .amend("appium:appPackage", "io.appium.android.apis")
//                .amend("appium:appActivity", "io.appium.android.apis.ApiDemos");


        URL remoteURL = new URL("http://127.0.0.1:4723/");
        AndroidDriver driver = new AndroidDriver(remoteURL, options);
        return driver;

    }

    public static void restartFreshApp() throws MalformedURLException {
        getApp().quit();
        DriverSetup db = new DriverSetup();
        db.startApplication();

    }


    @BeforeClass
    public void startApplication() throws MalformedURLException {
        startAppiumServer(); // Start the Appium server
        AndroidDriver driver = getDriver(appName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        setApp(driver);
    }


    @AfterClass
    public void quitApplication(){
        getApp().quit();
        stopAppiumServer(); // Stop the Appium server
    }

}
