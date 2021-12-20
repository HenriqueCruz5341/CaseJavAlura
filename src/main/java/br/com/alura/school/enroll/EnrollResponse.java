package br.com.alura.school.enroll;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.alura.school.support.dto.EnrollmentReportDto;

class EnrollResponse {

    @JsonProperty
    private final String email;

    @JsonProperty("quantidade_matriculas")
    private final Long quantidadeMatriculas;

    EnrollResponse(String email, Long quantidadeMatriculas) {
        this.email = email;
        this.quantidadeMatriculas = quantidadeMatriculas;
    }

    public static List<EnrollResponse> convertFromDto(List<EnrollmentReportDto> enrollmentReportDto) {
        return enrollmentReportDto.stream().map(dto -> new EnrollResponse(dto.getEmail(), dto.getQtdEnrollments()))
                .collect(Collectors.toList());
    }
}
