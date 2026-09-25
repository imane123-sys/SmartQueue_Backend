
INSERT IGNORE INTO users (nom, prenom, email, password, role)
VALUES
(
    'Maroc Telecom - Agence Béni Mellal',
    NULL,
    'maroctelecom.bm@smartqueue.ma',
    '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.',
    'ETABLISSEMENT'
),
(
    'CTM Messagerie & Voyage - Béni Mellal',
    NULL,
    'ctm.benimellal@smartqueue.ma',
    '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.',
    'ETABLISSEMENT'
),
(
    'Régie Autonome de Distribution d Eau et Electricite (RADEET)',
    NULL,
    'radeet.benimellal@smartqueue.ma',
    '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.',
    'ETABLISSEMENT'
);


INSERT IGNORE INTO etablissements (id, adresse, telephone, type, latitude, longitude, rayon_km, horaire_ouverture, horaire_fermeture)
SELECT id, 'Boulevard Mohammed V, Béni Mellal', '0523481122', 'Télécommunication', 32.3372, -6.3495, 30.0, '08:30:00', '18:30:00'
FROM users WHERE email = 'maroctelecom.bm@smartqueue.ma'
UNION ALL
SELECT id, 'Avenue Beyrouth, Béni Mellal', '0523483344', 'Transport & Logistique', 32.3395, -6.3530, 30.0, '07:30:00', '20:00:00'
FROM users WHERE email = 'ctm.benimellal@smartqueue.ma'
UNION ALL
SELECT id, 'Quartier Administratif, Béni Mellal', '0523485566', 'Service Public', 32.3355, -6.3520, 30.0, '08:30:00', '16:30:00'
FROM users WHERE email = 'radeet.benimellal@smartqueue.ma';

INSERT INTO services (nom, description, duree_moyenne, etablissement_id)
SELECT 'Abonnement & Facturation', 'Gestion des forfaits mobiles, fibre optique et paiement factures', 15, id
FROM users WHERE email = 'maroctelecom.bm@smartqueue.ma'
UNION ALL
SELECT 'Support Technique & SAV', 'Assistance technique box internet et cartes SIM défectueuses', 20, id
FROM users WHERE email = 'maroctelecom.bm@smartqueue.ma'

UNION ALL
SELECT 'Billetterie Voyage', 'Achat, modification et réservation de billets d autocar', 10, id
FROM users WHERE email = 'ctm.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Envoi & Retrait Colis', 'Dépôt et réception des colis messagerie express', 15, id
FROM users WHERE email = 'ctm.benimellal@smartqueue.ma'

UNION ALL
SELECT 'Paiement Factures', 'Règlement des factures d eau et d électricité', 10, id
FROM users WHERE email = 'radeet.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Nouveau Raccordement & Réclamations', 'Demande de compteur, abonnement et réclamations techniques', 25, id
FROM users WHERE email = 'radeet.benimellal@smartqueue.ma';


INSERT IGNORE INTO users (nom, prenom, email, password, role)
VALUES
    ('Bennani',  'Hamza',    'hamza.bennani@test.ma',    '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Idrissi',  'Salma',    'salma.idrissi@test.ma',    '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Mansouri', 'Yassine',  'yassine.mansouri@test.ma', '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Tahiri',   'Khadija',  'khadija.tahiri@test.ma',   '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Chraibi',  'Rachid',   'rachid.chraibi@test.ma',   '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT');


INSERT IGNORE INTO clients (id, telephone)
SELECT id, '0612345609' FROM users WHERE email = 'hamza.bennani@test.ma'
UNION ALL
SELECT id, '0612345610' FROM users WHERE email = 'salma.idrissi@test.ma'
UNION ALL
SELECT id, '0612345611' FROM users WHERE email = 'yassine.mansouri@test.ma'
UNION ALL
SELECT id, '0612345612' FROM users WHERE email = 'khadija.tahiri@test.ma'
UNION ALL
SELECT id, '0612345613' FROM users WHERE email = 'rachid.chraibi@test.ma';


INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    1,
    '2026-09-23 09:00:00',
    UUID(),
    1,
    15,
    'EN_COURS',
    (SELECT id FROM users WHERE email = 'hamza.bennani@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'maroctelecom.bm@smartqueue.ma'
  AND s.nom = 'Abonnement & Facturation'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    2,
    '2026-09-23 09:10:00',
    UUID(),
    2,
    30,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'salma.idrissi@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'maroctelecom.bm@smartqueue.ma'
  AND s.nom = 'Abonnement & Facturation'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    3,
    '2026-09-23 09:20:00',
    UUID(),
    3,
    45,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'yassine.mansouri@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'maroctelecom.bm@smartqueue.ma'
  AND s.nom = 'Abonnement & Facturation'
LIMIT 1;


INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    1,
    '2026-09-23 08:30:00',
    UUID(),
    0,
    0,
    'TERMINE',
    (SELECT id FROM users WHERE email = 'khadija.tahiri@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'maroctelecom.bm@smartqueue.ma'
  AND s.nom = 'Support Technique & SAV'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    2,
    '2026-09-23 09:30:00',
    UUID(),
    1,
    20,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'rachid.chraibi@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'maroctelecom.bm@smartqueue.ma'
  AND s.nom = 'Support Technique & SAV'
LIMIT 1;


INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    1,
    '2026-09-23 09:05:00',
    UUID(),
    1,
    10,
    'EN_COURS',
    (SELECT id FROM users WHERE email = 'hamza.bennani@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'ctm.benimellal@smartqueue.ma'
  AND s.nom = 'Billetterie Voyage'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    2,
    '2026-09-23 09:12:00',
    UUID(),
    0,
    0,
    'ABSENT',
    (SELECT id FROM users WHERE email = 'khadija.tahiri@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'ctm.benimellal@smartqueue.ma'
  AND s.nom = 'Billetterie Voyage'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    3,
    '2026-09-23 09:25:00',
    UUID(),
    2,
    20,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'salma.idrissi@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'ctm.benimellal@smartqueue.ma'
  AND s.nom = 'Billetterie Voyage'
LIMIT 1;


INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    1,
    '2026-09-23 08:40:00',
    UUID(),
    0,
    0,
    'TERMINE',
    (SELECT id FROM users WHERE email = 'rachid.chraibi@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'radeet.benimellal@smartqueue.ma'
  AND s.nom = 'Paiement Factures'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    2,
    '2026-09-23 09:15:00',
    UUID(),
    1,
    10,
    'EN_COURS',
    (SELECT id FROM users WHERE email = 'yassine.mansouri@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'radeet.benimellal@smartqueue.ma'
  AND s.nom = 'Paiement Factures'
LIMIT 1;
