package medical_clinic.service;

import by.dubrovskaya.dao.impl.TransactionFactoryImpl;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.domain.enumeration.Shift;
import by.dubrovskaya.exception.PersistentException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import by.dubrovskaya.service.DoctorService;
import by.dubrovskaya.service.ServiceFactory;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.impl.ServiceFactoryImpl;

import java.util.List;

public class DoctorServiceImplTest {
    DoctorService doctorService;
    Doctor doctorToDelete;
    Doctor doctorToSave;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        doctorService = serviceFactory.getDoctorService();
    }

    @BeforeTest
    public void createDoctorToDelete() {
        doctorToDelete = new Doctor.DoctorBuilder()
                .setId(10)
                .setPassword("password")
                .setLogin("doctor_to_delete")
                .setRole(Role.DOCTOR)
                .setName("Юрий")
                .setSurname("Лапицкий")
                .setSpecialization("Кардиолог")
                .setWorkingShift(Shift.FIRST)
                .build();
    }

    @BeforeTest
    public void createDoctorToSave() {
        doctorToSave = new Doctor.DoctorBuilder()
                .setId(11)
                .setPassword("password")
                .setLogin("doctor_to_save")
                .setRole(Role.DOCTOR)
                .setName("Иван")
                .setSurname("Лесниченко")
                .setSpecialization("Кардиолог")
                .setWorkingShift(Shift.FIRST)
                .build();
    }

    @DataProvider(name = "input_id")
    public Object[][] createDataId() {
        return
                new Object[][]{
                        {3, new Doctor.DoctorBuilder()
                                .setId(3)
                                .setLogin("alisa")
                                .setPassword("password")
                                .setRole(Role.DOCTOR)
                                .setName("Алиса")
                                .setSurname("Дубровская")
                                .setSpecialization("Кардиолог")
                                .setWorkingShift(Shift.FIRST)
                                .build()},
                        {4, new Doctor.DoctorBuilder()
                                .setId(4)
                                .setLogin("doctor")
                                .setPassword("password")
                                .setRole(Role.DOCTOR)
                                .setName("Виктория")
                                .setSurname("Монетина")
                                .setSpecialization("Кардиолог")
                                .setWorkingShift(Shift.SECOND)
                                .build()},
                };
    }

    @DataProvider(name = "input_surnameAndName")
    public Object[][] createDataSurnameAndName() {
        return
                new Object[][]{
                        {new String[]{"Дубровская", "Алиса"},
                                new Doctor.DoctorBuilder()
                                        .setId(3)
                                        .setLogin("alisa")
                                        .setPassword("password")
                                        .setRole(Role.DOCTOR)
                                        .setName("Алиса")
                                        .setSurname("Дубровская")
                                        .setSpecialization("Кардиолог")
                                        .setWorkingShift(Shift.FIRST)
                                        .build()},
                        {new String[]{"Монетина", "Виктория"},
                                new Doctor.DoctorBuilder()
                                        .setId(4)
                                        .setLogin("doctor")
                                        .setPassword("password")
                                        .setRole(Role.DOCTOR)
                                        .setName("Виктория")
                                        .setSurname("Монетина")
                                        .setSpecialization("Кардиолог")
                                        .setWorkingShift(Shift.SECOND)
                                        .build()},
                };
    }


    @Test
    public void deleteTest() throws ServicePersistentException {
        doctorService.delete(doctorToDelete.getId());
        List<Doctor> doctors = doctorService.findAll();
        Assert.assertFalse(doctors.contains(doctorToDelete));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        doctorService.save(doctorToSave);
        List<Doctor> doctors = doctorService.findAll();
        Assert.assertTrue(doctors.contains(doctorToSave));
    }

    @Test(dataProvider = "input_id")
    public void findByIdTest(Integer id, Doctor expectedDoctor) throws ServicePersistentException {
        Doctor actualDoctor = doctorService.findById(id);
        Assert.assertEquals(actualDoctor, expectedDoctor);
    }

    @Test(dataProvider = "input_surnameAndName")
    public void findBySurnameAndNameTest(String[] parameters, Doctor expectedDoctor) throws ServicePersistentException {
        Doctor actualDoctor = doctorService.findBySurnameAndName(parameters[0], parameters[1]);
        Assert.assertEquals(actualDoctor, expectedDoctor);
    }

    @Test
    public void findAllTest() throws ServicePersistentException {
        List<Doctor> doctors = doctorService.findAll();
        Assert.assertNotNull(doctors);
    }

    @Test
    public void findAllSpecializationsTest() throws ServicePersistentException {
        List<String> specializations = doctorService.findAllSpecializations();
        Assert.assertNotNull(specializations);
    }
}
