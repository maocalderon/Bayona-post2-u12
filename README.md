# calderon-post2-u12

![CI/CD Status](https://github.com/maocalderon/calderon-post2-u12/actions/workflows/ci.yml/badge.svg)

**Programación Web — Unidad 12: Despliegue y CI/CD**  
Post-Contenido 2 — Pipeline CI/CD con GitHub Actions y Docker Hub  
Ingeniería de Sistemas · 2026

---

## Descripción

Aplicación Spring Boot con pipeline CI/CD completo implementado con GitHub Actions. El pipeline automatiza compilación, pruebas unitarias con reporte de cobertura JaCoCo, construcción de imagen Docker multi-stage y publicación en Docker Hub en cada push a `main`.

---

## Pipeline CI/CD

El pipeline se activa automáticamente en cada `push` o `pull_request` a la rama `main` y ejecuta los siguientes jobs en orden:

| Job | Propósito | Se ejecuta en |
|---|---|---|
| `build-and-test` | Compilar con Maven, ejecutar pruebas, generar reporte JaCoCo | Push y PR |
| `docker-publish` | Construir imagen Docker multi-stage y publicar en Docker Hub | Solo push a `main` |

### Flujo del pipeline

```
push a main
    │
    ▼
build-and-test
├── Checkout código
├── Configurar JDK 21 (caché Maven)
├── mvn clean verify  ← compila + pruebas + JaCoCo
└── Upload artifact: jacoco-report (7 días)
    │
    ▼ (needs: build-and-test)
docker-publish
├── Checkout código
├── Login Docker Hub (secrets)
├── Extraer metadata (tags: latest + sha-<commit>)
└── Build & push imagen multi-stage
```

---

## GitHub Secrets requeridos

Configurar en: **Settings → Secrets and variables → Actions → New repository secret**

| Secret | Valor | Cómo obtenerlo |
|---|---|---|
| `DOCKERHUB_USERNAME` | Tu usuario de Docker Hub | hub.docker.com → tu nombre de usuario |
| `DOCKERHUB_TOKEN` | Access Token de Docker Hub | hub.docker.com → Account Settings → Security → New Access Token (permisos: Read & Write) |

> ⚠️ Nunca incluir credenciales en texto plano en el archivo YAML. Siempre usar `${{ secrets.NOMBRE }}`.

---

## Estructura del proyecto

```
calderon-post2-u12/
├── .github/
│   └── workflows/
│       └── ci.yml                  ← Pipeline CI/CD completo
├── src/
│   ├── main/
│   │   ├── java/com/calderon/app/
│   │   │   ├── AppApplication.java
│   │   │   ├── controller/ProductoController.java
│   │   │   ├── model/Producto.java
│   │   │   └── repository/ProductoRepository.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/calderon/app/
│           └── AppApplicationTests.java
├── Dockerfile                       ← Multi-stage: JDK builder + JRE producción
├── .dockerignore
├── pom.xml                          ← Spring Boot 3.2.5 + JaCoCo 0.8.11
└── README.md
```

---

## Imagen Docker

La imagen se publica automáticamente en Docker Hub con cada push a `main`:

```bash
# Descargar la imagen publicada
docker pull maocalderon/mi-spring-app:latest

# Ejecutar localmente con perfil dev
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev maocalderon/mi-spring-app:latest

# Verificar que la aplicación responde
curl http://localhost:8080/actuator/health
```

### Tags generados por el pipeline

| Tag | Descripción |
|---|---|
| `latest` | Última imagen publicada en la rama main |
| `sha-<commit>` | Tag con el SHA corto del commit para trazabilidad |

---

## Ejecución local

### Requisitos
- Java 21 + Maven 3.8+
- Docker Desktop

```bash
# Compilar y ejecutar pruebas con reporte JaCoCo
mvn clean verify

# Ver reporte de cobertura (abre en navegador)
open target/site/jacoco/index.html

# Construir imagen Docker localmente
docker build -t calderon-app:local .

# Ejecutar contenedor
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev calderon-app:local
```

---

## Commits del laboratorio

| # | Commit | Descripción |
|---|---|---|
| 1 | `ci: add GitHub Actions workflow with build-test and docker-publish jobs` | Workflow YAML inicial con los dos jobs encadenados |
| 2 | `ci: add JaCoCo coverage plugin and improve test suite` | JaCoCo en pom.xml, pruebas unitarias con assertThat |
| 3 | `docs: update README with CI/CD badge, secrets guide and Docker Hub instructions` | README completo con badge, tabla de Secrets y comandos docker pull |

---

## Autor

**Mauricio Calderón**  
Ingeniería de Sistemas — Universidad de Santander (UDES)  
Repositorio: [github.com/maocalderon/calderon-post2-u12](https://github.com/maocalderon/calderon-post2-u12)  
2026
