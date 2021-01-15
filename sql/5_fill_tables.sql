INSERT INTO `patient`
    (`id`, `name`, `surname`, `email`, `phone_number`, `address`)
VALUES (1, 'alisa', 'dubrovskaya', 'alicedubrovskaya@gmail.com', '123', 'Pr-t'),
       (2, 'pasha', 'denisik', 'denisik@gmail.com', '124', 'Pr-t');

INSERT INTO `doctor`
    (`id`, `name`, `surname`, `specialization_id`, `working_shift`)
VALUES (3, 'Алиса', 'Дубровская', 1, 0),
       (4, 'Виктория', 'Монетина', 1, 1);

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
VALUES (3, '2020-01-01', '2020-02-01');
#        (4, '2020-04-01', '2020-05-01');
