--
-- PostgreSQL database dump
--

-- Dumped from database version 11.12 (Debian 11.12-0+deb10u1)
-- Dumped by pg_dump version 13.3

-- Started on 2021-09-03 12:44:47

SET
statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

SET
default_tablespace = '';

--
-- TOC entry 205 (class 1259 OID 38862)
-- Name: assignment; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.assignment
(
    assignment_id        bigint                   NOT NULL,
    assignment_author_id bigint,
    assignment_dead_line timestamp with time zone NOT NULL,
    assignment_text      text                     NOT NULL,
    assignment_type      character varying(128)   NOT NULL,
    assignment_control   boolean                  NOT NULL,
    assignment_execution boolean                  NOT NULL,
    assignment_done      boolean DEFAULT false    NOT NULL
);


ALTER TABLE public.assignment OWNER TO dt_user;

--
-- TOC entry 204 (class 1259 OID 38860)
-- Name: assignment_assignment_id_seq; Type: SEQUENCE; Schema: public; Owner: dt_user
--

CREATE SEQUENCE public.assignment_assignment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.assignment_assignment_id_seq OWNER TO dt_user;

--
-- TOC entry 3004 (class 0 OID 0)
-- Dependencies: 204
-- Name: assignment_assignment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dt_user
--

ALTER SEQUENCE public.assignment_assignment_id_seq OWNED BY public.assignment.assignment_id;


--
-- TOC entry 202 (class 1259 OID 38823)
-- Name: department; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.department
(
    department_id              bigint                NOT NULL,
    department_name            character varying     NOT NULL,
    department_phone           character varying(12) NOT NULL,
    department_mail            character varying(64) NOT NULL,
    department_organization_id bigint                NOT NULL,
    department_chef_id         bigint
);


ALTER TABLE public.department OWNER TO dt_user;

--
-- TOC entry 201 (class 1259 OID 38821)
-- Name: department_department_id_seq; Type: SEQUENCE; Schema: public; Owner: dt_user
--

CREATE SEQUENCE public.department_department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.department_department_id_seq OWNER TO dt_user;

--
-- TOC entry 3005 (class 0 OID 0)
-- Dependencies: 201
-- Name: department_department_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dt_user
--

ALTER SEQUENCE public.department_department_id_seq OWNED BY public.department.department_id;


--
-- TOC entry 199 (class 1259 OID 38810)
-- Name: employee; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.employee
(
    employee_id          bigint                 NOT NULL,
    employee_first_name  character varying(64)  NOT NULL,
    employee_last_name   character varying(64)  NOT NULL,
    employee_middle_name character varying(64)  NOT NULL,
    employee_post        integer                NOT NULL,
    employee_password    character varying(512) NOT NULL,
    employee_login       character varying(64) DEFAULT 'ROLE_USER':: character varying NOT NULL,
    employee_role        character varying(64)  NOT NULL
);


ALTER TABLE public.employee OWNER TO dt_user;

--
-- TOC entry 209 (class 1259 OID 38922)
-- Name: employee_department; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.employee_department
(
    employee_department_department_id bigint NOT NULL,
    employee_department_employee_id   bigint NOT NULL
);


ALTER TABLE public.employee_department OWNER TO dt_user;

--
-- TOC entry 198 (class 1259 OID 38808)
-- Name: employee_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: dt_user
--

CREATE SEQUENCE public.employee_employee_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.employee_employee_id_seq OWNER TO dt_user;

--
-- TOC entry 3006 (class 0 OID 0)
-- Dependencies: 198
-- Name: employee_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dt_user
--

ALTER SEQUENCE public.employee_employee_id_seq OWNED BY public.employee.employee_id;


--
-- TOC entry 208 (class 1259 OID 38897)
-- Name: executor_assignment; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.executor_assignment
(
    executor_user_id       bigint                NOT NULL,
    executor_assignment_id bigint                NOT NULL,
    executor_check         boolean DEFAULT false NOT NULL
);


ALTER TABLE public.executor_assignment OWNER TO dt_user;

--
-- TOC entry 197 (class 1259 OID 38799)
-- Name: organization; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.organization
(
    organization_id               bigint                 NOT NULL,
    organization_name             character varying(128) NOT NULL,
    organization_physical_address character varying(256) NOT NULL,
    organization_legal_address    character varying(256) NOT NULL,
    organization_chef             bigint
);


