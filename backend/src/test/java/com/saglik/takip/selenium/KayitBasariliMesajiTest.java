package com.saglik.takip.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class KayitBasariliMesajiTest {

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
    public void testKayitBasariliMesaji() {
        // Kayit sayfasina git
        driver.get(baseUrl + "/kayit");

        // 2 saniye bekle
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Formu doldur
        String uniqueUsername = "testuser" + System.currentTimeMillis();

        driver.findElement(By.id("kullaniciAdi")).sendKeys(uniqueUsername);
        driver.findElement(By.id("sifre")).sendKeys("Test123!");
        driver.findElement(By.id("adSoyad")).sendKeys("Test Kullanici");
        driver.findElement(By.id("email")).sendKeys(uniqueUsername + "@test.com");

        // Kayit butonuna bas
        WebElement kayitBtn = driver.findElement(By.id("kayitBtn"));
        kayitBtn.click();

        // 3 saniye bekle (mesaj gorulsun)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Basarili mesajini kontrol et - id ile bul
        try {
            WebElement basariliMesaj = driver.findElement(By.id("basariliMesaj"));
            assertTrue(basariliMesaj.getText().contains("Kayit basarili!"),
                    "Kayit basarili mesaji yanlis!");
            System.out.println("Test basarili: Kayit basarili mesaji goruntulendi.");
        } catch (Exception e) {
            // Eger mesaj bulunamazsa sayfa kaynagina bak
            String pageSource = driver.getPageSource();
            assertTrue(pageSource.contains("Kayit basarili"),
                    "Kayit basarili mesaji gorunmuyor!");
            System.out.println("Test basarili: Kayit basarili mesaji (page source'da) goruntulendi.");
        }
    }
}
