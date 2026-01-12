package com.saglik.takip.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SENARYO 2: Saglik Verisi Ekleme ve Goruntuleme Testi
 * Bu test senaryosu kullanicinin saglik verisi eklemesini ve goruntülemesini test eder.
 */
public class SaglikVerisiEklemeSeleniumTest extends SeleniumTestBase {

    private String testKullaniciAdi;
    private String testSifre = "test123";

    @BeforeEach
    public void kullaniciOlusturVeGirisYap() {
        testKullaniciAdi = "sagliktest" + System.currentTimeMillis();

        // Kayit ol
        driver.get(baseUrl + "/kayit");
        bekle(1);

        driver.findElement(By.id("kullaniciAdi")).sendKeys(testKullaniciAdi);
        driver.findElement(By.id("sifre")).sendKeys(testSifre);
        driver.findElement(By.id("adSoyad")).sendKeys("Saglik Test");
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
    public void testSaglikVerisiEkleVeGoruntule() {
        // Saglik verileri sayfasina git
        driver.get(baseUrl + "/saglik-verileri");
        bekle(1);

        // Sayfa yuklendigini dogrula
        assertTrue(driver.getPageSource().contains("Saglik Verileri"),
                "Saglik verileri sayfasi yuklenemedi!");

        // Veri tipi sec
        Select veriTipiSelect = new Select(driver.findElement(By.id("veriTipi")));
        veriTipiSelect.selectByValue("TANSIYON_SISTOLIK");

        // Deger gir
        WebElement degerInput = driver.findElement(By.id("deger"));
        degerInput.clear();
        degerInput.sendKeys("120");

        // Birim gir
        WebElement birimInput = driver.findElement(By.id("birim"));
        birimInput.clear();
        birimInput.sendKeys("mmHg");

        // Notlar gir
        WebElement notlarInput = driver.findElement(By.id("notlar"));
        notlarInput.clear();
        notlarInput.sendKeys("Normal tansiyon olcumu");

        bekle(1);

        // Veri ekle butonuna tikla
        WebElement veriEkleBtn = driver.findElement(By.id("veriEkleBtn"));
        veriEkleBtn.click();

        bekle(2);

        // Tablodaki verileri kontrol et
        WebElement verilerTablosu = driver.findElement(By.id("verilerTablosu"));
        List<WebElement> rows = verilerTablosu.findElements(By.tagName("tr"));

        assertTrue(rows.size() > 1, "Saglik verisi tabloda gorunmuyor!");

        // Eklenen verinin tabloda oldugunu dogrula
        String tabloIcerigi = verilerTablosu.getText();
        assertTrue(tabloIcerigi.contains("120") && tabloIcerigi.contains("mmHg"),
                "Eklenen saglik verisi tabloda bulunamadi!");
    }

    @Test
    public void testFarkliSaglikVerileriEkle() {
        driver.get(baseUrl + "/saglik-verileri");
        bekle(2);

        // Sayfanin yuklendigini kontrol et
        assertTrue(driver.getPageSource().contains("Saglik Verileri"),
                "Saglik verileri sayfasi yuklenemedi!");

        // Kalp ritmi ekle
        Select veriTipiSelect = new Select(driver.findElement(By.id("veriTipi")));
        veriTipiSelect.selectByValue("KALP_RITMI");
        driver.findElement(By.id("deger")).sendKeys("75");
        driver.findElement(By.id("birim")).clear();
        driver.findElement(By.id("birim")).sendKeys("bpm");
        driver.findElement(By.id("veriEkleBtn")).click();
        bekle(2);

        // Kilo ekle
        driver.findElement(By.id("deger")).clear();
        Select veriTipiSelect2 = new Select(driver.findElement(By.id("veriTipi")));
        veriTipiSelect2.selectByValue("KILO");
        driver.findElement(By.id("deger")).sendKeys("75.5");
        driver.findElement(By.id("birim")).clear();
        driver.findElement(By.id("birim")).sendKeys("kg");
        driver.findElement(By.id("veriEkleBtn")).click();
        bekle(2);

        // Her iki verinin de tabloda oldugunu dogrula
        WebElement verilerTablosu = driver.findElement(By.id("verilerTablosu"));
        String tabloIcerigi = verilerTablosu.getText();

        assertTrue(tabloIcerigi.contains("75") && tabloIcerigi.contains("bpm"),
                "Kalp ritmi verisi bulunamadi!");
        assertTrue(tabloIcerigi.contains("75.5") && tabloIcerigi.contains("kg"),
                "Kilo verisi bulunamadi!");
    }
}
