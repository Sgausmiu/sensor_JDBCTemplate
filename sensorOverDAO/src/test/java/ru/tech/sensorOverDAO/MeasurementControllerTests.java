package test.java.ru.tech.sensorOverDAO;


import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.ru.tech.sensorOverDAO.dao.MeasurementDAOImpl;
import main.java.ru.tech.sensorOverDAO.dto.MeasurementDTO;
import main.java.ru.tech.sensorOverDAO.models.Measurement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


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
@SpringBootTest(classes = main.java.ru.tech.sensorOverDAO.SensorOverDaoApplication.class)
@ContextConfiguration(classes=main.java.ru.tech.sensorOverDAO.SensorOverDaoApplication.class)
public class MeasurementControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementDAOImpl measurementDAO;

    @Test
    public void testAddMeasurement() throws Exception {
        MeasurementDTO measurementDTO = new MeasurementDTO();
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
            List<Measurement> measurements = Arrays.asList(new Measurement(),
                    new Measurement());

            when(measurementDAO.getAll()).thenReturn(measurements);

            mockMvc.perform(get("/measurements"))
                    .andExpect(status().isOk())
                    .andExpect((ResultMatcher) jsonPath("$[0].value", is(100)))
                    .andExpect((ResultMatcher) jsonPath("$[0].raining", is(true)))
                    .andExpect((ResultMatcher) jsonPath("$[1].value", is(200)))
                    .andExpect((ResultMatcher) jsonPath("$[1].raining", is(false)));

        }

    @Test
    public void testGetRainyDaysCount() throws Exception {
        when(measurementDAO.getRainyDaysCount()).thenReturn(10L);

        mockMvc.perform(get("/measurements/rainyDaysCount"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("10"));
    }

}

