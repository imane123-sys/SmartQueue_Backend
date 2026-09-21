-- =========================================================
-- 1. CLIENTS DE TEST (INSERT IGNORE : deja existants en base)
-- =========================================================
INSERT IGNORE INTO users (nom, prenom, email, password, role)
VALUES
    ('Alami',  'Youssef', 'youssef.alami@test.ma', '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e', 'CLIENT'),
    ('Benali', 'Fatima',  'fatima.benali@test.ma',  '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e', 'CLIENT'),
    ('Ouali',  'Karim',   'karim.ouali@test.ma',    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e', 'CLIENT');

INSERT IGNORE INTO clients (id, telephone)
SELECT id, '0612345601' FROM users WHERE email = 'youssef.alami@test.ma'
UNION ALL
SELECT id, '0612345602' FROM users WHERE email = 'fatima.benali@test.ma'
UNION ALL
SELECT id, '0612345603' FROM users WHERE email = 'karim.ouali@test.ma';

-- =========================================================
-- 2. SERVICES POUR LA PHARMACIE (manquants en base)
-- =========================================================
INSERT INTO services (nom, description, duree_moyenne, etablissement_id)
SELECT 'Retrait ordonnance', 'Retrait de medicaments sur ordonnance', 10, id
FROM users WHERE email = 'pharmacie@gmail.com'
UNION ALL
SELECT 'Conseil pharmaceutique', 'Conseil et orientation par le pharmacien', 15, id
FROM users WHERE email = 'pharmacie@gmail.com';

-- =========================================================
-- 3. TICKETS POUR LA PHARMACIE
-- =========================================================

-- Retrait ordonnance - Ticket 1
INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    1,
    '2026-09-21 08:10:00',
    UUID(),
    1,
    10,
    'EN_COURS',
    (SELECT id FROM users WHERE email = 'youssef.alami@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;

-- Retrait ordonnance - Ticket 2
INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    2,
    '2026-09-21 08:20:00',
    UUID(),
    2,
    20,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'fatima.benali@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;

-- Retrait ordonnance - Ticket 3
INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    3,
    '2026-09-21 08:30:00',
    UUID(),
    3,
    30,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'karim.ouali@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;

-- Conseil pharmaceutique - Ticket 1
INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    1,
    '2026-09-21 09:00:00',
    UUID(),
    1,
    15,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'youssef.alami@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Conseil pharmaceutique'
LIMIT 1;

-- Conseil pharmaceutique - Ticket 2
INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    2,
    '2026-09-21 09:15:00',
    UUID(),
    2,
    30,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'fatima.benali@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Conseil pharmaceutique'
LIMIT 1;
