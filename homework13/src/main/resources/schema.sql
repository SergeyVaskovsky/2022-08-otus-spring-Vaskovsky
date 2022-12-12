DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS USR;

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

CREATE TABLE IF NOT EXISTS acl_sid (
                                       id bigint(20) NOT NULL AUTO_INCREMENT,
                                       principal tinyint(1) NOT NULL,
                                       sid varchar(100) NOT NULL,
                                       PRIMARY KEY (id),
                                       UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
                                         id bigint(20) NOT NULL AUTO_INCREMENT,
                                         class varchar(255) NOT NULL,
                                         PRIMARY KEY (id),
                                         UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
                                         id bigint(20) NOT NULL AUTO_INCREMENT,
                                         acl_object_identity bigint(20) NOT NULL,
                                         ace_order int(11) NOT NULL,
                                         sid bigint(20) NOT NULL,
                                         mask int(11) NOT NULL,
                                         granting tinyint(1) NOT NULL,
                                         audit_success tinyint(1) NOT NULL,
                                         audit_failure tinyint(1) NOT NULL,
                                         PRIMARY KEY (id),
                                         UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
                                                   id bigint(20) NOT NULL AUTO_INCREMENT,
                                                   object_id_class bigint(20) NOT NULL,
                                                   object_id_identity bigint(20) NOT NULL,
                                                   parent_object bigint(20) DEFAULT NULL,
                                                   owner_sid bigint(20) DEFAULT NULL,
                                                   entries_inheriting tinyint(1) NOT NULL,
                                                   PRIMARY KEY (id),
                                                   UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
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