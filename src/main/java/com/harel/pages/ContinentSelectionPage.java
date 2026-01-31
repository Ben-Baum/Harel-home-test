package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class ContinentSelectionPage extends BasePage {
    private By europeRadio = By.xpath("//div[contains(text(), 'אירופה')]");
    private By nextBtn = By.xpath("//button[.//span[contains(text(), 'הלאה')]]");

    public ContinentSelectionPage(WebDriver driver) {
        super(driver);
    }

    @Step("Select continent: {continentName}")
    public ContinentSelectionPage selectContinent(String continentName) {
        if (continentName.equals("אירופה")) {
            click(europeRadio);
        } else {
            WebElement continent = driver.findElement(By.xpath("//div[contains(text(), '" + continentName + "')]"));
            continent.click();
        }
        return this;
    }

    @Step("Proceed to date selection")
    public DateSelectionPage clickNext() {
        waitForElementToBeClickable(nextBtn);
        click(nextBtn);
        return new DateSelectionPage(driver);
    }
}
