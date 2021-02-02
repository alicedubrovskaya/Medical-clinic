INSERT INTO `patient`
    (`id`, `name`, `surname`, `email`, `phone_number`, `address`)
VALUES (1, 'Маргарита', 'Агеенко', 'marg@gmail.com', '375291111111', 'Минск'),
       (2, 'Алина', 'Концевая', 'aline@gmail.com', '375291111111', 'Минск'),
       (8, 'Эстер','Андрейченко', 'ester@mail.ru', '375292999922', 'Минск'),
       (9, 'Ясмин', 'Рогалевич', 'yasmin@gmail.com','375292999922','Минск');

INSERT INTO `doctor`
    (`id`, `name`, `surname`, `specialization_id`, `working_shift`)
VALUES (3, 'Алиса', 'Дубровская', 1, 0),
       (4, 'Виктория', 'Монетина', 1, 1),
       (5, 'Наталья', 'Юрченко', 1, 1);

INSERT INTO `appointment`
    (`time`, `status`, `patient_id`, `doctor_id`)
VALUES ('2020-02-12 12:00:00.0', 0, 1, 3),
       ('2020-01-01 12:00:00.0', 0, 2, 3);

INSERT INTO `patient_disease`
    (patient_id, disease_id, `appointment_id`)
VALUES (1, 1, 1),
       (1, 2, 2);


INSERT INTO `vacation`
    (`doctor_id`, `start`, `end`)
VALUES (3, '2020-01-01', '2020-02-01'),
       (5, '2020-02-01', '2020-02-03');
