package validator;

import domain.*;

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

    public DateValidator createDateValidator(){
        return new DateValidator();
    }
}
