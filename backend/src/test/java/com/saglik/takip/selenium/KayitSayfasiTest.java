package com.saglik.takip.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class KayitSayfasiTest {

    private WebDriver driver;
    private String baseUrl = "http://localhost:3000";

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testKayitSayfasiYuklendi() {
        // Ana sayfaya git
        driver.get(baseUrl);

        // Kayit Ol linkini bul ve tikla
        WebElement kayitOlLink = driver.findElement(By.linkText("Kayit Ol"));
        kayitOlLink.click();

        // 2 saniye bekle
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // URL kontrolu
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/kayit"),
                "Kayit sayfasina gidilemedi! Mevcut URL: " + currentUrl);

        // "Zaten hesabiniz var mi?" yazisini kontrol et
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Zaten hesabiniz var mi?"),
                "Kayit sayfasinda 'Zaten hesabiniz var mi?' yazisi bulunamadi!");

        System.out.println("Test basarili: Kayit sayfasi yuklendi ve 'Zaten hesabiniz var mi?' yazisi goruntulendi.");
    }
}
