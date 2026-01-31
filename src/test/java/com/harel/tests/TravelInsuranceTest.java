package com.harel.tests;

import com.harel.pages.ContinentSelectionPage;
import com.harel.pages.DateSelectionPage;
import com.harel.pages.HomePage;
import com.harel.pages.PassengerDetailsPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TravelInsuranceTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Run in headless mode if needed
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://digital.harel-group.co.il/travel-policy");
        homePage = new HomePage(driver);
    }

    @Test
    public void testTravelInsuranceFlow() {
        // 2. Click purchase for the first time
        ContinentSelectionPage continentPage = homePage.clickPurchaseFirstTime();

        // 3. Select continent (Europe)
        continentPage.selectEurope();

        // 4. Click Next
        DateSelectionPage datePage = continentPage.clickNext();

        // 5. Select dates: 7 days from now, and 30 days from departure
        LocalDate today = LocalDate.now();
        LocalDate departureDate = today.plusDays(7);
        LocalDate returnDate = departureDate.plusDays(30);

        datePage.selectDates(departureDate, returnDate);

        // 6. Verify total days
        long expectedDays = ChronoUnit.DAYS.between(departureDate, returnDate) + 1;
        String totalDaysText = datePage.getTotalDays();
        System.out.println("Total days displayed: " + totalDaysText);
        Assert.assertTrue(totalDaysText.contains(String.valueOf(expectedDays)),
                "Expected total days: " + expectedDays + " but got: " + totalDaysText);

        // 7. Click Next to passenger details
        PassengerDetailsPage detailsPage = datePage.clickNext();

        // 8. Verify passenger details page opened
        Assert.assertTrue(detailsPage.isPageOpened(), "Passenger details page should be opened");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
