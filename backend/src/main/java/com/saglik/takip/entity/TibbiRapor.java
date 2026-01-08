package com.saglik.takip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tibbi_raporlar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TibbiRapor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id", nullable = false)
    private Kullanici hasta;

    @ManyToOne
    @JoinColumn(name = "doktor_id")
    private Kullanici doktor;

    @Column(nullable = false)
    private String baslik;

    @Column(length = 5000)
    private String icerik;

    @Enumerated(EnumType.STRING)
    private RaporTipi raporTipi;

    private LocalDateTime raporTarihi;

    private LocalDateTime olusturmaTarihi;

    public enum RaporTipi {
        GENEL_MUAYENE,
        LABORATUVAR,
        GORUNTULEME,
        TESHIS,
        TEDAVI_PLANI,
        TABURCU
    }

    @PrePersist
    protected void onCreate() {
        olusturmaTarihi = LocalDateTime.now();
        if (raporTarihi == null) {
            raporTarihi = LocalDateTime.now();
        }
    }
}
