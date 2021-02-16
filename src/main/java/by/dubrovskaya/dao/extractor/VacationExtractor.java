package by.dubrovskaya.dao.extractor;

import by.dubrovskaya.domain.Vacation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationExtractor implements Extractor<Vacation> {
    @Override
    public Vacation extract(ResultSet resultSet) throws SQLException {
        Vacation vacation = new Vacation();
        vacation.setId(resultSet.getInt("doctor_id"));
        vacation.setStart(resultSet.getDate("start"));
        vacation.setEnd(resultSet.getDate("end"));
        return vacation;
    }
}
