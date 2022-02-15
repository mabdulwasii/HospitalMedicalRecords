CREATE DATABASE IF NOT EXISTS hmr;

DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS authority;

create TABLE IF NOT EXISTS patient
(
    id INT NOT NULL,
    first_name  VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    last_visit_date TIMESTAMP NOT NULL,
    PRIMARY KEY ( id )
);

create TABLE IF NOT EXISTS user
(
    id        INT AUTO_INCREMENT,
    username  VARCHAR(200) UNIQUE NOT NULL,
    password  VARCHAR(256)        NOT NULL,
    activated BOOL,
    authority VARCHAR(50),
    first_name  VARCHAR(50)        NOT NULL,
    last_name  VARCHAR(50)        NOT NULL,
    uuid  VARCHAR(50)         NOT NULL,
    registration_date DATE NOT NULL,
    PRIMARY KEY (id)
) ;

CREATE TABLE IF NOT EXISTS authority
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_authority
(
    id           INT PRIMARY KEY AUTO_INCREMENT COMMENT 'user authority id',
    user_id      BIGINT COMMENT 'User id',
    authority_id VARCHAR(50) COMMENT 'authority id'
);

INSERT INTO user (id, username, password, activated, first_name, last_name, uuid, registration_date)
VALUES (1, 'admin@example.com', '$2a$04$Ot6tX0QK8xzo/xW5A/J3F.QZDS7eio095dN5IoQjWJDOySs42f1S.', true, 'Sola', 'Greg',
        '586bcc2d-9a96-11e6-852c-4439c456d444', '2020-07-04'),
       (2, 'user@example.com', '$2a$04$Ot6tX0QK8xzo/xW5A/J3F.QZDS7eio095dN5IoQjWJDOySs42f1S.', true, 'Dempis',
        'Manga', '841ae775-9a96-11e6-852c-4439c456d444 ', '2021-07-24' );

INSERT INTO authority (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 1),
       (2, 2);

