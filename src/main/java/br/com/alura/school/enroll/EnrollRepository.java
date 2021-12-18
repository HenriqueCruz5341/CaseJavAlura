package br.com.alura.school.enroll;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.school.support.dto.EnrollmentReportDto;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    List<Enroll> findByUserUsernameAndCourseCode(String username, String courseCode);

    @Query("SELECT new br.com.alura.school.support.dto.EnrollmentReportDto(COUNT(c.code), u.email) FROM enroll e JOIN e.user u JOIN e.course c GROUP BY u.email ORDER BY COUNT(c.code) DESC")
    List<EnrollmentReportDto> getReport();
}
