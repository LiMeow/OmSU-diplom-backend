CREATE TABLE IF NOT EXISTS buildings
(
    id      SERIAL PRIMARY KEY,
    number  INTEGER NOT NULL,
    address TEXT    NOT NULL,
    unique (number, address)
);

INSERT INTO buildings (number, address)
VALUES (1, 'пр. Мира, 55-а'),
       (2, 'пр. Мира, 55'),
       (3, 'ул. Андрианова, 28'),
       (4, 'ул. Нефтезаводская, 11'),
       (5, 'ул. Красный путь, 36'),
       (6, 'пл. Лицкевича, 1'),
       (7, 'ул. 50 лет Профсоюзов, 100, корпус 1'),
       (8, 'ул. Пригородная, 10'),
       (9, 'ул. Гуртьева, 1-б'),
       (10, 'ул. Грозненская, 11'),
       (11, 'ул. 10 лет Октября 195/18');