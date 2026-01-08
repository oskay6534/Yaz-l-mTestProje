import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { kullaniciGiris } from '../services/api';

function GirisYap({ setKullanici }) {
  const [kullaniciAdi, setKullaniciAdi] = useState('');
  const [sifre, setSifre] = useState('');
  const [hata, setHata] = useState('');
  const navigate = useNavigate();

  const girisYapHandler = async (e) => {
    e.preventDefault();
    try {
      const response = await kullaniciGiris(kullaniciAdi, sifre);
      setKullanici(response.data);
      localStorage.setItem('kullanici', JSON.stringify(response.data));
      navigate('/');
    } catch (error) {
      setHata('Kullanici adi veya sifre hatali!');
    }
  };

  return (
    <div className="card" style={{ maxWidth: '400px', margin: '50px auto' }}>
      <h2>Giris Yap</h2>
      {hata && <div style={{ color: 'red', marginBottom: '10px' }}>{hata}</div>}
      <form onSubmit={girisYapHandler}>
        <div className="form-group">
          <label>Kullanici Adi:</label>
          <input
            type="text"
            value={kullaniciAdi}
            onChange={(e) => setKullaniciAdi(e.target.value)}
            required
            id="kullaniciAdi"
          />
        </div>
        <div className="form-group">
          <label>Sifre:</label>
          <input
            type="password"
            value={sifre}
            onChange={(e) => setSifre(e.target.value)}
            required
            id="sifre"
          />
        </div>
        <button type="submit" className="btn btn-primary" id="girisBtn">
          Giris Yap
        </button>
        <p style={{ marginTop: '20px' }}>
          Hesabiniz yok mu? <Link to="/kayit">Kayit Ol</Link>
        </p>
      </form>
    </div>
  );
}

export default GirisYap;
