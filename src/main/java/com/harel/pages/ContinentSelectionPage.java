package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class ContinentSelectionPage extends BasePage {
    private By nextBtn = By.id("next-btn");

    public ContinentSelectionPage(WebDriver driver) {
        super(driver);
    }

    @Step("Select continent: {continentName}")
    public void selectContinent(String continentName) {
        WebElement continent = driver.findElement(By.xpath("//div[contains(text(), '" + continentName + "')]"));
        continent.click();
    }

    @Step("Proceed to date selection")
    public DateSelectionPage clickNext() {
        click(nextBtn);
        return new DateSelectionPage(driver);
    }
}
