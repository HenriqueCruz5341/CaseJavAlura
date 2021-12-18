package br.com.alura.school.support.dto;

public class EnrollmentReportDto {
    private Integer qtdEnrollments;

    private String email;

    public EnrollmentReportDto(Integer qtdEnrollments, String email) {
        this.qtdEnrollments = qtdEnrollments;
        this.email = email;
    }

    public Integer getQtdEnrollments() {
        return qtdEnrollments;
    }

    public void setQtdEnrollments(Integer qtdEnrollments) {
        this.qtdEnrollments = qtdEnrollments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
