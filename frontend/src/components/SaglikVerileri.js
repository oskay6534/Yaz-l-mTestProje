import React, { useState, useEffect } from 'react';
import { kullanicininSaglikVerileriniGetir, saglikVerisiEkle, saglikVerisiSil } from '../services/api';

function SaglikVerileri({ kullanici }) {
  const [veriler, setVeriler] = useState([]);
  const [yeniVeri, setYeniVeri] = useState({
    veriTipi: 'TANSIYON_SISTOLIK',
    deger: '',
    birim: 'mmHg',
    notlar: ''
  });

  useEffect(() => {
    verileriYukle();
  }, []);

  const verileriYukle = async () => {
    try {
      const response = await kullanicininSaglikVerileriniGetir(kullanici.id);
      setVeriler(response.data);
    } catch (error) {
      console.error('Veriler yuklenirken hata:', error);
    }
  };

  const veriEkle = async (e) => {
    e.preventDefault();
    try {
      await saglikVerisiEkle({
        kullanici: { id: kullanici.id },
        ...yeniVeri,
        deger: parseFloat(yeniVeri.deger)
      });
      setYeniVeri({ veriTipi: 'TANSIYON_SISTOLIK', deger: '', birim: 'mmHg', notlar: '' });
      verileriYukle();
    } catch (error) {
      console.error('Veri eklenirken hata:', error);
    }
  };

  const veriSil = async (id) => {
    try {
      await saglikVerisiSil(id);
      verileriYukle();
    } catch (error) {
      console.error('Veri silinirken hata:', error);
    }
  };

  return (
    <div>
      <div className="card">
        <h2>Saglik Verileri</h2>
        <form onSubmit={veriEkle}>
          <div className="form-group">
            <label>Veri Tipi:</label>
            <select value={yeniVeri.veriTipi} onChange={(e) => setYeniVeri({...yeniVeri, veriTipi: e.target.value})} id="veriTipi">
              <option value="TANSIYON_SISTOLIK">Tansiyon Sistolik</option>
              <option value="TANSIYON_DIYASTOLIK">Tansiyon Diyastolik</option>
              <option value="KALP_RITMI">Kalp Ritmi</option>
              <option value="KAN_SEKERI">Kan Sekeri</option>
              <option value="KILO">Kilo</option>
              <option value="ATES">Ates</option>
            </select>
          </div>
          <div className="form-group">
            <label>Deger:</label>
            <input type="number" step="0.1" value={yeniVeri.deger} onChange={(e) => setYeniVeri({...yeniVeri, deger: e.target.value})} required id="deger" />
          </div>
          <div className="form-group">
            <label>Birim:</label>
            <input type="text" value={yeniVeri.birim} onChange={(e) => setYeniVeri({...yeniVeri, birim: e.target.value})} id="birim" />
          </div>
          <div className="form-group">
            <label>Notlar:</label>
            <textarea value={yeniVeri.notlar} onChange={(e) => setYeniVeri({...yeniVeri, notlar: e.target.value})} id="notlar" />
          </div>
          <button type="submit" className="btn btn-primary" id="veriEkleBtn">Veri Ekle</button>
        </form>
      </div>

      <div className="card">
        <h3>Kayitli Veriler</h3>
        <table id="verilerTablosu">
          <thead>
            <tr>
              <th>Tarih</th>
              <th>Tip</th>
              <th>Deger</th>
              <th>Birim</th>
              <th>Notlar</th>
              <th>Islem</th>
            </tr>
          </thead>
          <tbody>
            {veriler.map(veri => (
              <tr key={veri.id}>
                <td>{new Date(veri.olcumTarihi).toLocaleString('tr-TR')}</td>
                <td>{veri.veriTipi}</td>
                <td>{veri.deger}</td>
                <td>{veri.birim}</td>
                <td>{veri.notlar || '-'}</td>
                <td>
                  <button className="btn btn-danger" onClick={() => veriSil(veri.id)}>Sil</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default SaglikVerileri;