insert into Patient (id, first_name, last_name, age, last_visit_date) values (1, 'Melania', 'Ravillas', 36, '2021-07-24 04:34:52');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (2, 'Willis', 'Lockier', 46, '2022-02-04 13:51:45');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (3, 'Nicky', 'Ashborn', 80, '2021-10-19 17:17:07');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (4, 'Ezra', 'Botting', 80, '2021-09-05 06:08:13');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (5, 'Corilla', 'Quarrie', 6, '2021-09-17 11:56:29');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (6, 'Evangeline', 'Arnke', 32, '2021-05-24 21:22:50');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (7, 'Brenn', 'Raymond', 12, '2021-05-26 01:04:30');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (8, 'Hewie', 'Lightwood', 48, '2021-09-17 04:24:44');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (9, 'Desiree', 'Voak', 32, '2021-10-28 15:46:46');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (10, 'Stefa', 'FitzAlan', 87, '2021-11-19 08:55:24');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (11, 'Prinz', 'Hazzard', 100, '2021-10-13 09:16:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (12, 'Killian', 'Hiscoke', 7, '2021-11-07 00:28:38');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (13, 'Joletta', 'Amberger', 59, '2021-11-17 12:09:26');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (14, 'Kalle', 'Burchill', 21, '2022-01-05 01:07:14');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (15, 'Jilleen', 'Pau', 50, '2021-03-20 14:47:31');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (16, 'Solomon', 'Butteris', 12, '2021-05-22 00:14:55');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (17, 'Town', 'Warbrick', 43, '2022-02-02 17:50:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (18, 'Joy', 'Simonsson', 67, '2021-04-17 00:38:38');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (19, 'Sutherlan', 'Satterlee', 11, '2021-07-02 10:23:58');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (20, 'Carline', 'Brandacci', 62, '2021-09-28 18:40:16');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (21, 'Erina', 'Backshall', 64, '2021-11-29 05:59:57');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (22, 'Huey', 'Moynihan', 78, '2021-03-22 14:41:32');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (23, 'Forrest', 'Jelf', 33, '2021-11-30 05:31:24');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (24, 'Neile', 'Gwynne', 60, '2021-12-21 13:11:50');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (25, 'Luther', 'Belone', 13, '2021-11-01 06:33:33');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (26, 'Rupert', 'Gladdolph', 58, '2021-07-02 03:51:33');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (27, 'Tamqrah', 'Guynemer', 90, '2021-09-29 20:16:45');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (28, 'Abbie', 'Sheals', 55, '2021-06-05 20:25:32');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (29, 'Jacqueline', 'Brockherst', 20, '2022-01-03 11:33:23');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (30, 'Fifine', 'Aseef', 71, '2021-03-20 14:20:28');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (31, 'Elsinore', 'Alecock', 55, '2021-02-18 06:24:56');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (32, 'Raquel', 'Dinkin', 77, '2021-05-18 23:41:58');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (33, 'Pavia', 'Mellanby', 56, '2021-12-06 07:47:35');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (34, 'Tomlin', 'Lanham', 46, '2021-05-03 19:48:46');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (35, 'Jeremias', 'Conroy', 24, '2021-09-06 17:25:30');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (36, 'Sophi', 'McGurn', 90, '2021-05-05 19:35:44');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (37, 'Mohandas', 'McFadyen', 55, '2022-01-21 15:10:10');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (38, 'Berny', 'Pridie', 67, '2022-01-06 09:57:21');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (39, 'Lutero', 'Shatliff', 12, '2021-12-10 16:02:14');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (40, 'Annelise', 'Reightley', 13, '2021-04-02 07:47:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (41, 'Dalston', 'Stark', 52, '2021-03-09 19:40:36');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (42, 'Xenia', 'Shave', 32, '2021-07-17 21:24:28');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (43, 'Steve', 'Hunnybun', 38, '2021-07-17 12:37:09');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (44, 'Lorraine', 'Mantle', 9, '2021-09-03 21:10:57');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (45, 'Giraldo', 'Ashbee', 3, '2021-11-06 22:26:01');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (46, 'Bennie', 'Tregensoe', 100, '2021-02-26 19:02:29');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (47, 'Obie', 'De Blasio', 85, '2021-06-08 23:53:20');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (48, 'Carmel', 'Janoschek', 80, '2021-12-05 00:18:01');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (49, 'Case', 'Wraight', 72, '2021-05-12 13:49:50');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (50, 'Alma', 'Mcsarry', 26, '2021-10-23 18:42:25');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (51, 'Winfield', 'Doohey', 100, '2021-11-15 07:16:34');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (52, 'Almira', 'Sprankling', 31, '2022-01-13 17:38:14');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (53, 'Brenden', 'Barney', 53, '2021-03-30 20:43:08');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (54, 'Jacky', 'Drieu', 73, '2022-02-03 15:50:08');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (55, 'Westbrooke', 'Bigrigg', 3, '2021-06-17 09:12:07');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (56, 'Ky', 'Varndall', 77, '2021-12-19 00:07:33');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (57, 'Napoleon', 'Furtado', 26, '2021-04-12 20:05:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (58, 'Sid', 'Keepence', 10, '2021-08-19 06:00:41');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (59, 'Jaquenetta', 'Grassi', 45, '2022-01-03 02:20:16');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (60, 'Siward', 'Babalola', 90, '2021-03-20 00:42:56');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (61, 'Daisy', 'Stanley', 57, '2021-03-08 12:31:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (62, 'Ketti', 'Dossettor', 12, '2021-05-26 00:39:41');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (63, 'Robena', 'Simister', 9, '2021-02-19 00:40:31');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (64, 'Daisey', 'Spalding', 15, '2021-04-27 19:37:30');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (65, 'Anatol', 'Wakeling', 37, '2022-02-06 03:47:40');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (66, 'Ora', 'Ailsbury', 78, '2021-05-05 14:17:53');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (67, 'Lucille', 'Hadley', 70, '2021-10-16 20:24:08');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (68, 'Rikki', 'Tuhy', 98, '2021-07-29 21:28:43');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (69, 'Amelie', 'South', 64, '2021-07-27 19:47:09');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (70, 'Noellyn', 'Antognoni', 76, '2021-07-27 07:58:24');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (71, 'Myrah', 'Baversor', 5, '2021-09-22 14:22:11');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (72, 'Orton', 'Tschursch', 23, '2021-12-27 11:25:50');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (73, 'Jodie', 'Lampet', 22, '2021-05-08 20:09:11');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (74, 'Violette', 'Garrett', 92, '2021-07-25 16:20:52');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (75, 'Charmian', 'Seawright', 55, '2021-06-02 23:47:42');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (76, 'Aubrette', 'Barrack', 8, '2021-08-23 10:05:13');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (77, 'Tedda', 'Coton', 72, '2021-10-30 16:20:35');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (78, 'Elfrida', 'Shelf', 40, '2021-10-29 07:24:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (79, 'Bette-ann', 'Blague', 29, '2021-05-30 21:14:23');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (80, 'Allyce', 'Balasin', 80, '2021-08-30 18:03:21');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (81, 'Rica', 'Othen', 63, '2021-02-18 05:17:41');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (82, 'Gal', 'MacGeaney', 61, '2022-01-25 08:09:36');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (83, 'Babb', 'Seavers', 36, '2021-03-23 01:00:30');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (84, 'Karleen', 'Maddigan', 83, '2021-06-11 03:38:32');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (85, 'Jewell', 'Tapin', 42, '2021-06-01 14:28:07');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (86, 'Ruprecht', 'Fears', 1, '2021-03-29 07:31:09');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (87, 'Dominica', 'Barnes', 100, '2021-08-17 04:24:48');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (88, 'Jameson', 'Brim', 66, '2021-10-24 21:46:20');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (89, 'Mela', 'Sculley', 66, '2021-03-08 04:07:50');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (90, 'Flss', 'Booley', 16, '2021-05-29 11:01:09');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (91, 'Lolly', 'Dripps', 66, '2021-08-23 18:15:38');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (92, 'Gustaf', 'Donizeau', 40, '2021-06-15 05:57:04');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (93, 'Teresina', 'Oake', 62, '2021-11-23 01:19:58');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (94, 'Perry', 'Studdert', 84, '2021-06-26 17:40:33');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (95, 'Sharity', 'Cussons', 3, '2021-03-19 09:43:01');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (96, 'Jillene', 'Trusse', 69, '2021-04-18 08:06:27');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (97, 'Ronni', 'Friary', 63, '2022-02-05 18:31:00');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (98, 'Bar', 'Howick', 63, '2021-04-02 10:48:38');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (99, 'Warden', 'Fuxman', 71, '2021-07-30 05:50:49');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (100, 'Augustina', 'Mollatt', 91, '2021-10-08 17:57:41');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (101, 'Maxy', 'De Lasci', 74, '2021-04-22 02:10:49');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (102, 'Glori', 'Kilgrew', 4, '2022-02-13 03:51:10');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (103, 'Liv', 'Emmines', 13, '2021-08-19 20:04:00');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (104, 'Devlin', 'Phillipps', 18, '2021-09-07 14:16:30');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (105, 'Derrik', 'Suddards', 79, '2021-03-02 04:06:02');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (106, 'Giselbert', 'Botha', 64, '2021-10-31 11:24:24');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (107, 'Esme', 'Brunnen', 5, '2021-04-05 20:14:40');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (108, 'Timmie', 'Puddephatt', 97, '2022-02-02 04:30:58');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (109, 'Christina', 'Feasby', 10, '2021-06-24 20:21:53');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (110, 'Cherise', 'Duchenne', 9, '2022-02-08 17:55:49');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (111, 'Humfrey', 'Bonner', 4, '2021-10-28 01:14:10');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (112, 'Dusty', 'Attock', 53, '2021-04-07 08:02:31');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (113, 'Elga', 'Hutchings', 65, '2021-12-09 15:38:02');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (114, 'Hastings', 'Enevold', 63, '2021-08-16 12:54:31');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (115, 'Eustacia', 'MacMenamie', 24, '2021-11-11 21:32:01');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (116, 'Kippar', 'Chanter', 1, '2021-06-18 03:36:34');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (117, 'Natalina', 'Paver', 89, '2021-04-04 20:53:43');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (118, 'Maren', 'Roddam', 24, '2021-08-12 00:50:03');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (119, 'Wilie', 'Trevascus', 14, '2021-10-01 13:28:07');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (120, 'Andre', 'Fleeman', 73, '2021-12-19 00:12:35');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (121, 'Maible', 'Litster', 73, '2021-02-24 19:09:13');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (122, 'Kahaleel', 'Bodham', 59, '2021-04-07 21:14:22');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (123, 'Tammy', 'Vest', 34, '2021-04-04 18:51:11');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (124, 'Francyne', 'Banks', 41, '2021-03-07 13:26:01');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (125, 'Giacobo', 'Hubbuck', 33, '2021-06-09 13:13:03');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (126, 'Odetta', 'Twydell', 13, '2021-09-15 00:23:12');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (127, 'Gerrie', 'Milksop', 16, '2021-11-16 11:42:31');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (128, 'Godfrey', 'Storres', 1, '2022-02-05 00:30:20');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (129, 'Laetitia', 'Blewitt', 7, '2021-07-14 03:42:51');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (130, 'Michaela', 'Timothy', 74, '2021-04-27 06:18:59');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (131, 'Correna', 'Todman', 42, '2021-12-24 23:18:59');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (132, 'Ignacio', 'Cuttell', 92, '2021-04-12 22:09:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (133, 'Belvia', 'Applewhaite', 27, '2021-09-16 17:03:56');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (134, 'Giuditta', 'Conn', 94, '2021-10-19 13:47:22');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (135, 'Connie', 'Fantonetti', 13, '2022-01-26 11:28:41');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (136, 'Berk', 'Warder', 22, '2021-12-15 13:48:41');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (137, 'Blair', 'Zavattiero', 87, '2021-03-01 08:41:56');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (138, 'Harriette', 'McNamee', 73, '2021-06-26 01:05:27');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (139, 'Noe', 'Shilstone', 90, '2021-12-22 17:54:50');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (140, 'Magdalena', 'Boddie', 96, '2021-10-04 09:11:34');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (141, 'Pepe', 'Oldall', 65, '2021-05-03 15:28:25');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (142, 'Justino', 'Marcone', 80, '2021-04-17 04:22:10');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (143, 'Thomasa', 'Swalteridge', 82, '2021-10-05 20:04:04');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (144, 'Pen', 'Dy', 44, '2021-05-27 11:02:09');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (145, 'Sherill', 'Mourgue', 5, '2021-12-20 10:33:32');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (146, 'Jolyn', 'Pott', 53, '2021-05-03 10:47:31');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (147, 'Bernetta', 'Engall', 21, '2021-05-27 18:38:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (148, 'Helena', 'Balasin', 13, '2022-01-09 08:46:25');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (149, 'Zola', 'Wynter', 83, '2021-11-26 15:24:27');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (150, 'Gard', 'Coie', 35, '2021-03-22 19:32:44');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (151, 'Ware', 'Friedenbach', 83, '2021-10-31 02:43:14');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (152, 'Corilla', 'Wegner', 41, '2021-03-26 17:07:44');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (153, 'Bucky', 'Quilleash', 73, '2021-12-09 18:45:48');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (154, 'Fae', 'McPike', 86, '2021-05-25 21:39:33');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (155, 'La verne', 'Llewellyn', 68, '2021-09-27 13:18:49');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (156, 'Merrie', 'Vispo', 4, '2022-02-04 01:11:27');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (157, 'Malia', 'Roxbrough', 53, '2021-09-06 00:30:19');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (158, 'Cinderella', 'Meneer', 30, '2021-04-08 17:14:57');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (159, 'Abbi', 'Summerell', 81, '2021-12-03 00:17:58');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (160, 'Jimmie', 'Doolan', 61, '2021-09-28 06:17:29');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (161, 'Herold', 'Braithwait', 60, '2021-11-24 12:58:08');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (162, 'Vito', 'Banfield', 6, '2021-09-04 22:05:53');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (163, 'Nonie', 'Rain', 45, '2021-07-14 23:13:06');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (164, 'Estele', 'Orrett', 62, '2021-09-20 04:55:04');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (165, 'Rennie', 'Benardet', 6, '2021-11-10 17:13:52');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (166, 'Ingrim', 'Tebbet', 14, '2021-07-25 01:28:22');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (167, 'Harcourt', 'Mulryan', 13, '2021-11-23 03:15:34');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (168, 'Filmer', 'Lamyman', 93, '2021-07-19 23:50:37');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (169, 'Kip', 'Flannery', 38, '2021-07-07 13:37:37');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (170, 'Michelle', 'MacCartney', 87, '2021-04-26 23:52:37');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (171, 'Ramon', 'Perillo', 9, '2021-03-18 06:41:46');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (172, 'Verney', 'Block', 2, '2022-02-10 15:38:59');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (173, 'Jordon', 'Claiton', 36, '2021-11-28 12:38:57');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (174, 'Ansell', 'Yakunin', 40, '2021-12-02 16:46:15');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (175, 'Abby', 'Keegan', 52, '2022-01-13 06:04:05');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (176, 'Addy', 'Follacaro', 42, '2021-10-01 02:20:26');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (177, 'Tanny', 'Coopland', 50, '2021-03-17 18:35:57');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (178, 'Donny', 'Stegers', 11, '2021-09-06 01:56:10');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (179, 'Armin', 'Terbeck', 99, '2021-07-29 23:16:11');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (180, 'Stanley', 'McGoon', 44, '2022-01-07 11:41:43');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (181, 'Philis', 'McKew', 79, '2021-08-31 22:09:12');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (182, 'Dahlia', 'Francke', 97, '2021-08-04 17:09:59');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (183, 'Carline', 'Foddy', 27, '2021-12-15 18:20:01');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (184, 'Gabriello', 'Hebdon', 98, '2021-11-19 23:06:44');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (185, 'Jacky', 'Inold', 72, '2021-10-28 21:35:09');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (186, 'Aristotle', 'Siney', 47, '2022-01-11 23:10:31');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (187, 'Angeli', 'Elphick', 84, '2021-10-17 19:46:25');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (188, 'Novelia', 'Millership', 59, '2021-07-07 07:38:13');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (189, 'Georges', 'Pozer', 80, '2021-05-04 07:58:41');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (190, 'Mia', 'Epilet', 51, '2021-08-08 09:51:43');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (191, 'Leshia', 'Fenners', 39, '2021-11-16 09:37:38');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (192, 'Hastie', 'Armes', 24, '2021-12-19 20:40:43');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (193, 'Lanie', 'Perrelle', 2, '2021-07-02 16:40:38');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (194, 'Deni', 'Jonke', 59, '2021-11-26 14:08:08');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (195, 'Kayla', 'Vearncombe', 30, '2021-10-26 12:44:25');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (196, 'Grantham', 'Bodiam', 79, '2021-05-11 00:35:26');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (197, 'Tome', 'Pavey', 42, '2021-05-13 20:47:59');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (198, 'Nancey', 'Shovlin', 31, '2021-11-14 00:38:20');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (199, 'Gunner', 'Pinson', 38, '2021-05-07 21:59:36');
insert into Patient (id, first_name, last_name, age, last_visit_date) values (200, 'Rebbecca', 'Siggens', 41, '2021-06-06 01:37:21');
