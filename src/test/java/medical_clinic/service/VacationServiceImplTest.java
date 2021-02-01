package medical_clinic.service;

import dao.database.TransactionFactoryImpl;
import domain.Vacation;
import exception.PersistentException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.ServiceFactory;
import service.VacationService;
import service.exception.ServicePersistentException;
import service.impl.ServiceFactoryImpl;

import java.util.List;

public class VacationServiceImplTest {
    VacationService vacationService;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        vacationService = serviceFactory.getVacationService();
    }

    @Test
    public void findAll() throws ServicePersistentException {
        List<Vacation> vacations = vacationService.findAll();
        Assert.assertNotNull(vacations);
    }
}
