package br.com.alura.school.enroll;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    List<Enroll> findByUserUsernameAndCourseCode(String username, String courseCode);

    List<Enroll> findByCourseCode(String code);
}
