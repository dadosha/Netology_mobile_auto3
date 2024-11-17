import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class SampleTest {

    enum Platform {Android, IOS}

    Platform platform = Platform.Android;
    private AppiumDriver driver;
    private MobileObjects mobileObjects;
    private String inputText = "Test text";

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    public void setUp() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        if (platform == Platform.Android) {
            desiredCapabilities.setCapability("platformName", "Android");
            desiredCapabilities.setCapability("appium:deviceName", "Samsung");
            desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
            desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");
            desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
            driver = new AndroidDriver(getUrl(), desiredCapabilities);
        } else if (platform == Platform.IOS) {
            desiredCapabilities.setCapability("platformName", "IOS");
//            desiredCapabilities.setCapability("appium:deviceName", "iPhone 11");
//            desiredCapabilities.setCapability("appium:bundleId", "com.Shubham-iosdev.Calculator-UI");
//            desiredCapabilities.setCapability("appium:automationName", "XCUITest");
            driver = new IOSDriver(getUrl(), desiredCapabilities);
        }

        mobileObjects = new MobileObjects(driver);
    }

    @Test
    public void testChangeText() {
        mobileObjects.input.isDisplayed();
        mobileObjects.input.sendKeys(inputText);

        mobileObjects.buttonChange.isDisplayed();
        mobileObjects.buttonChange.click();

        mobileObjects.textToChanged.isDisplayed();
        Assertions.assertEquals(inputText, mobileObjects.textToChanged.getText());
    }

    @Test
    public void testEmptyText() {
        mobileObjects.textToChanged.isDisplayed();
        String defaultText = mobileObjects.textToChanged.getText();

        mobileObjects.input.isDisplayed();
        mobileObjects.input.sendKeys("");

        mobileObjects.buttonChange.isDisplayed();
        mobileObjects.buttonChange.click();

        mobileObjects.textToChanged.isDisplayed();
        Assertions.assertEquals(defaultText, mobileObjects.textToChanged.getText());
    }

    @Test
    public void testSpaceText() {
        mobileObjects.textToChanged.isDisplayed();
        String defaultText = mobileObjects.textToChanged.getText();

        mobileObjects.input.isDisplayed();
        mobileObjects.input.sendKeys("    ");

        mobileObjects.buttonChange.isDisplayed();
        mobileObjects.buttonChange.click();

        mobileObjects.textToChanged.isDisplayed();
        Assertions.assertEquals(defaultText, mobileObjects.textToChanged.getText());
    }

    @Test
    public void testNewActivity() {
        mobileObjects.input.isDisplayed();
        mobileObjects.input.sendKeys(inputText);

        mobileObjects.buttonActivity.isDisplayed();
        mobileObjects.buttonActivity.click();

        mobileObjects.textActivity.isDisplayed();
        Assertions.assertEquals(inputText, mobileObjects.textActivity.getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
