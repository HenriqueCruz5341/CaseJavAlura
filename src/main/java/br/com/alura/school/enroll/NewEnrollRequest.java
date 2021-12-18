package br.com.alura.school.enroll;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

class NewEnrollRequest {

    @NotBlank
    @Size(max = 20)
    @JsonProperty
    private final String username;

    @JsonProperty
    private final String useless;

    NewEnrollRequest(String username, String useless) {
        this.username = username;
        this.useless = useless;
    }

    String getUsername() {
        return username;
    }

    String getUseless() {
        return useless;
    }

}
