# NIDA Learning Platform — Backend

NIDA, Azərbaycan şagirdləri üçün hazırlanmış ağıllı öyrənmə platformasının backend servisidir.

## Texnologiyalar

- **Java 21** + **Spring Boot 3.4.5**
- **PostgreSQL 17** — əsas verilənlər bazası
- **Redis** — cache və session
- **Apache Kafka** — real-time event streaming
- **RabbitMQ** — email və bildiriş queue
- **Docker** + **Docker Compose**
- **MapStruct** — DTO mapping
- **JWT** — autentifikasiya

## Arxitektura

Monolith arxitektura, modul strukturu ilə:
├── auth/          # Qeydiyyat, giriş, JWT
├── user/          # Şagird, müəllim, valideyn
├── test/          # Test mexanizmi, sual, cavab
├── analytics/     # İnkişaf statistikası
├── exam/          # Sinaq imtahanları
├── permission/    # Müəllim-şagird icazə sistemi
├── messaging/     # Daxili mesajlaşma
├── payment/       # Ödəniş və balans
├── notification/  # Email bildirişləri
├── kafka/         # Real-time eventlər
├── config/        # Konfiqurasiyalar
└── common/        # Ümumi exception, response

## Başlamaq üçün

### Tələblər
- Java 21+
- Docker Desktop
- Maven 3.9+

### 1. Repo-nu clone et
```bash
git clone https://github.com/Aga011/nida-platform-backend.git
cd nida-platform-backend
```

### 2. `.env` faylı yarat
```bash
cp .env.example .env
# .env faylını öz məlumatlarınla doldur
```

### 3. Docker servislərini qaldır
```bash
docker-compose up -d
```

### 4. Layihəni işə sal
```bash
mvn spring-boot:run
```

Server `http://localhost:8080` ünvanında işləyəcək.

## API Endpoint-lər

| Modul | Base URL |
|-------|----------|
| Auth | `/api/auth` |
| Users | `/api/users` |
| Tests | `/api/tests` |
| Analytics | `/api/analytics` |
| Exams | `/api/exams` |
| Permissions | `/api/permissions` |
| Messages | `/api/messages` |
| Payments | `/api/payments` |

## Rollər

| Rol | Səlahiyyətlər |
|-----|---------------|
| `STUDENT` | Test həll et, statistikaya bax, mesaj göndər |
| `TEACHER` | Şagirdləri idarə et, imtahan yarat, analiz et |
| `PARENT` | Uşağı izlə, ödəniş et, nəticəyə bax |
| `ADMIN` | Tam sistem idarəetməsi |

## Əlaqə

**Developer:** [@Aga011](https://github.com/Aga011)