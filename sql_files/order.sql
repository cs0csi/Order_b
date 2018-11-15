CREATE TABLE public."order"
(
    "OrderId" integer NOT NULL,
    "BuyerEmail" character varying(100) COLLATE pg_catalog."default",
    "OrderDate" date,
    "OrderTotalValue" numeric,
    "Address" character varying(100) COLLATE pg_catalog."default",
    "Postcode" integer,
    "BuyerName" character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT order_pkey PRIMARY KEY ("OrderId")
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."order"
    OWNER to postgres;