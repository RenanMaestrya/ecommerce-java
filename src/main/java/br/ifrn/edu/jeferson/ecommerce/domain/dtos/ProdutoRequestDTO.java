package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de produto")
public class ProdutoRequestDTO {
    @Schema(description = "Nome do produto", example = "Notebook")
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Notebook Dell")
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @Schema(description = "Valor do produto", example = "1000.00")
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior do que 0")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "O limite de preço foi excedido")
    private BigDecimal preco;

    @Schema(description = "Quantidade em estoque do produto", example = "10")
    @NotNull(message = "A quantidade do produto no estoque é obrigatória")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer estoque;


    @Schema(description = "Lista de ids das categorias do produto", example = "[1, 2]")
    @NotEmpty(message = "A lista de categorias é obrigatória")
    private List<Long> categoriaIds;
}