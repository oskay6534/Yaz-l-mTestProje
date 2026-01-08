package com.saglik.takip.service;

import com.saglik.takip.entity.Randevu;
import com.saglik.takip.repository.RandevuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RandevuServisi {

    @Autowired
    private RandevuRepository randevuRepository;

    public Randevu randevuOlustur(Randevu randevu) {
        return randevuRepository.save(randevu);
    }

    public List<Randevu> tumRandevulariGetir() {
        return randevuRepository.findAll();
    }

    public Optional<Randevu> randevuBul(Long id) {
        return randevuRepository.findById(id);
    }

    public List<Randevu> hastayaAitRandevulariGetir(Long hastaId) {
        return randevuRepository.findByHastaId(hastaId);
    }

    public List<Randevu> doktoraAitRandevulariGetir(Long doktorId) {
        return randevuRepository.findByDoktorId(doktorId);
    }

    public List<Randevu> durumaGoreRandevulariGetir(Randevu.RandevuDurumu durum) {
        return randevuRepository.findByDurum(durum);
    }

    public List<Randevu> doktorunTarihAraliginaGoreRandevulariniGetir(
            Long doktorId,
            LocalDateTime baslangic,
            LocalDateTime bitis) {
        return randevuRepository.findByDoktorIdAndRandevuTarihiBetween(doktorId, baslangic, bitis);
    }

    public Randevu randevuGuncelle(Long id, Randevu yeniRandevu) {
        Optional<Randevu> mevcutRandevu = randevuRepository.findById(id);
        if (mevcutRandevu.isPresent()) {
            Randevu randevu = mevcutRandevu.get();
            randevu.setRandevuTarihi(yeniRandevu.getRandevuTarihi());
            randevu.setDurum(yeniRandevu.getDurum());
            randevu.setAciklama(yeniRandevu.getAciklama());
            randevu.setDoktorNotu(yeniRandevu.getDoktorNotu());
            return randevuRepository.save(randevu);
        }
        throw new RuntimeException("Randevu bulunamadi: " + id);
    }

    public Randevu randevuDurumunuGuncelle(Long id, Randevu.RandevuDurumu durum) {
        Optional<Randevu> randevu = randevuRepository.findById(id);
        if (randevu.isPresent()) {
            Randevu guncelRandevu = randevu.get();
            guncelRandevu.setDurum(durum);
            return randevuRepository.save(guncelRandevu);
        }
        throw new RuntimeException("Randevu bulunamadi: " + id);
    }

    public void randevuSil(Long id) {
        randevuRepository.deleteById(id);
    }

    public Randevu randevuIptalEt(Long id) {
        return randevuDurumunuGuncelle(id, Randevu.RandevuDurumu.IPTAL_EDILDI);
    }

    public Randevu randevuOnayla(Long id) {
        return randevuDurumunuGuncelle(id, Randevu.RandevuDurumu.ONAYLANDI);
    }
}
