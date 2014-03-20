package com.saucelabs.junit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * The test is annotated with the {@link ConcurrentParameterized} test runner...
 * The test also includes the {@link SauceOnDemandTestWatcher}...
 *
 * @author Ross Rowe
 */
@RunWith(ConcurrentParameterized.class)
public class SampleSauceTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    private String browser;
    private String os;
    private String version;
    /**
     *
     */
    private String sessionId;

    public SampleSauceTest(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }


    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() throws Exception {

        LinkedList browsers = new LinkedList();
        browsers.add(new String[]{"Windows 2003", null, "chrome"});
        browsers.add(new String[]{"Windows 2003", "17", "firefox"});
        browsers.add(new String[]{"MAC", "17", "firefox"});
        browsers.add(new String[]{"MAC", "5", "safari"});
        browsers.add(new String[]{"XP", "8", "internet explorer"});
        browsers.add(new String[]{"Windows 7", "9", "internet explorer"});
        browsers.add(new String[]{"Windows 7", "10", "internet explorer"});

        return browsers;
    }

    private WebDriver driver;

    public
    @Rule
    TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("screen-resolution", "1920x1200");
        capabilities.setCapability("name", "Standard :"
                + testName.getMethodName());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        this.driver = new RemoteWebDriver(
                new URL("http://influitive_dev:b372fec3-0552-4fb6-98bb-a027d82958b9@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();



        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);


    }

    @Test
    public void email_groupcode_signup() {

        driver.get("https://bradvocate.influitives.com/join/sanity");
        driver.findElement(By.id("registration_user_email")).click();
        driver.findElement(By.id("registration_user_email")).clear();
        driver.findElement(By.id("registration_user_email")).sendKeys("advocatebob7+test45@gmail.com");
        driver.findElement(By.id("registration_contact_name")).click();
        driver.findElement(By.id("registration_contact_name")).clear();
        driver.findElement(By.id("registration_contact_name")).sendKeys("Bob Advocate");
        driver.findElement(By.id("registration_user_password")).click();
        driver.findElement(By.id("registration_user_password")).clear();
        driver.findElement(By.id("registration_user_password")).sendKeys("macbook18");
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }

    }
    /*
    @Test
    public void linkedin_groupcode_signup() {

        driver.get("https://app.influitives.com/join/sanity");
        driver.findElement(By.cssSelector("img[alt=\"Linkedin\"]")).click();
        driver.findElement(By.id("session_key-oauthAuthorizeForm")).click();
        driver.findElement(By.id("session_key-oauthAuthorizeForm")).clear();
        driver.findElement(By.id("session_key-oauthAuthorizeForm")).sendKeys("pelican.pete123@gmail.com");
        driver.findElement(By.name("session_password")).click();
        driver.findElement(By.name("session_password")).clear();
        driver.findElement(By.name("session_password")).sendKeys("macbook18");
        driver.findElement(By.name("authorize")).click();
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
    }

    */
    @Test

    public void existing_advocate_signup() {

        //Sign into app and access challenge screen

        driver.get("https://bradvocate.influitives.com/users/sign_in");
        driver.findElement(By.id("user_email")).click();
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys("advocatebob7@gmail.com");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys("macbook18");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("sign-in-button")).click();
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }

    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
