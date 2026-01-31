package com.harel.pages;

import org.openqa.selenium.By;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class PassengerDetailsPage extends BasePage {
    private By pageIndicator = By.xpath("//*[contains(., 'נשמח להכיר את הנוסעים')]");

    public PassengerDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verify if Passenger Details page is opened")
    public boolean isPageOpened() {
        try {
            return waitForElementToBeVisible(pageIndicator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
