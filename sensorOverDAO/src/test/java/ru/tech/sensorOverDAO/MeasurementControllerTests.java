package test.java.ru.tech.sensorOverDAO;


import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.ru.tech.sensorOverDAO.dao.MeasurementDAOImpl;
import main.java.ru.tech.sensorOverDAO.dto.MeasurementDTO;
import main.java.ru.tech.sensorOverDAO.models.Measurement;
import main.java.ru.tech.sensorOverDAO.models.Sensor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=main.java.ru.tech.sensorOverDAO.SensorOverDaoApplication.class)
public class MeasurementControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Arrays.stream(webApplicationContext.getBeanDefinitionNames())
                .map(name -> webApplicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
        }
    @MockBean
    private MeasurementDAOImpl measurementDAO;

    @Test
    public void testAddMeasurement() throws Exception {
        Sensor sensor = new Sensor("Sensor_66");
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setSensor(sensor);
        measurementDTO.setValue(100.0);
        measurementDTO.setRaining(true);

        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(measurementDTO)))
                .andExpect(status().isOk());

        verify(measurementDAO, times(1)).save(any(Measurement.class));
    }

    @Test
    public void testGetAllMeasurement() throws Exception {
            List<Measurement> measurements = Arrays.asList(new Measurement(1, 100.0, true, LocalDateTime.now(), new Sensor()),
                    new Measurement(2, 200.0, false, LocalDateTime.now(), new Sensor()));

            when(measurementDAO.getAll()).thenReturn(measurements);

            mockMvc.perform(get("/measurements"))
                            .andExpect(status().isOk())
                                    .andExpect(jsonPath("$[0].value").value(100.0))
                                            .andExpect(jsonPath("$[0].raining").value(true))
                                                    .andExpect(jsonPath("$[1].value").value(200.0))
                                                            .andExpect(jsonPath("$[1].raining").value(false));
    }

    @Test
    public void testGetRainyDaysCount() throws Exception {
        when(measurementDAO.getRainyDaysCount()).thenReturn(10L);

        mockMvc.perform(get("/measurements/rainyDaysCount"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }
}

