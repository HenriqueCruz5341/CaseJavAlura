package br.com.alura.school.enroll;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EnrollControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void should_add_new_enroll() throws Exception {
        userRepository.save(new User("alex", "alex@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and O..."));
        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("alex", "");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_return_bad_request_when_user_enroll_more_than_one_time_in_same_course() throws Exception {
        userRepository.save(new User("alex", "alex@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and O..."));

        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("alex", "");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnrollRequest)));

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_bad_request_when_username_is_invalid() throws Exception {
        userRepository.save(new User("alex", "alex@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and O..."));

        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("nomedeusuariomuitomuitogrande", "");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnrollRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_retrieve_an_enrollment_report() throws Exception {
        userRepository.save(new User("alex", "alex@email.com"));
        courseRepository.save(new Course("java-1", "Java OO", "Java and O..."));

        NewEnrollRequest newEnrollRequest = new NewEnrollRequest("alex", "");

        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newEnrollRequest)));

        mockMvc.perform(get("/courses/enroll/report"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].quatidade_matriculas").value(1))
                .andExpect(jsonPath("$[0].email").value("alex@email.com"));
    }

}