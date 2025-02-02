package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de cliente")
public class ClienteDTO {
    @Schema(description = "ID do cliente", example = "1")
    private Long id;

    @Schema(description = "Nome do cliente", example = "Jeferson")
    private String nome;

    @Schema(description = "CPF do cliente", example = "123.456.789-10")
    private String cpf;

    @Schema(description = "E-mail do cliente", example = "jefersonprof@gmail.com")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(84) 99123-4567")
    private String telefone;
}
