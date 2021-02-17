package org.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PresentationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void successGetAllTest() throws Exception {
        mockMvc.perform(get("/presentations/all"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    public static final String ADD_PRESENTATION_JSON = "{\"Title\":\"Исследование наночастиц\",\"StartDateTime\":\"2021-02-13T10:00:00.000\",\"EndDateTime\":\"2021-02-13T11:00:00.000\",\"RoomID\":\"1\"}";

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("presenter")
    public void successAddPresentationTest() throws Exception {
        mockMvc.perform(post("/presentations").contentType(MediaType.APPLICATION_JSON).content(ADD_PRESENTATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}