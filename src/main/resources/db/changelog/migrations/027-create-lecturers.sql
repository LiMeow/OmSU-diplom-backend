INSERT INTO "user" (lastname, firstname, patronymic, email, user_type, enabled)
VALUES ('Агафонов', 'Александр', 'Леонидович', 'alex.agafonov.53@gmail.com', 'ROLE_LECTURER', false),
       ('Ашаев', 'Игорь', 'Викторович', 'ashaeviv@omsu.ru', 'ROLE_LECTURER', false),
       ('Ашаева', 'Юлия', 'Михайловна', 'Ashaeva73@mail.ru', 'ROLE_LECTURER', false),
       ('Исаченко', 'Николай', 'Андреевич', 'isachenko58@mail.ru', 'ROLE_LECTURER', false),
       ('Вязанкин', 'Олег', 'Николаевич', 'ovyazankin@gmail.com', 'ROLE_LECTURER', false);

INSERT INTO lecturer (chair_id, user_id)
VALUES (6, 1),
       (5, 2),
       (5, 3),
       (2, 4),
       (6, 5);