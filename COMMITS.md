# Instrucciones para los 3 commits — calderon-post2-u12

## Configuración inicial

```bash
cd calderon-post2-u12
git init
git remote add origin https://github.com/maocalderon/calderon-post2-u12.git
```

---

## Commit 1 — Workflow GitHub Actions

```bash
git add .github/workflows/ci.yml Dockerfile .dockerignore
git commit -m "ci: add GitHub Actions workflow with build-test and docker-publish jobs"
```

---

## Commit 2 — JaCoCo y pruebas

```bash
git add pom.xml src/test/java/com/calderon/app/AppApplicationTests.java
git commit -m "ci: add JaCoCo coverage plugin and improve test suite"
```

---

## Commit 3 — README con badge y documentación final

```bash
git add .
git commit -m "docs: update README with CI/CD badge, secrets guide and Docker Hub instructions"
```

---

## Subir a GitHub

```bash
git branch -M main
git push -u origin main
```

---

## Configurar GitHub Secrets (hacer ANTES del push)

1. Ir a: https://github.com/maocalderon/calderon-post2-u12/settings/secrets/actions
2. Crear secret: `DOCKERHUB_USERNAME` → tu usuario de Docker Hub
3. Crear secret: `DOCKERHUB_TOKEN` → Access Token de Docker Hub
   - Obtenerlo en: hub.docker.com → Account Settings → Security → New Access Token

---

## Nombre del repositorio: **calderon-post2-u12**
