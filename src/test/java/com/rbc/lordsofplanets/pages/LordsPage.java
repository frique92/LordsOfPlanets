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

public class LordsPage {
    private WebDriver driver;

    public final String PAGE_URL = "/lords";

    @FindBy(id = "name")
    private WebElement lordName;

    @FindBy(id = "age")
    private WebElement lordAge;

    @FindBy(id = "add_lord")
    private WebElement buttonAddLord;

    @FindBy(xpath = "/html/body/div/table/tbody")
    private WebElement bodyList;

    public LordsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setLordData(String name, int age) {
        lordName.sendKeys(name);
        lordAge.clear();
        lordAge.sendKeys(String.valueOf(age));
    }

    public void clickOnAddLord() {
        buttonAddLord.click();
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

    public void clickOnEditLord(int idRow) {
        try {
            bodyList.findElement(By.xpath("tr[" + idRow + "]/td[3]/a[1]")).click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

    }

    public void clickOnDeleteLord(int idRow) {
        try {
            bodyList.findElement(By.xpath("tr[" + idRow + "]/td[3]/a[2]")).click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

    }
}