ALTER TABLE public.organization OWNER TO dt_user;

--
-- TOC entry 196 (class 1259 OID 38797)
-- Name: organization_organization_id_seq; Type: SEQUENCE; Schema: public; Owner: dt_user
--

CREATE SEQUENCE public.organization_organization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.organization_organization_id_seq OWNER TO dt_user;

--
-- TOC entry 3007 (class 0 OID 0)
-- Dependencies: 196
-- Name: organization_organization_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dt_user
--

ALTER SEQUENCE public.organization_organization_id_seq OWNED BY public.organization.organization_id;


--
-- TOC entry 200 (class 1259 OID 38816)
-- Name: post; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.post
(
    post_name character varying(64) NOT NULL,
    post_id   integer               NOT NULL
);


ALTER TABLE public.post OWNER TO dt_user;

--
-- TOC entry 203 (class 1259 OID 38852)
-- Name: post_post_id_seq; Type: SEQUENCE; Schema: public; Owner: dt_user
--

CREATE SEQUENCE public.post_post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER TABLE public.post_post_id_seq OWNER TO dt_user;

--
-- TOC entry 3008 (class 0 OID 0)
-- Dependencies: 203
-- Name: post_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: dt_user
--

ALTER SEQUENCE public.post_post_id_seq OWNED BY public.post.post_id;


--
-- TOC entry 206 (class 1259 OID 38876)
-- Name: spring_session; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.spring_session
(
    primary_id            character(36) NOT NULL,
    session_id            character(36) NOT NULL,
    creation_time         bigint        NOT NULL,
    last_access_time      bigint        NOT NULL,
    max_inactive_interval integer       NOT NULL,
    expiry_time           bigint        NOT NULL,
    principal_name        character varying(100)
);


ALTER TABLE public.spring_session OWNER TO dt_user;

--
-- TOC entry 207 (class 1259 OID 38884)
-- Name: spring_session_attributes; Type: TABLE; Schema: public; Owner: dt_user
--

CREATE TABLE public.spring_session_attributes
(
    session_primary_id character(36)          NOT NULL,
    attribute_name     character varying(200) NOT NULL,
    attribute_bytes    bytea                  NOT NULL
);


ALTER TABLE public.spring_session_attributes OWNER TO dt_user;

--
-- TOC entry 2825 (class 2604 OID 38865)
-- Name: assignment assignment_id; Type: DEFAULT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.assignment ALTER COLUMN assignment_id SET DEFAULT nextval('public.assignment_assignment_id_seq'::regclass);


--
-- TOC entry 2824 (class 2604 OID 38826)
-- Name: department department_id; Type: DEFAULT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.department ALTER COLUMN department_id SET DEFAULT nextval('public.department_department_id_seq'::regclass);


--
-- TOC entry 2821 (class 2604 OID 38813)
-- Name: employee employee_id; Type: DEFAULT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.employee ALTER COLUMN employee_id SET DEFAULT nextval('public.employee_employee_id_seq'::regclass);


--
-- TOC entry 2820 (class 2604 OID 38802)
-- Name: organization organization_id; Type: DEFAULT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.organization ALTER COLUMN organization_id SET DEFAULT nextval('public.organization_organization_id_seq'::regclass);


--
-- TOC entry 2823 (class 2604 OID 38854)
-- Name: post post_id; Type: DEFAULT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.post ALTER COLUMN post_id SET DEFAULT nextval('public.post_post_id_seq'::regclass);


--
-- TOC entry 2994 (class 0 OID 38862)
-- Dependencies: 205
-- Data for Name: assignment; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.assignment
VALUES (2, 1, '2021-12-03 10:15:30+05', 'блаблабла', 'накодитвфы', false, false, false);


--
-- TOC entry 2991 (class 0 OID 38823)
-- Dependencies: 202
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.department
VALUES (3, 'Dep of 1.2', '123456789012', 'test@mail.ru', 1, 1);
INSERT INTO public.department
VALUES (4, 'Dep of 1.3', '123456789012', 'test@mail.ru', 1, 1);
INSERT INTO public.department
VALUES (1, 'Dep of 1.1 upd', '123456789012', 'test@mail.ru', 1, 1);


