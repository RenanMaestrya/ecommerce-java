package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de produto")
public class ProdutoDTO {
    @Schema(description = "ID do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Macbook Pro")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Notebook da Apple")
    private String descricao;

    @Schema(description = "Valor do produto", example = "6000.00")
    private BigDecimal preco;

    @Schema(description = "Quantidade em estoque do produto", example = "10")
    private Integer estoque;

    @Schema(description = "Categorias do produto")
    private List<CategoriaDTO> categorias;
}
