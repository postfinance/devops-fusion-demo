INSERT INTO issuer (name)
    VALUES ('FremdIssuer'), ('ForeignIssuer');

INSERT INTO card_range (min_card_number, max_card_number, issuer_id)
    VALUES (10000000, 19999999, (SELECT id FROM issuer WHERE name = 'FremdIssuer')),
           (20000000, 29999999, (SELECT id FROM issuer WHERE name = 'ForeignIssuer'));

INSERT INTO terminal (terminal_id)
    VALUES (12345678), (23456789);
