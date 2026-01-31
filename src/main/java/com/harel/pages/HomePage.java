package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By purchaseFirstTimeBtn = By.cssSelector("button[class*='MuiButton-containedPrimary']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public ContinentSelectionPage clickPurchaseForFirstTime() {
        click(purchaseFirstTimeBtn);
        return new ContinentSelectionPage(driver);
    }
}
