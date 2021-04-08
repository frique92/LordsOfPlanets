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

public class LordFilterPage {
    private WebDriver driver;

    @FindBy(xpath = "/html/body/div/table/tbody")
    private WebElement bodyList;

    public LordFilterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<Map<String, String>>  getListLords() {
        List<Map<String, String>> arrLords = new ArrayList<>();
        List<String> columnsTable = new ArrayList<>(List.of("name", "age"));

        bodyList.findElements(By.tagName("tr")).forEach(webElement -> {
            Map<String, String> lords = new HashMap<>();

            for (int i = 0; i < columnsTable.size(); i++) {
                lords.put(columnsTable.get(i), webElement.findElement(By.xpath("td[" + (i + 1) + "]")).getText());
            }

            arrLords.add(lords);
        });

        return arrLords;
    }
}
