package com.saglik.takip.service;

import com.saglik.takip.entity.TibbiRapor;
import com.saglik.takip.repository.TibbiRaporRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TibbiRaporServisi {

    @Autowired
    private TibbiRaporRepository tibbiRaporRepository;

    public TibbiRapor raporOlustur(TibbiRapor rapor) {
        return tibbiRaporRepository.save(rapor);
    }

    public List<TibbiRapor> tumRaporlariGetir() {
        return tibbiRaporRepository.findAll();
    }

    public Optional<TibbiRapor> raporBul(Long id) {
        return tibbiRaporRepository.findById(id);
    }

    public List<TibbiRapor> hastayaAitRaporlariGetir(Long hastaId) {
        return tibbiRaporRepository.findByHastaIdOrderByRaporTarihiDesc(hastaId);
    }

    public List<TibbiRapor> doktorunRaporlariniGetir(Long doktorId) {
        return tibbiRaporRepository.findByDoktorId(doktorId);
    }

    public List<TibbiRapor> raporTipineGoreRaporlariGetir(TibbiRapor.RaporTipi raporTipi) {
        return tibbiRaporRepository.findByRaporTipi(raporTipi);
    }

    public TibbiRapor raporGuncelle(Long id, TibbiRapor yeniRapor) {
        Optional<TibbiRapor> mevcutRapor = tibbiRaporRepository.findById(id);
        if (mevcutRapor.isPresent()) {
            TibbiRapor rapor = mevcutRapor.get();
            rapor.setBaslik(yeniRapor.getBaslik());
            rapor.setIcerik(yeniRapor.getIcerik());
            rapor.setRaporTipi(yeniRapor.getRaporTipi());
            rapor.setRaporTarihi(yeniRapor.getRaporTarihi());
            return tibbiRaporRepository.save(rapor);
        }
        throw new RuntimeException("Rapor bulunamadi: " + id);
    }

    public void raporSil(Long id) {
        tibbiRaporRepository.deleteById(id);
    }
}
