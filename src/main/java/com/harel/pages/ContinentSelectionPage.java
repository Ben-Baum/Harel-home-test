package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContinentSelectionPage extends BasePage {
    private By europeRadio = By.id("destination-3");
    private By nextBtn = By.xpath("//span[text()='הלאה לבחירת תאריכי הנסיעה']/parent::button");

    public ContinentSelectionPage(WebDriver driver) {
        super(driver);
    }

    public ContinentSelectionPage selectEurope() {
        click(europeRadio);
        return this;
    }

    public DateSelectionPage clickNext() {
        click(nextBtn);
        return new DateSelectionPage(driver);
    }
}
