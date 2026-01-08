package com.saglik.takip.repository;

import com.saglik.takip.entity.Randevu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RandevuRepository extends JpaRepository<Randevu, Long> {

    List<Randevu> findByHastaId(Long hastaId);

    List<Randevu> findByDoktorId(Long doktorId);

    List<Randevu> findByDurum(Randevu.RandevuDurumu durum);

    List<Randevu> findByHastaIdAndDurum(Long hastaId, Randevu.RandevuDurumu durum);

    List<Randevu> findByDoktorIdAndRandevuTarihiBetween(
            Long doktorId,
            LocalDateTime baslangic,
            LocalDateTime bitis
    );

    List<Randevu> findByRandevuTarihiBetween(LocalDateTime baslangic, LocalDateTime bitis);
}
