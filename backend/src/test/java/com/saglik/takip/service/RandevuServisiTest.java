package com.saglik.takip.service;

import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.entity.Randevu;
import com.saglik.takip.repository.RandevuRepository;
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
public class RandevuServisiTest {

    @Mock
    private RandevuRepository randevuRepository;

    @InjectMocks
    private RandevuServisi randevuServisi;

    private Randevu testRandevu;
    private Kullanici testHasta;
    private Kullanici testDoktor;

    @BeforeEach
    public void setUp() {
        testHasta = new Kullanici();
        testHasta.setId(1L);
        testHasta.setKullaniciAdi("hasta");
        testHasta.setRol(Kullanici.KullaniciRolu.HASTA);

        testDoktor = new Kullanici();
        testDoktor.setId(2L);
        testDoktor.setKullaniciAdi("doktor");
        testDoktor.setRol(Kullanici.KullaniciRolu.DOKTOR);

        testRandevu = new Randevu();
        testRandevu.setId(1L);
        testRandevu.setHasta(testHasta);
        testRandevu.setDoktor(testDoktor);
        testRandevu.setRandevuTarihi(LocalDateTime.now().plusDays(1));
        testRandevu.setDurum(Randevu.RandevuDurumu.BEKLEMEDE);
        testRandevu.setAciklama("Test randevusu");
    }

    @Test
    public void randevuOlustur_Basarili() {
        // Given
        when(randevuRepository.save(any(Randevu.class))).thenReturn(testRandevu);

        // When
        Randevu sonuc = randevuServisi.randevuOlustur(testRandevu);

        // Then
        assertNotNull(sonuc);
        assertEquals(testRandevu.getAciklama(), sonuc.getAciklama());
        assertEquals(Randevu.RandevuDurumu.BEKLEMEDE, sonuc.getDurum());
        verify(randevuRepository, times(1)).save(any(Randevu.class));
    }

    @Test
    public void tumRandevulariGetir_Basarili() {
        // Given
        List<Randevu> randevular = Arrays.asList(testRandevu);
        when(randevuRepository.findAll()).thenReturn(randevular);

        // When
        List<Randevu> sonuc = randevuServisi.tumRandevulariGetir();

        // Then
        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        verify(randevuRepository, times(1)).findAll();
    }

    @Test
    public void randevuBul_Basarili() {
        // Given
        when(randevuRepository.findById(1L)).thenReturn(Optional.of(testRandevu));

        // When
        Optional<Randevu> sonuc = randevuServisi.randevuBul(1L);

        // Then
        assertTrue(sonuc.isPresent());
        assertEquals(testRandevu.getAciklama(), sonuc.get().getAciklama());
        verify(randevuRepository, times(1)).findById(1L);
    }

    @Test
    public void hastayaAitRandevulariGetir_Basarili() {
        // Given
        List<Randevu> randevular = Arrays.asList(testRandevu);
        when(randevuRepository.findByHastaId(1L)).thenReturn(randevular);

        // When
        List<Randevu> sonuc = randevuServisi.hastayaAitRandevulariGetir(1L);

        // Then
        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        assertEquals(testHasta.getId(), sonuc.get(0).getHasta().getId());
        verify(randevuRepository, times(1)).findByHastaId(1L);
    }

    @Test
    public void doktoraAitRandevulariGetir_Basarili() {
        // Given
        List<Randevu> randevular = Arrays.asList(testRandevu);
        when(randevuRepository.findByDoktorId(2L)).thenReturn(randevular);

        // When
        List<Randevu> sonuc = randevuServisi.doktoraAitRandevulariGetir(2L);

        // Then
        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        assertEquals(testDoktor.getId(), sonuc.get(0).getDoktor().getId());
        verify(randevuRepository, times(1)).findByDoktorId(2L);
    }

    @Test
    public void randevuOnayla_Basarili() {
        // Given
        when(randevuRepository.findById(1L)).thenReturn(Optional.of(testRandevu));
        when(randevuRepository.save(any(Randevu.class))).thenReturn(testRandevu);

        // When
        Randevu sonuc = randevuServisi.randevuOnayla(1L);

        // Then
        assertNotNull(sonuc);
        verify(randevuRepository, times(1)).findById(1L);
        verify(randevuRepository, times(1)).save(any(Randevu.class));
    }

    @Test
    public void randevuIptalEt_Basarili() {
        // Given
        when(randevuRepository.findById(1L)).thenReturn(Optional.of(testRandevu));
        when(randevuRepository.save(any(Randevu.class))).thenReturn(testRandevu);

        // When
        Randevu sonuc = randevuServisi.randevuIptalEt(1L);

        // Then
        assertNotNull(sonuc);
        verify(randevuRepository, times(1)).findById(1L);
        verify(randevuRepository, times(1)).save(any(Randevu.class));
    }

    @Test
    public void randevuSil_Basarili() {
        // When
        randevuServisi.randevuSil(1L);

        // Then
        verify(randevuRepository, times(1)).deleteById(1L);
    }
}