--
-- TOC entry 2988 (class 0 OID 38810)
-- Dependencies: 199
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.employee
VALUES (2, 'blabla', 'balbalba', 'bafasfasafa', 2, '$2a$10$JrT/ve5ns5Ch1WENPUdoEu.FcnKbEy5vL98rhLpOKl7PMJiQZIGYO',
        'test', 'ROLE_USER');
INSERT INTO public.employee
VALUES (3, 'blabla', 'balbalba', 'bafasfasafa', 2, '$2a$10$L5rduMNbp1y6WwWuLvpkKeqN1LsyhQQY5/Oyk3e3C.Sk5rv19EBnS',
        'test2', 'ROLE_USER');
INSERT INTO public.employee
VALUES (4, 'тестовый4', 'balbalba', 'bafasfasafa', 2, '$2a$10$gZ6pg/0/JLiAKAaSF5XVL.ZPC8cDf3HSwxNTnrp5VdmhRjawbjKsO',
        'test4', 'ROLE_USER');
INSERT INTO public.employee
VALUES (1, 'Админ', 'Админый', 'upd', 1, '$2a$10$hXsbK0mxoZt2Qo52a8G9/e2gHLZqvSmdPaFxmgBuwq95Ym/e78Q/e', 'admin',
        'ROLE_ADMIN');


--
-- TOC entry 2998 (class 0 OID 38922)
-- Dependencies: 209
-- Data for Name: employee_department; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.employee_department
VALUES (1, 2);
INSERT INTO public.employee_department
VALUES (1, 3);
INSERT INTO public.employee_department
VALUES (1, 4);
INSERT INTO public.employee_department
VALUES (4, 1);


--
-- TOC entry 2997 (class 0 OID 38897)
-- Dependencies: 208
-- Data for Name: executor_assignment; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.executor_assignment
VALUES (1, 2, false);
INSERT INTO public.executor_assignment
VALUES (4, 2, false);


--
-- TOC entry 2986 (class 0 OID 38799)
-- Dependencies: 197
-- Data for Name: organization; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.organization
VALUES (2, 'Тестовая орг 2', 'Физ адрес 2', 'Юр адрес 2', 1);
INSERT INTO public.organization
VALUES (1, 'Тестовая орг 1 upd', 'Физ адрес 11111', 'Юр адрес 11111', 1);


--
-- TOC entry 2989 (class 0 OID 38816)
-- Dependencies: 200
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.post
VALUES ('Юзер', 2);
INSERT INTO public.post
VALUES ('Администратор', 1);


--
-- TOC entry 2995 (class 0 OID 38876)
-- Dependencies: 206
-- Data for Name: spring_session; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.spring_session
VALUES ('ec289e1d-2fc6-43fa-a29f-2189ca27ee86', 'f94df31d-ecc5-4b54-b485-f0ff56257e14', 1630150768481, 1630164874691,
        604800, 1630769674691, '1');


--
-- TOC entry 2996 (class 0 OID 38884)
-- Dependencies: 207
-- Data for Name: spring_session_attributes; Type: TABLE DATA; Schema: public; Owner: dt_user
--

