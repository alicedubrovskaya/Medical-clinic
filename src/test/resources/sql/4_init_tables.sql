INSERT INTO `user`
    (login, password, role)
VALUES ('marg', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('alisa', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('doctor', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('doctor2', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('admin', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 0),
       ('user', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient_to_delete', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient_to_save', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('doctor_to_delete', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('doctor_to_save', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1);
;

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