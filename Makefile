# Nombre del JAR
JAR_NAME=inflight-api-0.0.1-SNAPSHOT.jar

# Build del JAR con Gradle
build:
	./gradlew clean build

# Construye la imagen Docker
docker-build: build
	docker build -t inflight-api .

# Arranca el contenedor
up: docker-build
	docker-compose up -d

# Detiene el contenedor
down:
	docker-compose down

# Ejecuta los tests
test:
	./gradlew test
