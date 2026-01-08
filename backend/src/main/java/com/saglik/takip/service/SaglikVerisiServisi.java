package com.saglik.takip.service;

import com.saglik.takip.entity.SaglikVerisi;
import com.saglik.takip.repository.SaglikVerisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaglikVerisiServisi {

    @Autowired
    private SaglikVerisiRepository saglikVerisiRepository;

    public SaglikVerisi saglikVerisiKaydet(SaglikVerisi saglikVerisi) {
        return saglikVerisiRepository.save(saglikVerisi);
    }

    public List<SaglikVerisi> tumSaglikVerileriniGetir() {
        return saglikVerisiRepository.findAll();
    }

    public Optional<SaglikVerisi> saglikVerisiBul(Long id) {
        return saglikVerisiRepository.findById(id);
    }

    public List<SaglikVerisi> kullaniciyaAitSaglikVerileriniGetir(Long kullaniciId) {
        return saglikVerisiRepository.findByKullaniciIdOrderByOlcumTarihiDesc(kullaniciId);
    }

    public List<SaglikVerisi> veriTipineGoreSaglikVerileriniGetir(Long kullaniciId, SaglikVerisi.VeriTipi veriTipi) {
        return saglikVerisiRepository.findByKullaniciIdAndVeriTipi(kullaniciId, veriTipi);
    }

    public List<SaglikVerisi> tarihAraliginaGoreSaglikVerileriniGetir(
            Long kullaniciId,
            LocalDateTime baslangic,
            LocalDateTime bitis) {
        return saglikVerisiRepository.findByKullaniciIdAndOlcumTarihiBetween(
                kullaniciId, baslangic, bitis);
    }

    public SaglikVerisi saglikVerisiGuncelle(Long id, SaglikVerisi yeniVeri) {
        Optional<SaglikVerisi> mevcutVeri = saglikVerisiRepository.findById(id);
        if (mevcutVeri.isPresent()) {
            SaglikVerisi veri = mevcutVeri.get();
            veri.setDeger(yeniVeri.getDeger());
            veri.setNotlar(yeniVeri.getNotlar());
            veri.setOlcumTarihi(yeniVeri.getOlcumTarihi());
            return saglikVerisiRepository.save(veri);
        }
        throw new RuntimeException("Saglik verisi bulunamadi: " + id);
    }

    public void saglikVerisiSil(Long id) {
        saglikVerisiRepository.deleteById(id);
    }
}
