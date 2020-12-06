USE `clinic_db`;

/*Doctor's specialization?*/

/* One to one: patient - medical card?*/

CREATE TABLE `user`
(
    `id`          INTEGER             NOT NULL AUTO_INCREMENT,
    `login`       VARCHAR(255)        NOT NULL UNIQUE,
    `password`    CHAR(32)            NOT NULL,
    `name`        VARCHAR(255)        NOT NULL,
    `surname`     VARCHAR(255)        NOT NULL,
    `email`       varchar(255) UNIQUE NOT NULL,
    `phoneNumber` INTEGER,
    `address`     varchar(255),
    `role`        TINYINT             NOT NULL CHECK (`role` IN (0, 1, 2)),

    /*
     * 0 - administrator (Role.ADMINISTRATOR)
     * 1 - patient (Role.PATIENT)
     * 2 - doctor (Role.DOCTOR)
     */
    PRIMARY KEY (`id`)
);

CREATE TABLE `medical_card`
(
    `id`               INTEGER NOT NULL AUTO_INCREMENT,
    `chronic_diseases` TEXT,
    `vaccinations`     TEXT,

    PRIMARY KEY (`id`)
);

CREATE TABLE `timetable`
(
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
);

CREATE TABLE `appointment`
(
    `id`              INTEGER      NOT NULL AUTO_INCREMENT,
    `time`            DATETIME,
    /*
     was or not
     */
    `status`          VARCHAR(255) NOT NULL,
    `complaints`      TEXT         NOT NULL,
    `medical_report`  TEXT         NOT NULL,
    `recommendation`  TEXT         NOT NULL,

    `medical_card_id` INTEGER,
    `doctor_id`       INTEGER,
    `timetable_id`    INTEGER,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`medical_card_id`) REFERENCES `medical_card` (id),
    FOREIGN KEY (`doctor_id`) REFERENCES `user` (id),
    FOREIGN KEY (`timetable_id`) REFERENCES `timetable` (id)
);



