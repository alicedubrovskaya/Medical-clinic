package by.dubrovskaya.service;

public interface ServiceFactory {
    UserService getUserService();

    PatientService getPatientService();

    DoctorService getDoctorService();

    AppointmentService getAppointmentService();

    VacationService getVacationService();

    void close();
}
