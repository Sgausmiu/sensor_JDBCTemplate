package test.java.ru.tech.sensorOverDAO;


import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.ru.tech.sensorOverDAO.dao.SensorDAOImpl;
import main.java.ru.tech.sensorOverDAO.dto.SensorDTO;
import main.java.ru.tech.sensorOverDAO.models.Sensor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=main.java.ru.tech.sensorOverDAO.SensorOverDaoApplication.class)
@AutoConfigureMockMvc
public class SensorControllerTests {
    @Autowired(required = true)
    private MockMvc mockMvc;
    @MockBean
    private SensorDAOImpl sensorDAO;
    @Test
    public void testAddSensor() throws Exception {
        SensorDTO sensorDTO = new SensorDTO();

        mockMvc.perform(post("/sensors/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sensorDTO)))
                .andExpect(status().isOk());

        verify(sensorDAO, times(1)).save(Mockito.any(Sensor.class));
    }
}
