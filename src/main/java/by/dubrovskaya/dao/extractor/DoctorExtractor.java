package by.dubrovskaya.dao.extractor;

import by.dubrovskaya.domain.Doctor;
import by.dubrovskaya.domain.enumeration.Shift;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorExtractor implements Extractor<Doctor> {

    @Override
    public Doctor extract(ResultSet resultSet) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getInt("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSurname(resultSet.getString("surname"));
        doctor.setWorkingShift(Shift.getById(resultSet.getInt("working_shift")));
        return doctor;
    }
}
