package com.saglik.takip.service;

import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KullaniciServisi {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Kullanici kullaniciKaydet(Kullanici kullanici) {
        // Sifre duz kaydediliyor (hash yok)
        return kullaniciRepository.save(kullanici);
    }

    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    public Optional<Kullanici> kullaniciBul(Long id) {
        return kullaniciRepository.findById(id);
    }

    public Optional<Kullanici> kullaniciAdiylaKullaniciBul(String kullaniciAdi) {
        return kullaniciRepository.findByKullaniciAdi(kullaniciAdi);
    }

    public Optional<Kullanici> emailIleKullaniciBul(String email) {
        return kullaniciRepository.findByEmail(email);
    }

    public List<Kullanici> roluneGoreKullanicilariGetir(Kullanici.KullaniciRolu rol) {
        return kullaniciRepository.findByRol(rol);
    }

    public Kullanici kullaniciGuncelle(Long id, Kullanici yeniKullanici) {
        Optional<Kullanici> mevcutKullanici = kullaniciRepository.findById(id);
        if (mevcutKullanici.isPresent()) {
            Kullanici kullanici = mevcutKullanici.get();
            kullanici.setAdSoyad(yeniKullanici.getAdSoyad());
            kullanici.setEmail(yeniKullanici.getEmail());
            kullanici.setTelefon(yeniKullanici.getTelefon());
            kullanici.setYas(yeniKullanici.getYas());
            kullanici.setCinsiyet(yeniKullanici.getCinsiyet());
            return kullaniciRepository.save(kullanici);
        }
        throw new RuntimeException("Kullanici bulunamadi: " + id);
    }

    public void kullaniciSil(Long id) {
        kullaniciRepository.deleteById(id);
    }

    public boolean kullaniciAdiMevcutMu(String kullaniciAdi) {
        return kullaniciRepository.existsByKullaniciAdi(kullaniciAdi);
    }

    public boolean emailMevcutMu(String email) {
        return kullaniciRepository.existsByEmail(email);
    }

    public boolean girisYap(String kullaniciAdi, String sifre) {
        Optional<Kullanici> kullanici = kullaniciRepository.findByKullaniciAdi(kullaniciAdi);
        if (kullanici.isPresent()) {
            // Duz sifre karsilastirmasi (hash yok)
            return sifre.equals(kullanici.get().getSifre());
        }
        return false;
    }
}
