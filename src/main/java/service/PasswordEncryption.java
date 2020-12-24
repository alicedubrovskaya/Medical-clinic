package service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
