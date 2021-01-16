package validator;

import domain.Doctor;
import domain.User;

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

	public Validator<User> createUserValidator(){
		return new UserValidator();
	}
}
