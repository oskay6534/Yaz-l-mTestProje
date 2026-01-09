import React from 'react';

function AnaSayfa({ kullanici }) {
  return (
    <div>
      <div className="card">
        <h1>Hosgeldiniz, {kullanici.adSoyad}!</h1>
        <p>Saglik Takip Uygulamasina hos geldiniz.</p>
      </div>

      <div className="card">
        <h2>Hizli Erisim</h2>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '20px' }}>
          {kullanici.rol === 'HASTA' && (
            <>
              <div className="card" style={{ textAlign: 'center' }}>
                <h3>Saglik Verileri</h3>
                <p>Saglik verilerinizi takip edin</p>
              </div>
              <div className="card" style={{ textAlign: 'center' }}>
                <h3>Ilaclarim</h3>
                <p>Ilaç hatirlaticilarinizi yonetin</p>
              </div>
            </>
          )}
          <div className="card" style={{ textAlign: 'center' }}>
            <h3>Randevularim</h3>
            <p>{kullanici.rol === 'DOKTOR' ? 'Hasta randevularini goruntule' : 'Randevularinizi goruntuleyin'}</p>
          </div>
        </div>
      </div>

      <div className="card">
        <h2>Kullanici Bilgileri</h2>
        <p><strong>Email:</strong> {kullanici.email}</p>
        <p><strong>Telefon:</strong> {kullanici.telefon || 'Belirtilmemis'}</p>
        <p><strong>Yas:</strong> {kullanici.yas || 'Belirtilmemis'}</p>
        <p><strong>Rol:</strong> {kullanici.rol}</p>
      </div>
    </div>
  );
}

export default AnaSayfa;
