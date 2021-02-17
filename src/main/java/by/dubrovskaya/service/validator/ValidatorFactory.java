package by.dubrovskaya.service.validator;

import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.Patient;
import by.dubrovskaya.domain.User;
import by.dubrovskaya.domain.Vacation;

public class ValidatorFactory {
    private static final ValidatorFactory INSTANCE = new ValidatorFactory();

    private ValidatorFactory() {
    }

    public static ValidatorFactory getINSTANCE() {
        return INSTANCE;
    }

    public Validator<Doctor> createDoctorValidator() {
        return new DoctorValidator();
    }

    public Validator<User> createUserValidator() {
        return new UserValidator();
    }

    public Validator<Vacation> createVacationValidator() {
        return new VacationValidator();
    }

    public Validator<Patient> createPatientValidator() {
        return new PatientValidator();
    }

    public DateValidator createDateValidator() {
        return new DateValidator();
    }

    public PaginationValidator createPaginationValidator() {
        return new PaginationValidator();
    }

    public AppointmentValidator createAppointmentValidator() {
        return new AppointmentValidator();
    }
}
