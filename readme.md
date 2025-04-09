# 🐰 RabbitMQ Messaging Service

Spring Boot + RabbitMQ 기반의 메시지 전송 시스템입니다.  
우선순위 메시지 큐, 자동 바인딩 설정, 컨테이너 기반 실행 환경을 포함합니다.

---

## 🛠 기술 스택

- **Java 17**
- **Spring Boot 3.4.4**
- **RabbitMQ 4.0.8 (with clustering)**
- **Docker Compose**
- Gradle + Spotless (Google Java Format)

---

## 📁 프로젝트 구조

```
├── src/main/java/dev/mq/rabbit
│   ├── config            # RabbitMQ 설정 및 연결 정보
│   ├── controller        # 메시지 전송 REST API
│   ├── dto               # 요청/응답/우선순위 관련 DTO
│   ├── service           # 비즈니스 로직 (RabbitTemplate)
│   └── RabbitApplication.java
├── docker-compose.yml    # RabbitMQ 클러스터 + Prometheus + Grafana
├── build.gradle           # 의존성 및 포맷 설정
├── application.yml        # 전체 환경 설정
```

---

## ⚙️ 실행 방법

### 1. RabbitMQ 클러스터 실행

```bash
docker compose up -d
```

RabbitMQ는 3노드 클러스터로 구성됩니다.

- UI 접속: [http://localhost:15672](http://localhost:15672) (`guest/guest`)

### 2. Spring Boot 애플리케이션 실행

```bash
./gradlew bootRun
```

> 💡 `spring.docker.compose.enabled=false` 상태이므로 직접 실행 순서 관리해야 합니다.

---

## 📡 REST API

### 메시지 전송

```http
POST /send
Content-Type: application/json

{
  "message": "긴급 알림입니다",
  "priority": "HIGH"
}
```

응답:

```json
{
  "message": "메시지 전송 성공",
  "code": 200,
  "data": {
    "message": "긴급 알림입니다",
    "priority": "HIGH",
    "connectedHost": "rabbit1",
    "exchange": "sample-exchange",
    "routingKey": "key"
  }
}
```

---

## 📦 주요 설정 (application.yml)

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    queue:
      name: sample-queue
    exchange:
      name: sample-exchange
    routing:
      key: key
```

---

## 🔍 코드 포맷 자동화

Google Java Style 적용:

```bash
./gradlew spotlessApply
```

---

## 📊 모니터링 (옵션)

- Prometheus: [http://localhost:9090](http://localhost:9090)
- Grafana: [http://localhost:3000](http://localhost:3000) (admin/admin)

---

## 📝 참고

- MQ 연결은 `@PostConstruct`로 최대 10회(5초 간격) 재시도
- 메시지는 `RabbitTemplate`로 전송되며, 우선순위는 `x-max-priority`로 지정됨
- 우선순위는 `LOW`, `MEDIUM`, `HIGH`로 구분됨
