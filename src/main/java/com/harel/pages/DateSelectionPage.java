package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Page object for the Date Selection step in the wizard.
 * Handles date picking logic including month navigation.
 */
public class DateSelectionPage extends BasePage {
    private By totalDaysText = By.xpath("//div[contains(., 'סה\"כ')]");
    private By nextBtn = By.xpath("//span[text()='הלאה לפרטי הנוסעים']/parent::button");
    private By nextMonthBtn = By.cssSelector("button[aria-label='לעבור לחודש הבא']");
    private By startDateInput = By.id("travel_start_date");
    private By endDateInput = By.id("travel_end_date");

    public DateSelectionPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects the departure and return dates.
     * 
     * @param departureDate Start date of insurance
     * @param returnDate    End date of insurance
     * @return this page for chaining
     */
    public DateSelectionPage selectDates(LocalDate departureDate, LocalDate returnDate) {
        click(startDateInput); // Open picker
        selectDate(departureDate);

        try {
            Thread.sleep(2000); // Wait for potential animations
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        click(endDateInput); // Open picker for return date
        selectDate(returnDate);
        return this;
    }

    private void selectDate(LocalDate date) {
        String dateTitle = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        By dateLocator = By.xpath("//button[contains(@title, '" + dateTitle + "')]");

        // Wait for calendar to be fully visible and stable
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Try up to 12 months ahead
        for (int i = 0; i < 12; i++) {
            List<WebElement> buttons = driver.findElements(dateLocator);

            for (WebElement btn : buttons) {
                if (btn.isDisplayed()) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", btn);

                    try {
                        new WebDriverWait(driver, Duration.ofSeconds(2))
                                .until(ExpectedConditions.elementToBeClickable(btn));
                        btn.click();
                        return;
                    } catch (Exception e) {
                        // Fallback to JS click if standard click is intercepted
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                        return;
                    }
                }
            }

            // Navigate to next month if date not found
            try {
                WebElement nextHeader = driver.findElement(nextMonthBtn);
                // Use JS Scroll & Click directly to bypass rigid clickability checks
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", nextHeader);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextHeader);

                try {
                    Thread.sleep(1500); // Wait for transition
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                // If next button fails and we haven't found the date, verify and break
                if (driver.findElements(dateLocator).isEmpty()) {
                    break;
                }
            }
        }
        throw new RuntimeException("Failed to find and select date: " + dateTitle);
    }

    public String getTotalDays() {
        return readText(totalDaysText);
    }

    public PassengerDetailsPage clickNext() {
        click(nextBtn);
        return new PassengerDetailsPage(driver);
    }
}
