package ru.job4j.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Job4jAuthApplication;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jAuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void whenFindAll() throws Exception {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            Person person = Person.of("login" + i, "password" + i);
            person.setId(i);
            persons.add(person);
        }
        when(personRepository.findAll()).thenReturn(persons);
        String rsl = this.mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expected = '['
                + "{\"id\":0,\"login\":\"login0\",\"password\":\"password0\"},"
                + "{\"id\":1,\"login\":\"login1\",\"password\":\"password1\"},"
                + "{\"id\":2,\"login\":\"login2\",\"password\":\"password2\"}"
                + ']';
        assertEquals(rsl, expected);
    }

    @Test
    public void whenFindById() throws Exception {
        when(personRepository.findById(0))
                .thenReturn(Optional.of(Person.of("findLogin", "findPassword")));
        String rsl = this.mockMvc.perform(get("/person/0"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(rsl, "{\"id\":0,\"login\":\"findLogin\",\"password\":\"findPassword\"}");
    }

    @Test
    public void whenCreate() throws Exception {
        when(personRepository.save(any(Person.class)))
                .thenReturn(Person.of("saveLogin", "savePassword"));
        final String json = "{\n"
                + "  \"id\": 0,\n"
                + "  \"login\": \"saveLogin\",\n"
                + "  \"password\": \"savePassword\"\n"
                + "}";
        String rsl = this.mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertEquals(rsl, "{\"id\":0,\"login\":\"saveLogin\",\"password\":\"savePassword\"}");
    }

    @Test
    public void whenUpdate() throws Exception {
        final String json = "{\n"
                + "  \"id\": 1,\n"
                + "  \"login\": \"login\",\n"
                + "  \"password\": \"password\"\n"
                + "}";
        this.mockMvc.perform(put("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete() throws Exception {
        this.mockMvc.perform(delete("/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
