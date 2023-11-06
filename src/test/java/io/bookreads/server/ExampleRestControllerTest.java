package io.bookreads.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExampleRestControllerTest {

    @Autowired
    private MockMvc sut;

    @Autowired
    private ExampleRepository exampleRepository;

    @BeforeEach
    public void setup() {
        exampleRepository.deleteAll();
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setName("Test Name");
        exampleRepository.save(exampleEntity);
    }

    @Test
    void givenExampleEntity_whenGetExample_thenStatus200() throws Exception {
        sut.perform(get("/examples/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"));
    }

    @Test
    void whenPostExample_thenStatus201() throws Exception {
        sut.perform(post("/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Example\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("examples/")));
    }
}