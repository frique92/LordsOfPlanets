package com.rbc.lordsofplanets.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PlanetEditPage {
    private WebDriver driver;

    @FindBy(id = "name")
    private WebElement planetName;

    @FindBy(id = "lord")
    private WebElement lord;

    @FindBy(css = "button[type=\"submit\"]")
    private WebElement buttonEdit;

    public PlanetEditPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setPlanetName(String name) {
        planetName.clear();
        planetName.sendKeys(name);
    }

    public void setLord(String lordName) {
        Select selectLord = new Select(lord);
        selectLord.selectByVisibleText(lordName);
    }

    public void clickOnEditPlanet() {
        buttonEdit.click();
    }

}
