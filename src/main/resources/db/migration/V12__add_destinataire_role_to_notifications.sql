ALTER TABLE notifications ADD COLUMN destinataire_role VARCHAR(50);

UPDATE notifications SET destinataire_role = 'CLIENT' WHERE titre IN ('Confirmation de ticket', 'Ticket créé', 'Votre tour s''approche !', 'Votre tour approche', 'C''est votre tour !', 'C''est votre tour');
UPDATE notifications SET destinataire_role = 'ETABLISSEMENT' WHERE titre IN ('Annulation de ticket', 'Ticket annulé', 'Nouveau ticket');
UPDATE notifications SET destinataire_role = 'CLIENT' WHERE destinataire_role IS NULL;
