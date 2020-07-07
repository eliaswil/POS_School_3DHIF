-- Table: public.mitarbeiter

-- DROP TABLE public.mitarbeiter;

CREATE TABLE public.mitarbeiter
(
    pers_nr INTEGER NOT NULL,
    name VARCHAR(40) NOT NULL,
    vorname VARCHAR(40) NOT NULL,
    geb_datum DATE,
    gehalt NUMERIC(7,2),
    abt_nr INTEGER NOT NULL,
    geschlecht CHAR(1) NOT NULL,
    CONSTRAINT mitarbeiter_pkey PRIMARY KEY (pers_nr),
    CONSTRAINT unique_ma UNIQUE (vorname, name, geb_datum)
)

TABLESPACE pg_default;

ALTER TABLE public.mitarbeiter
    OWNER to postgres;