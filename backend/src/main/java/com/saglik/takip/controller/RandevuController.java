package com.saglik.takip.controller;

import com.saglik.takip.entity.Randevu;
import com.saglik.takip.service.RandevuServisi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/randevular")
@CrossOrigin(origins = "http://localhost:3000")
public class RandevuController {

    @Autowired
    private RandevuServisi randevuServisi;

    @PostMapping
    public ResponseEntity<?> randevuOlustur(@RequestBody Randevu randevu) {
        try {
            Randevu yeniRandevu = randevuServisi.randevuOlustur(randevu);
            return ResponseEntity.status(HttpStatus.CREATED).body(yeniRandevu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Randevu olusturulurken hata olustu: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Randevu>> tumRandevulariGetir() {
        return ResponseEntity.ok(randevuServisi.tumRandevulariGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> randevuBul(@PathVariable Long id) {
        return randevuServisi.randevuBul(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hasta/{hastaId}")
    public ResponseEntity<List<Randevu>> hastayaAitRandevulariGetir(@PathVariable Long hastaId) {
        return ResponseEntity.ok(randevuServisi.hastayaAitRandevulariGetir(hastaId));
    }

    @GetMapping("/doktor/{doktorId}")
    public ResponseEntity<List<Randevu>> doktoraAitRandevulariGetir(@PathVariable Long doktorId) {
        return ResponseEntity.ok(randevuServisi.doktoraAitRandevulariGetir(doktorId));
    }

    @GetMapping("/durum/{durum}")
    public ResponseEntity<List<Randevu>> durumaGoreRandevulariGetir(@PathVariable String durum) {
        Randevu.RandevuDurumu randevuDurumu = Randevu.RandevuDurumu.valueOf(durum.toUpperCase());
        return ResponseEntity.ok(randevuServisi.durumaGoreRandevulariGetir(randevuDurumu));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> randevuGuncelle(@PathVariable Long id, @RequestBody Randevu randevu) {
        try {
            Randevu guncelRandevu = randevuServisi.randevuGuncelle(id, randevu);
            return ResponseEntity.ok(guncelRandevu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/onayla")
    public ResponseEntity<?> randevuOnayla(@PathVariable Long id) {
        try {
            Randevu guncelRandevu = randevuServisi.randevuOnayla(id);
            return ResponseEntity.ok(guncelRandevu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/iptal-et")
    public ResponseEntity<?> randevuIptalEt(@PathVariable Long id) {
        try {
            Randevu guncelRandevu = randevuServisi.randevuIptalEt(id);
            return ResponseEntity.ok(guncelRandevu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> randevuSil(@PathVariable Long id) {
        try {
            randevuServisi.randevuSil(id);
            return ResponseEntity.ok("Randevu silindi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
