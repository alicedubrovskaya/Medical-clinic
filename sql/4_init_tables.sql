INSERT INTO `user`
    (login, password, role)
VALUES ('marg', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('alisa', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('doctor', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('admin', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 0);
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