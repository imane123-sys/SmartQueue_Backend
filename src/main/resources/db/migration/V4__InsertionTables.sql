-- =========================================================
-- 1. INSERTION DANS LA TABLE ADMIN
-- L'administrateur a été créé dans la migration V3 (dans la table users)
-- =========================================================
INSERT INTO admin (id)
SELECT id FROM users WHERE email = 'imaneramadane@gmail.com';

-- =========================================================
-- 2. INSERTION DANS LA TABLE USERS (ETABLISSEMENTS)
-- =========================================================
INSERT INTO users (nom, email, password, role)
VALUES
(
    'Centre Hospitalier Régional de Béni Mellal',
    'chr.benimellal@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'Bank Al Yousr - Agence Béni Mellal',
    'bankalyousr.bm@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'Banque Populaire - Tassamete',
    'bp.tassamete@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'BMCI - Agence Béni Mellal',
    'bmci.benimellal@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
),
(
    'Commune de Béni Mellal',
    'commune.benimellal@smartqueue.ma',
    '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
    'ETABLISSEMENT'
);

-- =========================================================
-- 3. INSERTION DANS LA TABLE ETABLISSEMENTS
-- L'id fait référence à users(id) via l'email
-- Note : rayon_km sera ajouté dans la migration V5 avec une valeur par défaut de 10.0
-- =========================================================
INSERT INTO etablissements (id, adresse, telephone, type, latitude, longitude, horaire_ouverture, horaire_fermeture)
SELECT id, 'Avenue Chohadaa, Béni Mellal', '0523483805', 'ETABLISSEMENT', 32.33420181, -6.35333014, '08:00:00', '18:00:00'
FROM users WHERE email = 'chr.benimellal@smartqueue.ma'
UNION ALL
SELECT id, 'Bd Hassan II, Résidence La Joie de Vivre, Bou Jaafar, Béni Mellal', '0521240127', 'ETABLISSEMENT', 32.3379, -6.3490, '08:15:00', '15:30:00'
FROM users WHERE email = 'bankalyousr.bm@smartqueue.ma'
UNION ALL
SELECT id, 'N° 88, Boulevard Mohamed V, Béni Mellal', '0523421519', 'ETABLISSEMENT', 32.3370, -6.3510, '08:15:00', '15:30:00'
FROM users WHERE email = 'bp.tassamete@smartqueue.ma'
UNION ALL
SELECT id, '65, Boulevard Hassan II, Béni Mellal', '0523480000', 'ETABLISSEMENT', 32.3390, -6.3475, '08:15:00', '15:30:00'
FROM users WHERE email = 'bmci.benimellal@smartqueue.ma'
UNION ALL
SELECT id, 'Palais de la Commune, Avenue Bourquia, Béni Mellal', '0523480000', 'ETABLISSEMENT', 32.3365, -6.3498, '08:30:00', '16:30:00'
FROM users WHERE email = 'commune.benimellal@smartqueue.ma';

-- =========================================================
-- 4. INSERTION DANS LA TABLE SERVICES
-- etablissement_id est récupéré depuis users/etablissements
-- =========================================================
INSERT INTO services (nom, description, duree_moyenne, etablissement_id)
SELECT 'Consultation médicale', 'Consultation médicale générale', 20, id
FROM users WHERE email = 'chr.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Urgences', 'Prise en charge des urgences médicales', 30, id
FROM users WHERE email = 'chr.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Services bancaires', 'Opérations et services bancaires', 15, id
FROM users WHERE email = 'bankalyousr.bm@smartqueue.ma'
UNION ALL
SELECT 'Retrait bancaire', 'Retrait et opérations courantes', 10, id
FROM users WHERE email = 'bankalyousr.bm@smartqueue.ma'
UNION ALL
SELECT 'Services bancaires', 'Opérations et services bancaires', 15, id
FROM users WHERE email = 'bp.tassamete@smartqueue.ma'
UNION ALL
SELECT 'Retrait bancaire', 'Retrait et opérations courantes', 10, id
FROM users WHERE email = 'bp.tassamete@smartqueue.ma'
UNION ALL
SELECT 'Services bancaires', 'Opérations et services bancaires', 15, id
FROM users WHERE email = 'bmci.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Retrait bancaire', 'Retrait et opérations courantes', 10, id
FROM users WHERE email = 'bmci.benimellal@smartqueue.ma'
UNION ALL
SELECT 'Services administratifs', 'Services administratifs pour les citoyens', 20, id
FROM users WHERE email = 'commune.benimellal@smartqueue.ma'
UNION ALL
SELECT 'État civil', 'Demandes et documents d’état civil', 15, id
FROM users WHERE email = 'commune.benimellal@smartqueue.ma';