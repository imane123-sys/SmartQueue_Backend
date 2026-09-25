# SmartQueue Backend 🎟️⏱️

> **Backend Spring Boot pour la gestion intelligente et dématérialisée des files d'attente.**

SmartQueue est une solution logicielle moderne conçue pour optimiser l'accueil des usagers et fluidifier le flux de visiteurs dans divers types d'établissements (services publics, cliniques, pharmacies, banques, commerces, etc.). Elle permet aux usagers de réserver des tickets à distance, d'obtenir un ticket numérique sécurisé avec QR Code, de suivre l'avancement de la file en temps réel et de recevoir des alertes personnalisées lors de l'approche de leur tour.

---

## 📑 Sommaire

1. [Fonctionnalités Clés](#-fonctionnalités-clés)
2. [Diagrammes du Projet](#-diagrammes-du-projet)
   - [Diagramme des Cas d'Utilisation (Use Case)](#1-diagramme-des-cas-dutilisation)
   - [Diagramme de Classes](#2-diagramme-de-classes)
   - [Diagramme de Séquence](#3-diagramme-de-séquence)
3. [Architecture & Stack Technologique](#-architecture--stack-technologique)
4. [Structure du Projet](#-structure-du-projet)
5. [Endpoints de l'API REST](#-endpoints-de-lapi-rest)
6. [Installation et Démarrage](#-installation-et-démarrage)
   - [Prérequis](#prérequis)
   - [Configuration de la base de données](#configuration)
   - [Exécution en Local](#exécution-en-local-avec-maven)
   - [Exécution avec Docker](#déploiement-avec-docker-compose)
7. [Documentation API (Swagger)](#-documentation-api-swagger)

---

## 🚀 Fonctionnalités Clés

### 👥 Gestion des Rôles et de la Sécurité
- **Authentification & Autorisation :** Sécurisation avec **Spring Security** et **JWT (JSON Web Tokens)**.
- **Gestion des rôles :**
  - `CLIENT` : Consultation des établissements/services, réservation de ticket, suivi de position, annulation.
  - `ETABLISSEMENT` : Gestion de ses services, appel des tickets suivants, mise à jour des statuts, consultation des statistiques et historiques.
  - `ADMIN` : Supervision globale, gestion des établissements et modération.

### 🎫 Gestion des Tickets & Files d'Attente
- **Réservation intelligente :** Attribution d'un numéro unique aléatoire et calcul de la position dans la file.
- **Génération de QR Code :** Génération dynamique d'un QR Code en Base64 via **Google ZXing** pour validation et contrôle physique.
- **Estimation dynamique du temps d'attente :** Calcul fondé sur la position du ticket et la durée moyenne de traitement du service.
- **Cycle de vie du ticket :** Statuts `EN_ATTENTE`, `EN_COURS`, `TERMINE`, `ABSENT`.
- **Suivi en temps réel :** Recalcul en direct des personnes précédant le ticket et du temps restant.

### 🔔 Notifications & Communication
- **Temps réel via WebSocket :** Diffusion instantanée des changements d'état et alertes de tour aux clients.
- **Notifications Email (SMTP) :** Envoi d'e-mails de confirmation de réservation et notifications d'approche du tour.
- **Historique des notifications :** Stockage et filtrage par rôle destinataire.

### 📍 Géolocalisation & Établissements
- **Intégration Google Maps :** Calcul des coordonnées géographiques et filtrage par rayon kilométrique (`rayonKm`).

---

## 📊 Diagrammes du Projet

Les diagrammes de conception ci-dessous proviennent du dossier de présentation du projet :  
`C:\Users\RM\Desktop\digramme_smartqueue_présentation\`

> [!NOTE]  
> *Pour un affichage optimal lors d'un hébergement distant (ex. GitHub / GitLab), ces fichiers peuvent être placés dans un sous-dossier de documentation du dépôt (ex. `docs/diagrams/`). Les liens ci-dessous pointent vers l'emplacement local de présentation.*

---

### 1. Diagramme des Cas d'Utilisation

Ce diagramme illustre les interactions entre les trois acteurs principaux (`Client`, `Etablissement`, `Admin`) et les cas d'utilisation du système SmartQueue (réservation, suivi, appel, notifications, gestion des services).

![Diagramme des Cas d'Utilisation](file:///C:/Users/RM/Desktop/digramme_smartqueue_présentation/UseCaseDiagram_smartqueue.jpg)

---

### 2. Diagramme de Classes

Ce diagramme structure le modèle de données objet du backend, mettant en évidence les entités JPA (`User`, `Client`, `Etablissement`, `Admin`, `Services`, `Ticket`, `Notification`), leurs attributs et leurs cardinalités.

![Diagramme de Classes](file:///C:/Users/RM/Desktop/digramme_smartqueue_présentation/diagramme_classe_SmartQueue.jpg)

---

### 3. Diagramme de Séquence

Ce diagramme détaille la cinématique des échanges entre le client, le contrôleur, la couche service, les dépôts de données et les services de notification lors de la réservation et du traitement d'un ticket.

![Diagramme de Séquence](file:///C:/Users/RM/Desktop/digramme_smartqueue_présentation/diagramme_sequence_smartqueue.jpg)

---

## 🛠️ Architecture & Stack Technologique

| Composant | Technologie / Librairie | Version | Rôle |
|---|---|---|---|
| **Langage** | Java | 21 (LTS) | Langage de développement principal |
| **Framework** | Spring Boot | 3.3.2 | Socle applicatif backend |
| **Sécurité** | Spring Security & JJWT | 0.12.5 | Chiffrement, RBAC, Tokens JWT |
| **ORM / Accès aux données** | Spring Data JPA / Hibernate | - | Persistance et requêtes relationnelles |
| **Base de données** | MySQL | 8.0 | SGBD relationnel |
| **Migrations BDD** | Flyway | - | Versioning incrémental du schéma de base |
| **Temps Réel** | Spring WebSocket (STOMP) | - | Notifications push bidirectionnelles |
| **QR Code** | Google ZXing | 3.5.3 | Encodage et génération des QR Codes |
| **Mailing** | Spring Boot Starter Mail | - | Envoi d'e-mails transactionnels |
| **Documentation API** | SpringDoc OpenAPI (Swagger UI) | 2.6.0 | Documentation interactive de l'API |
| **Utilitaires** | MapStruct / Lombok | 1.5.5 | Mappings DTO-Entité et réduction du boilerplate |
| **Conteneurisation** | Docker & Docker Compose | - | Déploiement et orchestration des services |

---

## 📂 Structure du Projet

```text
src/main/java/org/example/smartqueue/
├── config/                  # Configurations (Security, WebSocket, Swagger, RestTemplate)
│   ├── OpenApiConfig.java
│   ├── RestTemplateConfig.java
│   ├── SecurityConfig.java
│   └── WebSocketConfig.java
├── controller/              # Contrôleurs REST (Exposition des API)
│   ├── AuthController.java
│   ├── ClientController.java
│   ├── EtablissementController.java
│   ├── NotificationController.java
│   ├── ServiceController.java
│   └── TicketController.java
├── dto/                     # Objets de transfert de données (Request / Response)
│   ├── request/
│   └── response/
├── entity/                  # Entités JPA
│   ├── Admin.java
│   ├── Client.java
│   ├── Etablissement.java
│   ├── Notification.java
│   ├── Services.java
│   ├── Ticket.java
│   └── User.java
├── enums/                   # Énumérations (Role, StatutTicket, CategorieService, etc.)
├── exceptionHandler/        # Gestion globale des exceptions REST
├── mapper/                  # Mappers MapStruct (Entité <-> DTO)
├── repository/              # Dépôts Spring Data JPA
├── security/                # Filtres JWT, UserDetailsService, AuthEntryPoint
├── service/                 # Interfaces et implémentations métier
│   ├── imp/
│   │   ├── AuthServiceImp.java
│   │   ├── ClientServiceImp.java
│   │   ├── EtablissementServiceImp.java
│   │   ├── NotificationServiceImp.java
│   │   ├── ServiceServiceImp.java
│   │   └── TicketServiceImp.java
│   └── ...
└── SmartQueueApplication.java # Classe principale Spring Boot
```

---

## 🌐 Endpoints de l'API REST

### 🔐 Authentification (`/api/auth`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `POST` | `/api/auth/register/client` | Inscription d'un nouveau compte client | Public |
| `POST` | `/api/auth/register/etablissement` | Inscription d'un établissement | Public |
| `POST` | `/api/auth/login` | Connexion et obtention du token JWT | Public |

### 🎫 Tickets (`/api/tickets`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `POST` | `/api/tickets/reserve` | Réserver un nouveau ticket avec QR Code | `CLIENT`, `ADMIN` |
| `GET` | `/api/tickets/{id}` | Consulter les détails d'un ticket | `CLIENT`, `ETABLISSEMENT`, `ADMIN` |
| `GET` | `/api/tickets/suivre/{id}` | Suivre la position et le temps d'attente en temps réel | `CLIENT`, `ADMIN` |
| `GET` | `/api/tickets/attente` | Liste des tickets en attente pour un service | `ETABLISSEMENT`, `ADMIN` |
| `GET` | `/api/tickets/client/{clientId}` | Liste des tickets d'un client | `CLIENT`, `ADMIN` |
| `PUT` | `/api/tickets/annuler/{ticketid}` | Annuler un ticket réservé | `CLIENT` |
| `PUT` | `/api/tickets/appeler-suivant/{serviceId}` | Appeler le prochain ticket de la file | `ETABLISSEMENT` |
| `PUT` | `/api/tickets/statut/{ticketid}` | Modifier le statut d'un ticket | `ETABLISSEMENT`, `ADMIN` |
| `GET` | `/api/tickets/historique/{id}` | Statistiques d'historique des tickets d'un établissement | `ETABLISSEMENT` |
| `GET` | `/api/tickets/ticketPaginated/Statut/etablissement/{id}` | Récupérer les tickets paginés par établissement | `ETABLISSEMENT`, `ADMIN` |

### 🏢 Établissements & Services
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/api/etablissements` | Liste de tous les établissements | Authentifié |
| `GET` | `/api/etablissements/{id}` | Détails d'un établissement | Authentifié |
| `POST` | `/api/services` | Créer un service rattaché à un établissement | `ETABLISSEMENT`, `ADMIN` |
| `GET` | `/api/services/etablissement/{id}` | Liste des services d'un établissement | Authentifié |

### 🔔 Notifications (`/api/notifications`)
| Méthode | Endpoint | Description | Accès |
|---|---|---|---|
| `GET` | `/api/notifications/etablissement/{id}` | Récupérer les notifications d'un établissement | `ETABLISSEMENT`, `ADMIN` |
| `GET` | `/api/notifications/client/{clientId}` | Récupérer les notifications d'un client | `CLIENT`, `ADMIN` |

---

## ⚙️ Installation et Démarrage

### Prérequis
- **Java JDK 21** ou supérieur installé
- **Maven 3.8+** (ou utilisation du wrapper `mvnw` inclus)
- **MySQL 8.0+** en local ou un moteur **Docker** actif

### Configuration

Les paramètres de l'application sont centralisés dans le fichier `src/main/resources/application.properties` :

```properties
server.port=8080

# Base de données MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/smartqueue?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe

# Flyway (migrations automatiques)
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# Configuration JWT
app.jwt.secret=votre_cle_secrete_jwt_robuste
app.jwt.expiration-ms=86400000

# Configuration SMTP (Mail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=votre_email@gmail.com
spring.mail.password=votre_mot_de_passe_application
```

### Exécution en Local avec Maven

1. **Cloner le projet :**
   ```bash
   git clone https://github.com/imane123-sys/SmartQueue_Backend.git
   cd SmartQueue_Backend
   ```

2. **Compiler le projet :**
   ```bash
   ./mvnw clean install
   ```

3. **Lancer l'application :**
   ```bash
   ./mvnw spring-boot:run
   ```
   L'API sera disponible sur : `http://localhost:8080`

### Déploiement avec Docker Compose

Pour démarrer simultanément le backend, la base MySQL et le frontend :

```bash
docker compose up -d --build
```

---

## 📖 Documentation API (Swagger)

Une documentation interactive générée avec **Swagger UI** est accessible dès le lancement de l'application :

👉 **URL de Swagger UI :** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
👉 **Spécification OpenAPI JSON :** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 👥 Auteur
- **Imane Ramadane** - *Projet SmartQueue*
