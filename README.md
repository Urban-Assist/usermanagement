# <div align="center">

# 👥 User Management Microservice

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://github.com/username/urban-assist)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/license-Apache%202.0-red.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](http://makeapullrequest.com)

<img src="https://via.placeholder.com/200?text=User+Management" alt="User Management Service Logo" width="200"/>

*Comprehensive user profile and provider management for Urban Assist platform*

[Getting Started](#-getting-started) •
[Installation](#-installation) •
[Configuration](#-configuration) •
[API](#-api-endpoints) •
[Docker](#-docker) •
[Kubernetes](#-kubernetes) •
[Contributing](#-contributing)

</div>

---

## 📚 Overview

This microservice handles user profiles, provider management, and availability scheduling within the Urban Assist application. Built with Spring Boot and Java 17, it provides robust integration with MySQL for data persistence and RabbitMQ for asynchronous messaging.

<details>
<summary>✨ Key Features</summary>

- **User Profile Management** - Complete CRUD operations for user profiles
- **Provider Management** - Comprehensive provider profile handling with certification
- **Availability Management** - Scheduling and time slot management for providers
- **Booking System** - Integrated booking management with payment handling
- **JWT Authentication** - Secure endpoints with token-based authentication
- **Role-Based Authorization** - Granular access control for users and providers
- **Database Integration** - JPA/Hibernate with MySQL
- **Message Queue** - RabbitMQ integration for async operations
- **Kubernetes Ready** - Containerized deployment with full K8s support
- **High Availability** - Support for horizontal scaling
- **Configuration Management** - Externalized configuration via ConfigMaps and Secrets

</details>

---

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java** (JDK 17 or higher)
- **Maven** (3.8.4 or higher)
- **Git** for version control
- **Docker** for containerization
- **Kubernetes** for orchestration
- **MySQL** database
- **RabbitMQ** message broker

### 📦 Installation

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   ```

2. **Navigate to the user management directory**:
   ```bash
   cd urban-assist/usermanagement
   ```

3. **Build the project**:
   ```bash
   ./mvnw clean package
   ```

---

## ⚙️ Configuration

### Environment Variables

The service uses Kubernetes secrets and configmaps for configuration. Key variables from k8s/secret.yaml:

```plaintext
SPRING_DATASOURCE_USERNAME: root
SPRING_DATASOURCE_PASSWORD: admindevelopers
JWT_SECRET: e7b03c0c0329ed5a8bbac042d38c6d93f7344516ac51203552476cf58f07b62c
SPRING_RABBITMQ_USERNAME: guest
SPRING_RABBITMQ_PASSWORD: guest
```

ConfigMap variables from k8s/configmap.yaml:
```plaintext
SERVER_PORT: 8083
SPRING_APPLICATION_NAME: userManagement
SPRING_DATASOURCE_URL: jdbc:mysql://mysql-service:5151/user_management
SPRING_RABBITMQ_HOST: rabbitmq
RABBITMQ_QUEUE_NAME: user_profile_queue
```

---

## 🏃‍♂️ Running the Service

Choose your preferred deployment method:

| Command | Description |
|---------|-------------|
| `./mvnw spring-boot:run` | Run locally in development mode |
| `java -jar target/*.jar` | Run the packaged application |

---

## 🐳 Docker

Build and run the service using Docker:

```bash
# Build the image
docker build -t handyshare/user-management:latest .

# Run the container
docker run -p 8083:8083 handyshare/user-management:latest
```

---

## ☸️ Kubernetes

Deploy the service to Kubernetes:

```bash
# Apply all configurations
kubectl apply -f k8s/

# Verify deployment
kubectl get deployments
kubectl get services
kubectl get pods
```

### Kubernetes Features

| Feature | Description | Impact |
|---------|-------------|--------|
| **Deployment Strategy** | Type: Recreate | Ensures clean state transitions |
| **Service Exposure** | Internal cluster communication | Secure service mesh integration |
| **Config Management** | ConfigMaps and Secrets | Secure and flexible configuration |
| **Health Checks** | Readiness/Liveness probes | Automatic recovery from failures |
| **Resource Management** | CPU/Memory limits | Optimal resource utilization |

---

## 📡 API Endpoints

> **Authentication Note**: For authenticated endpoints, include the JWT token in the Authorization header:
> ```
> Authorization: Bearer <your-jwt-token>
> ```

### User Profile Management
| Endpoint | Method | Description | Authenticated |
|----------|--------|-------------|---------------|
| `/api/profile` | GET | Get current user profile | ✅ |
| `/api/profile` | PUT | Update user profile | ✅ |
| `/api/profile/details/{userID}` | GET | Get user details by ID | ✅ |
| `/api/profile/getUserInfo` | POST | Get user info by email | ✅ |

<details>
<summary>📋 Request/Response Examples</summary>

#### Get Current User Profile
```http
GET /api/profile
Authorization: Bearer <your-jwt-token>

Response:
{
    "email": "royep11756@deenur.com",
    "firstName": "Robert",
    "lastName": "Jr",
    "phoneNumber": null,
    "role": "user",
    "profilePicUrl": null,
    "address": null
}
```

#### Update User Profile
```http
PUT /api/profile
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

Request:
{
    "firstName": "Robert",
    "lastName": "Jr",
    "phoneNumber": null,
    "email":"royep11756@deenur.com", //mandatory
    "address": {
        "state":"ns"
    }
}

Response:
{
    "email": "royep11756@deenur.com",
    "firstName": "Robert",
    "lastName": "Jr",
    "phoneNumber": null,
    "role": null,
    "profilePicUrl": null,
    "address": {
        "address": null,
        "pincode": null,
        "city": null,
        "province": null
    }
}
```


#### Get User details By ID 
```http
Request:
GET /api/profile/details/2
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

Response:
{
    "email": "royep11756@deenur.com",
    "firstName": "Robert",
    "lastName": "Jr",
    "phoneNumber": null,
    "role": null,
    "profilePicUrl": null,
    "address": null
}
```


#### Get User user info from the Email
```http
Request:
GET /api/profile/getUserInfo
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
Request:
{
    "email":"vaibhavpatel9196@gmail.com"
}
Response:
{
    "id": 1,
    "email": "vaibhavpatel9196@gmail.com",
    "firstName": "vaibhav",
    "lastName": "patel",
    "phoneNumber": null,
    "role": "admin",
    "profilePicUrl": null,
    "address": null
}
```
</details>

### Provider Management
| Endpoint | Method | Description | Authenticated |
|----------|--------|-------------|---------------|
| `/api/provider` | GET | Get provider profile | ✅ |
| `/api/provider` | POST | Create provider profile | ✅ |
| `/api/provider` | PUT | Update provider profile | ✅ |
| `/api/provider/service` | GET | Get providers by service | ✅ |
| `/api/provider/certify/{id}` | POST | Certify a provider | ✅ |

<details>
<summary>📋 Request/Response Examples</summary>

#### Create Provider Profile
```http
POST /api/provider
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

Request:
{
    "businessName": "Home Services Pro",
    "services": ["cleaning", "plumbing"],
    "experience": 5,
    "certifications": ["Master Plumber"],
    "serviceArea": ["New York", "Brooklyn"]
}

Response:
{
    "id": "456",
    "status": "PENDING_VERIFICATION",
    "profile": {
        ...provider details
    }
}
```
</details>

### Availability Management
| Endpoint | Method | Description | Authenticated |
|----------|--------|-------------|---------------|
| `/api/availabilities` | POST | Create availability | ✅ |
| `/api/availabilities/get` | POST | Get availabilities | ✅ |
| `/api/availabilities/{id}` | DELETE | Delete availability | ✅ |

<details>
<summary>📋 Request/Response Examples</summary>

#### Create Availability
```http
POST /api/availabilities
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

Request:
{
    "providerId": "456",
    "startTime": "2024-02-20T09:00:00",
    "endTime": "2024-02-20T17:00:00",
    "slotDuration": 60
}

Response:
{
    "message": "Availability created successfully",
    "slots": [
        {
            "id": "789",
            "startTime": "2024-02-20T09:00:00",
            "endTime": "2024-02-20T10:00:00",
            "status": "AVAILABLE"
        },
        ...more slots
    ]
}
```
</details>

### Booking Management
| Endpoint | Method | Description | Authenticated |
|----------|--------|-------------|---------------|
| `/api/booking/success` | POST | Handle successful booking | ✅ |
| `/api/booking` | GET | Get bookings | ✅ |

<details>
<summary>📋 Request/Response Examples</summary>

#### Get Bookings
```http
GET /api/booking
Authorization: Bearer <your-jwt-token>

Response:
{
    "bookings": [
        {
            "id": "789",
            "providerId": "456",
            "userId": "123",
            "service": "cleaning",
            "startTime": "2024-02-20T09:00:00",
            "status": "CONFIRMED",
            "price": 75.00
        },
        ...more bookings
    ]
}
```
</details>

---

## 🛠 Technologies

<div align="center">

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)](https://www.rabbitmq.com/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white)](https://kubernetes.io/)

</div>

---

## 🚢 Deployment Impact Features

| Feature | Description | Business Impact |
|---------|-------------|----------------|
| **JWT Authentication** | Secure endpoint access | Enhanced API security |
| **Modular Architecture** | Separate services for different functionalities | Improved maintainability |
| **Event-Driven Design** | RabbitMQ integration | Reliable async operations |
| **Data Persistence** | JPA/Hibernate with MySQL | Reliable data storage |
| **API Documentation** | Clear endpoint documentation | Easier integration |
| **Environment Isolation** | Kubernetes namespace separation | Secure multi-environment setup |
| **Scalability** | Horizontal pod scaling | Handles increased load |

---

## 👥 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

**Urban Assist Platform** • Built with ❤️ by the Urban Assist Team

</div>