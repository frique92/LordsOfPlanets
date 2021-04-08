package com.rbc.lordsofplanets.controllers;

import com.rbc.lordsofplanets.pages.LordEditPage;
import com.rbc.lordsofplanets.pages.LordsPage;
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
    public void openLordsPage() {
        String mainURL = "http://localhost:" + serverPort;

        LordsPage lordsPage = new LordsPage(driver);

        driver.get(mainURL + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        lordsPage.setLordData("Dart Vader", 30);
        lordsPage.clickOnAddLord();

        assertEquals(1, lordsPage.getListLords().size());

        lordsPage.clickOnDeleteLord(1);

        assertEquals(0, lordsPage.getListLords().size());

    }

    @Test
    void openLordEditPage() {
        String mainURL = "http://localhost:" + serverPort;

        LordsPage lordsPage = new LordsPage(driver);
        LordEditPage lordEditPage = new LordEditPage(driver);

        String lordName = "Dart Vader";
        String newLordName = "John";

        driver.get(mainURL + lordsPage.PAGE_URL);
        assertEquals("Lords", getTextActiveLinkNavbar(driver));

        lordsPage.setLordData(lordName, 30);
        lordsPage.clickOnAddLord();

        assertEquals(1, lordsPage.getListLords().size());

        lordsPage.clickOnEditLord(1);
        assertEquals("Edit lord", driver.getTitle());

        lordEditPage.setLordName(newLordName);
        lordEditPage.clickOnEditLord();

        assertEquals(1, lordsPage.getListLords().size());
        assertEquals(newLordName, lordsPage.getListLords().get(0).get("name"));

    }
}
