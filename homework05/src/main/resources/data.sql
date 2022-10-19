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