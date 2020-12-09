CREATE DATABASE `clinic_db` DEFAULT CHARACTER SET utf8;

CREATE USER clinic_user@localhost
IDENTIFIED BY 'clinic_password';

GRANT SELECT, INSERT, UPDATE, DELETE
ON `clinic_db`.*
TO clinic_user@localhost;

# GRANT SELECT, INSERT, UPDATE, DELETE
#     ON `clinic_db`.*
#     TO clinic_user@localhost
#         IDENTIFIED BY 'clinic_password';

GRANT SELECT, INSERT, UPDATE, DELETE
    ON `clinic_db`.*
    TO clinic_user@'%'
        IDENTIFIED BY 'clinic_password';

