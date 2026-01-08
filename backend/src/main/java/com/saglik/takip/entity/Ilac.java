package com.saglik.takip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "ilaclar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ilac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Column(nullable = false)
    private String ilacAdi;

    private String doz;

    @Column(nullable = false)
    private Integer gunlukKullanimSayisi;

    private String kullanimSaatleri; // Ornek: "08:00,14:00,20:00"

    private LocalDateTime baslangicTarihi;

    private LocalDateTime bitisTarihi;

    private String notlar;

    private Boolean aktif = true;

    @PrePersist
    protected void onCreate() {
        if (baslangicTarihi == null) {
            baslangicTarihi = LocalDateTime.now();
        }
    }
}
