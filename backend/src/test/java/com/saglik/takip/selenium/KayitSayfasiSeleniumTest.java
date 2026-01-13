package com.saglik.takip.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KayitSayfasiSeleniumTest {

    private WebDriver driver;
    private final String baseUrl = "http://localhost:3000";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testKayitSayfasiYuklendi() throws InterruptedException {
        driver.get(baseUrl);

        WebElement kayitOlLink = driver.findElement(By.linkText("Kayit Ol"));
        kayitOlLink.click();

        Thread.sleep(2000);

        String currentUrl = driver.getCurrentUrl();
        assertTrue(
                currentUrl.contains("/kayit"),
                "Kayit sayfasina gidilemedi! URL: " + currentUrl
        );

        String pageSource = driver.getPageSource();
        assertTrue(
                pageSource.contains("Zaten hesabiniz var mi?"),
                "Kayit sayfasinda beklenen yazi bulunamadi!"
        );

        System.out.println(" Selenium Test Basarili: Kayit sayfasi dogru sekilde yuklendi.");
    }
}
