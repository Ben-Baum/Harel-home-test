package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class ContinentSelectionPage extends BasePage {
    private By europeRadio = By.id("destination-3");
    private By nextBtn = By.xpath("//span[text()='הלאה לבחירת תאריכי הנסיעה']/parent::button");

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
