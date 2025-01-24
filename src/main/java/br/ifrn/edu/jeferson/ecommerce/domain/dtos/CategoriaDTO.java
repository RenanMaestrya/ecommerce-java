package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de categoria")
public class CategoriaDTO {
    @Schema(description = "ID da categoria", example = "5")
    private Long id;

    @Schema(description = "Nome da categoria", example = "Smartphones")
    private String nome;

    @Schema(description = "Descrição da categoria", example = "Produtos em geral")
    private String descricao;
}
