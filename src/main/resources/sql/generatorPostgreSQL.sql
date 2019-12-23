drop schema public cascade;
create schema public;

CREATE TABLE users
(
    id        serial primary key,
    email     text unique not null,
    pswrd     text default null,
    user_type text        not null
);

CREATE TABLE buildings
(
    id      serial primary key,
    number  integer not null,
    address text    not null,
    unique (number, address)
);

CREATE table time_blocks
(
    id        serial primary key,
    time_from text unique not null,
    time_to   text unique not null
);

CREATE TABLE auditories
(
    id          serial primary key,
    building_id integer not null REFERENCES buildings (id) on delete cascade,
    number      text    NOT null,
    unique (building_id, number)
);

CREATE TABLE tags
(
    id  serial primary key,
    tag text unique not null
);

CREATE TABLE auditory_tags
(
    auditory_id integer not null REFERENCES auditories (id) on delete cascade,
    tag_id      integer not null REFERENCES tags (id) on delete cascade,
    primary key (auditory_id, tag_id)
);

CREATE TABLE faculties
(
    id          serial primary key,
    building_id integer     not null REFERENCES buildings (id) on delete cascade,
    name        text unique null
);

create TABLE chairs
(
    id         serial primary key,
    name       text    not null,
    faculty_id integer not null REFERENCES faculties (id) on delete cascade,
);

CREATE TABLE personal_datas
(
    id         serial primary key,
    user_id    integer not null REFERENCES users (id) on delete cascade,
    firstname  text    not null,
    patronymic text default null,
    lastname   text    not null
);


CREATE TABLE lecturers
(
    id               serial primary key,
    chair_id         integer not null REFERENCES chairs (id) on delete cascade,
    personal_data_id integer not null REFERENCES personal_datas (id) on delete cascade,
    enabled          boolean not null
);


CREATE TABLE lecturer_preferences
(
    id          serial primary key,
    lecturer_id integer not null REFERENCES lecturers (id) on delete cascade,
    preference  text    not null
);

CREATE TABLE study_directions
(
    id            serial primary key,
    faculty_id    integer     not null REFERENCES faculties (id) on delete cascade,
    qualification text        not null,
    name          text unique null
);

CREATE TABLE groups
(
    id                 serial primary key,
    study_direction_id integer not null REFERENCES study_directions (id) on delete cascade,
    name               text    not null,
    unique (study_direction_id, name)
);

CREATE TABLE students
(
    id               serial primary key,
    group_id         integer not null REFERENCES groups (id) on delete cascade,
    personal_data_id integer not null REFERENCES personal_datas (id) on delete cascade
);


CREATE TABLE auditory_occupations
(
    id            serial primary key,
    auditory_id   integer not null REFERENCES auditories (id) on delete cascade,
    time_block_id integer not null REFERENCES time_blocks (id) on delete cascade,
    date          text    not null,
    lecturer_id   integer DEFAULT null REFERENCES lecturers (id),
    group_id      integer DEFAULT null REFERENCES groups (id) on delete cascade,
    comment       text    DEFAULT null
);

CREATE TABLE disciplines
(
    id   serial primary key,
    name text unique not null
);


CREATE table activity_types
(
    id   serial primary key,
    type text unique not null
);

CREATE TABLE schedule_items
(
    id                     serial primary key,
    auditory_occupation_id integer not null REFERENCES auditory_occupations (id) on delete cascade,
    discipline_id          integer not null REFERENCES disciplines (id) on delete cascade,
    activity_type_id       integer not null REFERENCES activity_types (id) on delete cascade
);

CREATE TABLE schedules
(
    id               serial primary key,
    course           integer not null,
    semester         integer not null,
    study_form       text    not null,
    study_year       text    not null,
    schedule_item_id integer not null REFERENCES schedule_items (id) on delete cascade
);

 
 
 
 
