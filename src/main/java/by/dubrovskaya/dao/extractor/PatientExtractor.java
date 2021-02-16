package by.dubrovskaya.dao.extractor;

import by.dubrovskaya.domain.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientExtractor implements Extractor<Patient> {

    @Override
    public Patient extract(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("id"));
        patient.setName(resultSet.getString("name"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setEmail(resultSet.getString("email"));
        patient.setPhoneNumber(resultSet.getString("phone_number"));
        patient.setAddress(resultSet.getString("address"));
        return patient;
    }
}
