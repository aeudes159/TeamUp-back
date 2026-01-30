# Étape 1: Build de l'application
FROM gradle:8.14-jdk21 AS build

WORKDIR /app

# Copier les fichiers Gradle pour le cache des dépendances
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Télécharger les dépendances (cette couche sera mise en cache)
RUN gradle dependencies --no-daemon || true

# Copier le code source
COPY src ./src

# Builder l'application
RUN gradle bootJar --no-daemon

# Étape 2: Image de production légère
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Créer un utilisateur non-root pour la sécurité
RUN groupadd -r spring && useradd -r -g spring spring

# Copier le JAR depuis l'étape de build
COPY --from=build /app/build/libs/*.jar app.jar

# Changer le propriétaire du fichier
RUN chown spring:spring app.jar

# Utiliser l'utilisateur non-root
USER spring:spring

# Exposer le port (Render utilise la variable PORT)
EXPOSE 8080

# Variables d'environnement pour optimiser la JVM
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Commande de démarrage
CMD java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar