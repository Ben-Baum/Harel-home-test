package com.harel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContinentSelectionPage extends BasePage {
    private By europeRadio = By.id("destination-3");
    private By nextBtn = By.xpath("//span[text()='הלאה לבחירת תאריכי הנסיעה']/parent::button");

    public ContinentSelectionPage(WebDriver driver) {
        super(driver);
    }

    public ContinentSelectionPage selectContinent(String continentName) {
        if (continentName.equals("אירופה")) {
            click(europeRadio);
        } else {
            throw new IllegalArgumentException("Continent not supported yet: " + continentName);
        }
        return this;
    }

    public DateSelectionPage clickNext() {
        click(nextBtn);
        return new DateSelectionPage(driver);
    }
}
