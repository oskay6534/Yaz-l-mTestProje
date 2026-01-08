package com.saglik.takip.repository;

import com.saglik.takip.entity.Ilac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IlacRepository extends JpaRepository<Ilac, Long> {

    List<Ilac> findByKullaniciId(Long kullaniciId);

    List<Ilac> findByKullaniciIdAndAktif(Long kullaniciId, Boolean aktif);

    List<Ilac> findByAktif(Boolean aktif);
}
