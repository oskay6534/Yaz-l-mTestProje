import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import GirisYap from './components/GirisYap';
import KayitOl from './components/KayitOl';
import AnaSayfa from './components/AnaSayfa';
import SaglikVerileri from './components/SaglikVerileri';
import Ilaclar from './components/Ilaclar';
import Randevular from './components/Randevular';

function App() {
  const [kullanici, setKullanici] = useState(null);

  const cikisYap = () => {
    setKullanici(null);
    localStorage.removeItem('kullanici');
  };

  return (
    <Router>
      <div className="App">
        {kullanici ? (
          <nav className="nav">
            <div className="container">
              <ul>
                <li><Link to="/">Ana Sayfa</Link></li>
                {kullanici.rol === 'HASTA' && (
                  <>
                    <li><Link to="/saglik-verileri">Saglik Verileri</Link></li>
                    <li><Link to="/ilaclar">Ilaclarim</Link></li>
                  </>
                )}
                <li><Link to="/randevular">{kullanici.rol === 'DOKTOR' ? 'Hasta Randevulari' : 'Randevularim'}</Link></li>
                <li style={{ marginLeft: 'auto' }}>
                  <span>Hosgeldiniz, {kullanici.adSoyad} ({kullanici.rol})</span>
                  <button className="btn btn-danger" onClick={cikisYap} style={{ marginLeft: '10px' }}>
                    Cikis Yap
                  </button>
                </li>
              </ul>
            </div>
          </nav>
        ) : null}

        <div className="container">
          <Routes>
            <Route path="/giris" element={<GirisYap setKullanici={setKullanici} />} />
            <Route path="/kayit" element={<KayitOl />} />
            <Route path="/" element={kullanici ? <AnaSayfa kullanici={kullanici} /> : <GirisYap setKullanici={setKullanici} />} />
            <Route path="/saglik-verileri" element={kullanici ? <SaglikVerileri kullanici={kullanici} /> : <GirisYap setKullanici={setKullanici} />} />
            <Route path="/ilaclar" element={kullanici ? <Ilaclar kullanici={kullanici} /> : <GirisYap setKullanici={setKullanici} />} />
            <Route path="/randevular" element={kullanici ? <Randevular kullanici={kullanici} /> : <GirisYap setKullanici={setKullanici} />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
