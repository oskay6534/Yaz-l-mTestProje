package com.saglik.takip.service;

import com.saglik.takip.entity.Ilac;
import com.saglik.takip.repository.IlacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class IlacServisi {

    @Autowired
    private IlacRepository ilacRepository;

    public Ilac ilacKaydet(Ilac ilac) {
        return ilacRepository.save(ilac);
    }

    public List<Ilac> tumIlaclarıGetir() {
        return ilacRepository.findAll();
    }

    public Optional<Ilac> ilacBul(Long id) {
        return ilacRepository.findById(id);
    }

    public List<Ilac> kullaniciyaAitIlaclarıGetir(Long kullaniciId) {
        return ilacRepository.findByKullaniciId(kullaniciId);
    }

    public List<Ilac> aktifIlaclarıGetir(Long kullaniciId) {
        return ilacRepository.findByKullaniciIdAndAktif(kullaniciId, true);
    }

    public Ilac ilacGuncelle(Long id, Ilac yeniIlac) {
        Optional<Ilac> mevcutIlac = ilacRepository.findById(id);
        if (mevcutIlac.isPresent()) {
            Ilac ilac = mevcutIlac.get();
            ilac.setIlacAdi(yeniIlac.getIlacAdi());
            ilac.setDoz(yeniIlac.getDoz());
            ilac.setGunlukKullanimSayisi(yeniIlac.getGunlukKullanimSayisi());
            ilac.setKullanimSaatleri(yeniIlac.getKullanimSaatleri());
            ilac.setBitisTarihi(yeniIlac.getBitisTarihi());
            ilac.setNotlar(yeniIlac.getNotlar());
            ilac.setAktif(yeniIlac.getAktif());
            return ilacRepository.save(ilac);
        }
        throw new RuntimeException("Ilac bulunamadi: " + id);
    }

    public void ilacSil(Long id) {
        ilacRepository.deleteById(id);
    }

    public Ilac ilaciPasifYap(Long id) {
        Optional<Ilac> ilac = ilacRepository.findById(id);
        if (ilac.isPresent()) {
            Ilac guncelIlac = ilac.get();
            guncelIlac.setAktif(false);
            return ilacRepository.save(guncelIlac);
        }
        throw new RuntimeException("Ilac bulunamadi: " + id);
    }
}
