package com.saglik.takip.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SENARYO 1: Kullanici Kayit ve Giris Testi
 * Bu test senaryosu kullanicinin sisteme kayit olmasini ve giris yapmasini test eder.
 */
public class KullaniciKayitVeGirisSeleniumTest extends SeleniumTestBase {

    @Test
    public void testKullaniciKayitVeGiris() {
        // Kayit sayfasina git
        driver.get(baseUrl + "/kayit");
        bekle(1);

        // Form alanlarini doldur
        String benzersizKullaniciAdi = "testkullanici" + System.currentTimeMillis();

        WebElement kullaniciAdiInput = driver.findElement(By.id("kullaniciAdi"));
        kullaniciAdiInput.sendKeys(benzersizKullaniciAdi);

        WebElement sifreInput = driver.findElement(By.id("sifre"));
        sifreInput.sendKeys("test123");

        WebElement adSoyadInput = driver.findElement(By.id("adSoyad"));
        adSoyadInput.sendKeys("Test Kullanici");

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys(benzersizKullaniciAdi + "@test.com");

        WebElement telefonInput = driver.findElement(By.id("telefon"));
        telefonInput.sendKeys("5551234567");

        WebElement yasInput = driver.findElement(By.id("yas"));
        yasInput.sendKeys("30");

        Select cinsiyetSelect = new Select(driver.findElement(By.id("cinsiyet")));
        cinsiyetSelect.selectByValue("ERKEK");

        bekle(1);

        // Kayit butonuna tikla
        WebElement kayitButton = driver.findElement(By.id("kayitBtn"));
        kayitButton.click();

        // Basarili kayit mesajini bekle
        wait.until(ExpectedConditions.urlContains("/giris"));
        bekle(2);

        // Giris yap
        WebElement girisKullaniciAdi = driver.findElement(By.id("kullaniciAdi"));
        girisKullaniciAdi.sendKeys(benzersizKullaniciAdi);

        WebElement girisSifre = driver.findElement(By.id("sifre"));
        girisSifre.sendKeys("test123");

        WebElement girisButton = driver.findElement(By.id("girisBtn"));
        girisButton.click();

        bekle(2);

        // Ana sayfaya yonlendirildigini dogrula
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.equals(baseUrl + "/") || currentUrl.equals(baseUrl),
                "Kullanici basariyla giris yapamadi!");

        // Hosgeldiniz mesajini dogrula
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Hosgeldiniz") || pageSource.contains("Test Kullanici"),
                "Hosgeldiniz mesaji bulunamadi!");
    }

    @Test
    public void testYanlisGirisBilgileri() {
        // Giris sayfasina git
        driver.get(baseUrl + "/giris");
        bekle(1);

        // Yanlis bilgilerle giris yap
        WebElement kullaniciAdiInput = driver.findElement(By.id("kullaniciAdi"));
        kullaniciAdiInput.sendKeys("olmayankimse");

        WebElement sifreInput = driver.findElement(By.id("sifre"));
        sifreInput.sendKeys("yanlissifre");

        WebElement girisButton = driver.findElement(By.id("girisBtn"));
        girisButton.click();

        bekle(2);

        // Hata mesajini dogrula
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("hatali") || pageSource.contains("Kullanici adi veya sifre hatali"),
                "Hata mesaji goruntulenmedi!");
    }
}
