INSERT INTO "user" (lastname, firstname, patronymic, email, user_type, enabled)
VALUES ('Агафонов', 'Александр', 'Леонидович', 'alex.agafonov.53@gmail.com', 'LECTURER', false),
       ('Ашаев', 'Игорь', 'Викторович', 'ashaeviv@omsu.ru', 'LECTURER', false),
       ('Ашаева', 'Юлия', 'Михайловна', 'Ashaeva73@mail.ru', 'LECTURER', false),
       ('Исаченко', 'Николай', 'Андреевич', 'isachenko58@mail.ru', 'LECTURER', false),
       ('Вязанкин', 'Олег', 'Николаевич', 'ovyazankin@gmail.com', 'LECTURER', false);

INSERT INTO lecturer (chair_id, user_id)
VALUES (6, 1),
       (5, 2),
       (5, 3),
       (2, 4),
       (6, 5);