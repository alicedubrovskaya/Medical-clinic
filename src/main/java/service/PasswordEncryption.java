package service;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncryption {

    public static String encrypt(String password) {
        return DigestUtils.md5Hex(password);
    }
}
