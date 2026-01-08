package com.saglik.takip.repository;

import com.saglik.takip.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);

    Optional<Kullanici> findByEmail(String email);

    List<Kullanici> findByRol(Kullanici.KullaniciRolu rol);

    List<Kullanici> findByAktif(Boolean aktif);

    boolean existsByKullaniciAdi(String kullaniciAdi);

    boolean existsByEmail(String email);
}
