INSERT INTO AUTHOR(NAME)
VALUES ('Пушкин А. С.');
INSERT INTO AUTHOR(NAME)
VALUES ('Кайт Том');
INSERT INTO AUTHOR(NAME)
VALUES ('Макконел Стивен');
INSERT INTO GENRE(NAME)
VALUES ('Стихи');
INSERT INTO GENRE(NAME)
VALUES ('Программирование для профессионалов');


INSERT INTO BOOK(NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Евгений Онегин', 1, 1);
INSERT INTO BOOK(NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Совершенный код', 3, 2);
INSERT INTO BOOK(NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Oracle для профессионалов', 2, 1);

INSERT INTO COMMENT(DESCRIPTION, BOOK_ID)
VALUES ('Паршиво', 1);
INSERT INTO COMMENT(DESCRIPTION, BOOK_ID)
VALUES ('Сносно', 1);
INSERT INTO COMMENT(DESCRIPTION, BOOK_ID)
VALUES ('Ничего', 1);
INSERT INTO COMMENT(DESCRIPTION, BOOK_ID)
VALUES ('Можно читать', 1);
INSERT INTO COMMENT(DESCRIPTION, BOOK_ID)
VALUES ('Увлекло', 1);

INSERT INTO COMMENT(DESCRIPTION, BOOK_ID)
VALUES ('Можно читать', 2);
INSERT INTO COMMENT(DESCRIPTION, BOOK_ID)
VALUES ('Увлекло', 2);

INSERT INTO USR(USERNAME, PASSWORD)
VALUES('admin', 'admin');

INSERT INTO USR(USERNAME, PASSWORD)
VALUES('user', 'user');

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'admin'),
(2, 1, 'user');


INSERT INTO acl_class (id, class) VALUES
    (1, 'ru.otus.homework13.model.Genre');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 1, 0),
(2, 1, 2, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 1, 1, 1, 1, 1),
(4, 2, 1, 2, 1, 1, 1, 1),
(5, 2, 2, 2, 1, 1, 1, 1);