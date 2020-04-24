-- Table: public.mitarbeiter

-- DROP TABLE public.mitarbeiter;

CREATE TABLE public.mitarbeiter
(
    pers_nr integer NOT NULL DEFAULT nextval('mitarbeiter_pers_nr_seq'::regclass),
    name character varying(40) COLLATE pg_catalog."default" NOT NULL,
    vorname character varying(40) COLLATE pg_catalog."default" NOT NULL,
    geb_datum date,
    gehalt numeric(7,2),
    abt_nr integer NOT NULL,
    geschlecht character varying(1) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT mitarbeiter_pkey PRIMARY KEY (pers_nr),
    CONSTRAINT unique_ma UNIQUE (vorname, name, geb_datum)
)

TABLESPACE pg_default;

ALTER TABLE public.mitarbeiter
    OWNER to postgres;