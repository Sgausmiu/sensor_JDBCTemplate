package main.java.ru.tech.sensorOverDAO.dao;

import main.java.ru.tech.sensorOverDAO.models.Measurement;
import main.java.ru.tech.sensorOverDAO.models.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MeasurementDAOImpl implements MeasurementDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeasurementDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Measurement save(Measurement measurement){
        jdbcTemplate.update("INSERT INTO measurement(value, raining, sensor, local_resolution_time) VALUES(?, ?, ?, ?)", measurement.getValue(), measurement.isRaining()
                , measurement.getSensor().getName(), measurement.getLocalResolutionTime());
        return measurement;
    }

    @Override
    public List<Measurement> getAll() {
        return  jdbcTemplate.query("SELECT * FROM measurement", (resultSet, rowNum) ->
                convertToMeasurement(resultSet));   }

    @Override
    public Long getRainyDaysCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM measurement WHERE raining = TRUE", Long.class);
    }


    private Measurement convertToMeasurement(ResultSet resultSet) throws SQLException {

        Measurement measurement = new Measurement();
        measurement.setId(resultSet.getInt("id"));
        measurement.setValue(resultSet.getDouble("value"));
        measurement.setRaining(resultSet.getBoolean("raining"));
        measurement.setLocalResolutionTime(resultSet.getTimestamp("local_resolution_time").toLocalDateTime());
        // Fetch the Sensor entity using nested query
        Sensor sensor = getSensorById(resultSet.getInt("id"));
        measurement.setSensor(sensor);

        return measurement;
    }


    private Sensor getSensorById(Integer id){
        return jdbcTemplate.queryForObject("SELECT * from measurement WHERE id=?", new Object[]{id}, (resultSet, rowNum) -> {
            Sensor sensor = new Sensor();
            sensor.setId(resultSet.getInt("id"));

            return sensor;
        });
    }
}
