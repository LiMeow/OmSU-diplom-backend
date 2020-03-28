INSERT INTO study_direction (faculty_id, code, name, qualification, study_form)
VALUES (1, '01.03.02', 'Прикладная математика и информатика', 'BACHELOR', 'FULL_TIME'),
       (1, '01.05.01', 'Фундаментальные математика и механика', 'SPECIALIST', 'FULL_TIME'),
       (2, '03.03.01', 'Прикладные математика и физика', 'BACHELOR', 'FULL_TIME'),
       (2, '03.03.02', 'Физика', 'BACHELOR', 'FULL_TIME'),
       (2, '03.03.03', 'Радиофизика', 'BACHELOR', 'FULL_TIME'),
       (2, '12.03.04', 'Биотехнические системы и технологии', 'BACHELOR', 'FULL_TIME');

INSERT INTO course (faculty_id, start_year, finish_year)
VALUES (1, '2016', '2020'),
       (1, '2017', '2021'),
       (1, '2018', '2022'),
       (1, '2019', '2023');


INSERT INTO "group" (study_direction_id, course_id, name, formation_year)
VALUES (2, 1, 'ММС-601-О', 2016),
       (1, 1, 'МПБ-602-О', 2016),
       (1, 1, 'МПБ-603-О', 2016),
       (1, 1, 'МПБ-604-О', 2016);