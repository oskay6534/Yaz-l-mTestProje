-- Basit demo veriler - PostgreSQL uyumlu

-- Doktor kullanicilar
INSERT INTO kullanicilar (kullanici_adi, sifre, ad_soyad, email, telefon, rol, yas, cinsiyet, kayit_tarihi, aktif)
VALUES
('drali', 'doktor123', 'Dr. Ali Demir', 'drali@hastane.com', '5559876543', 'DOKTOR', 45, 'ERKEK', CURRENT_TIMESTAMP, true),
('drayse', 'doktor123', 'Dr. Ayşe Yılmaz', 'drayse@hastane.com', '5559876544', 'DOKTOR', 38, 'KADIN', CURRENT_TIMESTAMP, true);

-- Hasta kullanicilar
INSERT INTO kullanicilar (kullanici_adi, sifre, ad_soyad, email, telefon, rol, yas, cinsiyet, kayit_tarihi, aktif)
VALUES
('ahmet', 'hasta123', 'Ahmet Yılmaz', 'ahmet@gmail.com', '5551234567', 'HASTA', 35, 'ERKEK', CURRENT_TIMESTAMP, true),
('elif', 'hasta123', 'Elif Kaya', 'elif@gmail.com', '5551234568', 'HASTA', 28, 'KADIN', CURRENT_TIMESTAMP, true);

-- Admin kullanici
INSERT INTO kullanicilar (kullanici_adi, sifre, ad_soyad, email, telefon, rol, yas, cinsiyet, kayit_tarihi, aktif)
VALUES
('admin', 'admin123', 'Admin User', 'admin@hastane.com', '5550000000', 'ADMIN', 30, 'Erkek', CURRENT_TIMESTAMP, true);
