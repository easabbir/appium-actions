package pages;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static utilities.DriverSetup.getApp;

public class BasePage {

    public WebElement getElement(By locator) {
        return getApp().findElement(locator);
    }

    public void writeOnElement(By locator, String text) {
        getElement(locator).click();
        getElement(locator).clear();
        getElement(locator).sendKeys(text);
//        getApp().hideKeyboard();
    }

    public void writeOnElementWithRetry(By locator, String text, int retries) {
        for (int i = 0; i < retries; i++) {
            try {
                WebElement element = getApp().findElement(locator);
                element.click();
                element.clear();
                element.sendKeys(text);
                if (element.getText().equals(text)) {
                    break; // Exit loop if text is correctly entered
                }
            } catch (Exception e) {
                if (i == retries - 1) {
                    throw e; // Throw exception if all retries fail
                }
            }
        }
    }

    public Boolean displayStatus(By locator) {
        try {
            return getElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickOnElement(By locator) {
        getElement(locator).click();
    }

    public String getElementText(By locator) {
        return getElement(locator).getText();
    }

    public void swipeUp() {
        // Get screen dimensions
        Dimension size = getApp().manage().window().getSize();
        int screenHeight = getApp().manage().window().getSize().getHeight();
        int screenWidth = getApp().manage().window().getSize().getWidth();

        // Calculate start and end points for the swipe
        int startX = screenWidth / 2;  // Horizontally center
        int startY = (int) (screenHeight * 0.8); // Near the bottom
        int endY = (int) (screenHeight * 0.2);   // Near the top

        // Create a pointer input for touch gestures
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create the swipe sequence
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the swipe action
        getApp().perform(Arrays.asList(swipe));
    }

    public void swipeUpForCalender() {
        // Get screen dimensions
        Dimension size = getApp().manage().window().getSize();
        int screenHeight = getApp().manage().window().getSize().getHeight();
        int screenWidth = getApp().manage().window().getSize().getWidth();

        int startX = screenWidth / 3;  // Horizontally center
        int startY = (int) (screenHeight * 0.8); // Near the bottom
        int endY = (int) (screenHeight * 0.2);   // Near the top

        // Create a pointer input for touch gestures
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create the swipe sequence
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the swipe action
        getApp().perform(Arrays.asList(swipe));
    }

    // Helper method to scroll down
    public void scrollDown() {
        // Get the screen size
        int screenHeight = getApp().manage().window().getSize().height;
        int screenWidth = getApp().manage().window().getSize().width;

        // Define start and end points for the swipe
        int startX = screenWidth / 2;
        int startY = (int) (screenHeight * 0.8); // Start near the bottom
        int endY = (int) (screenHeight * 0.2);  // End near the top

        // Perform the scroll action
        new TouchAction<>(getApp())
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    public void swipeToElement(By elementLocator, int maxSwipes) {
        int swipes = 0;

        while (swipes < maxSwipes) {
            try {
                // Check if the element is present and displayed
                WebElement element = getApp().findElement(elementLocator);
                if (element.isDisplayed()) {
                    System.out.println("Element found!");
                    return;
                }
            } catch (NoSuchElementException e) {
                // Element not found; continue swiping
                swipeUp();
            }

            swipes++;
        }

        throw new RuntimeException("Element not found after " + maxSwipes + " swipes.");
    }

    public void tapOnElement(By elementLocator) {
        WebElement element = getApp().findElement(elementLocator);
        TouchAction action = new TouchAction(getApp());
        action.tap(ElementOption.element(element)).perform();
    }

    public void tapOnElementForModernWay(By elementLocator) {
        WebElement element = getApp().findElement(elementLocator);

        // Create a PointerInput for touch interaction
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create a tap sequence
        Sequence tap = new Sequence(finger, 0);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                element.getLocation().getX() + (element.getSize().getWidth() / 2),
                element.getLocation().getY() + (element.getSize().getHeight() / 2)));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the action
        getApp().perform(Collections.singletonList(tap));
    }

    public void longPress(By elementLocator, int durationInMilliseconds) {
        WebElement element = getApp().findElement(elementLocator);

        // Create a PointerInput for touch interaction (simulating a finger touch)
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create the sequence for the long press
        Sequence longPress = new Sequence(finger, 0);
        longPress.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                element.getLocation().getX() + (element.getSize().getWidth() / 2),
                element.getLocation().getY() + (element.getSize().getHeight() / 2)));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // Instead of a pause, we simulate the duration by extending the "down" action
        longPress.addAction(finger.createPointerMove(Duration.ofMillis(durationInMilliseconds), PointerInput.Origin.viewport(),
                element.getLocation().getX() + (element.getSize().getWidth() / 2),
                element.getLocation().getY() + (element.getSize().getHeight() / 2)));

        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the long press action
        getApp().perform(Collections.singletonList(longPress));
    }

    public void pullToRefresh(By elementLocator) {
        WebElement element = getApp().findElement(elementLocator);

        // Get height
        // Get screen dimensions
        Dimension size = getApp().manage().window().getSize();
        int screenHeight = getApp().manage().window().getSize().getHeight();

        // Get the dimensions of the element (e.g., SwipeRefreshLayout or RecyclerView)
        Dimension dimension = element.getSize();
        Point location = element.getLocation();

        // Define the start and end points for the swipe
        int startX = location.getX() + (dimension.getWidth() / 2);  // Middle of the screen horizontally
        int startY = location.getY() + 10;  // Just below the top of the screen to simulate pulling from top

        // Set the end point for the swipe (a few pixels down)
        int endY = screenHeight;

        // Create a PointerInput for touch interaction
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create the swipe action sequence
        Sequence swipeSequence = new Sequence(finger, 0);
        swipeSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipeSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipeSequence.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));  // Swipe down
        swipeSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the swipe action
        getApp().perform(Collections.singletonList(swipeSequence));

    }

    public void waitForPresenceOfAllElements(By locator, int maximum_timeout) {
        WebDriverWait wait = new WebDriverWait(getApp(), Duration.ofSeconds(maximum_timeout));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void waitForVisibilityOfElement(By locator, int maximum_timeout) {
        WebDriverWait wait = new WebDriverWait(getApp(), Duration.ofSeconds(maximum_timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickableOfElement(By locator, int maximum_timeout) {
        WebDriverWait wait = new WebDriverWait(getApp(), Duration.ofSeconds(maximum_timeout));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void chooseMonthWithScroll(By elementLocator, String monthToSelect) {
        // Maximum number of scroll attempts
        int maxScrollAttempts = 5;
        boolean isMonthFound = false;
        for (int i = 0; i < maxScrollAttempts; i++) {
            try {
                // Try to find the element
                WebElement element = getApp().findElement(elementLocator);
                // If found, click it
                element.click();
                isMonthFound = true;
                break;
            } catch (Exception e) {
                // If not found, perform a scroll
                swipeUpForCalender();
            }
        }
        if (!isMonthFound) {
            throw new RuntimeException("Unable to find the current month: " + monthToSelect);
        }

    }

    public void chooseDropDownValueWithScroll(By elementLocator, int dropdownToSelect) {
        // Maximum number of scroll attempts
        int maxScrollAttempts = 5;
        boolean isDropDownFound = false;
        for (int i = 0; i < maxScrollAttempts; i++) {
            try {
                // Try to find the element
                WebElement element = getApp().findElement(elementLocator);
                // If found, click it
                element.click();
                isDropDownFound = true;
                break;
            } catch (Exception e) {
                // If not found, perform a scroll
                swipeUpForCalender();
            }
        }
        if (!isDropDownFound) {
            throw new RuntimeException("Unable to find the current month: " + dropdownToSelect);
        }

    }

    public static int generateRandomNumber(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        int lowerBound = (int) Math.pow(10, length - 1);
        int upperBound = (int) Math.pow(10, length) - 1;

        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
    }

    public String generateRandomNumberString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        StringBuilder randomNumber = new StringBuilder();
        Random random = new Random();

        // Ensure the first digit is not 0
        randomNumber.append(random.nextInt(9) + 1); // Random number from 1 to 9

        // Append the remaining digits
        for (int i = 1; i < length; i++) {
            randomNumber.append(random.nextInt(10)); // Random number from 0 to 9
        }

        return randomNumber.toString();
    }

    public String generateRandomWord(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        StringBuilder randomWord = new StringBuilder();
        Random random = new Random();

        // Generate random letters for the word
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26)); // Generate a random letter from 'a' to 'z'
            randomWord.append(randomChar);
        }

        return randomWord.toString();
    }

    public void addScreenshot(String name) {
        Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot) getApp()).getScreenshotAs(OutputType.BYTES)));
    }

}