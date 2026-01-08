package com.saglik.takip.controller;

import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.service.KullaniciServisi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kullanicilar")
@CrossOrigin(origins = "http://localhost:3000")
public class KullaniciController {

    @Autowired
    private KullaniciServisi kullaniciServisi;

    @PostMapping("/kayit")
    public ResponseEntity<?> kullaniciKaydet(@RequestBody Kullanici kullanici) {
        try {
            if (kullaniciServisi.kullaniciAdiMevcutMu(kullanici.getKullaniciAdi())) {
                return ResponseEntity.badRequest().body("Bu kullanici adi zaten kullaniliyor");
            }
            if (kullaniciServisi.emailMevcutMu(kullanici.getEmail())) {
                return ResponseEntity.badRequest().body("Bu email zaten kullaniliyor");
            }
            Kullanici yeniKullanici = kullaniciServisi.kullaniciKaydet(kullanici);
            return ResponseEntity.status(HttpStatus.CREATED).body(yeniKullanici);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kullanici kaydedilirken hata olustu: " + e.getMessage());
        }
    }

    @PostMapping("/giris")
    public ResponseEntity<?> girisYap(@RequestBody Map<String, String> girisVerileri) {
        try {
            String kullaniciAdi = girisVerileri.get("kullaniciAdi");
            String sifre = girisVerileri.get("sifre");

            if (kullaniciServisi.girisYap(kullaniciAdi, sifre)) {
                Kullanici kullanici = kullaniciServisi.kullaniciAdiylaKullaniciBul(kullaniciAdi).get();
                return ResponseEntity.ok(kullanici);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Kullanici adi veya sifre hatali");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Giris yapilirken hata olustu: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Kullanici>> tumKullanicilariGetir() {
        return ResponseEntity.ok(kullaniciServisi.tumKullanicilariGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> kullaniciBul(@PathVariable Long id) {
        return kullaniciServisi.kullaniciBul(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<Kullanici>> roluneGoreKullanicilariGetir(@PathVariable String rol) {
        Kullanici.KullaniciRolu kullaniciRolu = Kullanici.KullaniciRolu.valueOf(rol.toUpperCase());
        return ResponseEntity.ok(kullaniciServisi.roluneGoreKullanicilariGetir(kullaniciRolu));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> kullaniciGuncelle(@PathVariable Long id, @RequestBody Kullanici kullanici) {
        try {
            Kullanici guncelKullanici = kullaniciServisi.kullaniciGuncelle(id, kullanici);
            return ResponseEntity.ok(guncelKullanici);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> kullaniciSil(@PathVariable Long id) {
        try {
            kullaniciServisi.kullaniciSil(id);
            return ResponseEntity.ok("Kullanici silindi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
