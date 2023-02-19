DROP TABLE IF EXISTS public.usr_roles;
DROP TABLE IF EXISTS public."role";
DROP TABLE IF EXISTS public."comment";
DROP TABLE IF EXISTS public.usr;
DROP TABLE IF EXISTS public.poem_text_element;
DROP TABLE IF EXISTS public.poem_picture_element;
DROP TABLE IF EXISTS public.poem_element;
DROP TABLE IF EXISTS public.poem;

CREATE TABLE public."role"
(
    id        bigserial NOT NULL,
    role_name text      NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE public.usr
(
    id         bigserial NOT NULL,
    email      text      NULL,
    "name"     text      NULL,
    "password" text      NULL,
    CONSTRAINT usr_pkey PRIMARY KEY (id)
);

CREATE TABLE public.usr_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT fk6rhwrqm0y7yrwlpli9b94toyj FOREIGN KEY (role_id) REFERENCES public."role" (id),
    CONSTRAINT fk8fls6uiuo5dxpqdjk3y2p8v8a FOREIGN KEY (user_id) REFERENCES public.usr (id)
);

CREATE TABLE public.poem
(
    id           bigserial NOT NULL,
    publish_time timestamp NULL,
    title        text      NULL,
    CONSTRAINT poem_pkey PRIMARY KEY (id)
);

CREATE TABLE public.poem_element
(
    id      bigserial NOT NULL,
    poem_id bigint    NULL,
    CONSTRAINT poem_element_pkey PRIMARY KEY (id),
    CONSTRAINT fk6w8oqpfwqkt9p9t9l8dgpjtij FOREIGN KEY (poem_id) REFERENCES public.poem (id)
);

CREATE TABLE public.poem_picture_element
(
    picture bytea  NULL,
    "scale" int2   NULL,
    id      bigint NOT NULL,
    CONSTRAINT poem_picture_element_pkey PRIMARY KEY (id),
    CONSTRAINT fkset6b0latjnbb2dto34qi9nv9 FOREIGN KEY (id) REFERENCES public.poem_element (id)
);

CREATE TABLE public.poem_text_element
(
    "content" text   NULL,
    id        bigint NOT NULL,
    CONSTRAINT poem_text_element_pkey PRIMARY KEY (id),
    CONSTRAINT fkc93qmmfp2gabxw494uihevknf FOREIGN KEY (id) REFERENCES public.poem_element (id)
);

CREATE TABLE public."comment"
(
    id              bigserial NOT NULL,
    moderated       bool      NULL,
    publish_time    timestamp NULL,
    "text"          text      NULL,
    poem_id         bigint    NULL,
    root_comment_id bigint    NULL,
    user_id         bigint    NULL,
    CONSTRAINT comment_pkey PRIMARY KEY (id)
);

ALTER TABLE public."comment"
    ADD CONSTRAINT fkd7pyfra657y2rspo598ovjqe4 FOREIGN KEY (poem_id) REFERENCES public.poem (id);
ALTER TABLE public."comment"
    ADD CONSTRAINT fkgcgdcgly6u49hf4g8y2di3g4p FOREIGN KEY (user_id) REFERENCES public.usr (id);
ALTER TABLE public."comment"
    ADD CONSTRAINT fkh3bwncut05j8e0s1p86o4hvln FOREIGN KEY (root_comment_id) REFERENCES public."comment" (id);