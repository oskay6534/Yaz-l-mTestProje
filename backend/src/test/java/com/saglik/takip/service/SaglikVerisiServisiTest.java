package com.saglik.takip.service;

import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.entity.SaglikVerisi;
import com.saglik.takip.repository.SaglikVerisiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaglikVerisiServisiTest {

    @Mock
    private SaglikVerisiRepository saglikVerisiRepository;

    @InjectMocks
    private SaglikVerisiServisi saglikVerisiServisi;

    private SaglikVerisi testSaglikVerisi;
    private Kullanici testKullanici;

    @BeforeEach
    public void setUp() {
        testKullanici = new Kullanici();
        testKullanici.setId(1L);
        testKullanici.setKullaniciAdi("testkullanici");

        testSaglikVerisi = new SaglikVerisi();
        testSaglikVerisi.setId(1L);
        testSaglikVerisi.setKullanici(testKullanici);
        testSaglikVerisi.setVeriTipi(SaglikVerisi.VeriTipi.TANSIYON_SISTOLIK);
        testSaglikVerisi.setDeger(120.0);
        testSaglikVerisi.setBirim("mmHg");
        testSaglikVerisi.setOlcumTarihi(LocalDateTime.now());
    }

    @Test
    public void saglikVerisiKaydet_Basarili() {
        // Given
        when(saglikVerisiRepository.save(any(SaglikVerisi.class))).thenReturn(testSaglikVerisi);

        // When
        SaglikVerisi sonuc = saglikVerisiServisi.saglikVerisiKaydet(testSaglikVerisi);

        // Then
        assertNotNull(sonuc);
        assertEquals(testSaglikVerisi.getDeger(), sonuc.getDeger());
        assertEquals(testSaglikVerisi.getVeriTipi(), sonuc.getVeriTipi());
        verify(saglikVerisiRepository, times(1)).save(any(SaglikVerisi.class));
    }

    @Test
    public void tumSaglikVerileriniGetir_Basarili() {
        // Given
        List<SaglikVerisi> veriler = Arrays.asList(testSaglikVerisi);
        when(saglikVerisiRepository.findAll()).thenReturn(veriler);

        // When
        List<SaglikVerisi> sonuc = saglikVerisiServisi.tumSaglikVerileriniGetir();

        // Then
        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        verify(saglikVerisiRepository, times(1)).findAll();
    }

    @Test
    public void saglikVerisiBul_Basarili() {
        // Given
        when(saglikVerisiRepository.findById(1L)).thenReturn(Optional.of(testSaglikVerisi));

        // When
        Optional<SaglikVerisi> sonuc = saglikVerisiServisi.saglikVerisiBul(1L);

        // Then
        assertTrue(sonuc.isPresent());
        assertEquals(testSaglikVerisi.getDeger(), sonuc.get().getDeger());
        verify(saglikVerisiRepository, times(1)).findById(1L);
    }

    @Test
    public void kullaniciyaAitSaglikVerileriniGetir_Basarili() {
        // Given
        List<SaglikVerisi> veriler = Arrays.asList(testSaglikVerisi);
        when(saglikVerisiRepository.findByKullaniciIdOrderByOlcumTarihiDesc(1L)).thenReturn(veriler);

        // When
        List<SaglikVerisi> sonuc = saglikVerisiServisi.kullaniciyaAitSaglikVerileriniGetir(1L);

        // Then
        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        assertEquals(testSaglikVerisi.getKullanici().getId(), sonuc.get(0).getKullanici().getId());
        verify(saglikVerisiRepository, times(1)).findByKullaniciIdOrderByOlcumTarihiDesc(1L);
    }

    @Test
    public void veriTipineGoreSaglikVerileriniGetir_Basarili() {
        // Given
        List<SaglikVerisi> veriler = Arrays.asList(testSaglikVerisi);
        when(saglikVerisiRepository.findByKullaniciIdAndVeriTipi(1L, SaglikVerisi.VeriTipi.TANSIYON_SISTOLIK))
                .thenReturn(veriler);

        // When
        List<SaglikVerisi> sonuc = saglikVerisiServisi.veriTipineGoreSaglikVerileriniGetir(
                1L, SaglikVerisi.VeriTipi.TANSIYON_SISTOLIK);

        // Then
        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        assertEquals(SaglikVerisi.VeriTipi.TANSIYON_SISTOLIK, sonuc.get(0).getVeriTipi());
    }

    @Test
    public void saglikVerisiSil_Basarili() {
        // When
        saglikVerisiServisi.saglikVerisiSil(1L);

        // Then
        verify(saglikVerisiRepository, times(1)).deleteById(1L);
    }

    @Test
    public void saglikVerisiGuncelle_Basarili() {
        // Given
        SaglikVerisi guncelVeri = new SaglikVerisi();
        guncelVeri.setDeger(130.0);
        guncelVeri.setNotlar("Yeni not");
        guncelVeri.setOlcumTarihi(LocalDateTime.now());

        when(saglikVerisiRepository.findById(1L)).thenReturn(Optional.of(testSaglikVerisi));
        when(saglikVerisiRepository.save(any(SaglikVerisi.class))).thenReturn(testSaglikVerisi);

        // When
        SaglikVerisi sonuc = saglikVerisiServisi.saglikVerisiGuncelle(1L, guncelVeri);

        // Then
        assertNotNull(sonuc);
        verify(saglikVerisiRepository, times(1)).findById(1L);
        verify(saglikVerisiRepository, times(1)).save(any(SaglikVerisi.class));
    }
}
