INSERT INTO AUTHOR(ID, NAME)
VALUES (1, 'Достаевский Ф. М.');

INSERT INTO GENRE(ID, NAME)
VALUES (1, 'Триллер');

INSERT INTO AUTHOR(ID, NAME)
VALUES (2, 'Коллектив авторов');

INSERT INTO GENRE(ID, NAME)
VALUES (2, 'Научпоп');

INSERT INTO BOOK(ID, NAME, AUTHOR_ID, GENRE_ID)
VALUES (1, 'Преступление и наказание', 1, 1);

INSERT INTO BOOK(ID, NAME, AUTHOR_ID, GENRE_ID)
VALUES (2, 'Идиот', 1, 1);

INSERT INTO BOOK(ID, NAME, AUTHOR_ID, GENRE_ID)
VALUES (3, 'Записки путешественника', 2, 2);
