# financial-credit-case

**NOTE: This project requires Java 17. Please install it before running the project.**

## Prepare Docker volume for project

```bash
sudo mkdir var/financial-case/postgresql
sudo chmod -R 777 /var/financial-case
```

## Build Project

```bash
./mvnw clean install
docker-compose build
```

## Run Project

```bash
docker-compose up -d
```

#### [Get Postman Collection](./Colendi%20-%20Financial%20Case%20Study.postman_collection.json)
