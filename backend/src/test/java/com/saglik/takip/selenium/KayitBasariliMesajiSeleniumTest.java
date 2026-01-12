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
        driver.get(baseUrl + "/kayit");

        Thread.sleep(2000);

        String uniqueUsername = "testuser" + System.currentTimeMillis();

        driver.findElement(By.id("kullaniciAdi")).sendKeys(uniqueUsername);
        driver.findElement(By.id("sifre")).sendKeys("Test123!");
        driver.findElement(By.id("adSoyad")).sendKeys("Test Kullanici");
        driver.findElement(By.id("email")).sendKeys(uniqueUsername + "@test.com");

        driver.findElement(By.id("kayitBtn")).click();

        Thread.sleep(3000);

        WebElement basariliMesaj = driver.findElement(By.id("basariliMesaj"));
        assertTrue(
                basariliMesaj.getText().contains("Kayit basarili"),
                "Kayit basarili mesaji goruntulenemedi!"
        );

        System.out.println(" Selenium Test Basarili: Kayit basarili mesaji goruntulendi.");
    }
}
