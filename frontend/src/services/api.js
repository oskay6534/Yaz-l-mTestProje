import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Kullanici API
export const kullaniciKayit = (kullanici) => {
  return api.post('/kullanicilar/kayit', kullanici);
};

export const kullaniciGiris = (kullaniciAdi, sifre) => {
  return api.post('/kullanicilar/giris', { kullaniciAdi, sifre });
};

export const tumKullanicilariGetir = () => {
  return api.get('/kullanicilar');
};

export const kullaniciBul = (id) => {
  return api.get(`/kullanicilar/${id}`);
};

// Saglik Verisi API
export const saglikVerisiEkle = (veri) => {
  return api.post('/saglik-verileri', veri);
};

export const kullanicininSaglikVerileriniGetir = (kullaniciId) => {
  return api.get(`/saglik-verileri/kullanici/${kullaniciId}`);
};

export const saglikVerisiSil = (id) => {
  return api.delete(`/saglik-verileri/${id}`);
};

// Ilac API
export const ilacEkle = (ilac) => {
  return api.post('/ilaclar', ilac);
};

export const kullanicininIlaclariniGetir = (kullaniciId) => {
  return api.get(`/ilaclar/kullanici/${kullaniciId}`);
};

export const aktifIlaclarıGetir = (kullaniciId) => {
  return api.get(`/ilaclar/kullanici/${kullaniciId}/aktif`);
};

export const ilacSil = (id) => {
  return api.delete(`/ilaclar/${id}`);
};

export const ilaciPasifYap = (id) => {
  return api.put(`/ilaclar/${id}/pasif-yap`);
};

// Randevu API
export const randevuOlustur = (randevu) => {
  return api.post('/randevular', randevu);
};

export const hastayaAitRandevulariGetir = (hastaId) => {
  return api.get(`/randevular/hasta/${hastaId}`);
};

export const randevuIptalEt = (id) => {
  return api.put(`/randevular/${id}/iptal-et`);
};

export const randevuSil = (id) => {
  return api.delete(`/randevular/${id}`);
};

export const doktorlariGetir = () => {
  return api.get('/kullanicilar/rol/doktor');
};

export default api;
