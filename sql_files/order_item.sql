CREATE TYPE "status_t" AS ENUM ('IN_STOCK','OUT_OF_STOCK');

CREATE TABLE public.order_item
(
    "OrderItemId" integer NOT NULL,
    "OrderId" integer NOT NULL,
    "SalePrice" numeric,
    "ShippingPrice" numeric,
    "TotalItemPrice" numeric,
    "SKU" character varying(20) COLLATE pg_catalog."default",
	"Status" "status_t",                                                                                                              
    CONSTRAINT orederitemidkey PRIMARY KEY ("OrderItemId"),
    CONSTRAINT forekey FOREIGN KEY ("OrderId")
        REFERENCES public.orders ("OrderId") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.order_item
    OWNER to postgres;