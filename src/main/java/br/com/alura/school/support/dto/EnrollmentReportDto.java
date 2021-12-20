package br.com.alura.school.support.dto;

public class EnrollmentReportDto {
    private Long qtdEnrollments;

    private String email;

    public EnrollmentReportDto(Long qtdEnrollments, String email) {
        this.qtdEnrollments = qtdEnrollments;
        this.email = email;
    }

    public Long getQtdEnrollments() {
        return qtdEnrollments;
    }

    public void setQtdEnrollments(Long qtdEnrollments) {
        this.qtdEnrollments = qtdEnrollments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
