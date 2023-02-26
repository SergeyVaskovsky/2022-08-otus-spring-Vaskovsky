DELETE
FROM USR_ROLES;
DELETE
FROM ROLE;
DELETE
FROM POEM;
DELETE
FROM USR;

INSERT INTO ROLE(ROLE_NAME)
VALUES ('AUTHOR');

INSERT INTO ROLE(ROLE_NAME)
VALUES ('READER');

INSERT INTO ROLE(ROLE_NAME)
VALUES ('WRITER');

INSERT INTO ROLE(ROLE_NAME)
VALUES ('MODERATOR');

INSERT INTO USR(EMAIL, NAME, PASSWORD)
VALUES ('zina@yandex.ru', 'Зинаида', '123'),
       ('user@yandex.ru', 'user', '1'),
       ('sergey@yandex.ru', 'Сергей', '111');

INSERT INTO USR_ROLES(USER_ID, ROLE_ID)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 2),
       (3, 3),
       (3, 4);

INSERT INTO POEM(TITLE)
VALUES ('Парус');

INSERT INTO POEM(TITLE)
VALUES ('Кузнечик');

INSERT INTO COMMENT(MODERATED, PUBLISH_TIME, TEXT, POEM_ID, ROOT_COMMENT_ID, USER_ID)
VALUES (TRUE, '2023-02-26', 'Очень классное стихотворение', 1, NULL, 1);

INSERT INTO COMMENT(MODERATED, PUBLISH_TIME, TEXT, POEM_ID, ROOT_COMMENT_ID, USER_ID)
VALUES (TRUE, '2023-02-26', 'Вообще ерунда', 1, NULL, 1);

INSERT INTO POEM_ELEMENT(POEM_ID)
VALUES (1),
       (2),
       (2);

INSERT INTO POEM_TEXT_ELEMENT(CONTENT, ID)
VALUES ('Белеет парус одинокий
В тумане моря голубом
Что ищет он в краю далеком,
Что бросил он в краю родном.', 1);

INSERT INTO POEM_TEXT_ELEMENT(CONTENT, ID)
VALUES ('В траве сидел кузнечик
В траве сидел кузнечик
Совсем как огуречик
Зелененький он был', 2);

INSERT INTO POEM_TEXT_ELEMENT(CONTENT, ID)
VALUES ('Но тут пришла лягушка
Прожорливое брюшко
Прожорливое брюшко
И съела кузнеца', 3);