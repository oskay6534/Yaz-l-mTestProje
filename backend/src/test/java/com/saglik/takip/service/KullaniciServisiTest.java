package com.saglik.takip.service;

import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.repository.KullaniciRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KullaniciServisiTest {

    @Mock
    private KullaniciRepository kullaniciRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private KullaniciServisi kullaniciServisi;

    private Kullanici testKullanici;

    @BeforeEach
    public void setUp() {
        testKullanici = new Kullanici();
        testKullanici.setId(1L);
        testKullanici.setKullaniciAdi("testkullanici");
        testKullanici.setSifre("sifre123");
        testKullanici.setAdSoyad("Test Kullanici");
        testKullanici.setEmail("test@example.com");
        testKullanici.setRol(Kullanici.KullaniciRolu.HASTA);
        testKullanici.setAktif(true);
    }

    @Test
    public void kullaniciKaydet_Basarili() {
        // Given
        when(kullaniciRepository.save(any(Kullanici.class))).thenReturn(testKullanici);

        // When
        Kullanici sonuc = kullaniciServisi.kullaniciKaydet(testKullanici);

        // Then
        assertNotNull(sonuc);
        assertEquals(testKullanici.getKullaniciAdi(), sonuc.getKullaniciAdi());
        // Plain text password kullaniliyor, passwordEncoder.encode() cagrilmiyor
        verify(kullaniciRepository, times(1)).save(any(Kullanici.class));
    }

    @Test
    public void tumKullanicilariGetir_Basarili() {
        // Given
        List<Kullanici> kullaniciListesi = Arrays.asList(testKullanici);
        when(kullaniciRepository.findAll()).thenReturn(kullaniciListesi);

        // When
        List<Kullanici> sonuc = kullaniciServisi.tumKullanicilariGetir();

        // Then
        assertNotNull(sonuc);
        assertEquals(1, sonuc.size());
        verify(kullaniciRepository, times(1)).findAll();
    }

    @Test
    public void kullaniciBul_Basarili() {
        // Given
        when(kullaniciRepository.findById(1L)).thenReturn(Optional.of(testKullanici));

        // When
        Optional<Kullanici> sonuc = kullaniciServisi.kullaniciBul(1L);

        // Then
        assertTrue(sonuc.isPresent());
        assertEquals(testKullanici.getKullaniciAdi(), sonuc.get().getKullaniciAdi());
        verify(kullaniciRepository, times(1)).findById(1L);
    }

    @Test
    public void kullaniciAdiylaKullaniciBul_Basarili() {
        // Given
        when(kullaniciRepository.findByKullaniciAdi("testkullanici"))
                .thenReturn(Optional.of(testKullanici));

        // When
        Optional<Kullanici> sonuc = kullaniciServisi.kullaniciAdiylaKullaniciBul("testkullanici");

        // Then
        assertTrue(sonuc.isPresent());
        assertEquals("testkullanici", sonuc.get().getKullaniciAdi());
        verify(kullaniciRepository, times(1)).findByKullaniciAdi("testkullanici");
    }

    @Test
    public void kullaniciAdiMevcutMu_DogruDonmeli() {
        // Given
        when(kullaniciRepository.existsByKullaniciAdi("testkullanici")).thenReturn(true);

        // When
        boolean sonuc = kullaniciServisi.kullaniciAdiMevcutMu("testkullanici");

        // Then
        assertTrue(sonuc);
        verify(kullaniciRepository, times(1)).existsByKullaniciAdi("testkullanici");
    }

    @Test
    public void emailMevcutMu_YanlisDonmeli() {
        // Given
        when(kullaniciRepository.existsByEmail("yenimail@example.com")).thenReturn(false);

        // When
        boolean sonuc = kullaniciServisi.emailMevcutMu("yenimail@example.com");

        // Then
        assertFalse(sonuc);
        verify(kullaniciRepository, times(1)).existsByEmail("yenimail@example.com");
    }

    @Test
    public void girisYap_BasariliGiris() {
        // Given
        when(kullaniciRepository.findByKullaniciAdi("testkullanici"))
                .thenReturn(Optional.of(testKullanici));

        // When
        boolean sonuc = kullaniciServisi.girisYap("testkullanici", "sifre123");

        // Then
        assertTrue(sonuc);
        verify(kullaniciRepository, times(1)).findByKullaniciAdi("testkullanici");
        // Plain text password karsilastiriliyor, passwordEncoder.matches() cagrilmiyor
    }

    @Test
    public void girisYap_YanlisSifre() {
        // Given
        when(kullaniciRepository.findByKullaniciAdi("testkullanici"))
                .thenReturn(Optional.of(testKullanici));

        // When
        boolean sonuc = kullaniciServisi.girisYap("testkullanici", "yanlisSifre");

        // Then
        assertFalse(sonuc);
    }

    @Test
    public void kullaniciSil_Basarili() {
        // When
        kullaniciServisi.kullaniciSil(1L);

        // Then
        verify(kullaniciRepository, times(1)).deleteById(1L);
    }
}
