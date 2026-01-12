package com.saglik.takip.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BONUS SENARYO 1: Ilac Ekleme ve Yonetim Testi
 * Bu test senaryosu kullanicinin ilac eklemesini ve yonetmesini test eder.
 */
public class IlacYonetimSeleniumTest extends SeleniumTestBase {

    private String testKullaniciAdi;
    private String testSifre = "test123";

    @BeforeEach
    public void kullaniciOlusturVeGirisYap() {
        testKullaniciAdi = "ilactest" + System.currentTimeMillis();

        driver.get(baseUrl + "/kayit");
        bekle(1);

        driver.findElement(By.id("kullaniciAdi")).sendKeys(testKullaniciAdi);
        driver.findElement(By.id("sifre")).sendKeys(testSifre);
        driver.findElement(By.id("adSoyad")).sendKeys("Ilac Test");
        driver.findElement(By.id("email")).sendKeys(testKullaniciAdi + "@test.com");
        driver.findElement(By.id("kayitBtn")).click();
        bekle(2);

        // Giris sayfasina git
        driver.get(baseUrl + "/giris");
        bekle(1);

        driver.findElement(By.id("kullaniciAdi")).sendKeys(testKullaniciAdi);
        driver.findElement(By.id("sifre")).sendKeys(testSifre);
        driver.findElement(By.id("girisBtn")).click();
        bekle(2);
    }

    @Test
    public void testIlacEkleVeGoruntule() {
        driver.get(baseUrl + "/ilaclar");
        bekle(1);

        assertTrue(driver.getPageSource().contains("Ilac"),
                "Ilaclar sayfasi yuklenemedi!");

        // Ilac bilgilerini gir
        driver.findElement(By.id("ilacAdi")).sendKeys("Aspirin");
        driver.findElement(By.id("doz")).sendKeys("100mg");
        driver.findElement(By.id("gunlukKullanimSayisi")).sendKeys("2");
        driver.findElement(By.id("kullanimSaatleri")).sendKeys("08:00,20:00");
        driver.findElement(By.id("notlar")).sendKeys("Yemekten sonra alinacak");
        driver.findElement(By.id("ilacEkleBtn")).click();

        bekle(2);

        // Tabloda ilaç oldugunu dogrula
        WebElement ilaclarTablosu = driver.findElement(By.id("ilaclarTablosu"));
        String tabloIcerigi = ilaclarTablosu.getText();

        assertTrue(tabloIcerigi.contains("Aspirin") && tabloIcerigi.contains("100mg"),
                "Ilac tabloda gorunmuyor!");
    }
}
