package com.saglik.takip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "randevular")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Randevu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id", nullable = false)
    private Kullanici hasta;

    @ManyToOne
    @JoinColumn(name = "doktor_id", nullable = false)
    private Kullanici doktor;

    @Column(nullable = false)
    private LocalDateTime randevuTarihi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RandevuDurumu durum;

    private String aciklama;

    private String doktorNotu;

    private LocalDateTime olusturmaTarihi;

    public enum RandevuDurumu {
        BEKLEMEDE,
        ONAYLANDI,
        TAMAMLANDI,
        IPTAL_EDILDI
    }

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();
        if (durum == null) {
            durum = RandevuDurumu.BEKLEMEDE;
        }
    }
}
