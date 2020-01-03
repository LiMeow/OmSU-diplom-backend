CREATE TABLE IF NOT EXISTS faculties
(
    id          SERIAL PRIMARY KEY,
    building_id INTEGER     NOT NULL REFERENCES buildings (id) ON DELETE CASCADE,
    name        TEXT UNIQUE NOT NULL
);

INSERT INTO faculties (id, building_id, name)
VALUES (1, 1, 'ИНСТИТУТ МАТЕМАТИКИ И ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ'),
       (2, 1, 'ФИЗИЧЕСКИЙ ФАКУЛЬТЕТ'),
       (3, 1, 'ХИМИЧЕСКИЙ ФАКУЛЬТЕТ'),
       (4, 2, 'ИСТОРИЧЕСКИЙ ФАКУЛЬТЕТ'),
       (5, 2, 'ФАКУЛЬТЕТ ФИЛОЛОГИИ И МЕДИАКОММУНИКАЦИЙ'),
       (6, 2, 'ФАКУЛЬТЕТ ИНОСТРАННЫХ ЯЗЫКОВ'),
       (7, 2, 'ФАКУЛЬТЕТ ТЕОЛОГИИ, ФИЛОСОФИИ И МИРОВЫХ КУЛЬТУР'),
       (8, 3, 'ЦЕНТР ДОВУЗОВСКОЙ ПОДГОТОВКИ И ПРОФОРИЕНТАЦИИ'),
       (9, 4, 'ФАКУЛЬТЕТ МЕЖДУНАРОДНОГО БИЗНЕСА'),
       (10, 4, 'ФАКУЛЬТЕТ ПСИХОЛОГИИ'),
       (11, 4, 'ЦЕНТР ДЕЛОВОГО ОБРАЗОВАНИЯ ОМГУ'),
       (12, 5, 'ФАКУЛЬТЕТ КУЛЬТУРЫ И ИСКУССТВ'),
       (13, 6, 'ЭКОНОМИЧЕСКИЙ ФАКУЛЬТЕТ'),
       (14, 6, 'ФАКУЛЬТЕТ ФИЗИЧЕСКОЙ КУЛЬТУРЫ, РЕАБИЛИТАЦИИ И СПОРТА'),
       (15, 7, 'ЮРИДИЧЕСКИЙ ФАКУЛЬТЕТ'),
       (16, 7, 'ИНСТИТУТ НЕПРЕРЫВНОГО И ОТКРЫТОГО ОБРАЗОВАНИЯ'),
       (17, 10, 'ФАКУЛЬТЕТ КОМПЬЮТЕРНЫХ НАУК'),
       (18, 11, 'ИНСТИТУТ СРЕДНЕГО ПРОФЕССИОНАЛЬНОГО ОБРАЗОВАНИЯ И ДОВУЗОВСКОЙ ПОДГОТОВКИ');