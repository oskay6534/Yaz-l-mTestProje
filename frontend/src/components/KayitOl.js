import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { kullaniciKayit } from '../services/api';

function KayitOl() {
  const [formData, setFormData] = useState({
    kullaniciAdi: '',
    sifre: '',
    adSoyad: '',
    email: '',
    telefon: '',
    yas: '',
    cinsiyet: 'BELIRTILMEMIS',
    rol: 'HASTA'
  });
  const [hata, setHata] = useState('');
  const [basarili, setBasarili] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await kullaniciKayit(formData);
      setBasarili(true);
      setTimeout(() => navigate('/'), 2000);
    } catch (error) {
      setHata(error.response?.data || 'Kayit sirasinda hata olustu!');
    }
  };

  return (
    <div className="card" style={{ maxWidth: '500px', margin: '50px auto' }}>
      <h2>Kayit Ol</h2>
      {hata && <div style={{ color: 'red', marginBottom: '10px' }}>{hata}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Kullanici Adi:</label>
          <input type="text" name="kullaniciAdi" value={formData.kullaniciAdi} onChange={handleChange} required id="kullaniciAdi" />
        </div>
        <div className="form-group">
          <label>Sifre:</label>
          <input type="password" name="sifre" value={formData.sifre} onChange={handleChange} required id="sifre" />
        </div>
        <div className="form-group">
          <label>Ad Soyad:</label>
          <input type="text" name="adSoyad" value={formData.adSoyad} onChange={handleChange} required id="adSoyad" />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input type="email" name="email" value={formData.email} onChange={handleChange} required id="email" />
        </div>
        <div className="form-group">
          <label>Telefon:</label>
          <input type="tel" name="telefon" value={formData.telefon} onChange={handleChange} id="telefon" />
        </div>
        <div className="form-group">
          <label>Yas:</label>
          <input type="number" name="yas" value={formData.yas} onChange={handleChange} id="yas" />
        </div>
        <div className="form-group">
          <label>Cinsiyet:</label>
          <select name="cinsiyet" value={formData.cinsiyet} onChange={handleChange} id="cinsiyet">
            <option value="BELIRTILMEMIS">Belirtilmemis</option>
            <option value="ERKEK">Erkek</option>
            <option value="KADIN">Kadin</option>
          </select>
        </div>
        <button type="submit" className="btn btn-success" id="kayitBtn">Kayit Ol</button>
        {basarili && <div style={{ color: 'green', marginTop: '20px' }} id="basariliMesaj">Kayit basarili! Ana sayfaya yonlendiriliyorsunuz...</div>}
        <p style={{ marginTop: '20px' }}>
          Zaten hesabiniz var mi? <Link to="/giris">Giris Yap</Link>
        </p>
      </form>
    </div>
  );
}

export default KayitOl;
