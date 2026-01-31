package com.harel.tests;

import com.harel.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Test class for Harel Travel Insurance purchase flow.
 * Verifies end-to-end scenario from homepage to passenger details.
 */
public class TravelInsuranceTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        // Support for Cloud execution (GitHub Actions)
        if (Boolean.getBoolean("selenium.headless")) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testTravelInsuranceFlow() throws InterruptedException {
        // 1. Initialize Page Objects
        homePage = new HomePage(driver);

        // 2. Navigate and start process
        homePage.navigateTo("https://digital.harel-group.co.il/travel-policy/");
        ContinentSelectionPage continentPage = homePage.clickPurchaseForFirstTime();

        // 3. Select Europe
        continentPage.selectContinent("אירופה");
        DateSelectionPage datePage = continentPage.clickNext();

        // 4. Calculate Dates
        // Departure: 7 days from today
        LocalDate departureDate = LocalDate.now().plusDays(7);
        // Return: 30 days inclusive (Departure + 29 days)
        LocalDate returnDate = departureDate.plusDays(29);

        // 5. Select Dates
        datePage.selectDates(departureDate, returnDate);

        // 6. Verify Total Days (Should be exactly 30)
        String totalDaysText = datePage.getTotalDays();
        Assert.assertNotNull(totalDaysText, "Total days verification failed!");
        Assert.assertTrue(totalDaysText.contains("30"),
                "Expected total days to be 30, but got: " + totalDaysText);

        // 7. Proceed to Passenger Details
        if (driver != null) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            PassengerDetailsPage passengerPage = datePage.clickNext();
            Assert.assertTrue(passengerPage.isPageOpened(), "Passenger Details page did not load!");

        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(5000); // Wait 5 seconds before closing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.quit();
        }
    }
}
