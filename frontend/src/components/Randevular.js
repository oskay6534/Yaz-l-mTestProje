import React, { useState, useEffect } from 'react';
import { hastayaAitRandevulariGetir, doktoraAitRandevulariGetir, randevuOlustur, randevuIptalEt, doktorlariGetir, randevuDurumGuncelle } from '../services/api';

function Randevular({ kullanici }) {
  const [randevular, setRandevular] = useState([]);
  const [doktorlar, setDoktorlar] = useState([]);
  const [yeniRandevu, setYeniRandevu] = useState({
    doktorId: '',
    randevuTarihi: '',
    aciklama: ''
  });

  useEffect(() => {
    randevulariYukle();
    doktorlariYukle();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const randevulariYukle = async () => {
    try {
      let response;
      if (kullanici.rol === 'DOKTOR') {
        response = await doktoraAitRandevulariGetir(kullanici.id);
      } else {
        response = await hastayaAitRandevulariGetir(kullanici.id);
      }
      setRandevular(response.data);
    } catch (error) {
      console.error('Randevular yuklenirken hata:', error);
    }
  };

  const doktorlariYukle = async () => {
    try {
      const response = await doktorlariGetir();
      setDoktorlar(response.data);
    } catch (error) {
      console.error('Doktorlar yuklenirken hata:', error);
    }
  };

  const randevuOlusturHandler = async (e) => {
    e.preventDefault();
    try {
      await randevuOlustur({
        hasta: { id: kullanici.id },
        doktor: { id: parseInt(yeniRandevu.doktorId) },
        randevuTarihi: yeniRandevu.randevuTarihi,
        aciklama: yeniRandevu.aciklama,
        durum: 'BEKLEMEDE'
      });
      setYeniRandevu({ doktorId: '', randevuTarihi: '', aciklama: '' });
      randevulariYukle();
    } catch (error) {
      console.error('Randevu olusturulurken hata:', error);
    }
  };

  const randevuIptalEtHandler = async (id) => {
    try {
      await randevuIptalEt(id);
      randevulariYukle();
    } catch (error) {
      console.error('Randevu iptal edilirken hata:', error);
    }
  };

  const randevuOnaylaHandler = async (id) => {
    try {
      await randevuDurumGuncelle(id, 'ONAYLANDI');
      randevulariYukle();
    } catch (error) {
      console.error('Randevu onaylanirken hata:', error);
    }
  };

  return (
    <div>
      {kullanici.rol === 'HASTA' && (
        <div className="card">
          <h2>Yeni Randevu Olustur</h2>
          <form onSubmit={randevuOlusturHandler}>
            <div className="form-group">
              <label>Doktor Seciniz:</label>
              <select value={yeniRandevu.doktorId} onChange={(e) => setYeniRandevu({...yeniRandevu, doktorId: e.target.value})} required id="doktorId">
                <option value="">Doktor secin...</option>
                {doktorlar.map(doktor => (
                  <option key={doktor.id} value={doktor.id}>{doktor.adSoyad}</option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label>Randevu Tarihi:</label>
              <input type="datetime-local" value={yeniRandevu.randevuTarihi} onChange={(e) => setYeniRandevu({...yeniRandevu, randevuTarihi: e.target.value})} required id="randevuTarihi" />
            </div>
            <div className="form-group">
              <label>Aciklama:</label>
              <textarea value={yeniRandevu.aciklama} onChange={(e) => setYeniRandevu({...yeniRandevu, aciklama: e.target.value})} id="aciklama" />
            </div>
            <button type="submit" className="btn btn-primary" id="randevuOlusturBtn">Randevu Olustur</button>
          </form>
        </div>
      )}

      <div className="card">
        <h3>{kullanici.rol === 'DOKTOR' ? 'Hasta Randevulari' : 'Randevularim'}</h3>
        <table id="randevularTablosu">
          <thead>
            <tr>
              <th>Tarih</th>
              {kullanici.rol === 'DOKTOR' ? (
                <>
                  <th>Hasta</th>
                  <th>Telefon</th>
                  <th>Email</th>
                </>
              ) : (
                <th>Doktor</th>
              )}
              <th>Aciklama</th>
              <th>Durum</th>
              <th>Islem</th>
            </tr>
          </thead>
          <tbody>
            {randevular.map(randevu => (
              <tr key={randevu.id}>
                <td>{new Date(randevu.randevuTarihi).toLocaleString('tr-TR')}</td>
                {kullanici.rol === 'DOKTOR' ? (
                  <>
                    <td>{randevu.hasta.adSoyad}</td>
                    <td>{randevu.hasta.telefon}</td>
                    <td>{randevu.hasta.email}</td>
                  </>
                ) : (
                  <td>{randevu.doktor.adSoyad}</td>
                )}
                <td>{randevu.aciklama}</td>
                <td>{randevu.durum}</td>
                <td>
                  {kullanici.rol === 'DOKTOR' ? (
                    randevu.durum === 'BEKLEMEDE' && (
                      <button className="btn btn-success" onClick={() => randevuOnaylaHandler(randevu.id)}>Onayla</button>
                    )
                  ) : (
                    randevu.durum !== 'IPTAL_EDILDI' && randevu.durum !== 'TAMAMLANDI' && (
                      <button className="btn btn-danger" onClick={() => randevuIptalEtHandler(randevu.id)}>Iptal Et</button>
                    )
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Randevular;
