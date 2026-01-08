import React, { useState, useEffect } from 'react';
import { kullanicininIlaclariniGetir, ilacEkle, ilacSil } from '../services/api';

function Ilaclar({ kullanici }) {
  const [ilaclar, setIlaclar] = useState([]);
  const [yeniIlac, setYeniIlac] = useState({
    ilacAdi: '',
    doz: '',
    gunlukKullanimSayisi: '',
    kullanimSaatleri: '',
    notlar: ''
  });

  useEffect(() => {
    ilaclariYukle();
  }, []);

  const ilaclariYukle = async () => {
    try {
      const response = await kullanicininIlaclariniGetir(kullanici.id);
      setIlaclar(response.data);
    } catch (error) {
      console.error('Ilaclar yuklenirken hata:', error);
    }
  };

  const ilacEkleHandler = async (e) => {
    e.preventDefault();
    try {
      await ilacEkle({
        kullanici: { id: kullanici.id },
        ...yeniIlac,
        gunlukKullanimSayisi: parseInt(yeniIlac.gunlukKullanimSayisi)
      });
      setYeniIlac({ ilacAdi: '', doz: '', gunlukKullanimSayisi: '', kullanimSaatleri: '', notlar: '' });
      ilaclariYukle();
    } catch (error) {
      console.error('Ilac eklenirken hata:', error);
    }
  };

  const ilacSilHandler = async (id) => {
    try {
      await ilacSil(id);
      ilaclariYukle();
    } catch (error) {
      console.error('Ilac silinirken hata:', error);
    }
  };

  return (
    <div>
      <div className="card">
        <h2>Ilac Ekle</h2>
        <form onSubmit={ilacEkleHandler}>
          <div className="form-group">
            <label>Ilac Adi:</label>
            <input type="text" value={yeniIlac.ilacAdi} onChange={(e) => setYeniIlac({...yeniIlac, ilacAdi: e.target.value})} required id="ilacAdi" />
          </div>
          <div className="form-group">
            <label>Doz:</label>
            <input type="text" value={yeniIlac.doz} onChange={(e) => setYeniIlac({...yeniIlac, doz: e.target.value})} id="doz" />
          </div>
          <div className="form-group">
            <label>Gunluk Kullanim Sayisi:</label>
            <input type="number" value={yeniIlac.gunlukKullanimSayisi} onChange={(e) => setYeniIlac({...yeniIlac, gunlukKullanimSayisi: e.target.value})} required id="gunlukKullanimSayisi" />
          </div>
          <div className="form-group">
            <label>Kullanim Saatleri (Ornek: 08:00,14:00,20:00):</label>
            <input type="text" value={yeniIlac.kullanimSaatleri} onChange={(e) => setYeniIlac({...yeniIlac, kullanimSaatleri: e.target.value})} id="kullanimSaatleri" />
          </div>
          <div className="form-group">
            <label>Notlar:</label>
            <textarea value={yeniIlac.notlar} onChange={(e) => setYeniIlac({...yeniIlac, notlar: e.target.value})} id="notlar" />
          </div>
          <button type="submit" className="btn btn-primary" id="ilacEkleBtn">Ilac Ekle</button>
        </form>
      </div>

      <div className="card">
        <h3>Ilaclarim</h3>
        <table id="ilaclarTablosu">
          <thead>
            <tr>
              <th>Ilac Adi</th>
              <th>Doz</th>
              <th>Gunluk Kullanim</th>
              <th>Saatler</th>
              <th>Durum</th>
              <th>Islem</th>
            </tr>
          </thead>
          <tbody>
            {ilaclar.map(ilac => (
              <tr key={ilac.id}>
                <td>{ilac.ilacAdi}</td>
                <td>{ilac.doz}</td>
                <td>{ilac.gunlukKullanimSayisi}</td>
                <td>{ilac.kullanimSaatleri}</td>
                <td>{ilac.aktif ? 'Aktif' : 'Pasif'}</td>
                <td>
                  <button className="btn btn-danger" onClick={() => ilacSilHandler(ilac.id)}>Sil</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Ilaclar;
