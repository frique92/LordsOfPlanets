package com.rbc.lordsofplanets.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetsPage {
    private WebDriver driver;

    public final String PAGE_URL = "/planets";

    @FindBy(id = "name")
    private WebElement planetName;

    @FindBy(id = "add_planet")
    private WebElement buttonAddPlanet;

    @FindBy(xpath = "/html/body/div/table/tbody")
    private WebElement bodyList;

    public PlanetsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setPlanetName(String name) {
        planetName.sendKeys(name);
    }

    public void clickOnAddPlanet() {
        buttonAddPlanet.click();
    }

    public List<Map<String, String>>  getListPlanets() {
        List<Map<String, String>> arrLords = new ArrayList<>();
        List<String> columnsTable = new ArrayList<>(List.of("name", "lord"));

        bodyList.findElements(By.tagName("tr")).forEach(webElement -> {
            Map<String, String> lords = new HashMap<>();

            for (int i = 0; i < columnsTable.size(); i++) {
                lords.put(columnsTable.get(i), webElement.findElement(By.xpath("td[" + (i + 1) + "]")).getText());
            }

            arrLords.add(lords);
        });

        return arrLords;
    }

    public void clickOnEditPlanet(int idRow) {
        try {
            bodyList.findElement(By.xpath("tr[" + idRow + "]/td[3]/a[1]")).click();
        } catch (WebDriverException e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click()",
                    bodyList.findElement(By.xpath("tr[" + idRow + "]/td[3]/a[1]")));
        }
    }

    public void clickOnDeletePlanet(int idRow) {
        try {
            bodyList.findElement(By.xpath("tr[" + idRow + "]/td[3]/a[2]")).click();
        } catch (WebDriverException e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click()",
                    bodyList.findElement(By.xpath("tr[" + idRow + "]/td[3]/a[2]")));
        }
    }
}
