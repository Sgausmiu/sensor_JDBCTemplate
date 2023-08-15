package test.java.ru.tech.sensorOverDAO;


import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.ru.tech.sensorOverDAO.controllers.SensorController;
import main.java.ru.tech.sensorOverDAO.dao.SensorDAOImpl;
import main.java.ru.tech.sensorOverDAO.dto.SensorDTO;
import main.java.ru.tech.sensorOverDAO.models.Sensor;
import main.java.ru.tech.sensorOverDAO.utils.SensorValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = main.java.ru.tech.sensorOverDAO.SensorOverDaoApplication.class)
@WebMvcTest(SensorController.class)
public class SensorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorDAOImpl sensorDAO;

    @MockBean
    private SensorValidator sensorValidator;

    @Test
    public void testAddSensor() throws Exception {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("Sensor 1");

        mockMvc.perform(post("/sensors/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sensorDTO)))
                .andExpect(status().isOk());

        verify(sensorDAO, times(1)).save(Sensor.class.newInstance());
    }
}
