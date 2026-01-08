package com.saglik.takip.controller;

import com.saglik.takip.entity.TibbiRapor;
import com.saglik.takip.service.TibbiRaporServisi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/raporlar")
@CrossOrigin(origins = "http://localhost:3000")
public class TibbiRaporController {

    @Autowired
    private TibbiRaporServisi tibbiRaporServisi;

    @PostMapping
    public ResponseEntity<?> raporOlustur(@RequestBody TibbiRapor rapor) {
        try {
            TibbiRapor yeniRapor = tibbiRaporServisi.raporOlustur(rapor);
            return ResponseEntity.status(HttpStatus.CREATED).body(yeniRapor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Rapor olusturulurken hata olustu: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TibbiRapor>> tumRaporlariGetir() {
        return ResponseEntity.ok(tibbiRaporServisi.tumRaporlariGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> raporBul(@PathVariable Long id) {
        return tibbiRaporServisi.raporBul(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hasta/{hastaId}")
    public ResponseEntity<List<TibbiRapor>> hastayaAitRaporlariGetir(@PathVariable Long hastaId) {
        return ResponseEntity.ok(tibbiRaporServisi.hastayaAitRaporlariGetir(hastaId));
    }

    @GetMapping("/doktor/{doktorId}")
    public ResponseEntity<List<TibbiRapor>> doktorunRaporlariniGetir(@PathVariable Long doktorId) {
        return ResponseEntity.ok(tibbiRaporServisi.doktorunRaporlariniGetir(doktorId));
    }

    @GetMapping("/tip/{raporTipi}")
    public ResponseEntity<List<TibbiRapor>> raporTipineGoreRaporlariGetir(@PathVariable String raporTipi) {
        TibbiRapor.RaporTipi tip = TibbiRapor.RaporTipi.valueOf(raporTipi.toUpperCase());
        return ResponseEntity.ok(tibbiRaporServisi.raporTipineGoreRaporlariGetir(tip));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> raporGuncelle(@PathVariable Long id, @RequestBody TibbiRapor rapor) {
        try {
            TibbiRapor guncelRapor = tibbiRaporServisi.raporGuncelle(id, rapor);
            return ResponseEntity.ok(guncelRapor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> raporSil(@PathVariable Long id) {
        try {
            tibbiRaporServisi.raporSil(id);
            return ResponseEntity.ok("Rapor silindi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
