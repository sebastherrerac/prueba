# iComers 2.0 — DevOps e Integración Continua

API REST de e-commerce de figuras coleccionables construida con Spring Boot 4 + Java 21 + MySQL 8.4.

---

## 1. Dockerfile — Contenerización del microservicio (IE1)

El proyecto ya incluye un `Dockerfile` con construcción en dos etapas:

```dockerfile
# Etapa 1: compilar el JAR con Maven
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw package -DskipTests -B

# Etapa 2: imagen de ejecución mínima
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
COPY --from=builder /app/target/*.jar app.jar
USER spring
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Construir y ejecutar localmente:

```bash
docker build -t icomers:latest .
docker run -p 8080:8080 icomers:latest
```

---

## 2. Pipeline en GitHub Actions (IE1 · IE2 · IE3 · IE4)

El archivo `.github/workflows/ci-cd.yml` implementa el pipeline completo.

### IE1 — Construcción y publicación de imagen Docker

El pipeline actual construye y publica la imagen en GitHub Container Registry (ghcr.io):

```yaml
name: CI/CD Pipeline - iComers

on:
  push:
    branches: [main]

jobs:
  build-and-push:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Log in to GitHub Container Registry
        uses: docker/login-action@v3
        with:
          registry: ghcr.io
          username: ${{ github.actor }}
          password: ${{ secrets.GITHUB_TOKEN }}

      - name: Build and push Docker image
        uses: docker/build-push-action@v6
        with:
          context: .
          push: true
          tags: ghcr.io/${{ github.repository_owner }}/icomers-api:latest
```

### IE2 — Ejecución de pruebas unitarias con JUnit 5

El proyecto tiene pruebas en `src/test/java/com/icomer/icomers/service/FigurasServiceTest.java` usando **JUnit 5 + Mockito**. Para ejecutarlas en el pipeline, se agrega un job antes del build:

```yaml
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Set up Java 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: 'maven'

      - name: Run unit tests
        run: ./mvnw test -B

      - name: Upload test results
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: test-results
          path: target/surefire-reports/
```

Las pruebas utilizan H2 en memoria (perfil `test` en `application-test.properties`) por lo que no requieren MySQL para ejecutarse.

### IE3 — Análisis de código con SonarCloud

El `pom.xml` ya tiene configurado el plugin de SonarCloud:

```xml
<properties>
  <sonar.projectKey>icomers</sonar.projectKey>
  <sonar.organization>icomers-org</sonar.organization>
  <sonar.host.url>https://sonarcloud.io</sonar.host.url>
</properties>
```

Se agrega el job de análisis al pipeline:

```yaml
  sonar:
    runs-on: ubuntu-latest
    needs: test
    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Set up Java 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: 'maven'

      - name: Analyze with SonarCloud
        run: ./mvnw verify sonar:sonar -B -Dsonar.token=${{ secrets.SONAR_TOKEN }}
```

El secret `SONAR_TOKEN` se configura en **GitHub → Settings → Secrets and variables → Actions**.

### IE4 — Despliegue automático con Docker Compose

```yaml
  deploy:
    runs-on: ubuntu-latest
    needs: sonar
    if: github.ref == 'refs/heads/main'
    steps:
      - uses: actions/checkout@v4

      - name: Deploy with Docker Compose
        run: docker compose up -d --build

      - name: Health check
        run: |
          sleep 40
          curl --fail http://localhost:8080/actuator/health
```

---

## 3. Alertas y bloqueos si el análisis de seguridad falla (IE3)

### Dependabot — Escaneo automático de dependencias

Crear `.github/dependabot.yml` en el repositorio:

```yaml
version: 2
updates:
  - package-ecosystem: "maven"
    directory: "/"
    schedule:
      interval: "weekly"
    labels:
      - "dependencies"
      - "security"
```

Dependabot crea pull requests automáticos cuando detecta dependencias con vulnerabilidades conocidas en el `pom.xml`.

### Bloquear merges si SonarCloud falla

En GitHub, ir a **Settings → Branches → Add branch protection rule** para la rama `main` y activar:

- **Require status checks to pass before merging**
  - Agregar: `test`, `sonar`, `build-and-push`
- **Require branches to be up to date before merging**

Con esto, si el Quality Gate de SonarCloud falla o las pruebas no pasan, el merge queda bloqueado automáticamente.

---

## 4. Orquestación de contenedores con Docker Compose (IE5)

### Entorno de desarrollo — `docker-compose.yml`

Levanta la aplicación junto con MySQL 8.4 con health checks:

```yaml
services:
  db:
    image: mysql:8.4
    container_name: icomers-db
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD:-rootpassword}
      MYSQL_DATABASE: ${MYSQL_DATABASE:-icomers_db}
      MYSQL_USER: ${MYSQL_USER:-icomers}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD:-icomers_pass}
    ports:
      - "3306:3306"
    volumes:
      - db-data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      retries: 5

  app:
    build: .
    container_name: icomers-app
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/${MYSQL_DATABASE:-icomers_db}
      SPRING_DATASOURCE_USERNAME: ${MYSQL_USER:-icomers}
      SPRING_DATASOURCE_PASSWORD: ${MYSQL_PASSWORD:-icomers_pass}
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    depends_on:
      db:
        condition: service_healthy
    networks:
      - icomers-net
```

Comandos:

```bash
# Levantar todo el stack
docker compose up -d

# Ver logs en tiempo real
docker compose logs -f app

# Detener y eliminar contenedores
docker compose down
```

### Entorno de producción — `docker-compose.prod.yml`

Configurado para Docker Swarm con 2 réplicas, límites de recursos y rotación de logs:

```yaml
services:
  app:
    image: ghcr.io/${REPO_OWNER}/icomers-api:latest
    deploy:
      replicas: 2
      restart_policy:
        condition: on-failure
        max_attempts: 3
      resources:
        limits:
          cpus: "1.0"
          memory: 512M
    logging:
      driver: json-file
      options:
        max-size: "10m"
        max-file: "3"
```

Despliegue en Swarm:

```bash
docker swarm init
docker stack deploy -c docker-compose.prod.yml icomers
docker stack ps icomers
```

---

## Resumen del flujo CI/CD

```
Push a main
    │
    ▼
[test] — JUnit 5 + Mockito (H2 en memoria)
    │
    ▼
[sonar] — SonarCloud Quality Gate
    │  (bloquea merge si falla)
    ▼
[build-and-push] — Docker image → ghcr.io
    │
    ▼
[deploy] — Docker Compose (staging)
```

---

## Secrets requeridos en GitHub Actions

| Secret         | Descripción                                   |
|----------------|-----------------------------------------------|
| `GITHUB_TOKEN` | Provisto automáticamente por GitHub           |
| `SONAR_TOKEN`  | Token de autenticación de SonarCloud          |
