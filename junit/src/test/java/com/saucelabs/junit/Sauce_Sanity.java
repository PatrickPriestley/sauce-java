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
        browsers.add(new String[]{"MAC", "5", "safari"});
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
        capabilities.setCapability("name", "Sanity :"
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
    public void createactivitybadge() {
        /**
         * Create a badge based on an activity
         */

        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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

    @Test
    public void ssurvey() {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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

        //Add survey challenge

        driver.findElement(By.linkText("Add a challenge")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Start With Blank Challenge")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Survey");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#s2id_challenge_type_id0 > a.select2-choice > span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys("Survey");
        driver.findElement(By.className("select2-match")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("headline")).click();
        driver.findElement(By.name("headline")).clear();
        driver.findElement(By.name("headline")).sendKeys("Survey");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("description")).click();
        driver.findElement(By.name("description")).clear();
        driver.findElement(By.name("description")).sendKeys("What a wonderful survey! Complete this challenge.");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("label.input.checkbox")).click();
        //driver.findElement(By.xpath("//div[@id='challenge']/section/fieldset[2]/label[2]")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#add-stage > img.icon")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.xpath("//img[@alt='Questions']")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Preview")).click();
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("button.close")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("subject")).click();
        driver.findElement(By.name("subject")).clear();
        driver.findElement(By.name("subject")).sendKeys("Answer this great question!");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("ui-id-2")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("NOT PUBLISHED")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Test
    public void share_link() {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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
    public void Blog_Post() {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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

        //Create Blog Post challenge

        driver.findElement(By.linkText("Add a challenge")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Start With Blank Challenge")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Comment on a Blog Post");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#s2id_challenge_type_id0 > a.select2-choice > span")).click();
        driver.findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys("Testimonial");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.className("select2-match")).click();
        driver.findElement(By.name("headline")).click();
        driver.findElement(By.name("headline")).clear();
        driver.findElement(By.name("headline")).sendKeys("Comment on a Blog Post");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("description")).click();
        driver.findElement(By.name("description")).clear();
        driver.findElement(By.name("description")).sendKeys("Comment on a Blog Post");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("featured1")).click();
        driver.findElement(By.id("allow_multiple_response2")).click();
        driver.findElement(By.cssSelector("img.icon")).click();
        driver.findElement(By.xpath("//img[@alt='Blog_post']")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("params.url")).click();
        driver.findElement(By.name("params.url")).clear();
        driver.findElement(By.name("params.url")).sendKeys("http://www.slashgear.com/jobs-movie-tanks-on-opening-weekend-19294106/");
        driver.findElement(By.className("summarize-url")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("ui-id-2")).click();
        driver.findElement(By.id("visibility_setting_membership_specific")).click();
        driver.findElement(By.id("s2id_autogen1")).click();
        driver.findElement(By.id("s2id_autogen1")).clear();
        driver.findElement(By.id("s2id_autogen1")).sendKeys("Pelican");
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("img[alt=\"Pelican Pete\"]")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        //driver.findElement(By.id("ui-id-1")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("NOT PUBLISHED")).click();
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Test
    public void group_sanity () {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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
    public void multi_stage() {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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

        //Add multistage challenge

        driver.findElement(By.linkText("Add a challenge")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Start With Blank Challenge")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Multi-Stage Challenge");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#s2id_challenge_type_id0 > a.select2-choice > span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys("Social Media");
        driver.findElement(By.className("select2-match")).click();
        driver.findElement(By.name("headline")).click();
        driver.findElement(By.name("headline")).clear();
        driver.findElement(By.name("headline")).sendKeys("Multi-Stage Challenge");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("description")).click();
        driver.findElement(By.name("description")).clear();
        driver.findElement(By.name("description")).sendKeys("Multi-Stage Challenge is amazing. Complete this challenge.");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("featured1")).click();
        driver.findElement(By.id("allow_multiple_response2")).click();
        driver.findElement(By.cssSelector("#add-stage > img.icon")).click();
        driver.findElement(By.xpath("//img[@alt='Questions']")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("public")).click();
        driver.findElement(By.name("subject")).click();
        driver.findElement(By.name("subject")).clear();
        driver.findElement(By.name("subject")).sendKeys("Answer this question!");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Preview")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("button.close")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#s2id_data_type18 > a.select2-choice > span.select2-chosen")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']/ul/li[2]/div")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#add-stage > img.icon")).click();
        driver.findElement(By.xpath("//img[@alt='Corporate_confirmation']")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("ui-id-2")).click();
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("NOT PUBLISHED")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Test
    public void Referral() {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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

        //Search for persistent referral challenge and select

        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("search_name_or_headline_contains")).click();
        driver.findElement(By.id("search_name_or_headline_contains")).clear();
        driver.findElement(By.id("search_name_or_headline_contains")).sendKeys("New!");
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("New! Trackable Referral With Salesforce Integration")).click();

        //Close publish challenge modal if it appears

        boolean close = driver.findElements( By.linkText("x")).size() != 0;

        if (close)
        {
            driver.findElement(By.linkText("x")).click();
            try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
            System.out.println("Publish tutorial dismiss");
        }
        else
        {
            try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
            System.out.println("Publish tutorial not encoutnered");
        }

        //Edit the challenge

        driver.findElement(By.linkText("Edit")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#ui-id-4 > img.icon")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Edit")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("lead_status[01Ji00000025nZjEAI]")).click();
        driver.findElement(By.xpath("(//input[@name='lead_status[01Ji00000025nZkEAI]'])[2]")).click();
        driver.findElement(By.name("lead_status[01Ji00000025nZmEAI]")).click();
        driver.findElement(By.xpath("(//input[@name='lead_status[01Ji00000025nZlEAI]'])[3]")).click();
        driver.findElement(By.id("submit-lead-status-mappings")).click();
        try { Thread.sleep(10000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("NOT PUBLISHED")).click();

            /*
            //Close publish challenge modal if it appears

            boolean commit = driver.findElements( By.name("commit")).size() != 0;

            if (commit)
            {
                driver.findElement(By.name("commit")).click();
                try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
                System.out.println("Published!");
            }
            else
            {
                try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
                System.out.println("Published!");
            }
            try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
            */
    }

    @Test
    public void reward_duffel() {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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
    public void twitter_post() {
        driver.get("http://mar10.influitiveqa.com/users/sign_in");
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

        //Add twitter post challenge

        driver.findElement(By.linkText("Add a challenge")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Start With Blank Challenge")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("The Great Twitter Challenge");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("#s2id_challenge_type_id0 > a.select2-choice > span")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys("Twitter");
        driver.findElement(By.className("select2-match")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("headline")).click();
        driver.findElement(By.name("headline")).clear();
        driver.findElement(By.name("headline")).sendKeys("The Great Twitter Challenge");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("description")).click();
        driver.findElement(By.name("description")).clear();
        driver.findElement(By.name("description")).sendKeys("The Great Twitter Challenge is amazing. Complete this challenge.");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("featured1")).click();
        driver.findElement(By.id("allow_multiple_response2")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("img.icon")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.xpath("//img[@alt='Twitter_post']")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Preview")).click();
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.cssSelector("button.close")).click();
        //driver.findElement(By.xpath("//div[@id='s2id_params.content_type18']/a/span")).click();
        driver.findElement(By.linkText("Choose One")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.xpath("//div[@id='select2-drop']/ul/li[2]/div")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("params.required_content")).click();
        driver.findElement(By.name("params.required_content")).clear();
        driver.findElement(By.name("params.required_content")).sendKeys("#testing123");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("params.default_text")).click();
        driver.findElement(By.name("params.default_text")).clear();
        driver.findElement(By.name("params.default_text")).sendKeys("Tweet #testing123");
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.id("ui-id-2")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("NOT PUBLISHED")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Test
    public void social_signupp() {
        /*
        driver.get("http://mar10.influitiveqa.com/join/Test");
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
        */

        driver.get("http://mar10.influitiveqa.com/join/sanity");
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

        driver.get("http://mar10.influitiveqa.com/join/sanity");
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
    public void NPSChallenge() {

        //Sign into app and access challenge screen

        driver.get("https://advocatetest.influitives.com/users/sign_in");
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

        //Load challenge

        driver.get("https://advocatetest.influitives.com/challenges/29");
        try { Thread.sleep(5000l); } catch (Exception e) { throw new RuntimeException(e); }

        //Select NPS value

        driver.findElement(By.id("activity_responses_attributes_0_body_8")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }

        //Submit response

        driver.findElement(By.name("commit")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }

        //Signout

        driver.findElement(By.id("contact-dropdown")).click();
        try { Thread.sleep(2000l); } catch (Exception e) { throw new RuntimeException(e); }
        driver.findElement(By.linkText("Sign out")).click();
        try { Thread.sleep(3000l); } catch (Exception e) { throw new RuntimeException(e); }
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
