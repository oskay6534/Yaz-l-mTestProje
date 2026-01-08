package com.saglik.takip.repository;

import com.saglik.takip.entity.Kullanici;
import com.saglik.takip.entity.SaglikVerisi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaglikVerisiRepository extends JpaRepository<SaglikVerisi, Long> {

    List<SaglikVerisi> findByKullanici(Kullanici kullanici);

    List<SaglikVerisi> findByKullaniciId(Long kullaniciId);

    List<SaglikVerisi> findByKullaniciIdAndVeriTipi(Long kullaniciId, SaglikVerisi.VeriTipi veriTipi);

    List<SaglikVerisi> findByKullaniciIdAndOlcumTarihiBetween(
            Long kullaniciId,
            LocalDateTime baslangic,
            LocalDateTime bitis
    );

    List<SaglikVerisi> findByKullaniciIdOrderByOlcumTarihiDesc(Long kullaniciId);
}
