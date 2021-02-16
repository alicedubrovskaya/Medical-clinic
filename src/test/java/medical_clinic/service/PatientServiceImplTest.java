package medical_clinic.service;

import by.dubrovskaya.dao.impl.TransactionFactoryImpl;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.enumeration.Role;
import by.dubrovskaya.exception.PersistentException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import by.dubrovskaya.service.PatientService;
import by.dubrovskaya.service.ServiceFactory;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.impl.ServiceFactoryImpl;

import java.util.List;

public class PatientServiceImplTest {
    PatientService patientService;
    Patient patientToDelete;
    Patient patientToSave;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        patientService = serviceFactory.getPatientService();
    }

    @DataProvider(name = "input_id")
    public Object[][] createDataId() {
        return
                new Object[][]{
                        {1, new Patient.Builder()
                                .setId(1)
                                .setLogin("marg")
                                .setPassword("password")
                                .setRole(Role.PATIENT)
                                .setName("Маргарита")
                                .setSurname("Агеенко")
                                .setEmail("marg@gmail.com")
                                .setPhoneNumber("375291111111")
                                .setAddress("Минск")
                                .build()},
                        {2, new Patient.Builder()
                                .setId(2)
                                .setLogin("patient")
                                .setPassword("password")
                                .setRole(Role.PATIENT)
                                .setName("Алина")
                                .setSurname("Концевая")
                                .setEmail("aline@gmail.com")
                                .setPhoneNumber("375291111111")
                                .setAddress("Минск")
                                .build()},
                };
    }

    @BeforeTest
    public void createPatientToDelete() {
        patientToDelete = new Patient.Builder()
                .setId(8)
                .setPassword("password")
                .setLogin("patient_to_delete")
                .setRole(Role.PATIENT)
                .setEmail("ester@mail.ru")
                .setPhoneNumber("375292999922")
                .setAddress("Минск")
                .build();
    }

    @BeforeTest
    public void createPatientToSave() {
        patientToSave = new Patient.Builder()
                .setId(9)
                .setLogin("patient_to_save")
                .setPassword("password")
                .setRole(Role.PATIENT)

                .setName("Ясмин")
                .setSurname("Рогалевич")
                .setEmail("yasmin@gmail.com")
                .setPhoneNumber("375292999922")
                .setAddress("Минск")
                .build();
    }

    @Test
    public void deleteTest() throws ServicePersistentException {
        patientService.delete(patientToDelete.getId());
        List<Patient> patients = patientService.findAll();
        Assert.assertFalse(patients.contains(patientToDelete));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        patientService.save(patientToSave);
        List<Patient> patients = patientService.findAll();
        Assert.assertTrue(patients.contains(patientToSave));
    }

    @Test(dataProvider = "input_id")
    public void findByIdTest(Integer id, Patient expectedPatient) throws ServicePersistentException {
        Patient actualPatient = patientService.findById(id);
        Assert.assertEquals(actualPatient, expectedPatient);
    }

    @Test
    public void findAllTest() throws ServicePersistentException {
        List<Patient> patients = patientService.findAll();
        Assert.assertNotNull(patients);
    }

}
