CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nom VARCHAR(255) NOT NULL,
                       prenom VARCHAR(255),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE clients (
                         id BIGINT PRIMARY KEY,
                         telephone VARCHAR(50),
                         CONSTRAINT fk_client_user FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE admin (
                        id BIGINT PRIMARY KEY,
                        CONSTRAINT fk_admin_user FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE etablissements (
                                id BIGINT PRIMARY KEY,
                                adresse VARCHAR(255) NOT NULL,
                                telephone VARCHAR(50),
                                type VARCHAR(100),
                                latitude DOUBLE NOT NULL,
                                longitude DOUBLE NOT NULL,
                                horaire_ouverture TIME,
                                horaire_fermeture TIME,
                                CONSTRAINT fk_etablissement_user FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE services (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nom VARCHAR(255) NOT NULL,
                          description TEXT,
                          duree_moyenne INT NOT NULL,
                          etablissement_id BIGINT NOT NULL,
                          CONSTRAINT fk_service_etablissement FOREIGN KEY (etablissement_id) REFERENCES etablissements(id) ON DELETE CASCADE
);

CREATE TABLE tickets (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         numero INT NOT NULL,
                         date_creation DATETIME NOT NULL,
                         qr_code VARCHAR(255) NOT NULL,
                         position INT NOT NULL,
                         temps_estime INT NOT NULL,
                         statut VARCHAR(50) NOT NULL,
                         client_id BIGINT NOT NULL,
                         service_id BIGINT NOT NULL,
                         CONSTRAINT fk_ticket_client FOREIGN KEY (client_id) REFERENCES clients(id),
                         CONSTRAINT fk_ticket_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               titre VARCHAR(255) NOT NULL,
                               message VARCHAR(255) NOT NULL,
                               date_envoi DATETIME NOT NULL,
                               statut VARCHAR(50) NOT NULL,
                               ticket_id BIGINT NOT NULL,
                               CONSTRAINT fk_notification_ticket FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE
);