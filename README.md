# NIDA Learning Platform — Backend

NIDA, Azərbaycan şagirdləri üçün hazırlanmış ağıllı öyrənmə platformasının backend servisidir.

## Texnologiyalar

- **Java 21** + **Spring Boot 3.4.5**
- **PostgreSQL 17** — əsas verilənlər bazası
- **Redis** — cache və session
- **Apache Kafka** — real-time event streaming
- **RabbitMQ** — email və bildiriş queue
- **WebSocket (STOMP)** — real-time bildirişlər
- **Docker** + **Docker Compose**
- **MapStruct** — DTO mapping
- **JWT** — autentifikasiya

## Arxitektura

Monolith arxitektura, modul strukturu ilə:
├── auth/              # Qeydiyyat, giriş, JWT
├── user/              # Şagird, müəllim, valideyn
├── test/              # Test mexanizmi, sual, cavab
├── analytics/         # İnkişaf statistikası
├── exam/              # Sinaq imtahanları
├── practice_exam/     # Müəllim tərəfindən sinaq imtahanları
├── permission/        # Müəllim-şagird icazə sistemi
├── messaging/         # Daxili mesajlaşma
├── payment/           # Ödəniş və balans
├── notification/      # Email + real-time bildirişlər
├── group/             # Qrup idarəetməsi
├── homework/          # Ev tapşırıqları
├── parent_child/      # Valideyn-uşaq bağlantısı
├── gamification/      # XP, level, badge, streak
├── dashboard/         # Dashboard data
├── live_session/      # Canlı dərslər
├── kafka/             # Real-time eventlər
├── config/            # Konfiqurasiyalar
└── common/            # Ümumi exception, response

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

### 3. Docker ilə işə sal
```bash
docker-compose up -d --build
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
| Practice Exams | `/api/practice-exams` |
| Permissions | `/api/permissions` |
| Messages | `/api/messages` |
| Payments | `/api/payments` |
| Notifications | `/api/notifications` |
| Groups | `/api/groups` |
| Homeworks | `/api/homeworks` |
| Parent-Child | `/api/parent-child` |
| Gamification | `/api/gamification` |
| Dashboard | `/api/dashboard` |
| Live Sessions | `/api/live-sessions` |

## Rollər

| Rol | Səlahiyyətlər |
|-----|---------------|
| `STUDENT` | Test həll et, statistikaya bax, mesaj göndər, qrupa qoşul |
| `TEACHER` | Şagirdləri idarə et, imtahan yarat, qrup yarat, canlı dərs keçir |
| `PARENT` | Uşağı izlə, ödəniş et, nəticəyə bax, müəllimlə əlaqə |
| `ADMIN` | Tam sistem idarəetməsi |

## WebSocket

Real-time bildirişlər üçün WebSocket endpoint:
ws://localhost:8080/ws

Subscribe:
/user/{userId}/queue/notifications

## Əlaqə

**Developer:** [@Aga011](https://github.com/Aga011)