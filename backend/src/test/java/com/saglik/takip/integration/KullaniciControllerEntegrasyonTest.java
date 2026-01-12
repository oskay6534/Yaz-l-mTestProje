package com.saglik.takip.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.repository.KullaniciRepository;
import com.saglik.takip.repository.RandevuRepository;
import com.saglik.takip.repository.IlacRepository;
import com.saglik.takip.repository.SaglikVerisiRepository;
import com.saglik.takip.repository.TibbiRaporRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class KullaniciControllerEntegrasyonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private RandevuRepository randevuRepository;

    @Autowired
    private IlacRepository ilacRepository;

    @Autowired
    private SaglikVerisiRepository saglikVerisiRepository;

    @Autowired
    private TibbiRaporRepository tibbiRaporRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Foreign key constraint'ler yuzunden once child tablolari temizle
        randevuRepository.deleteAll();
        ilacRepository.deleteAll();
        saglikVerisiRepository.deleteAll();
        tibbiRaporRepository.deleteAll();
        // Son olarak parent tablo
        kullaniciRepository.deleteAll();
    }

    @Test
    public void kullaniciKayit_Basarili() throws Exception {
        // Given
        Kullanici yeniKullanici = new Kullanici();
        yeniKullanici.setKullaniciAdi("yenikullanici");
        yeniKullanici.setSifre("sifre123");
        yeniKullanici.setAdSoyad("Yeni Kullanici");
        yeniKullanici.setEmail("yeni@example.com");
        yeniKullanici.setRol(Kullanici.KullaniciRolu.HASTA);
        yeniKullanici.setYas(30);

        // When & Then
        mockMvc.perform(post("/api/kullanicilar/kayit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(yeniKullanici)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.kullaniciAdi").value("yenikullanici"))
                .andExpect(jsonPath("$.email").value("yeni@example.com"));
    }

    @Test
    public void kullaniciKayit_MevcutKullaniciAdi() throws Exception {
        // Given - Onceden kayitli kullanici
        Kullanici mevcutKullanici = new Kullanici();
        mevcutKullanici.setKullaniciAdi("mevcut");
        mevcutKullanici.setSifre("sifre123");
        mevcutKullanici.setAdSoyad("Mevcut Kullanici");
        mevcutKullanici.setEmail("mevcut@example.com");
        mevcutKullanici.setRol(Kullanici.KullaniciRolu.HASTA);
        kullaniciRepository.save(mevcutKullanici);

        // Ayni kullanici adiyla yeni kayit denemesi
        Kullanici yeniKullanici = new Kullanici();
        yeniKullanici.setKullaniciAdi("mevcut");
        yeniKullanici.setSifre("baskasifre");
        yeniKullanici.setAdSoyad("Baska Kullanici");
        yeniKullanici.setEmail("baska@example.com");
        yeniKullanici.setRol(Kullanici.KullaniciRolu.HASTA);

        // When & Then
        mockMvc.perform(post("/api/kullanicilar/kayit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(yeniKullanici)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void tumKullanicilariGetir_Basarili() throws Exception {
        // Given
        Kullanici kullanici1 = new Kullanici();
        kullanici1.setKullaniciAdi("kullanici1");
        kullanici1.setSifre("sifre123");
        kullanici1.setAdSoyad("Kullanici Bir");
        kullanici1.setEmail("bir@example.com");
        kullanici1.setRol(Kullanici.KullaniciRolu.HASTA);
        kullaniciRepository.save(kullanici1);

        Kullanici kullanici2 = new Kullanici();
        kullanici2.setKullaniciAdi("kullanici2");
        kullanici2.setSifre("sifre456");
        kullanici2.setAdSoyad("Kullanici Iki");
        kullanici2.setEmail("iki@example.com");
        kullanici2.setRol(Kullanici.KullaniciRolu.DOKTOR);
        kullaniciRepository.save(kullanici2);

        // When & Then
        mockMvc.perform(get("/api/kullanicilar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void kullaniciBul_Basarili() throws Exception {
        // Given
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("testkullanici");
        kullanici.setSifre("sifre123");
        kullanici.setAdSoyad("Test Kullanici");
        kullanici.setEmail("test@example.com");
        kullanici.setRol(Kullanici.KullaniciRolu.HASTA);
        Kullanici kaydedilen = kullaniciRepository.save(kullanici);

        // When & Then
        mockMvc.perform(get("/api/kullanicilar/" + kaydedilen.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kullaniciAdi").value("testkullanici"));
    }

    @Test
    public void kullaniciBul_Bulunamadi() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/kullanicilar/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void kullaniciGuncelle_Basarili() throws Exception {
        // Given
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("eski");
        kullanici.setSifre("sifre123");
        kullanici.setAdSoyad("Eski Ad");
        kullanici.setEmail("eski@example.com");
        kullanici.setRol(Kullanici.KullaniciRolu.HASTA);
        Kullanici kaydedilen = kullaniciRepository.save(kullanici);

        // Guncel bilgiler
        Kullanici guncelKullanici = new Kullanici();
        guncelKullanici.setAdSoyad("Yeni Ad Soyad");
        guncelKullanici.setEmail("yeni@example.com");
        guncelKullanici.setTelefon("5551234567");

        // When & Then
        mockMvc.perform(put("/api/kullanicilar/" + kaydedilen.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guncelKullanici)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adSoyad").value("Yeni Ad Soyad"));
    }

    @Test
    public void kullaniciSil_Basarili() throws Exception {
        // Given
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("silinecek");
        kullanici.setSifre("sifre123");
        kullanici.setAdSoyad("Silinecek Kullanici");
        kullanici.setEmail("silinecek@example.com");
        kullanici.setRol(Kullanici.KullaniciRolu.HASTA);
        Kullanici kaydedilen = kullaniciRepository.save(kullanici);

        // When & Then
        mockMvc.perform(delete("/api/kullanicilar/" + kaydedilen.getId()))
                .andExpect(status().isOk());
    }
}
