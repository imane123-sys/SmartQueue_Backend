

INSERT INTO users (nom, email, password, role)
VALUES
    (
        'Attijariwafa Bank - Agence Souk Sebt',
        'attijari.souksebt@smartqueue.ma',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
        'ETABLISSEMENT'
    ),
    (
        'CIH Bank - Agence Souk Sebt',
        'cih.souksebt@smartqueue.ma',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
        'ETABLISSEMENT'
    ),
    (
        'Crédit Agricole du Maroc - Agence Souk Sebt',
        'creditagricole.souksebt@smartqueue.ma',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
        'ETABLISSEMENT'
    ),
    (
        'Société Générale - Agence Souk Sebt',
        'sgmb.souksebt@smartqueue.ma',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
        'ETABLISSEMENT'
    ),
    (
        'Clinique Souk Sebt',
        'clinique.souksebt@smartqueue.ma',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
        'ETABLISSEMENT'
    ),
    (
        'Clinique Al Amal - Souk Sebt',
        'clinique.alamal.souksebt@smartqueue.ma',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5Y7f6j7mW6mV7L9K6z7W9Y4z7n2mQ7e',
        'ETABLISSEMENT'
    );



INSERT INTO etablissements
(id, adresse, telephone, type, latitude, longitude,
 rayon_km, horaire_ouverture, horaire_fermeture)

SELECT id,
       'Avenue Hassan II, Souk Sebt Oulad Nemma',
       '0523488100',
       'Banque',
       32.5320,
       -6.5305,
       10.0,
       '08:15:00',
       '15:30:00'
FROM users
WHERE email = 'attijari.souksebt@smartqueue.ma'

UNION ALL

SELECT id,
       'Avenue Mohammed V, Souk Sebt Oulad Nemma',
       '0523488200',
       'Banque',
       32.5332,
       -6.5290,
       10.0,
       '08:15:00',
       '15:30:00'
FROM users
WHERE email = 'cih.souksebt@smartqueue.ma'

UNION ALL

SELECT id,
       'Centre-ville, Souk Sebt Oulad Nemma',
       '0523488300',
       'Banque',
       32.5310,
       -6.5315,
       10.0,
       '08:15:00',
       '15:30:00'
FROM users
WHERE email = 'creditagricole.souksebt@smartqueue.ma'

UNION ALL

SELECT id,
       'Boulevard Principal, Souk Sebt Oulad Nemma',
       '0523488400',
       'Banque',
       32.5340,
       -6.5280,
       10.0,
       '08:15:00',
       '15:30:00'
FROM users
WHERE email = 'sgmb.souksebt@smartqueue.ma'

UNION ALL

SELECT id,
       'Quartier Centre, Souk Sebt Oulad Nemma',
       '0523488500',
       'Clinique',
       32.5295,
       -6.5330,
       10.0,
       '08:00:00',
       '19:00:00'
FROM users
WHERE email = 'clinique.souksebt@smartqueue.ma'

UNION ALL

SELECT id,
       'Route de Béni Mellal, Souk Sebt Oulad Nemma',
       '0523488600',
       'Clinique',
       32.5275,
       -6.5350,
       10.0,
       '08:00:00',
       '19:00:00'
FROM users
WHERE email = 'clinique.alamal.souksebt@smartqueue.ma';




INSERT INTO services
(nom, description, duree_moyenne, etablissement_id)



SELECT 'Services bancaires',
       'Opérations bancaires courantes et virements',
       15,
       id
FROM users
WHERE email = 'attijari.souksebt@smartqueue.ma'

UNION ALL

SELECT 'Retrait bancaire',
       'Retrait d’espèces au guichet',
       10,
       id
FROM users
WHERE email = 'attijari.souksebt@smartqueue.ma'


UNION ALL

SELECT 'Services bancaires',
       'Opérations bancaires et gestion de compte',
       15,
       id
FROM users
WHERE email = 'cih.souksebt@smartqueue.ma'

UNION ALL

SELECT 'Retrait bancaire',
       'Retrait d’espèces au guichet',
       10,
       id
FROM users
WHERE email = 'cih.souksebt@smartqueue.ma'


UNION ALL

SELECT 'Services bancaires',
       'Opérations bancaires et financement agricole',
       15,
       id
FROM users
WHERE email = 'creditagricole.souksebt@smartqueue.ma'

UNION ALL

SELECT 'Retrait bancaire',
       'Retrait d’espèces et opérations courantes',
       10,
       id
FROM users
WHERE email = 'creditagricole.souksebt@smartqueue.ma'




UNION ALL

SELECT 'Services bancaires',
       'Services et conseil bancaire',
       15,
       id
FROM users
WHERE email = 'sgmb.souksebt@smartqueue.ma'

UNION ALL

SELECT 'Retrait bancaire',
       'Retrait d’espèces et change',
       10,
       id
FROM users
WHERE email = 'sgmb.souksebt@smartqueue.ma'




UNION ALL

SELECT 'Consultation médicale',
       'Consultation médicale générale et spécialisée',
       20,
       id
FROM users
WHERE email = 'clinique.souksebt@smartqueue.ma'

UNION ALL

SELECT 'Urgences',
       'Service d’urgences 24/7',
       30,
       id
FROM users
WHERE email = 'clinique.souksebt@smartqueue.ma'




UNION ALL

SELECT 'Consultation médicale',
       'Consultation médicale générale et spécialisée',
       20,
       id
FROM users
WHERE email = 'clinique.alamal.souksebt@smartqueue.ma'

UNION ALL

SELECT 'Urgences',
       'Service d’urgences médicales',
       30,
       id
FROM users
WHERE email = 'clinique.alamal.souksebt@smartqueue.ma';