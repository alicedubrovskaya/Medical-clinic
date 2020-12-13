USE `clinic_db`;

CREATE TABLE `user`
(
    `id`       INTEGER      NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(255) NOT NULL UNIQUE,
    `password` CHAR(32)     NOT NULL,
    `role`     TINYINT CHECK (`role` IN (0, 1, 2)),
    PRIMARY KEY (`id`)
);

CREATE TABLE `patient`
(
    `id`           INTEGER             NOT NULL,
    `name`         VARCHAR(255)        NOT NULL,
    `surname`      VARCHAR(255)        NOT NULL,
    `email`        VARCHAR(255) UNIQUE NOT NULL,
    `phone_number` VARCHAR(255)        NOT NULL,
    `address`      VARCHAR(255),
    FOREIGN KEY (`id`) REFERENCES `user` (`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `specialization`
(
    `id`   INTEGER             NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `disease`
(
    `id`   INTEGER             NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `patient_disease`
(
    `patient_id` INTEGER NOT NULL,
    `disease_id` INTEGER NOT NULL,
    FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
    FOREIGN KEY (`disease_id`) REFERENCES `disease` (`id`),
    PRIMARY KEY (`patient_id`, `disease_id`)
);

CREATE TABLE `doctor`
(
    `id`                INTEGER      NOT NULL,
    `name`              VARCHAR(255) NOT NULL,
    `surname`           VARCHAR(255) NOT NULL,
    `specialization_id` INTEGER,
    `working_shift`     TINYINT CHECK (`working_shift` IN (0, 1)),

    FOREIGN KEY (`specialization_id`) REFERENCES `specialization` (id),
    FOREIGN KEY (`id`) REFERENCES `user` (id),
    PRIMARY KEY (`id`)
);

CREATE TABLE `vacation`
(
    `doctor_id` INTEGER NOT NULL,
    `start`     DATE    NOT NULL,
    `end`       DATE    NOT NULL,
    FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
    PRIMARY KEY (`doctor_id`)
);

CREATE TABLE `appointment`
(
    `id`             INTEGER NOT NULL AUTO_INCREMENT,
    `time`           DATETIME,
    `approved`       BOOLEAN DEFAULT FALSE,
    `status`         TINYINT CHECK (`status` IN (0, 1)),
    `complaints`     TEXT,
    `medical_report` TEXT,
    `recommendation` TEXT,

    `patient_id`     INTEGER,
    `doctor_id`      INTEGER NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`patient_id`) REFERENCES `patient` (id),
    FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (id)
);





