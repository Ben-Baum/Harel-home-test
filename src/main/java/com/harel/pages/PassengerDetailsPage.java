package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PassengerDetailsPage extends BasePage {
    private By pageIndicator = By.xpath("//div[contains(text(), 'פרטי הנוסע הראשון')]");

    public PassengerDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        try {
            return waitForElementToBeVisible(pageIndicator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
