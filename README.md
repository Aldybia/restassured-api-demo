# 🧪 REST Assured API Automation Demo

Proyek otomasi pengujian API menggunakan **REST Assured** berbasis Java, terintegrasi dengan pipeline **CI/CD menggunakan Jenkins dan Docker**.

---

## 📋 Deskripsi

Proyek ini merupakan implementasi studi kasus **2.10.3 — Penyiapan Proyek, Struktur Kode, dan Integrasi Rest Assured dalam Pipeline CI/CD**.

API yang diuji: [DummyJSON](https://dummyjson.com) — API publik yang menyediakan data produk dummy untuk keperluan testing.

---

## ✅ Hasil Pengujian

| Test Case | Endpoint | Status |
|-----------|----------|--------|
| Validasi produk beauty | GET /products/1 | ✅ PASSED |
| Validasi penambahan produk | POST /products/add | ✅ PASSED |

```
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## 🛠️ Tech Stack

| Tool | Versi | Fungsi |
|------|-------|--------|
| Java | 26.0.1 | Bahasa pemrograman utama |
| Apache Maven | 3.9.15 | Build tool dan dependency manager |
| REST Assured | 5.4.0 | Library pengujian API REST |
| JUnit Jupiter | 5.10.0 | Framework test runner |
| Hamcrest | 2.2 | Library assertion/matcher |
| Docker | 29.4.2 | Container platform |
| Jenkins | 2.555.1 | CI/CD automation server |
| Git | 2.53.0 | Version control |

---

## 📁 Struktur Project

```
rest-assured-demo/
├── src/
│   └── test/
│       └── java/
│           └── ProductApiRegressionTest.java   # File test utama
├── pom.xml                                      # Konfigurasi Maven
├── Dockerfile                                   # Image Jenkins custom
├── docker-compose.yml                           # Konfigurasi Docker Compose
├── start-jenkins.sh                             # Script menjalankan Jenkins
└── README.md
```

---

## 🚀 Cara Menjalankan

### Prasyarat
Pastikan tools berikut sudah terinstall:
- Java JDK 17+
- Apache Maven 3.x
- Git

### 1. Clone Repository
```bash
git clone https://github.com/Aldybia/restassured-api-demo.git
cd restassured-api-demo
```

### 2. Jalankan Test Lokal
```bash
mvn clean test
```

### 3. Jalankan Jenkins dengan Docker
```bash
# Pastikan Docker sudah berjalan
chmod +x start-jenkins.sh
./start-jenkins.sh
```

Akses Jenkins di: **http://localhost:8080**

---

## 🔬 Test Cases

### Test Case 1 — GET /products/1
Memvalidasi endpoint GET yang mengembalikan data produk.

```java
@Test
@DisplayName("GET /products/1 - Validasi produk beauty")
void testGetProductById() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/products/1")
    .then()
        .assertThat()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("title", not(emptyString()))
        .body("category", equalTo("beauty"))
        .header("Content-Type", containsString("application/json"))
        .time(lessThan(2000L));
}
```

**Validasi yang dilakukan:**
- ✅ Status code = 200 OK
- ✅ Field `id` = 1
- ✅ Field `title` tidak kosong
- ✅ Field `category` = "beauty"
- ✅ Header Content-Type mengandung "application/json"
- ✅ Response time < 2000ms

---

### Test Case 2 — POST /products/add
Memvalidasi endpoint POST untuk menambahkan produk baru.

```java
@Test
@DisplayName("POST /products/add - Validasi penambahan produk")
void testAddProductToCart() {
    String requestBody = """
    {
        "title": "Test Product",
        "description": "Produk uji regression test",
        "price": 123
    }
    """;

    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
    .when()
        .post("/products/add")
    .then()
        .assertThat()
        .statusCode(201)
        .body("title", equalTo("Test Product"))
        .body("description", containsString("regression"))
        .body("price", equalTo(123))
        .body("id", greaterThan(0))
        .time(lessThan(3000L));
}
```

**Validasi yang dilakukan:**
- ✅ Status code = 201 Created
- ✅ Field `title` = "Test Product"
- ✅ Field `description` mengandung kata "regression"
- ✅ Field `price` = 123
- ✅ Field `id` > 0 (ID valid)
- ✅ Response time < 3000ms

---

## ⚙️ Konfigurasi pom.xml

```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>

<dependencies>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🔄 Jenkins Pipeline

```groovy
pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-21'
        }
    }
    stages {
        stage('Clone') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Aldybia/restassured-api-demo.git'
            }
        }
        stage('Run Maven Test') {
            steps {
                sh 'mvn clean test'
            }
        }
    }
}
```

### Hasil Pipeline CI/CD

| Build | Status | Duration |
|-------|--------|----------|
| #4 | ✅ SUCCESS | 2m 14s |

```
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[Pipeline] End of Pipeline
Finished: SUCCESS
```

---

## 📖 Cara Kerja CI/CD Pipeline

```
Developer push kode ke GitHub
        ↓
Jenkins mendeteksi perubahan
        ↓
Jenkins clone repository terbaru
        ↓
Jenkins jalankan mvn clean test di Docker container Maven
        ↓
Hasil test ditampilkan di Jenkins UI
```

---

## 👤 Author

**Aldi (Aldybia)**
- GitHub: [@Aldybia](https://github.com/Aldybia)

---

## 📄 Lisensi

Proyek ini dibuat untuk keperluan pembelajaran dan studi kasus QA Automation.
