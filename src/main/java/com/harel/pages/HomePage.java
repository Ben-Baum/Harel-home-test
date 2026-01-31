package com.harel.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By purchaseFirstTimeBtn = By.cssSelector("button[class*='MuiButton-containedPrimary']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Harel Travel Insurance portal")
    public void navigateTo(String url) {
        driver.get(url);
    }

    @Step("Click on 'Purchase for the first time' button")
    public ContinentSelectionPage clickPurchaseForFirstTime() {
        click(purchaseFirstTimeBtn);
        return new ContinentSelectionPage(driver);
    }
}
