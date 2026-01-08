package com.saglik.takip.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.entity.SaglikVerisi;
import com.saglik.takip.repository.KullaniciRepository;
import com.saglik.takip.repository.SaglikVerisiRepository;
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
public class SaglikVerisiControllerEntegrasyonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaglikVerisiRepository saglikVerisiRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Kullanici testKullanici;

    @BeforeEach
    public void setUp() {
        saglikVerisiRepository.deleteAll();
        kullaniciRepository.deleteAll();

        testKullanici = new Kullanici();
        testKullanici.setKullaniciAdi("testkullanici");
        testKullanici.setSifre("sifre123");
        testKullanici.setAdSoyad("Test Kullanici");
        testKullanici.setEmail("test@example.com");
        testKullanici.setRol(Kullanici.KullaniciRolu.HASTA);
        testKullanici = kullaniciRepository.save(testKullanici);
    }

    @Test
    public void saglikVerisiKaydet_Basarili() throws Exception {
        // Given
        SaglikVerisi yeniVeri = new SaglikVerisi();
        yeniVeri.setKullanici(testKullanici);
        yeniVeri.setVeriTipi(SaglikVerisi.VeriTipi.TANSIYON_SISTOLIK);
        yeniVeri.setDeger(120.0);
        yeniVeri.setBirim("mmHg");
        yeniVeri.setOlcumTarihi(LocalDateTime.now());

        // When & Then
        mockMvc.perform(post("/api/saglik-verileri")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(yeniVeri)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.deger").value(120.0))
                .andExpect(jsonPath("$.veriTipi").value("TANSIYON_SISTOLIK"));
    }

    @Test
    public void tumSaglikVerileriniGetir_Basarili() throws Exception {
        // Given
        SaglikVerisi veri1 = new SaglikVerisi();
        veri1.setKullanici(testKullanici);
        veri1.setVeriTipi(SaglikVerisi.VeriTipi.TANSIYON_SISTOLIK);
        veri1.setDeger(120.0);
        veri1.setBirim("mmHg");
        veri1.setOlcumTarihi(LocalDateTime.now());
        saglikVerisiRepository.save(veri1);

        SaglikVerisi veri2 = new SaglikVerisi();
        veri2.setKullanici(testKullanici);
        veri2.setVeriTipi(SaglikVerisi.VeriTipi.KALP_RITMI);
        veri2.setDeger(75.0);
        veri2.setBirim("bpm");
        veri2.setOlcumTarihi(LocalDateTime.now());
        saglikVerisiRepository.save(veri2);

        // When & Then
        mockMvc.perform(get("/api/saglik-verileri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void kullaniciyaAitSaglikVerileriniGetir_Basarili() throws Exception {
        // Given
        SaglikVerisi veri = new SaglikVerisi();
        veri.setKullanici(testKullanici);
        veri.setVeriTipi(SaglikVerisi.VeriTipi.KAN_SEKERI);
        veri.setDeger(95.0);
        veri.setBirim("mg/dL");
        veri.setOlcumTarihi(LocalDateTime.now());
        saglikVerisiRepository.save(veri);

        // When & Then
        mockMvc.perform(get("/api/saglik-verileri/kullanici/" + testKullanici.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].veriTipi").value("KAN_SEKERI"))
                .andExpect(jsonPath("$[0].deger").value(95.0));
    }

    @Test
    public void saglikVerisiSil_Basarili() throws Exception {
        // Given
        SaglikVerisi veri = new SaglikVerisi();
        veri.setKullanici(testKullanici);
        veri.setVeriTipi(SaglikVerisi.VeriTipi.KILO);
        veri.setDeger(75.0);
        veri.setBirim("kg");
        veri.setOlcumTarihi(LocalDateTime.now());
        SaglikVerisi kaydedilen = saglikVerisiRepository.save(veri);

        // When & Then
        mockMvc.perform(delete("/api/saglik-verileri/" + kaydedilen.getId()))
                .andExpect(status().isOk());
    }
}
