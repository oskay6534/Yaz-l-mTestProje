package com.saglik.takip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "saglik_verileri")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaglikVerisi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VeriTipi veriTipi;

    @Column(nullable = false)
    private Double deger;

    private String birim;

    private String notlar;

    @Column(nullable = false)
    private LocalDateTime olcumTarihi;

    public enum VeriTipi {
        TANSIYON_SISTOLIK,
        TANSIYON_DIYASTOLIK,
        KALP_RITMI,
        KAN_SEKERI,
        KILO,
        BOY,
        ATES,
        OKSIJEN_SATURASYONU
    }

    @PrePersist
    protected void onCreate() {
        if (olcumTarihi == null) {
            olcumTarihi = LocalDateTime.now();
        }
    }
}
