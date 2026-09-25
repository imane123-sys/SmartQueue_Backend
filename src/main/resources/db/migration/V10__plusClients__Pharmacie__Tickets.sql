
-- 200585
INSERT IGNORE INTO users (nom, prenom, email, password, role)
VALUES
    ('Tazi',    'Mehdi',    'mehdi.tazi@test.ma',     '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Elhilali','Nadia',    'nadia.elhilali@test.ma', '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Bouziane', 'Omar',    'omar.bouziane@test.ma',  '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Mrani',   'Sara',     'sara.mrani@test.ma',     '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT'),
    ('Chaoui',  'Amine',    'amine.chaoui@test.ma',   '$2a$10$EKG3Ugx82Oe.k74tnjGrKOkfFUXlOCl2NAyCQUvq6.7MdUxTtACq.', 'CLIENT');

INSERT IGNORE INTO clients (id, telephone)
SELECT id, '0612345604' FROM users WHERE email = 'mehdi.tazi@test.ma'
UNION ALL
SELECT id, '0612345605' FROM users WHERE email = 'nadia.elhilali@test.ma'
UNION ALL
SELECT id, '0612345606' FROM users WHERE email = 'omar.bouziane@test.ma'
UNION ALL
SELECT id, '0612345607' FROM users WHERE email = 'sara.mrani@test.ma'
UNION ALL
SELECT id, '0612345608' FROM users WHERE email = 'amine.chaoui@test.ma';


INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    4,
    '2026-09-22 08:05:00',
    UUID(),
    4,
    40,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'mehdi.tazi@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    5,
    '2026-09-22 08:15:00',
    UUID(),
    5,
    50,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'nadia.elhilali@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    6,
    '2026-09-22 08:25:00',
    UUID(),
    6,
    60,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'omar.bouziane@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    7,
    '2026-09-22 08:35:00',
    UUID(),
    7,
    70,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'sara.mrani@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    8,
    '2026-09-22 08:45:00',
    UUID(),
    8,
    80,
    'TERMINE',
    (SELECT id FROM users WHERE email = 'amine.chaoui@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Retrait ordonnance'
LIMIT 1;


INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    3,
    '2026-09-22 09:00:00',
    UUID(),
    3,
    45,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'omar.bouziane@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Conseil pharmaceutique'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    4,
    '2026-09-22 09:15:00',
    UUID(),
    4,
    60,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'sara.mrani@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Conseil pharmaceutique'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    5,
    '2026-09-22 09:30:00',
    UUID(),
    5,
    75,
    'TERMINE',
    (SELECT id FROM users WHERE email = 'mehdi.tazi@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Conseil pharmaceutique'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    6,
    '2026-09-22 09:45:00',
    UUID(),
    6,
    90,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'nadia.elhilali@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Conseil pharmaceutique'
LIMIT 1;

INSERT INTO tickets (numero, date_creation, qr_code, position, temps_estime, statut, client_id, service_id)
SELECT
    7,
    '2026-09-22 10:00:00',
    UUID(),
    7,
    105,
    'EN_ATTENTE',
    (SELECT id FROM users WHERE email = 'amine.chaoui@test.ma'),
    s.id
FROM services s
JOIN users u ON s.etablissement_id = u.id
WHERE u.email = 'pharmacie@gmail.com'
  AND s.nom = 'Conseil pharmaceutique'
LIMIT 1;
