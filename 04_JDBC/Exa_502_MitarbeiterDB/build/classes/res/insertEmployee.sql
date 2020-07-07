INSERT INTO mitarbeiter (pers_nr, name, vorname, geb_datum, gehalt, abt_nr, geschlecht)
VALUES (%d, '%s', '%s', TO_DATE('%s', 'DD.MM.YYYY'), %s, %d, '%c');