INSERT INTO public.spring_session_attributes
VALUES ('ec289e1d-2fc6-43fa-a29f-2189ca27ee86', 'SPRING_SECURITY_CONTEXT',
        '\xaced00057372003d6f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e636f6e746578742e5365637572697479436f6e74657874496d706c00000000000002260200014c000e61757468656e7469636174696f6e7400324c6f72672f737072696e676672616d65776f726b2f73656375726974792f636f72652f41757468656e7469636174696f6e3b78707372004f6f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e557365726e616d6550617373776f726441757468656e7469636174696f6e546f6b656e00000000000002260200024c000b63726564656e7469616c737400124c6a6176612f6c616e672f4f626a6563743b4c00097072696e636970616c71007e0004787200476f72672e737072696e676672616d65776f726b2e73656375726974792e61757468656e7469636174696f6e2e416273747261637441757468656e7469636174696f6e546f6b656ed3aa287e6e47640e0200035a000d61757468656e746963617465644c000b617574686f7269746965737400164c6a6176612f7574696c2f436f6c6c656374696f6e3b4c000764657461696c7371007e0004787001737200266a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c654c697374fc0f2531b5ec8e100200014c00046c6973747400104c6a6176612f7574696c2f4c6973743b7872002c6a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65436f6c6c656374696f6e19420080cb5ef71e0200014c00016371007e00067870737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a65787000000001770400000001737200426f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e617574686f726974792e53696d706c654772616e746564417574686f7269747900000000000002260200014c0004726f6c657400124c6a6176612f6c616e672f537472696e673b787074000a524f4c455f41444d494e7871007e000d737200486f72672e737072696e676672616d65776f726b2e73656375726974792e7765622e61757468656e7469636174696f6e2e57656241757468656e7469636174696f6e44657461696c7300000000000002260200024c000d72656d6f74654164647265737371007e000f4c000973657373696f6e496471007e000f787074000f303a303a303a303a303a303a303a31707372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b020000787000000000000000017372002472752e64726f6d72616e2e74657374747a2e64746f2e53657373696f6e5573657244544f457b6870fa2283020200014c000675736572496471007e000f787200326f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e7573657264657461696c732e5573657200000000000002260200075a00116163636f756e744e6f6e457870697265645a00106163636f756e744e6f6e4c6f636b65645a001563726564656e7469616c734e6f6e457870697265645a0007656e61626c65644c000b617574686f72697469657374000f4c6a6176612f7574696c2f5365743b4c000870617373776f726471007e000f4c0008757365726e616d6571007e000f787001010101737200256a6176612e7574696c2e436f6c6c656374696f6e7324556e6d6f6469666961626c65536574801d92d18f9b80550200007871007e000a737200116a6176612e7574696c2e54726565536574dd98509395ed875b0300007870737200466f72672e737072696e676672616d65776f726b2e73656375726974792e636f72652e7573657264657461696c732e5573657224417574686f72697479436f6d70617261746f720000000000000226020000787077040000000171007e0010787400007400013171007e0023');


--
-- TOC entry 3009 (class 0 OID 0)
-- Dependencies: 204
-- Name: assignment_assignment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dt_user
--

SELECT pg_catalog.setval('public.assignment_assignment_id_seq', 2, true);


--
-- TOC entry 3010 (class 0 OID 0)
-- Dependencies: 201
-- Name: department_department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dt_user
--

SELECT pg_catalog.setval('public.department_department_id_seq', 5, true);


--
-- TOC entry 3011 (class 0 OID 0)
-- Dependencies: 198
-- Name: employee_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dt_user
--

SELECT pg_catalog.setval('public.employee_employee_id_seq', 6, true);


--
-- TOC entry 3012 (class 0 OID 0)
-- Dependencies: 196
-- Name: organization_organization_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dt_user
--

SELECT pg_catalog.setval('public.organization_organization_id_seq', 3, true);


--
-- TOC entry 3013 (class 0 OID 0)
-- Dependencies: 203
-- Name: post_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: dt_user
--

SELECT pg_catalog.setval('public.post_post_id_seq', 1, true);


--
-- TOC entry 2843 (class 2606 OID 38870)
-- Name: assignment assignment_pkey; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.assignment
    ADD CONSTRAINT assignment_pkey PRIMARY KEY (assignment_id);


--
-- TOC entry 2839 (class 2606 OID 38831)
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_pkey PRIMARY KEY (department_id);


--
-- TOC entry 2854 (class 2606 OID 38926)
-- Name: employee_department employee_department_pkey; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.employee_department
    ADD CONSTRAINT employee_department_pkey PRIMARY KEY (employee_department_department_id, employee_department_employee_id);


--
-- TOC entry 2833 (class 2606 OID 38815)
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (employee_id);


--
-- TOC entry 2852 (class 2606 OID 38901)
-- Name: executor_assignment executor_assignment_pkey; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.executor_assignment
    ADD CONSTRAINT executor_assignment_pkey PRIMARY KEY (executor_user_id, executor_assignment_id);


--
-- TOC entry 2829 (class 2606 OID 38807)
-- Name: organization organization_pkey; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (organization_id);


--
-- TOC entry 2837 (class 2606 OID 38859)
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (post_id);


--
-- TOC entry 2850 (class 2606 OID 38891)
-- Name: spring_session_attributes spring_session_attributes_pk; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.spring_session_attributes
    ADD CONSTRAINT spring_session_attributes_pk PRIMARY KEY (session_primary_id, attribute_name);


--
-- TOC entry 2848 (class 2606 OID 38880)
-- Name: spring_session spring_session_pk; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.spring_session
    ADD CONSTRAINT spring_session_pk PRIMARY KEY (primary_id);


