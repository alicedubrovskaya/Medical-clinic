USE `clinic_db`;

CREATE TABLE `user`
(
    `id`       INTEGER      NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(255) NOT NULL UNIQUE,
    `password` CHAR(32)     NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `medical_card`
(
    `id`               INTEGER NOT NULL AUTO_INCREMENT,
    `chronic_diseases` TEXT,
    `vaccinations`     TEXT,
    PRIMARY KEY (`id`)
);

CREATE TABLE `patient`
(
    `id`              INTEGER             NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(255)        NOT NULL,
    `surname`         VARCHAR(255)        NOT NULL,
    `email`           VARCHAR(255) UNIQUE NOT NULL,
    `phone_number`    VARCHAR(255)        NOT NULL,
    `address`         VARCHAR(255),
    `medical_card_id` INTEGER UNIQUE,
    FOREIGN KEY (`medical_card_id`) REFERENCES `medical_card` (`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `specialization`
(
    `id`   INTEGER             NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `doctor`
(
    `id`                INTEGER      NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(255) NOT NULL,
    `surname`           VARCHAR(255) NOT NULL,
    `specialization_id` INTEGER,

    FOREIGN KEY (`specialization_id`) REFERENCES `specialization` (id),
    PRIMARY KEY (`id`)
);

CREATE TABLE `timetable`
(
    `id`   INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE `appointment`
(
    `id`              INTEGER NOT NULL AUTO_INCREMENT,
    `time`            DATETIME,
    `approved`        BOOLEAN DEFAULT FALSE,
    `status`          TINYINT CHECK (`status` IN (0, 1)),
    `complaints`      TEXT,
    `medical_report`  TEXT,
    `recommendation`  TEXT,

    `medical_card_id` INTEGER,
    `doctor_id`       INTEGER,
    `timetable_id`    INTEGER,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`medical_card_id`) REFERENCES `medical_card` (id),
    FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (id),
    FOREIGN KEY (`timetable_id`) REFERENCES `timetable` (id)
);



