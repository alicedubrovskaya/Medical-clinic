INSERT INTO `user`
    (login, password, role)
VALUES ('alisa', 'pass', 2),
       ('pasha', 'pass', 2),
       ('alisia', 'pass', 1),
       ('victoria', 'pass', 1);
INSERT INTO `specialization`
    (type)
VALUES ('Кардиолог');

INSERT INTO `disease` (name)
VALUES ('аллергия'),
       ('ринит'),
       ('артроз');

INSERT INTO `holiday` (name, day)
VALUES ('New year', '2020-12-31'),
       ('New year', '2021-01-1');