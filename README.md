# financial-credit-case

NOTE: This project requires Java 17. Please install it before running the project.

## prepare Docker volume for project

```bash
sudo mkdir var/financial-case/postgresql
sudo chmod -R 777 /var/financial-case
```

## build project

```bash
./mvnw clean install -DskipTests
docker-compose build
```

## run project

```bash
docker-compose up -d
```
