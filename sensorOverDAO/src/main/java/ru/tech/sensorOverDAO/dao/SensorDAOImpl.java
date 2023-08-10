package ru.tech.sensorOverDAO.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tech.sensorOverDAO.models.Sensor;

import java.util.List;
import java.util.Optional;

@Component
public class SensorDAOImpl implements SensorDAO{

    private final JdbcTemplate jdbcTemplate;

    public SensorDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sensor save(Sensor sensor) {
        jdbcTemplate.update("INSERT INTO sensor\n" +
                "(\"name\")\n" +
                "VALUES(?);", sensor.getName());
        return sensor;
    }

    @Override
    public List<Sensor> getAll() {
        return jdbcTemplate.query("SELECT * FROM Sensor",
                (resultSet, rowNum) ->
                        new Sensor(resultSet.getString("name")));
    }

    @Override
    public Optional<Sensor> searchByName(String name) {
        return jdbcTemplate.query("SELECT * FROM Sensor WHERE name = ?", new Object[]{name},
                new BeanPropertyRowMapper<>(Sensor.class)).stream().findAny();
                //(resultSet, rowNum) ->
                //Optional.of(
                //        new Sensor(resultSet.getString("name")) //при данном логике не видим вновь создаваемый name
    }
}
