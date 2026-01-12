package com.saglik.takip.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.entity.Randevu;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RandevuControllerEntegrasyonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RandevuRepository randevuRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private IlacRepository ilacRepository;

    @Autowired
    private SaglikVerisiRepository saglikVerisiRepository;

    @Autowired
    private TibbiRaporRepository tibbiRaporRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Kullanici testHasta;
    private Kullanici testDoktor;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        // Foreign key constraint'ler yuzunden once child tablolari temizle
        randevuRepository.deleteAll();
        ilacRepository.deleteAll();
        saglikVerisiRepository.deleteAll();
        tibbiRaporRepository.deleteAll();
        // Son olarak parent tablo
        kullaniciRepository.deleteAll();

        testHasta = new Kullanici();
        testHasta.setKullaniciAdi("hasta");
        testHasta.setSifre("sifre123");
        testHasta.setAdSoyad("Test Hasta");
        testHasta.setEmail("hasta@example.com");
        testHasta.setRol(Kullanici.KullaniciRolu.HASTA);
        testHasta = kullaniciRepository.save(testHasta);

        testDoktor = new Kullanici();
        testDoktor.setKullaniciAdi("doktor");
        testDoktor.setSifre("sifre456");
        testDoktor.setAdSoyad("Test Doktor");
        testDoktor.setEmail("doktor@example.com");
        testDoktor.setRol(Kullanici.KullaniciRolu.DOKTOR);
        testDoktor = kullaniciRepository.save(testDoktor);
    }

    @Test
    public void randevuOlustur_Basarili() throws Exception {
        // Given
        Randevu yeniRandevu = new Randevu();
        yeniRandevu.setHasta(testHasta);
        yeniRandevu.setDoktor(testDoktor);
        yeniRandevu.setRandevuTarihi(LocalDateTime.now().plusDays(1));
        yeniRandevu.setAciklama("Test randevusu");
        yeniRandevu.setDurum(Randevu.RandevuDurumu.BEKLEMEDE);

        // When & Then
        mockMvc.perform(post("/api/randevular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(yeniRandevu)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.aciklama").value("Test randevusu"))
                .andExpect(jsonPath("$.durum").value("BEKLEMEDE"));
    }

    @Test
    public void tumRandevulariGetir_Basarili() throws Exception {
        // Given
        Randevu randevu1 = new Randevu();
        randevu1.setHasta(testHasta);
        randevu1.setDoktor(testDoktor);
        randevu1.setRandevuTarihi(LocalDateTime.now().plusDays(1));
        randevu1.setAciklama("Randevu 1");
        randevu1.setDurum(Randevu.RandevuDurumu.BEKLEMEDE);
        randevuRepository.save(randevu1);

        Randevu randevu2 = new Randevu();
        randevu2.setHasta(testHasta);
        randevu2.setDoktor(testDoktor);
        randevu2.setRandevuTarihi(LocalDateTime.now().plusDays(2));
        randevu2.setAciklama("Randevu 2");
        randevu2.setDurum(Randevu.RandevuDurumu.ONAYLANDI);
        randevuRepository.save(randevu2);

        // When & Then
        mockMvc.perform(get("/api/randevular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void hastayaAitRandevulariGetir_Basarili() throws Exception {
        // Given
        Randevu randevu = new Randevu();
        randevu.setHasta(testHasta);
        randevu.setDoktor(testDoktor);
        randevu.setRandevuTarihi(LocalDateTime.now().plusDays(1));
        randevu.setAciklama("Hasta randevusu");
        randevu.setDurum(Randevu.RandevuDurumu.BEKLEMEDE);
        randevuRepository.save(randevu);

        // When & Then
        mockMvc.perform(get("/api/randevular/hasta/" + testHasta.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].aciklama").value("Hasta randevusu"));
    }

    @Test
    public void randevuOnayla_Basarili() throws Exception {
        // Given
        Randevu randevu = new Randevu();
        randevu.setHasta(testHasta);
        randevu.setDoktor(testDoktor);
        randevu.setRandevuTarihi(LocalDateTime.now().plusDays(1));
        randevu.setAciklama("Onaylanacak randevu");
        randevu.setDurum(Randevu.RandevuDurumu.BEKLEMEDE);
        Randevu kaydedilen = randevuRepository.save(randevu);

        // When & Then
        mockMvc.perform(put("/api/randevular/" + kaydedilen.getId() + "/onayla"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.durum").value("ONAYLANDI"));
    }

    @Test
    public void randevuIptalEt_Basarili() throws Exception {
        // Given
        Randevu randevu = new Randevu();
        randevu.setHasta(testHasta);
        randevu.setDoktor(testDoktor);
        randevu.setRandevuTarihi(LocalDateTime.now().plusDays(1));
        randevu.setAciklama("Iptal edilecek randevu");
        randevu.setDurum(Randevu.RandevuDurumu.BEKLEMEDE);
        Randevu kaydedilen = randevuRepository.save(randevu);

        // When & Then
        mockMvc.perform(put("/api/randevular/" + kaydedilen.getId() + "/iptal-et"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.durum").value("IPTAL_EDILDI"));
    }

    @Test
    public void randevuSil_Basarili() throws Exception {
        // Given
        Randevu randevu = new Randevu();
        randevu.setHasta(testHasta);
        randevu.setDoktor(testDoktor);
        randevu.setRandevuTarihi(LocalDateTime.now().plusDays(1));
        randevu.setAciklama("Silinecek randevu");
        randevu.setDurum(Randevu.RandevuDurumu.BEKLEMEDE);
        Randevu kaydedilen = randevuRepository.save(randevu);

        // When & Then
        mockMvc.perform(delete("/api/randevular/" + kaydedilen.getId()))
                .andExpect(status().isOk());
    }
}
