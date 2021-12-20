package br.com.alura.school.enroll;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

class NewEnrollRequest {

    @NotBlank
    @Size(max = 20)
    @JsonProperty
    private final String username;

    // Por algum motivo, não estava aceitando uma classe de Request com apenas um
    // atributo. Gerava um erro de que não havia um construtor válido. Pesquisei
    // bastante e a única "solução" que encontrei foi criar um atributo inútil para
    // esse Request. Mas vale ressaltar que na hora de fazer a request, não é
    // necessário enviar ele, apenas o username. Essa seria a hora que eu iria atrás
    // de alguém que manja mais sobre o assunto para me ajudar, o que não tenho no
    // atual momento :(.
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
