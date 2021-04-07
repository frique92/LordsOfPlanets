package com.rbc.lordsofplanets.controllers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumControllerTest {

    private WebDriver driver;

    @LocalServerPort
    protected int serverPort;

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
    public void openMainPage() throws InterruptedException {
        driver.get("http://localhost:" + serverPort);
        assertEquals(driver.getTitle(), "Lords of planets");
    }

    @Test
    public void openLordsPage() {
        driver.get("http://localhost:" + serverPort + "/lords");
        assertEquals(driver.getTitle(), "Lords");

        WebElement nameLord = driver.findElement(By.id("name"));
        nameLord.sendKeys("Putin");

        WebElement ageLord = driver.findElement(By.id("age"));
        nameLord.sendKeys("30");

        driver.findElement(By.className("btn")).click();

        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> listLords = tbody.findElements(By.tagName("tr"));
        assertEquals(1, listLords.size());

        driver.findElement(By.xpath("//a[@href ='/lords/delete/1']")).click();
        tbody = driver.findElement(By.tagName("tbody"));
        listLords = tbody.findElements(By.tagName("tr"));
        assertEquals(0, listLords.size());
    }

}
