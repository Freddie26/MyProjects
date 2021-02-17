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
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("admin")
    public void test() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    private static final String SUCCESS_REGISTER_JSON = "{\"UserName\":\"Alice\",\"Password\":\"12345\"}";

    @Test
    public void successRegisterTest() throws Exception {
        mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).content(SUCCESS_REGISTER_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    private static final String FAIL_REGISTER_JSON = "{\"UserName\":\"admin\",\"Password\":\"1234\"}";

    @Test
    public void failRegisterTest() throws Exception {
        mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).content(FAIL_REGISTER_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}