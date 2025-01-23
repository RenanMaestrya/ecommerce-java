package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de cliente")
public class ClienteRequestDTO {
    @Schema(description = "Nome do cliente", example = "Jeferson")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
    private String nome;

    @Schema(description = "Email do cliente", example = "jeferson@gmail.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "O email deve seguir o formato correto. ex: exemplo@exemplo.com")
    @Size(min = 2, max = 255, message = "Email deve ter entre 2 e 255 caracteres")
    private String email;

    @Schema(description = "CPF do cliente", example = "000.000.000-00")
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve seguir o formato correto. ex: 000.000.000-00")
    private String cpf;

    @Schema(description = "Telefone do cliente", example = "(84) 99999-9999")
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "Telefone deve seguir o formato correto. ex: (84) 99999-9999")
    private String telefone;
}
