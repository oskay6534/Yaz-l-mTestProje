-- ================================================
-- AKILLI SAGLIK TAKIP UYGULAMASI
-- Baslangic Verileri (Demo & Test icin)
-- ================================================

-- 1. DOKTOR KULLANICILAR (Sifre: doktor123)
-- ================================================

INSERT INTO kullanicilar (kullanici_adi, sifre, ad_soyad, email, telefon, rol, yas, cinsiyet, kayit_tarihi, aktif)
VALUES
('drali', 'doktor123', 'Dr. Ali Demir', 'drali@hastane.com', '5559876543', 'DOKTOR', 45, 'ERKEK', NOW(), true),
('drayse', 'doktor123', 'Dr. Ayşe Yılmaz', 'drayse@hastane.com', '5559876544', 'DOKTOR', 38, 'KADIN', NOW(), true),
('drmehmet', 'doktor123', 'Dr. Mehmet Kaya', 'drmehmet@hastane.com', '5559876545', 'DOKTOR', 50, 'ERKEK', NOW(), true),
('drfatma', 'doktor123', 'Dr. Fatma Şahin', 'drfatma@hastane.com', '5559876546', 'DOKTOR', 42, 'KADIN', NOW(), true),
('drcan', 'doktor123', 'Dr. Can Öztürk', 'drcan@hastane.com', '5559876547', 'DOKTOR', 48, 'ERKEK', NOW(), true);

-- 2. DEMO HASTA KULLANICILAR (Sifre: hasta123)
-- ================================================
INSERT INTO kullanicilar (kullanici_adi, sifre, ad_soyad, email, telefon, rol, yas, cinsiyet, kayit_tarihi, aktif)
VALUES
('ahmet', 'hasta123', 'Ahmet Yılmaz', 'ahmet@gmail.com', '5551234567', 'HASTA', 35, 'ERKEK', NOW(), true),
('elif', 'hasta123', 'Elif Kaya', 'elif@gmail.com', '5551234568', 'HASTA', 28, 'KADIN', NOW(), true);

-- 3. ADMIN KULLANICI (Sifre: admin123)
-- ================================================
INSERT INTO kullanicilar (kullanici_adi, sifre, ad_soyad, email, telefon, rol, yas, cinsiyet, kayit_tarihi, aktif)
VALUES
('admin', 'admin123', 'Admin User', 'admin@hastane.com', '5550000000', 'ADMIN', 30, 'BELIRTILMEMIS', NOW(), true);

-- 4. ORNEK SAGLIK VERILERI (Ahmet icin)
-- ================================================
-- Ahmet'in ID'si 6 olacak (5 doktor + 1 önceki hasta)
INSERT INTO saglik_verileri (kullanici_id, veri_tipi, deger, birim, notlar, olcum_tarihi)
VALUES
-- Tansiyon verileri
(6, 'TANSIYON_SISTOLIK', 120, 'mmHg', 'Sabah olcumu - Normal', NOW() - INTERVAL '7 days'),
(6, 'TANSIYON_SISTOLIK', 125, 'mmHg', 'Aksam olcumu', NOW() - INTERVAL '6 days'),
(6, 'TANSIYON_SISTOLIK', 118, 'mmHg', 'Sabah olcumu', NOW() - INTERVAL '5 days'),
(6, 'TANSIYON_DIYASTOLIK', 80, 'mmHg', 'Sabah olcumu', NOW() - INTERVAL '7 days'),
(6, 'TANSIYON_DIYASTOLIK', 82, 'mmHg', 'Aksam olcumu', NOW() - INTERVAL '6 days'),

-- Kalp ritmi
(6, 'KALP_RITMI', 72, 'bpm', 'Istirahat halinde', NOW() - INTERVAL '5 days'),
(6, 'KALP_RITMI', 75, 'bpm', 'Normal', NOW() - INTERVAL '3 days'),
(6, 'KALP_RITMI', 68, 'bpm', 'Sabah olcumu', NOW() - INTERVAL '1 day'),

-- Kan sekeri
(6, 'KAN_SEKERI', 95, 'mg/dL', 'Aclik kan sekeri', NOW() - INTERVAL '4 days'),
(6, 'KAN_SEKERI', 110, 'mg/dL', 'Yemekten sonra', NOW() - INTERVAL '2 days'),

