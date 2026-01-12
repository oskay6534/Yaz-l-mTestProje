package com.saglik.takip.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GirisHataMesajiSeleniumTest {

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
    void testGirisHataMesaji() throws InterruptedException {
        driver.get(baseUrl);

        driver.findElement(By.id("kullaniciAdi"))
                .sendKeys("deneme");

        driver.findElement(By.id("sifre"))
                .sendKeys("123456789");

        driver.findElement(By.id("girisBtn")).click();

        Thread.sleep(2000);

        String pageSource = driver.getPageSource();
        assertTrue(
                pageSource.contains("Kullanici adi veya sifre hatali!"),
                "Hata mesaji goruntulenmedi!"
        );

        System.out.println("✅ Selenium Test Basarili: Giris hata mesaji goruntulendi.");
    }
}
