package service.impl;

import dao.UserDao;
import domain.User;
import exception.PersistentException;
import service.UserService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public void save(User user) throws PersistentException {
        //TODO password encoding
        UserDao userDao = transaction.createDao(UserDao.class);
        if (user.getId() == null) {
            user.setPassword(encodePBKDF2(user.getPassword()));
            user.setId(userDao.create(user));
        } else {
            if (user.getPassword() != null) {
                user.setPassword(user.getPassword()); //TODO encode
            } else {
                User existingUser = userDao.read(user.getId());
                user.setPassword(existingUser.getPassword());
            }
            userDao.update(user);
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        userDao.delete(id);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.read(login, password);
    }

    @Override
    public User findById(Integer id) throws PersistentException {
        UserDao userDao = transaction.createDao(UserDao.class);
        return userDao.read(id);
    }

    private String encodePBKDF2(String password) {
        //TODO encoding
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        byte[] hash = new byte[0];
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new String(hash);
    }
}
