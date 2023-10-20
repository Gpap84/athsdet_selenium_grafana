package com.prometheus.selenium;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ PrometheusListeners.class })
public class NavigateToURLTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        FirefoxOptions chromeOptions = new FirefoxOptions();
        this.driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
    }

    @Test
    public void shouldOpenUrl() {
        this.driver.get("https://the-internet.herokuapp.com/");
        Assert.assertEquals("Welcome to the-internet", this.driver.findElement(By.tagName("h1")).getText(),
                "Asserting header of the page");
    }

    @AfterClass
    public void tearDown() {
        this.driver.close();

    }

}
