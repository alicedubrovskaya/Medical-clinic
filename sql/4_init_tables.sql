INSERT INTO `user`
    (login, password, role)
VALUES ('marg', '5F4DCC3B5AA765D61D8327DEB882CF99', 2),
       ('patient', '5F4DCC3B5AA765D61D8327DEB882CF99', 2),
       ('alisa', '5F4DCC3B5AA765D61D8327DEB882CF99', 1),
       ('doctor', '5F4DCC3B5AA765D61D8327DEB882CF99', 1),
       ('admin', '5F4DCC3B5AA765D61D8327DEB882CF99', 0);
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