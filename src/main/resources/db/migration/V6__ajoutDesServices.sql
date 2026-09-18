-- =========================================================
-- 1. INSERTION DANS LA TABLE USERS (NOUVEAUX ETABLISSEMENTS A BENI MELLAL)
-- =========================================================
INSERT INTO users (nom, email, password, role)
VALUES
(
    'Attijariwafa Bank - Agence Hassan II Béni Mellal',
    'attijari.benimellal@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'CIH Bank - Agence Béni Mellal Centre',
    'cih.benimellal@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'Crédit Agricole du Maroc - Agence Béni Mellal',
    'creditagricole.bm@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'Société Générale - Agence Béni Mellal',
    'sgmb.benimellal@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'Clinique Atlas - Béni Mellal',
    'clinique.atlas@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'Clinique Ibn Sina - Béni Mellal',
    'clinique.ibnsina@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
);

-- =========================================================
-- 2. INSERTION DANS LA TABLE ETABLISSEMENTS
-- L'id fait référence à users(id) via l'email
-- rayon_km est renseigné avec la valeur par défaut (10.0 km)
-- =========================================================
INSERT INTO etablissements (id, adresse, telephone, type, latitude, longitude, rayon_km, horaire_ouverture, horaire_fermeture)
SELECT id, 'Boulevard Hassan II, Béni Mellal', '0523482100', 'Banque', 32.3385, -6.3482, 10.0, '08:15:00', '15:30:00'
FROM users WHERE email = 'attijari.benimellal@smartqueue.ma'
UNION ALL
SELECT id, 'Avenue Mohammed V, Béni Mellal', '0523485560', 'Banque', 32.3360, -6.3525, 10.0, '08:15:00', '15:30:00'
FROM users WHERE email = 'cih.benimellal@smartqueue.ma'
UNION ALL
SELECT id, 'Boulevard de la Liberté, Béni Mellal', '0523483321', 'Banque', 32.3350, -6.3540, 10.0, '08:15:00', '15:30:00'
FROM users WHERE email = 'creditagricole.bm@smartqueue.ma'
UNION ALL
SELECT id, 'Boulevard Mohammed V, Béni Mellal', '0523484412', 'Banque', 32.3375, -6.3505, 10.0, '08:15:00', '15:30:00'
FROM users WHERE email = 'sgmb.benimellal@smartqueue.ma'
UNION ALL
SELECT id, 'Quartier Administratif, Béni Mellal', '0523486000', 'Clinique', 32.3330, -6.3550, 10.0, '08:00:00', '19:00:00'
FROM users WHERE email = 'clinique.atlas@smartqueue.ma'
UNION ALL
SELECT id, 'Route de Marrakech, Béni Mellal', '0523487000', 'Clinique', 32.3315, -6.3580, 10.0, '08:00:00', '19:00:00'
FROM users WHERE email = 'clinique.ibnsina@smartqueue.ma';

-- =========================================================
-- 3. INSERTION DANS LA TABLE SERVICES
-- Mêmes services proposés ('Services bancaires', 'Retrait bancaire', 'Consultation médicale', 'Urgences')
-- pour tester la recherche et l'affichage d'une liste d'établissements proches offrant le même service
-- =========================================================
INSERT INTO services (nom, description, duree_moyenne, etablissement_id)
-- Services pour Attijariwafa Bank
SELECT 'Services bancaires', 'Opérations bancaires courantes et virements', 15, id
FROM users WHERE email = 'attijari.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Retrait bancaire', 'Retrait d’espèces au guichet', 10, id
FROM users WHERE email = 'attijari.benimellal@smartqueue.ma'

-- Services pour CIH Bank
UNION ALL
SELECT 'Services bancaires', 'Opérations bancaires et gestion de compte', 15, id
FROM users WHERE email = 'cih.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Retrait bancaire', 'Retrait d’espèces au guichet', 10, id
FROM users WHERE email = 'cih.benimellal@smartqueue.ma'

-- Services pour Crédit Agricole du Maroc
UNION ALL
SELECT 'Services bancaires', 'Opérations bancaires et financement agricole', 15, id
FROM users WHERE email = 'creditagricole.bm@smartqueue.ma'
UNION ALL
SELECT 'Retrait bancaire', 'Retrait d’espèces et opérations courantes', 10, id
FROM users WHERE email = 'creditagricole.bm@smartqueue.ma'

-- Services pour Société Générale
UNION ALL
SELECT 'Services bancaires', 'Services et conseil bancaire', 15, id
FROM users WHERE email = 'sgmb.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Retrait bancaire', 'Retrait d’espèces et change', 10, id
FROM users WHERE email = 'sgmb.benimellal@smartqueue.ma'

-- Services pour Clinique Atlas
UNION ALL
SELECT 'Consultation médicale', 'Consultation médicale générale et spécialisée', 20, id
FROM users WHERE email = 'clinique.atlas@smartqueue.ma'
UNION ALL
SELECT 'Urgences', 'Service d’urgences 24/7', 30, id
FROM users WHERE email = 'clinique.atlas@smartqueue.ma'

-- Services pour Clinique Ibn Sina
UNION ALL
SELECT 'Consultation médicale', 'Consultation médicale générale et spécialisée', 20, id
FROM users WHERE email = 'clinique.ibnsina@smartqueue.ma'
UNION ALL
SELECT 'Urgences', 'Service d’urgences médicales', 30, id
FROM users WHERE email = 'clinique.ibnsina@smartqueue.ma';
