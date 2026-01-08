package com.saglik.takip.controller;

import com.saglik.takip.entity.SaglikVerisi;
import com.saglik.takip.service.SaglikVerisiServisi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/saglik-verileri")
@CrossOrigin(origins = "http://localhost:3000")
public class SaglikVerisiController {

    @Autowired
    private SaglikVerisiServisi saglikVerisiServisi;

    @PostMapping
    public ResponseEntity<?> saglikVerisiKaydet(@RequestBody SaglikVerisi saglikVerisi) {
        try {
            SaglikVerisi yeniVeri = saglikVerisiServisi.saglikVerisiKaydet(saglikVerisi);
            return ResponseEntity.status(HttpStatus.CREATED).body(yeniVeri);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Saglik verisi kaydedilirken hata olustu: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SaglikVerisi>> tumSaglikVerileriniGetir() {
        return ResponseEntity.ok(saglikVerisiServisi.tumSaglikVerileriniGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> saglikVerisiBul(@PathVariable Long id) {
        return saglikVerisiServisi.saglikVerisiBul(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/kullanici/{kullaniciId}")
    public ResponseEntity<List<SaglikVerisi>> kullaniciyaAitSaglikVerileriniGetir(@PathVariable Long kullaniciId) {
        return ResponseEntity.ok(saglikVerisiServisi.kullaniciyaAitSaglikVerileriniGetir(kullaniciId));
    }

    @GetMapping("/kullanici/{kullaniciId}/tip/{veriTipi}")
    public ResponseEntity<List<SaglikVerisi>> veriTipineGoreSaglikVerileriniGetir(
            @PathVariable Long kullaniciId,
            @PathVariable String veriTipi) {
        SaglikVerisi.VeriTipi tip = SaglikVerisi.VeriTipi.valueOf(veriTipi.toUpperCase());
        return ResponseEntity.ok(saglikVerisiServisi.veriTipineGoreSaglikVerileriniGetir(kullaniciId, tip));
    }

    @GetMapping("/kullanici/{kullaniciId}/tarih-araligi")
    public ResponseEntity<List<SaglikVerisi>> tarihAraliginaGoreSaglikVerileriniGetir(
            @PathVariable Long kullaniciId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime baslangic,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bitis) {
        return ResponseEntity.ok(saglikVerisiServisi.tarihAraliginaGoreSaglikVerileriniGetir(
                kullaniciId, baslangic, bitis));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> saglikVerisiGuncelle(@PathVariable Long id, @RequestBody SaglikVerisi saglikVerisi) {
        try {
            SaglikVerisi guncelVeri = saglikVerisiServisi.saglikVerisiGuncelle(id, saglikVerisi);
            return ResponseEntity.ok(guncelVeri);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> saglikVerisiSil(@PathVariable Long id) {
        try {
            saglikVerisiServisi.saglikVerisiSil(id);
            return ResponseEntity.ok("Saglik verisi silindi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
