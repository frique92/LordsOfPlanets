package com.rbc.lordsofplanets.controllers;

import com.rbc.lordsofplanets.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumControllerTest {

    private WebDriver driver;

    @LocalServerPort
    protected int serverPort;

    private String getTextActiveLinkNavbar(WebDriver driver) {
        String textActiveLink;
        try {
            textActiveLink = driver.findElement(By.xpath("//a[contains(@class, 'nav-link active')]"))
                    .getAttribute("textContent");
        } catch (NoSuchElementException e) {
            textActiveLink = "";
        }

        return textActiveLink;
    }

    @BeforeAll
    public void initDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public void closeDriver() {
        driver.close();
        driver.quit();
    }

    @Test
    public void openMainPage() {
        String mainURL = "http://localhost:" + serverPort;

        driver.get(mainURL);
        assertEquals("", getTextActiveLinkNavbar(driver));
    }

    @Test
    public void openLordsPageAddNewLordAndDeleteHim() {
        String mainURL = "http://localhost:" + serverPort;

        LordsPage lordsPage = new LordsPage(driver);

        driver.get(mainURL + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBeforeAdding = lordsPage.getListLords().size();

        lordsPage.setLordData("Dart Vader", 30);
        lordsPage.clickOnAddLord();

        assertEquals(countLordsBeforeAdding + 1, lordsPage.getListLords().size());

        lordsPage.clickOnDeleteLord(countLordsBeforeAdding + 1);

        assertEquals(countLordsBeforeAdding, lordsPage.getListLords().size());

    }

    @Test
    void openLordsPageAddNewLordAndEditHim() {
        String mainURL = "http://localhost:" + serverPort;

        LordsPage lordsPage = new LordsPage(driver);
        LordEditPage lordEditPage = new LordEditPage(driver);

        String lordName = "Dart Vader";
        String newLordName = "John";

        driver.get(mainURL + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBeforeAdding = lordsPage.getListLords().size();

        lordsPage.setLordData(lordName, 30);
        lordsPage.clickOnAddLord();

        assertEquals(countLordsBeforeAdding + 1, lordsPage.getListLords().size());

        lordsPage.clickOnEditLord(countLordsBeforeAdding + 1);
        assertEquals("Edit lord", driver.getTitle());

        lordEditPage.setLordName(newLordName);
        lordEditPage.clickOnEditLord();

        assertEquals("Lords", getTextActiveLinkNavbar(driver));
        assertEquals(newLordName, lordsPage.getListLords()
                .get(countLordsBeforeAdding).get("name"));

    }

    @Test
    void openLazyLordsPage() {
        String mainURL = "http://localhost:" + serverPort;

        LordsPage lordsPage = new LordsPage(driver);
        LordFilterPage lordFilterPage = new LordFilterPage(driver);

        //add lord
        driver.get(mainURL + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBeforeAdding = lordsPage.getListLords().size();

        lordsPage.setLordData("testLord-1", 30);
        lordsPage.clickOnAddLord();

        assertEquals(countLordsBeforeAdding + 1, lordsPage.getListLords().size());

        driver.get(mainURL + "/lords/lazy");
        assertEquals("Lazy lords", getTextActiveLinkNavbar(driver));

        assertTrue(lordFilterPage.getListLords().size() > 0);

    }

    @Test
    void openTop10YoungestLordsPage() {
        String mainURL = "http://localhost:" + serverPort;

        LordsPage lordsPage = new LordsPage(driver);
        LordFilterPage lordFilterPage = new LordFilterPage(driver);

        //add 11 lords
        driver.get(mainURL + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBeforeAdding = lordsPage.getListLords().size();
        int countLords = 11;

        for (int i = 0; i < countLords; i++) {
            lordsPage.setLordData("testLord-" + (i + 1), i);
            lordsPage.clickOnAddLord();
        }

        assertEquals(countLordsBeforeAdding + countLords, lordsPage.getListLords().size());



        driver.get(mainURL + "/lords/top-youngest");
        assertEquals("Top 10 youngest lords", getTextActiveLinkNavbar(driver));

        assertTrue(lordFilterPage.getListLords().size() <= 10);

        assertTrue(lordsPage.getListLords().stream().noneMatch(el -> el.get("name").equals("testLord-11")));

    }

    @Test
    public void openPlanetsPageAddNewPlanetAndDeleteHer() {
        String mainURL = "http://localhost:" + serverPort;

        PlanetsPage planetsPage = new PlanetsPage(driver);

        driver.get(mainURL + planetsPage.PAGE_URL);
        assertEquals("Planets", getTextActiveLinkNavbar(driver));

        int countLordsBeforeAdding = planetsPage.getListPlanets().size();

        planetsPage.setPlanetName("Earth");
        planetsPage.clickOnAddPlanet();

        assertEquals(countLordsBeforeAdding + 1, planetsPage.getListPlanets().size());

        planetsPage.clickOnDeletePlanet(countLordsBeforeAdding + 1);

        assertEquals(countLordsBeforeAdding, planetsPage.getListPlanets().size());

    }

    @Test
    void openPlanetsPageAddNewPlanetAndEditHer() {
        String mainURL = "http://localhost:" + serverPort;

        LordsPage lordsPage = new LordsPage(driver);
        PlanetsPage planetsPage = new PlanetsPage(driver);
        PlanetEditPage planetEditPage = new PlanetEditPage(driver);

        String planetName = "Mercury-1";
        String newPlanetName = "Mercury";
        String lordName = "LordOfMercury";

        //add new lord
        driver.get(mainURL + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBeforeAdding = lordsPage.getListLords().size();

        lordsPage.setLordData("testLord", 30);
        lordsPage.clickOnAddLord();

        assertEquals(countLordsBeforeAdding + 1, lordsPage.getListLords().size());

        lordsPage.setLordData(lordName, 30);
        lordsPage.clickOnAddLord();


        //add new planet
        driver.get(mainURL + planetsPage.PAGE_URL);
        assertEquals("Planets", getTextActiveLinkNavbar(driver));

        int countPlanetsBeforeAdding = planetsPage.getListPlanets().size();

        planetsPage.setPlanetName(planetName);
        planetsPage.clickOnAddPlanet();

        assertEquals(countPlanetsBeforeAdding + 1, planetsPage.getListPlanets().size());

        //edit added planet
        planetsPage.clickOnEditPlanet(countPlanetsBeforeAdding + 1);
        assertEquals("Edit planet", driver.getTitle());

        planetEditPage.setPlanetName(newPlanetName);
        planetEditPage.setLord(lordName);
        planetEditPage.clickOnEditPlanet();

        assertEquals("Planets", getTextActiveLinkNavbar(driver));
        assertEquals(newPlanetName, planetsPage.getListPlanets()
                .get(countPlanetsBeforeAdding).get("name"));
        assertEquals(lordName, planetsPage.getListPlanets()
                .get(countPlanetsBeforeAdding).get("lord"));

    }

}
