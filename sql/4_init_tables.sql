INSERT INTO `user`
    (login, password, role)
VALUES ('marg', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('alisa', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('doctor', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('admin', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 0),
       ('user_delete', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('surgeon', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('patient1', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient2', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient3', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient4', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('patient5', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 2),
       ('doctor1', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('doctor2', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1),
       ('doctor3', '$2a$10$B/RmAhwRl88LK.V28XEIEuD/X1qbkC/btkzQm5QRaaWzu/SCsnDJK', 1);

INSERT INTO `specialization`
    (type)
VALUES ('Кардиолог'),
       ('Травматолог'),
       ('Хирург');

INSERT INTO `disease` (name)
VALUES ('Аллергия'),
       ('Ринит'),
       ('Артроз'),
       ('Сердечная недостаточность');

INSERT INTO `holiday` (name, day)
VALUES ('New year', '2021-01-01'),
       ('Christmas', '2021-01-07'),
       ('Womens day', '2021-03-08'),
       ('Labor day', '2021-05-01'),
       ('Victory day', '2021-05-09'),
       ('Independency day', '2021-07-03'),
       ('Christmas', '2021-12-25');