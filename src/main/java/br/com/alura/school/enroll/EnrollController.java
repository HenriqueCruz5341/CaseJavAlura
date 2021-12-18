package br.com.alura.school.enroll;

import static java.lang.String.format;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.support.dto.EnrollmentReportDto;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

@RestController
class EnrollController {

    private final EnrollRepository enrollRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    EnrollController(EnrollRepository enrollRepository, CourseRepository courseRepository,
            UserRepository userRepository) {
        this.enrollRepository = enrollRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/courses/enroll/report")
    ResponseEntity<List<EnrollResponse>> enrollmentReport() {
        List<EnrollmentReportDto> enrollmentReportDto = enrollRepository.getReport();
        if (enrollmentReportDto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No enrollments found");

        return ResponseEntity.ok(EnrollResponse.convertFromDto(enrollmentReportDto));
    }

    @PostMapping("/courses/{courseCode}/enroll")
    ResponseEntity<Void> newEnroll(@RequestBody @Valid NewEnrollRequest newEnrollRequest,
            @PathVariable("courseCode") String courseCode) {
        String username = newEnrollRequest.getUsername();

        List<Enroll> enrollsUserInCourse = enrollRepository.findByUserUsernameAndCourseCode(username, courseCode);

        if (!enrollsUserInCourse.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    format("User %s already enrolled in course %s", username, courseCode));

        Course course = courseRepository.findByCode(courseCode).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        format("Course with code %s not found", courseCode)));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        format("User with username %s not found", username)));

        Enroll enroll = new Enroll(user, course);
        course.getEnrolls().add(enroll);
        user.getEnrolls().add(enroll);

        enrollRepository.save(enroll);
        courseRepository.save(course);
        userRepository.save(user);

        URI location = URI.create(format("/courses/%s/enroll/%s", courseCode, username));
        return ResponseEntity.created(location).build();
    }

}
