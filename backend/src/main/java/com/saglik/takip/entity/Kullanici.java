package com.saglik.takip.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "kullanicilar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kullaniciAdi;

    @Column(nullable = false)
    private String sifre;

    @Column(nullable = false)
    private String adSoyad;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefon;

    @Enumerated(EnumType.STRING)
    private KullaniciRolu rol;

    private Integer yas;

    @Enumerated(EnumType.STRING)
    private Cinsiyet cinsiyet;

    private LocalDateTime kayitTarihi;

    private Boolean aktif = true;

    public enum KullaniciRolu {
        HASTA,
        DOKTOR,
        ADMIN
    }

    public enum Cinsiyet {
        ERKEK,
        KADIN,
        BELIRTILMEMIS
    }

    @PrePersist
    protected void onCreate() {
        kayitTarihi = LocalDateTime.now();
    }
}
