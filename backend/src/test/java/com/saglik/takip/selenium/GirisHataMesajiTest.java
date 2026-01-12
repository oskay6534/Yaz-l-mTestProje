package com.saglik.takip.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class GirisHataMesajiTest {

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
    public void testGirisHataMesaji() {
        // Ana sayfaya git
        driver.get(baseUrl);

        // Kullanici adi gir
        WebElement kullaniciAdi = driver.findElement(By.id("kullaniciAdi"));
        kullaniciAdi.sendKeys("deneme");

        // Sifre gir
        WebElement sifre = driver.findElement(By.id("sifre"));
        sifre.sendKeys("123456789");

        // Giris butonuna bas
        WebElement girisBtn = driver.findElement(By.id("girisBtn"));
        girisBtn.click();

        // 2 saniye bekle
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Hata mesajini kontrol et
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Kullanici adi veya sifre hatali!"),
                "Hata mesaji gorunmuyor!");

        System.out.println("Test basarili: Giris hata mesaji goruntulendi.");
    }
}
