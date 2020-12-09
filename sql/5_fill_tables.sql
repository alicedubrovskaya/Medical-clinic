INSERT INTO `patient`
    (`id`, `name`, `surname`, `email`, `phone_number`, `address`)
VALUES (1, 'Денисик', 'Павел', 'denisik@gmail.com', '123', 'Минск'),
       (2, 'Агеенко', 'Маргарита', 'ageenko@gmail.com', '456', 'Ратомка');

INSERT INTO `doctor`
    (`id`, `name`, `surname`, `specialization`)
VALUES (1, 'Денисик', 'Дарья', 1),
       (2, 'Монетина', 'Виктория', 2);

INSERT INTO `medical_card`
(`id`, `chronic_diseases`, `vaccinations`)
VALUES (1, 'Аллергия на пениц ряд', 'Прививка Б'),
       (2, 'Вазомоторный ринит', 'Прививка А');