--
-- TOC entry 2835 (class 2606 OID 38916)
-- Name: employee uq_login; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT uq_login UNIQUE (employee_login);


--
-- TOC entry 2831 (class 2606 OID 38919)
-- Name: organization uq_name; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.organization
    ADD CONSTRAINT uq_name UNIQUE (organization_name);


--
-- TOC entry 2841 (class 2606 OID 39818)
-- Name: department uq_org_name; Type: CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT uq_org_name UNIQUE (department_id, department_name);


--
-- TOC entry 2844 (class 1259 OID 38881)
-- Name: spring_session_ix1; Type: INDEX; Schema: public; Owner: dt_user
--

CREATE UNIQUE INDEX spring_session_ix1 ON public.spring_session USING btree (session_id);


--
-- TOC entry 2845 (class 1259 OID 38882)
-- Name: spring_session_ix2; Type: INDEX; Schema: public; Owner: dt_user
--

CREATE INDEX spring_session_ix2 ON public.spring_session USING btree (expiry_time);


--
-- TOC entry 2846 (class 1259 OID 38883)
-- Name: spring_session_ix3; Type: INDEX; Schema: public; Owner: dt_user
--

CREATE INDEX spring_session_ix3 ON public.spring_session USING btree (principal_name);


--
-- TOC entry 2861 (class 2606 OID 38907)
-- Name: executor_assignment fk_assignment; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.executor_assignment
    ADD CONSTRAINT fk_assignment FOREIGN KEY (executor_assignment_id) REFERENCES public.assignment(assignment_id) ON
UPDATE CASCADE
ON
DELETE CASCADE;


--
-- TOC entry 2858 (class 2606 OID 38871)
-- Name: assignment fk_author; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.assignment
    ADD CONSTRAINT fk_author FOREIGN KEY (assignment_author_id) REFERENCES public.employee(employee_id) ON
UPDATE CASCADE
ON
DELETE
SET NULL;


--
-- TOC entry 2855 (class 2606 OID 38842)
-- Name: organization fk_chef; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.organization
    ADD CONSTRAINT fk_chef FOREIGN KEY (organization_chef) REFERENCES public.employee(employee_id) ON
UPDATE CASCADE
ON
DELETE
SET NULL NOT VALID;


--
-- TOC entry 2857 (class 2606 OID 38847)
-- Name: department fk_chef; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT fk_chef FOREIGN KEY (department_chef_id) REFERENCES public.employee(employee_id) ON
UPDATE CASCADE
ON
DELETE
SET NULL NOT VALID;


--
-- TOC entry 2863 (class 2606 OID 38932)
-- Name: employee_department fk_dep; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.employee_department
    ADD CONSTRAINT fk_dep FOREIGN KEY (employee_department_department_id) REFERENCES public.department(department_id) ON
UPDATE CASCADE
ON
DELETE CASCADE;


--
-- TOC entry 2856 (class 2606 OID 38832)
-- Name: department fk_organiztion; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT fk_organiztion FOREIGN KEY (department_organization_id) REFERENCES public.organization(organization_id) ON
UPDATE CASCADE
ON
DELETE CASCADE;


--
-- TOC entry 2860 (class 2606 OID 38902)
-- Name: executor_assignment fk_user; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.executor_assignment
    ADD CONSTRAINT fk_user FOREIGN KEY (executor_user_id) REFERENCES public.employee(employee_id) ON
UPDATE CASCADE
ON
DELETE CASCADE;


--
-- TOC entry 2862 (class 2606 OID 38927)
-- Name: employee_department fk_user; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.employee_department
    ADD CONSTRAINT fk_user FOREIGN KEY (employee_department_employee_id) REFERENCES public.employee(employee_id) ON
UPDATE CASCADE
ON
DELETE CASCADE;


--
-- TOC entry 2859 (class 2606 OID 38892)
-- Name: spring_session_attributes spring_session_attributes_fk; Type: FK CONSTRAINT; Schema: public; Owner: dt_user
--

ALTER TABLE ONLY public.spring_session_attributes
    ADD CONSTRAINT spring_session_attributes_fk FOREIGN KEY (session_primary_id) REFERENCES public.spring_session(primary_id) ON
DELETE
CASCADE;


-- Completed on 2021-09-03 12:44:47

--
-- PostgreSQL database dump complete
--

