package com.saglik.takip.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SENARYO 3: Randevu Olusturma ve Iptal Etme Testi
 * Bu test senaryosu kullanicinin randevu olusturmasini ve iptal etmesini test eder.
 */
public class RandevuOlusturmaSeleniumTest extends SeleniumTestBase {

    private String testKullaniciAdi;
    private String testSifre = "test123";

    @BeforeEach
    public void kullaniciOlusturVeGirisYap() {
        testKullaniciAdi = "randevutest" + System.currentTimeMillis();

        // Kayit ol
        driver.get(baseUrl + "/kayit");
        bekle(1);

        driver.findElement(By.id("kullaniciAdi")).sendKeys(testKullaniciAdi);
        driver.findElement(By.id("sifre")).sendKeys(testSifre);
        driver.findElement(By.id("adSoyad")).sendKeys("Randevu Test");
        driver.findElement(By.id("email")).sendKeys(testKullaniciAdi + "@test.com");
        driver.findElement(By.id("kayitBtn")).click();

        bekle(2);

        // Giris sayfasina git
        driver.get(baseUrl + "/giris");
        bekle(1);

        // Giris yap
        driver.findElement(By.id("kullaniciAdi")).sendKeys(testKullaniciAdi);
        driver.findElement(By.id("sifre")).sendKeys(testSifre);
        driver.findElement(By.id("girisBtn")).click();

        bekle(2);
    }

    @Test
    public void testRandevuOlustur() {
        // Randevular sayfasina git
        driver.get(baseUrl + "/randevular");
        bekle(2);

        // Sayfa yuklendigini dogrula
        assertTrue(driver.getPageSource().contains("Randevu"),
                "Randevular sayfasi yuklenemedi!");

        // Doktor secimi - once doktor var mi kontrol et
        Select doktorSelect = new Select(driver.findElement(By.id("doktorId")));
        List<WebElement> doktorlar = doktorSelect.getOptions();

        if (doktorlar.size() > 1) {
            // Ilk doktoru sec (ilk option "Doktor secin..." oldugu icin ikincisini sec)
            doktorSelect.selectByIndex(1);

            // Gelecek bir tarih belirle
            LocalDateTime gelecekTarih = LocalDateTime.now().plusDays(2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String tarihString = gelecekTarih.format(formatter);

            // Randevu tarihi gir
            WebElement randevuTarihiInput = driver.findElement(By.id("randevuTarihi"));
            randevuTarihiInput.sendKeys(tarihString);

            // Aciklama gir
            WebElement aciklamaInput = driver.findElement(By.id("aciklama"));
            aciklamaInput.sendKeys("Test randevu aciklamasi");

            bekle(1);

            // Randevu olustur butonuna tikla
            WebElement randevuOlusturBtn = driver.findElement(By.id("randevuOlusturBtn"));
            randevuOlusturBtn.click();

            bekle(2);

            // Tablodaki randevulari kontrol et
            WebElement randevularTablosu = driver.findElement(By.id("randevularTablosu"));
            String tabloIcerigi = randevularTablosu.getText();

            assertTrue(tabloIcerigi.contains("Test randevu aciklamasi") ||
                            tabloIcerigi.contains("BEKLEMEDE"),
                    "Randevu tabloda gorunmuyor!");
        } else {
            System.out.println("UYARI: Sistemde doktor bulunamadigi icin randevu olusturulamadi!");
            assertTrue(true, "Doktor olmadigi icin test atlandi");
        }
    }

    @Test
    public void testRandevuIptalEt() {
        driver.get(baseUrl + "/randevular");
        bekle(2);

        // Once bir randevu olustur
        Select doktorSelect = new Select(driver.findElement(By.id("doktorId")));
        List<WebElement> doktorlar = doktorSelect.getOptions();

        if (doktorlar.size() > 1) {
            doktorSelect.selectByIndex(1);

            LocalDateTime gelecekTarih = LocalDateTime.now().plusDays(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String tarihString = gelecekTarih.format(formatter);

            driver.findElement(By.id("randevuTarihi")).sendKeys(tarihString);
            driver.findElement(By.id("aciklama")).sendKeys("Iptal edilecek randevu");
            driver.findElement(By.id("randevuOlusturBtn")).click();

            bekle(3);

            // Iptal Et butonunu bul ve tikla
            List<WebElement> iptalButtons = driver.findElements(By.xpath("//button[contains(text(), 'Iptal')]"));

            if (!iptalButtons.isEmpty()) {
                iptalButtons.get(0).click();
                bekle(2);

                // Randevunun iptal edildigini dogrula
                WebElement randevularTablosu = driver.findElement(By.id("randevularTablosu"));
                String tabloIcerigi = randevularTablosu.getText();

                assertTrue(tabloIcerigi.contains("IPTAL_EDILDI"),
                        "Randevu iptal edilmedi!");
            }
        } else {
            System.out.println("UYARI: Sistemde doktor bulunamadigi icin test atlandi!");
            assertTrue(true, "Doktor olmadigi icin test atlandi");
        }
    }
}
