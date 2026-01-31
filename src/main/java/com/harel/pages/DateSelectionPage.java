package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateSelectionPage extends BasePage {
    private By totalDaysText = By.xpath("//div[contains(text(), 'סה\"כ')]");
    private By nextBtn = By.xpath("//span[text()='הלאה לפרטי הנוסעים']/parent::button");
    private By nextMonthBtn = By.cssSelector("button[aria-label='לעבור לחודש הבא']");

    public DateSelectionPage(WebDriver driver) {
        super(driver);
    }

    public DateSelectionPage selectDates(LocalDate departureDate, LocalDate returnDate) {
        selectDate(departureDate);
        selectDate(returnDate);
        return this;
    }

    private void selectDate(LocalDate date) {
        String dateTitle = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        By dateLocator = By.xpath("//button[starts-with(@title, '" + dateTitle + "')]");

        System.out.println("Attempting to select date: " + dateTitle);

        int maxMonthClicks = 12;
        while (maxMonthClicks > 0) {
            try {
                // Wait for any animations to finish
                Thread.sleep(800);

                WebElement dateElement = findVisibleElement(dateLocator);
                if (dateElement != null) {
                    System.out.println("Date " + dateTitle + " found. Clicking...");
                    try {
                        dateElement.click();
                    } catch (Exception e) {
                        System.out.println("Regular click failed, trying JS click...");
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();",
                                dateElement);
                    }
                    Thread.sleep(500);
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error during date selection: " + e.getMessage());
            }

            // If date not found, click next month
            try {
                System.out.println("Date not found in visible months, clicking next month...");
                WebElement nextMonth = waitForElementToBeClickable(nextMonthBtn);
                try {
                    nextMonth.click();
                } catch (Exception e) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", nextMonth);
                }
            } catch (Exception e) {
                System.out.println("Next month button not clickable/found.");
                // If we can't click next month, check one last time if the date appeared
                if (findVisibleElement(dateLocator) == null)
                    break;
            }
            maxMonthClicks--;
        }

        throw new RuntimeException("Failed to select date: " + dateTitle);
    }

    private WebElement findVisibleElement(By locator) {
        for (WebElement element : driver.findElements(locator)) {
            if (element.isDisplayed())
                return element;
        }
        return null;
    }

    public String getTotalDays() {
        return readText(totalDaysText);
    }

    public PassengerDetailsPage clickNext() {
        click(nextBtn);
        return new PassengerDetailsPage(driver);
    }
}
