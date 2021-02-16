package by.dubrovskaya.dao.extractor;

import by.dubrovskaya.domain.Appointment;
import by.dubrovskaya.domain.enumeration.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentExtractor implements Extractor<Appointment> {
    @Override
    public Appointment extract(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getInt("id"));
        appointment.setTime(resultSet.getTimestamp("time"));
        appointment.setApproved(resultSet.getBoolean("approved"));
        appointment.setStatus(Status.getById(resultSet.getInt("status")));
        appointment.setComplaints(resultSet.getString("complaints"));
        appointment.setMedicalReport(resultSet.getString("medical_report"));
        appointment.setRecommendation(resultSet.getString("recommendation"));
        return appointment;
    }
}
