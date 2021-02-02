package medical_clinic.service;

import dao.database.TransactionFactoryImpl;
import domain.User;
import domain.Vacation;
import domain.enumeration.Role;
import exception.PersistentException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.ServiceFactory;
import service.UserService;
import service.exception.ServicePersistentException;
import service.impl.ServiceFactoryImpl;

import java.util.List;
import java.util.Map;

public class UserServiceImplTest {
    UserService userService;
    User userToDelete;
    User userToSave;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        userService = serviceFactory.getUserService();
    }

    @BeforeTest
    public void createUserToDelete() {
        userToDelete = new User("user", "password", Role.PATIENT);
        userToDelete.setId(7);
    }

    @DataProvider(name = "input_login")
    public Object[][] createDataLogin() {
        return
                new Object[][]{
                        {"marg", new User(1, "marg", "password", Role.PATIENT)},
                        {"patient", new User(2, "patient", "password", Role.PATIENT)},
                };
    }

    @DataProvider(name = "input_id")
    public Object[][] createDataId() {
        return
                new Object[][]{
                        {1, new User(1, "marg", "password", Role.PATIENT)},
                        {2, new User(2, "patient", "password", Role.PATIENT)},
                };
    }


    @DataProvider(name = "input_loginAndPassword")
    public Object[][] createDataLoginAndPassword() {
        return
                new Object[][]{
                        {new String[]{"marg", "password"}, new User(1, "marg", "password", Role.PATIENT)},
                        {new String[]{"patient", "password"}, new User(2, "patient", "password", Role.PATIENT)},
                };
    }

    @DataProvider(name = "offsetsRecords")
    public Object[] offsets() {
        return new Object[][]{
                {1, 1},
                {0, 1},
                {2, 4}
        };
    }

    @BeforeTest
    public void createUserToSave() {
        userToSave = new User("user_save", "password", Role.DOCTOR);
    }

    @Test
    public void deleteTest() throws ServicePersistentException {
        userService.delete(userToDelete.getId());
        List<User> users = userService.findAll();
        Assert.assertFalse(users.contains(userToDelete));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        userService.save(userToSave);
        userToSave.setId(8);
        List<User> users = userService.findAll();
        Assert.assertTrue(users.contains(userToSave));
    }

    @Test(dataProvider = "input_login")
    public void findByLoginTest(String login, User expectedUser) throws ServicePersistentException {
        User actualUser = userService.findByLogin(login);
        Assert.assertEquals(actualUser, expectedUser);
    }

    @Test(dataProvider = "input_id")
    public void findByIdTest(Integer id, User expectedUser) throws ServicePersistentException {
        User actualUser = userService.findById(id);
        Assert.assertEquals(actualUser, expectedUser);
    }

    @Test(dataProvider = "input_loginAndPassword")
    public void findByLoginAndPasswordTest(String[] parameters, User expectedUser) throws ServicePersistentException {
        User actualUser = userService.findByLoginAndPassword(parameters[0], parameters[1]);
        Assert.assertEquals(actualUser, expectedUser);
    }

    @Test
    public void findAllTest() throws ServicePersistentException {
        List<User> users = userService.findAll();
        Assert.assertNotNull(users);
    }

    @Test(dataProvider = "offsetsRecords")
    public void findAllOffset(Integer offset, Integer noOfRecords) throws ServicePersistentException {
        Map<Integer, List<User>> map = userService.find(offset, noOfRecords);
        Assert.assertNotNull(map);
    }

}
