package com.saglik.takip.repository;

import com.saglik.takip.entity.TibbiRapor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TibbiRaporRepository extends JpaRepository<TibbiRapor, Long> {

    List<TibbiRapor> findByHastaId(Long hastaId);

    List<TibbiRapor> findByDoktorId(Long doktorId);

    List<TibbiRapor> findByRaporTipi(TibbiRapor.RaporTipi raporTipi);

    List<TibbiRapor> findByHastaIdOrderByRaporTarihiDesc(Long hastaId);
}
