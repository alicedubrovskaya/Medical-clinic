package by.dubrovskaya.dao;

import by.dubrovskaya.exception.PersistentException;

/**
 * Transaction interface
 */
public interface Transaction {
	UserDao createUserDao();

	DoctorDao createDoctorDao();

	PatientDao createPatientDao();

	AppointmentDao createAppointmentDao();

	VacationDao createVacationDao();

	void commit() throws PersistentException;

	void rollback() throws PersistentException;

	void setWithoutAutoCommit();
}
