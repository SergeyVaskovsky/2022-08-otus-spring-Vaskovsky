INSERT INTO AUTHOR(NAME)
VALUES ('Достаевский Ф. М.');

INSERT INTO GENRE(NAME)
VALUES ('Триллер');

INSERT INTO AUTHOR(NAME)
VALUES ('Коллектив авторов');

INSERT INTO GENRE(NAME)
VALUES ('Научпоп');

INSERT INTO BOOK(NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Преступление и наказание', 1, 1);

INSERT INTO BOOK(NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Идиот', 1, 1);

INSERT INTO BOOK(NAME, AUTHOR_ID, GENRE_ID)
VALUES ('Записки путешественника', 2, 2);