-- Kilo takibi
(6, 'KILO', 75.5, 'kg', 'Haftalik kilo takibi', NOW() - INTERVAL '7 days'),
(6, 'KILO', 75.2, 'kg', 'Haftalik kilo takibi', NOW());

-- 5. ORNEK ILACLAR (Ahmet icin)
-- ================================================
INSERT INTO ilaclar (kullanici_id, ilac_adi, doz, gunluk_kullanim_sayisi, kullanim_saatleri, baslangic_tarihi, bitis_tarihi, notlar, aktif)
VALUES
(6, 'Aspirin', '100mg', 2, '08:00,20:00', NOW() - INTERVAL '30 days', NOW() + INTERVAL '60 days', 'Yemekten sonra alinacak', true),
(6, 'Vitamin D', '1000 IU', 1, '09:00', NOW() - INTERVAL '15 days', NOW() + INTERVAL '75 days', 'Kahvalti ile birlikte', true),
(6, 'Omega-3', '1000mg', 1, '20:00', NOW() - INTERVAL '20 days', NOW() + INTERVAL '70 days', 'Aksam yemegi ile', true);

-- 6. ORNEK RANDEVULAR
-- ================================================
-- Gecmis randevular
INSERT INTO randevular (hasta_id, doktor_id, randevu_tarihi, durum, aciklama, doktor_notu, olusturma_tarihi)
VALUES
(6, 1, NOW() - INTERVAL '15 days', 'TAMAMLANDI', 'Genel kontrol muayenesi', 'Hasta genel olarak saglikli. Tansiyonu takip edilmeli.', NOW() - INTERVAL '20 days'),
(6, 2, NOW() - INTERVAL '10 days', 'TAMAMLANDI', 'Kan tahlili sonuclari', 'Kan degerleri normal aralıkta.', NOW() - INTERVAL '12 days');

-- Gelecek randevular
INSERT INTO randevular (hasta_id, doktor_id, randevu_tarihi, durum, aciklama, olusturma_tarihi)
VALUES
(6, 1, NOW() + INTERVAL '7 days', 'ONAYLANDI', 'Kontrol randevusu', NOW() - INTERVAL '2 days'),
(6, 3, NOW() + INTERVAL '14 days', 'BEKLEMEDE', 'Uzman gorusu', NOW());

-- 7. ORNEK TIBBI RAPORLAR
-- ================================================
INSERT INTO tibbi_raporlar (hasta_id, doktor_id, baslik, icerik, rapor_tipi, rapor_tarihi, olusturma_tarihi)
VALUES
(6, 1, 'Genel Muayene Raporu',
'Hasta Ahmet Yılmaz genel kontrol muayenesi icin klinigimize basvurmustur.

MUAYENE BULGULARI:
- Tansiyon: 120/80 mmHg (Normal)
- Nabiz: 72/dk (Normal)
- Ates: 36.5°C (Normal)
- Kilo: 75.5 kg

SONUC:
Hasta genel olarak saglikli durumdadir. Tansiyonun duzenli takip edilmesi onerilmistir.

ONERILER:
- Haftalik kilo takibi
- Tuz tuketiminin azaltilmasi
- Duzenli egzersiz (Haftada 3 gun, 30 dakika)

Kontrol randevusu: 1 ay sonra',
'GENEL_MUAYENE', NOW() - INTERVAL '15 days', NOW() - INTERVAL '15 days'),

(6, 2, 'Laboratuvar Sonuclari',
'HEMOGRAM:
- Hemoglobin: 14.5 g/dL (Normal: 13-17)
- Lökosit: 7200/mm3 (Normal: 4000-10000)
- Trombosit: 250000/mm3 (Normal: 150000-400000)

BIYOKIMYA:
- Aclik Kan Sekeri: 95 mg/dL (Normal: 70-100)
- Kolesterol: 180 mg/dL (Normal: <200)
- HDL: 55 mg/dL (Normal: >40)
- LDL: 110 mg/dL (Normal: <130)

SONUC: Tum degerler normal sinirlarda.',
'LABORATUVAR', NOW() - INTERVAL '10 days', NOW() - INTERVAL '10 days');

-- ================================================
-- SIFRE BILGILERI
-- ================================================
-- Doktorlar: drali, drayse, drmehmet, drfatma, drcan
--   Sifre: doktor123
--
-- Hastalar: ahmet, elif
--   Sifre: hasta123
--
-- Admin: admin
--   Sifre: admin123
-- ================================================
