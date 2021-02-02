package medical_clinic.service;

import dao.database.TransactionFactoryImpl;
import domain.Patient;
import domain.User;
import exception.PersistentException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.PatientService;
import service.ServiceFactory;
import service.exception.ServicePersistentException;
import service.impl.ServiceFactoryImpl;

import java.util.List;

public class PatientServiceImplTest {
    PatientService patientService;
    Patient patientToDelete;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        patientService = serviceFactory.getPatientService();
    }

//    @Test
//    public void deleteTest() throws ServicePersistentException {
//        patientService.delete(patientToDelete.getId());
//        List<Patient> patients = patientService.findAll();
//        Assert.assertFalse(patients.contains(patientToDelete));
//    }

}
