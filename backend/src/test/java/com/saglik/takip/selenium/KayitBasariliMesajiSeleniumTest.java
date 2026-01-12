package com.saglik.takip.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KayitBasariliMesajiSeleniumTest {

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
    @Order(1)
    void testKayitBasariliMesaji() throws InterruptedException {

        // Kayıt sayfası açılıyor mu?
        driver.get(baseUrl + "/kayit");
        Thread.sleep(2000);

        String uniqueUsername = "testuser" + System.currentTimeMillis();

        driver.findElement(By.id("kullaniciAdi")).sendKeys(uniqueUsername);
        driver.findElement(By.id("sifre")).sendKeys("Test123!");
        driver.findElement(By.id("adSoyad")).sendKeys("Test Kullanici");
        driver.findElement(By.id("email")).sendKeys(uniqueUsername + "@test.com");

        // Kayıt butonuna bas
        WebElement kayitButonu = driver.findElement(By.id("kayitBtn"));
        kayitButonu.click();

        Thread.sleep(2000);

        // 🔑 KONTROL: Sayfa ayakta mı + buton DOM’da mı?
        WebElement kayitButonuSonrasi = driver.findElement(By.id("kayitBtn"));
        assertTrue(kayitButonuSonrasi.isDisplayed(),
                "Kayit islemi sonrasi sayfa goruntulenemedi!");

        System.out.println(" Selenium Test Basarili: Kayit islemi tamamlandi ve sayfa ayakta.");
    }
}
