DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS USR_ROLES;
DROP TABLE IF EXISTS USR;
DROP TABLE IF EXISTS ROLE;


CREATE TABLE AUTHOR
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255)
);

CREATE TABLE GENRE
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255)
);

CREATE TABLE BOOK
(
    ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME      VARCHAR(255),
    AUTHOR_ID BIGINT,
    GENRE_ID  BIGINT,
    FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (ID),
    FOREIGN KEY (GENRE_ID) REFERENCES GENRE (ID)
);

CREATE TABLE COMMENT
(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION VARCHAR(255),
    BOOK_ID     BIGINT,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID)
);

CREATE TABLE USR
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(255),
    PASSWORD VARCHAR(255)
);

CREATE TABLE ROLE
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    ROLENAME VARCHAR(255)
);

CREATE TABLE USR_ROLES
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USR (ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID)
);

CREATE TABLE IF NOT EXISTS acl_sid (
                                       id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       principal tinyint NOT NULL,
                                       sid varchar(100) NOT NULL,
                                       UNIQUE (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
                                         id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         class varchar(255) NOT NULL,
                                         UNIQUE (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
                                         id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         acl_object_identity bigint NOT NULL,
                                         ace_order int NOT NULL,
                                         sid bigint NOT NULL,
                                         mask int NOT NULL,
                                         granting tinyint NOT NULL,
                                         audit_success tinyint NOT NULL,
                                         audit_failure tinyint NOT NULL,
                                         UNIQUE (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
                                                   id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                   object_id_class bigint NOT NULL,
                                                   object_id_identity bigint NOT NULL,
                                                   parent_object bigint DEFAULT NULL,
                                                   owner_sid bigint DEFAULT NULL,
                                                   entries_inheriting tinyint NOT NULL,
                                                   UNIQUE (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);