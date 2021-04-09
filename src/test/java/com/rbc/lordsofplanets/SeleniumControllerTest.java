package com.rbc.lordsofplanets;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumControllerTest {

    private static final String MAIN_URL = "http://localhost:";

    private static WebDriver driver;
    private static MainPage mainPage;
    private static LordsPage lordsPage;
    private static LordFilterPage lordFilterPage;
    private static LordEditPage lordEditPage;
    private static PlanetsPage planetsPage;
    private static PlanetEditPage planetEditPage;

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
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        lordsPage = new LordsPage(driver);
        lordFilterPage = new LordFilterPage(driver);
        lordEditPage = new LordEditPage(driver);
        planetsPage = new PlanetsPage(driver);
        planetEditPage = new PlanetEditPage(driver);
    }

    @AfterAll
    public void closeDriver() {
        driver.close();
        driver.quit();
    }

    @Test
    public void openMainPage() {
        driver.get(MAIN_URL + serverPort + mainPage.PAGE_URL);
        assertEquals("Lords of planets", driver.getTitle());
    }

    @Test
    public void openLordsPageAddNewLordAndDeleteHim() {
        driver.get(MAIN_URL + serverPort + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBefore = lordsPage.getListLords().size();
        lordsPage.setLordData("Dart Vader", 30);
        lordsPage.clickOnAddLord();
        int countLordsAfter = countLordsBefore + 1;
        assertEquals(countLordsAfter, lordsPage.getListLords().size());

        lordsPage.clickOnDeleteLord(countLordsAfter);
        assertEquals(countLordsBefore, lordsPage.getListLords().size());
    }

    @Test
    void openLordsPageAddNewLordAndEditHim() {
        String lordName = "Dart Vader";
        String newLordName = "John";

        driver.get(MAIN_URL + serverPort + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBefore = lordsPage.getListLords().size();
        lordsPage.setLordData(lordName, 30);
        lordsPage.clickOnAddLord();
        int countLordsAfter = countLordsBefore + 1;
        assertEquals(countLordsAfter, lordsPage.getListLords().size());

        lordsPage.clickOnEditLord(countLordsAfter);
        assertEquals("Edit lord", driver.getTitle());

        lordEditPage.setLordName(newLordName);
        lordEditPage.clickOnEditLord();

        assertEquals("Lords", getTextActiveLinkNavbar(driver));
        assertEquals(newLordName, lordsPage.getListLords()
                .get(countLordsAfter - 1).get("name"));
    }

    @Test
    void openLazyLordsPage() {
        driver.get(MAIN_URL + serverPort + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBefore = lordsPage.getListLords().size();
        lordsPage.setLordData("testLord-1", 30);
        lordsPage.clickOnAddLord();
        assertEquals(countLordsBefore + 1, lordsPage.getListLords().size());

        driver.get(MAIN_URL + serverPort + "/lords/lazy");
        assertEquals("Lazy lords", getTextActiveLinkNavbar(driver));
        assertTrue(lordFilterPage.getListLords().size() > 0);
    }

    @Test
    void openTop10YoungestLordsPage() {
        driver.get(MAIN_URL + serverPort + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBeforeAdding = lordsPage.getListLords().size();
        int countLords = 11;
        for (int i = 0; i < countLords; i++) {
            lordsPage.setLordData("testLord-" + (i + 1), i);
            lordsPage.clickOnAddLord();
        }
        assertEquals(countLordsBeforeAdding + countLords, lordsPage.getListLords().size());


        driver.get(MAIN_URL + serverPort + "/lords/top-youngest");
        assertEquals("Top 10 youngest lords", getTextActiveLinkNavbar(driver));
        assertTrue(lordFilterPage.getListLords().size() <= 10);
        assertTrue(lordsPage.getListLords().stream().noneMatch(el -> el.get("name").equals("testLord-11")));
    }

    @Test
    public void openPlanetsPageAddNewPlanetAndDeleteHer() {
        driver.get(MAIN_URL + serverPort + planetsPage.PAGE_URL);
        assertEquals("Planets", getTextActiveLinkNavbar(driver));

        int countLordsBefore = planetsPage.getListPlanets().size();
        planetsPage.setPlanetName("Earth");
        planetsPage.clickOnAddPlanet();
        int countPlanetsAfter = countLordsBefore + 1;
        assertEquals(countPlanetsAfter, planetsPage.getListPlanets().size());

        planetsPage.clickOnDeletePlanet(countPlanetsAfter);
        assertEquals(countLordsBefore, planetsPage.getListPlanets().size());
    }

    @Test
    void openPlanetsPageAddNewPlanetAndEditHer() {
        String planetName = "Mercury-1";
        String newPlanetName = "Mercury";
        String lordName = "LordOfMercury";

        driver.get(MAIN_URL + serverPort + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        int countLordsBefore = lordsPage.getListLords().size();
        lordsPage.setLordData("testLord", 30);
        lordsPage.clickOnAddLord();

        lordsPage.setLordData(lordName, 20);
        lordsPage.clickOnAddLord();
        assertEquals(countLordsBefore + 2, lordsPage.getListLords().size());

        driver.get(MAIN_URL + serverPort + planetsPage.PAGE_URL);
        assertEquals("Planets", getTextActiveLinkNavbar(driver));

        int countPlanetsBefore = planetsPage.getListPlanets().size();
        planetsPage.setPlanetName(planetName);
        planetsPage.clickOnAddPlanet();
        assertEquals(countPlanetsBefore + 1, planetsPage.getListPlanets().size());

        planetsPage.clickOnEditPlanet(countPlanetsBefore + 1);
        assertEquals("Edit planet", driver.getTitle());

        planetEditPage.setPlanetName(newPlanetName);
        planetEditPage.setLord(lordName);
        planetEditPage.clickOnEditPlanet();

        assertEquals("Planets", getTextActiveLinkNavbar(driver));
        assertEquals(newPlanetName, planetsPage.getListPlanets()
                .get(countPlanetsBefore).get("name"));
        assertEquals(lordName, planetsPage.getListPlanets()
                .get(countPlanetsBefore).get("lord"));
    }

}
