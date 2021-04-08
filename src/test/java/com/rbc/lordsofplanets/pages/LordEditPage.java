package com.rbc.lordsofplanets.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LordEditPage {
    private WebDriver driver;

    @FindBy(id = "name")
    private WebElement lordName;

    @FindBy(id = "age")
    private WebElement lordAge;

    @FindBy(css = "button[type=\"submit\"]")
    private WebElement buttonEdit;

    public LordEditPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setLordName(String name) {
        lordName.sendKeys(name);
    }

    public void setLordAge(int age) {
        lordAge.clear();
        lordAge.sendKeys(String.valueOf(age));
    }

    public void clickOnEditLord() {
        buttonEdit.click();
    }

}
