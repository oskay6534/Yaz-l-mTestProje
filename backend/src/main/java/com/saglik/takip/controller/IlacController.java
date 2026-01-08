package com.saglik.takip.controller;

import com.saglik.takip.entity.Ilac;
import com.saglik.takip.service.IlacServisi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ilaclar")
@CrossOrigin(origins = "http://localhost:3000")
public class IlacController {

    @Autowired
    private IlacServisi ilacServisi;

    @PostMapping
    public ResponseEntity<?> ilacKaydet(@RequestBody Ilac ilac) {
        try {
            Ilac yeniIlac = ilacServisi.ilacKaydet(ilac);
            return ResponseEntity.status(HttpStatus.CREATED).body(yeniIlac);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ilac kaydedilirken hata olustu: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Ilac>> tumIlaclarıGetir() {
        return ResponseEntity.ok(ilacServisi.tumIlaclarıGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ilacBul(@PathVariable Long id) {
        return ilacServisi.ilacBul(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/kullanici/{kullaniciId}")
    public ResponseEntity<List<Ilac>> kullaniciyaAitIlaclarıGetir(@PathVariable Long kullaniciId) {
        return ResponseEntity.ok(ilacServisi.kullaniciyaAitIlaclarıGetir(kullaniciId));
    }

    @GetMapping("/kullanici/{kullaniciId}/aktif")
    public ResponseEntity<List<Ilac>> aktifIlaclarıGetir(@PathVariable Long kullaniciId) {
        return ResponseEntity.ok(ilacServisi.aktifIlaclarıGetir(kullaniciId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> ilacGuncelle(@PathVariable Long id, @RequestBody Ilac ilac) {
        try {
            Ilac guncelIlac = ilacServisi.ilacGuncelle(id, ilac);
            return ResponseEntity.ok(guncelIlac);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/pasif-yap")
    public ResponseEntity<?> ilaciPasifYap(@PathVariable Long id) {
        try {
            Ilac guncelIlac = ilacServisi.ilaciPasifYap(id);
            return ResponseEntity.ok(guncelIlac);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> ilacSil(@PathVariable Long id) {
        try {
            ilacServisi.ilacSil(id);
            return ResponseEntity.ok("Ilac silindi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
