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
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple browsers in parallel.
 * <p/>
 * The test is annotated with the {@link ConcurrentParameterized} test runner...
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher}...
 *
 * @author Ross Rowe
 */
@RunWith(ConcurrentParameterized.class)
public class Sauce_Sanity implements SauceOnDemandSessionIdProvider {

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

    public Sauce_Sanity(String os, String version, String browser) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
    }

    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() throws Exception {

        LinkedList browsers = new LinkedList();
        //browsers.add(new String[]{"Windows 2003", null, "chrome"});
        //browsers.add(new String[]{"Windows 2003", "17", "firefox"});
        browsers.add(new String[]{"MAC", "17", "firefox"});
        //browsers.add(new String[]{"MAC", "5", "safari"});
        //browsers.add(new String[]{"XP", "8", "internet explorer"});
        //browsers.add(new String[]{"Windows 7", "9", "internet explorer"});
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
        capabilities.setCapability("name", "Sanity : "
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
    public void share_link_creation() {
        driver.get("http://sanitymar17.influitives.com/users/sign_in");
        driver.findElement(By.id("user_email")).click();
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys("admin@influitive.com");
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys("1nflu1t1v3");
        driver.findElement(By.id("sign-in-button")).click();
        driver.findElement(By.id("Challenges")).click();

        //If tutorial hasn't been completed, dismiss pop-up.. Otherwise continue.

        boolean exists = driver.findElements( By.linkText("No Thanks")).size() != 0;

        if (exists)
        {
            driver.findElement(By.linkText("No Thanks")).click();
            System.out.println("Challenge Tutorial Dismissed");
        }
        else
        {
            try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
            System.out.println("Tutorial not encountered");
        }

        //Add share link challenge

        try { Thread.sleep(4000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Add a challenge")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Start With Blank Challenge")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Share this link");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#s2id_challenge_type_id0 > a.select2-choice > span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys("Social Media");
        driver.findElement(By.className("select2-match")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("headline")).click();
        driver.findElement(By.name("headline")).clear();
        driver.findElement(By.name("headline")).sendKeys("Share this link");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("description")).click();
        driver.findElement(By.name("description")).clear();
        driver.findElement(By.name("description")).sendKeys("Share this link is amazing. Complete this challenge.");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("featured1")).click();
        driver.findElement(By.id("allow_multiple_response2")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("add-stage")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.xpath("//img[@alt='Share_link']")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("params.url")).click();
        driver.findElement(By.name("params.url")).clear();
        driver.findElement(By.name("params.url")).sendKeys("http://www.nfl.com/draft/2012");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("params.default_content")).click();
        driver.findElement(By.name("params.default_content")).clear();
        driver.findElement(By.name("params.default_content")).sendKeys("This is default twitter text!");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("ui-id-2")).click();
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(4000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("NOT PUBLISHED")).click();
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Test
    public void sanity_group_creation () {
        driver.get("http://sanitymar17.influitives.com/users/sign_in");
        driver.findElement(By.id("user_email")).click();
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys("admin@influitive.com");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys("1nflu1t1v3");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("sign-in-button")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("Groups")).click();

        //If tutorial hasn't been completed, dismiss pop-up.. Otherwise continue.

        boolean exists = driver.findElements( By.linkText("No Thanks")).size() != 0;

        if (exists)
        {
            driver.findElement(By.linkText("No Thanks")).click();
            System.out.println("Group Tutorial Dismissed");
        }
        else
        {
            try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
            System.out.println("Tutorial not encountered");
        }

        //Add Test Group

        driver.findElement(By.linkText("Add a group")).click();
        driver.findElement(By.id("group_name")).click();
        driver.findElement(By.id("group_name")).click();
        driver.findElement(By.id("group_name")).clear();
        driver.findElement(By.id("group_name")).sendKeys("Sanity Group");
        driver.findElement(By.id("group_description")).click();
        driver.findElement(By.id("group_description")).clear();
        driver.findElement(By.id("group_description")).sendKeys("This is a group for sane people!");
        driver.findElement(By.id("group_token")).click();
        driver.findElement(By.id("group_token")).clear();
        driver.findElement(By.id("group_token")).sendKeys("Sanity");
        driver.findElement(By.id("link-membership rules")).click();
        driver.findElement(By.id("save-button")).click();
    }

    @Test
    public void reward_duffelbag_creation() {
        driver.get("http://sanitymar17.influitives.com/users/sign_in");
        driver.findElement(By.id("user_email")).click();
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys("admin@influitive.com");
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys("1nflu1t1v3");
        driver.findElement(By.id("sign-in-button")).click();
        driver.findElement(By.id("Rewards")).click();

        //If tutorial hasn't been completed, dismiss pop-up.. Otherwise continue.

        boolean exists = driver.findElements( By.linkText("No Thanks")).size() != 0;

        if (exists)
        {
            driver.findElement(By.linkText("No Thanks")).click();
            System.out.println("Challenge Tutorial Dismissed");
        }
        else
        {
            try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
            System.out.println("Tutorial not encountered");
        }

        driver.findElement(By.linkText("Add a reward")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("link-headline")).click();
        driver.findElement(By.id("reward_name")).click();
        driver.findElement(By.id("reward_name")).click();
        driver.findElement(By.id("reward_name")).clear();
        driver.findElement(By.id("reward_name")).sendKeys("Duffel Bag");
        driver.findElement(By.id("reward_description")).click();
        driver.findElement(By.id("reward_description")).clear();
        driver.findElement(By.id("reward_description")).sendKeys("This is a great Duffel Bag!");
        driver.findElement(By.cssSelector("#s2id_reward_reward_type_id > a.select2-choice")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        //driver.findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys("Swag");
        driver.findElement(By.cssSelector("div.select2-result-label")).click();
        driver.findElement(By.id("link-redeeming")).click();
        driver.findElement(By.id("awardable")).click();
        driver.findElement(By.cssSelector("#s2id_reward_settings_challenge_id > a.select2-choice > span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.xpath("//*[@id=\"select2-drop\"]/div/input")).sendKeys("Follow on Twitter");
        driver.findElement(By.className("select2-match")).click();
        driver.findElement(By.id("save-button")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("NOT PUBLISHED")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
    }

    @Test
    public void social_signup() {

        driver.get("http://sanitymar17.influitives.com/join/Test");
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
        driver.findElement(By.cssSelector("span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Sign out")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }


        driver.get("http://sanitymar17.influitives.com/join/sanity");
        driver.findElement(By.cssSelector("img[alt=\"Linkedin\"]")).click();
        driver.findElement(By.id("session_key-oauthAuthorizeForm")).click();
        driver.findElement(By.id("session_key-oauthAuthorizeForm")).clear();
        driver.findElement(By.id("session_key-oauthAuthorizeForm")).sendKeys("pelican.pete123@gmail.com");
        driver.findElement(By.id("session_password-oauthAuthorizeForm")).click();
        driver.findElement(By.id("session_password-oauthAuthorizeForm")).clear();
        driver.findElement(By.id("session_password-oauthAuthorizeForm")).sendKeys("macbook18");
        driver.findElement(By.name("authorize")).click();
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Sign out")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }

        driver.get("http://sanitymar17.influitives.com/join/sanity");
        driver.findElement(By.cssSelector("img[alt=\"Facebook\"]")).click();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("advocate_john@yahoo.com");
        driver.findElement(By.id("pass")).click();
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("macbook18");
        driver.findElement(By.id("u_0_1")).click();
        driver.findElement(By.name("commit")).click();
        driver.findElement(By.cssSelector("span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Sign out")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Test
    public void create_activity_badge() {
        /**
         * Create a badge based on an activity
         */

        driver.get("http://sanitymar17.influitives.com/users/sign_in");
        driver.findElement(By.id("user_email")).click();
        driver.findElement(By.id("user_email")).clear();
        driver.findElement(By.id("user_email")).sendKeys("admin@influitive.com");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys("1nflu1t1v3");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("sign-in-button")).click();
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/ul[1]/li/a")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Settings")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.xpath("//ul[@id='accordion']/li[2]/h4")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Levels & Badges")).click();

        //If tutorial hasn't been completed, dismiss pop-up.. Otherwise continue.

        boolean exists = driver.findElements( By.linkText("No Thanks")).size() != 0;

        if (exists)
        {
            driver.findElement(By.linkText("No Thanks")).click();
            System.out.println("Challenge Tutorial Dismissed");
        }
        else
        {
            try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
            System.out.println("Tutorial not encountered");
        }

        //Continue creating badges

        driver.findElement(By.id("add_badge")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("badge_name")).click();
        driver.findElement(By.id("badge_name")).clear();
        driver.findElement(By.id("badge_name")).sendKeys("Yet Another Badge");
        driver.findElement(By.id("badge_description")).click();
        driver.findElement(By.id("badge_description")).clear();
        driver.findElement(By.id("badge_description")).sendKeys("This is a yet another badge! It is awarded to advocates who have joined the hub.");
        driver.findElement(By.id("icon_8")).click();
        driver.findElement(By.id("link-rules")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("badge_settings_rule_type_event_logged")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Any Type")).click();
        driver.findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys("Advocate Joined");
        driver.findElement(By.className("select2-match")).click();
        driver.findElement(By.id("badge_settings_event_times")).click();
        driver.findElement(By.id("badge_settings_event_times")).clear();
        driver.findElement(By.id("badge_settings_event_times")).sendKeys("1");
        driver.findElement(By.id("create_badge_button")).click();
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
