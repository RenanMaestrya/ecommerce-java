package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de categoria")
public class CategoriaRequestDTO {

    @Schema(description = "Nome da categoria", example = "Eletrônicos")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Schema(description = "Descrição da categoria", example = "Produtos eletrônicos em geral")
    private String descricao;

}
