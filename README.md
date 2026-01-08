# Akıllı Sağlık Takip Uygulaması

YDG Dersi - Yazılım Test ve CI/CD Projesi

## Proje Hakkında

Bu proje, hastaların sağlık verilerini takip edebilecekleri, ilaç hatırlatıcıları oluşturabilecekleri ve doktor randevularını yönetebilecekleri kapsamlı bir web uygulamasıdır.

## Teknolojiler

### Backend
- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Security
- PostgreSQL
- H2 Database (Test)
- Maven

### Frontend
- React 18
- React Router
- Axios
- JavaScript (ES6+)

### Test
- JUnit 5
- Mockito
- Spring Boot Test
- Selenium WebDriver
- WebDriverManager

### DevOps
- Docker
- Docker Compose
- Jenkins
- Git/GitHub

## Özellikler

1. **Kullanıcı Yönetimi**
   - Kullanıcı kaydı ve girişi
   - Rol bazlı yetkilendirme (Hasta, Doktor, Admin)
   - Profil yönetimi

2. **Sağlık Verileri Takibi**
   - Tansiyon, kalp ritmi, kan şekeri gibi verilerin kaydı
   - Geçmiş verilerin görüntülenmesi
   - Veri filtreleme

3. **İlaç Hatırlatıcı**
   - İlaç ekleme ve yönetme
   - Kullanım saatleri belirleme
   - Aktif/pasif ilaç durumu

4. **Randevu Sistemi**
   - Doktor randevusu oluşturma
   - Randevu iptal etme
   - Randevu durumu takibi

## Kurulum

### Ön Gereksinimler
- Java 17+
- Maven 3.6+
- Node.js 18+
- Docker ve Docker Compose
- PostgreSQL 15+ (Docker olmadan çalıştırmak için)

### Backend Kurulumu
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend Kurulumu
```bash
cd frontend
npm install
npm start
```

### Docker ile Çalıştırma
```bash
docker-compose up -d
```

Uygulama şu adreslerde çalışacaktır:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- PostgreSQL: localhost:5432

## Test Yapısı

### Birim Testleri
```bash
cd backend
mvn test
```

### Entegrasyon Testleri
```bash
cd backend
mvn verify
```

### Selenium Testleri
```bash
cd backend
mvn test -Dtest=*SeleniumTest
```

## CI/CD Pipeline

Proje Jenkins ile otomatik test ve deployment sürecine sahiptir. Pipeline aşamaları:

1. **Github'dan Kodları Çekme** (5 puan)
2. **Build** (5 puan)
3. **Birim Testleri** (15 puan)
4. **Entegrasyon Testleri** (15 puan)
5. **Docker Container Oluşturma** (5 puan)
6. **Selenium Test Senaryoları** (55 puan)
   - Kullanıcı Kayıt ve Giriş
   - Sağlık Verisi Ekleme
   - Randevu Oluşturma
   - İlaç Yönetimi (Bonus)

### Jenkins Kurulumu

1. Jenkins'i yükleyin ve başlatın
2. Gerekli pluginler:
   - Pipeline
   - Git
   - Docker Pipeline
   - HTML Publisher
   - JUnit

3. Yeni Pipeline Job oluşturun
4. Repository URL'ini ekleyin
5. Jenkinsfile path: `Jenkinsfile`

## API Endpoints

### Kullanıcı
- POST `/api/kullanicilar/kayit` - Yeni kullanıcı kaydı
- POST `/api/kullanicilar/giris` - Kullanıcı girişi
- GET `/api/kullanicilar` - Tüm kullanıcılar
- GET `/api/kullanicilar/{id}` - Kullanıcı detayı

### Sağlık Verileri
- POST `/api/saglik-verileri` - Yeni veri ekleme
- GET `/api/saglik-verileri/kullanici/{id}` - Kullanıcının verileri
- DELETE `/api/saglik-verileri/{id}` - Veri silme

### İlaçlar
- POST `/api/ilaclar` - İlaç ekleme
- GET `/api/ilaclar/kullanici/{id}` - Kullanıcının ilaçları
- DELETE `/api/ilaclar/{id}` - İlaç silme

### Randevular
- POST `/api/randevular` - Randevu oluşturma
- GET `/api/randevular/hasta/{id}` - Hastanın randevuları
- PUT `/api/randevular/{id}/iptal-et` - Randevu iptal

## Puanlama Detayı

| Kriter | Puan |
|--------|------|
| Github'dan kod çekme | 5 |
| Build işlemi | 5 |
| Birim testleri | 15 |
| Entegrasyon testleri | 15 |
| Docker container | 5 |
| Selenium testleri (3 senaryo) | 55 |
| **TOPLAM** | **100** |

Bonus: Her ek Selenium senaryosu için +2 puan (max 10 senaryo)

## Geliştirici

- **Proje:** YDG Dersi CI/CD Projesi
- **Konu:** Akıllı Sağlık Takip Uygulaması

## Lisans

Bu proje eğitim amaçlı hazırlanmıştır.
