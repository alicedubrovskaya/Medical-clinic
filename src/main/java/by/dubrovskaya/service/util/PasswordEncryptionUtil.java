package by.dubrovskaya.service.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptionUtil {

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String existingPassword) {
        return BCrypt.checkpw(password, existingPassword);
    }
}
