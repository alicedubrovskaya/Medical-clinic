package validator;

import domain.Doctor;

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
}
