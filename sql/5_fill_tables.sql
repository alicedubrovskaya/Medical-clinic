INSERT INTO `patient`
    (`id`, `name`, `surname`, `email`, `phone_number`, `address`)
VALUES (1, 'Маргарита', 'Агеенко', 'marg@gmail.com', '375 29 111 11 11', 'Минск'),
       (2, 'Алина', 'Концевая', 'aline@gmail.com', '375 29 113 11 11', 'Минск'),
       (8, 'Ангелина', 'Осипенко', 'ang@gmail.com', '375 29 112 11 11', 'Минск'),
       (9, 'Алина', 'Осипович', 'al@gmail.com', '375 29 111 11 12', 'Минск'),
       (10, 'Андрей', 'Малинов', 'andrey@gmail.com', '375 29 111 12 11', 'Минск'),
       (11, 'Алексей', 'Зубенко', 'alex@gmail.com', '375 29 111 12 13', 'Минск'),
       (12, 'Анастасия', 'Шестеренко', 'nastasia@gmail.com', '375 29 111 12 13', 'Орша');

INSERT INTO `doctor`
    (`id`, `name`, `surname`, `specialization_id`, `working_shift`)
VALUES (3, 'Алиса', 'Дубровская', 1, 0),
       (4, 'Виктория', 'Монетина', 1, 1),
       (7, 'Геннадий', 'Борисов', 2, 1),
       (13, 'Артем', 'Короткин', 1, 0),
       (14, 'Иван', 'Шиклун', 2, 1),
       (15, 'Анатолий', 'Столь', 3, 1);

INSERT INTO `appointment`
    (`time`, `status`, `patient_id`, `doctor_id`)
VALUES ('2020-02-12 12:00:00.0', 0, 1, 3),
       ('2020-01-01 12:00:00.0', 0, 2, 3),
       ('2020-01-01 12:00:00.0', 0, 2, 3);

INSERT INTO `patient_disease`
    (patient_id, disease_id, `appointment_id`)
VALUES (2, 1, 2),
       (2, 2, 3);

INSERT INTO `vacation`
    (`doctor_id`, `start`, `end`)
VALUES (3, '2020-01-01', '2020-02-01'),
       (4, '2020-04-01', '2020-05-01');
