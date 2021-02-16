package medical_clinic.service;

import by.dubrovskaya.dao.impl.TransactionFactoryImpl;
import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Vacation;
import by.dubrovskaya.domain.enumeration.Shift;
import by.dubrovskaya.exception.PersistentException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import by.dubrovskaya.service.ServiceFactory;
import by.dubrovskaya.service.VacationService;
import by.dubrovskaya.exception.ServicePersistentException;
import by.dubrovskaya.service.impl.ServiceFactoryImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class VacationServiceImplTest {
    VacationService vacationService;
    Vacation vacationToSave;
    Vacation vacation;

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl();
        vacationService = serviceFactory.getVacationService();
    }

    @BeforeTest
    public void createVacation() {
        vacation = new Vacation();
        vacation.setId(3);
        Calendar calendar = new GregorianCalendar(2020, 0, 1);
        Date dateStart = calendar.getTime();
        vacation.setStart(dateStart);
        calendar = new GregorianCalendar(2020, 1, 1);
        Date dateEnd = calendar.getTime();
        vacation.setEnd(dateEnd);

        Doctor doctor = new Doctor();
        doctor.setId(3);
        doctor.setName("Алиса");
        doctor.setSurname("Дубровская");
        doctor.setSpecialization("Кардиолог");
        doctor.setWorkingShift(Shift.FIRST);
        vacation.setDoctor(doctor);
    }

    @BeforeTest
    public void createVacationToSave() {
        vacationToSave = new Vacation();
        vacationToSave.setId(4);
        Calendar calendar = new GregorianCalendar(2022, 0, 1);
        Date dateStart = calendar.getTime();
        vacationToSave.setStart(dateStart);
        calendar = new GregorianCalendar(2022, 1, 1);
        Date dateEnd = calendar.getTime();
        vacationToSave.setEnd(dateEnd);

        Doctor doctor = new Doctor();
        doctor.setId(4);
        doctor.setName("Виктория");
        doctor.setSurname("Монетина");
        doctor.setSpecialization("Кардиолог");
        doctor.setWorkingShift(Shift.SECOND);
        vacationToSave.setDoctor(doctor);
    }


    @Test
    public void delete() throws ServicePersistentException {
        vacationService.delete(vacationToSave.getId());
        List<Vacation> vacations = vacationService.findAll();
        Assert.assertFalse(vacations.contains(vacationToSave));
    }


    @Test
    public void findAllTest() throws ServicePersistentException {
        List<Vacation> vacations = vacationService.findAll();
        Assert.assertNotNull(vacations);
    }

    @Test
    public void findByIdTest() throws ServicePersistentException {
        Vacation foundVacation = vacationService.findById(vacation.getId());
        Assert.assertEquals(vacation.getId(), foundVacation.getId());
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        vacationService.save(vacationToSave);
        List<Vacation> vacations = vacationService.findAll();
        Assert.assertTrue(vacations.contains(vacationToSave));
    }
}
