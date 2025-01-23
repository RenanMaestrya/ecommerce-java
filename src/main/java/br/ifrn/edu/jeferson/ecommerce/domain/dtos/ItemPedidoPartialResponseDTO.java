package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de um item de um produto")
public class ItemPedidoPartialResponseDTO {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Notebook Dell")
    private String nome;

    @Schema(example = "1000")
    private BigDecimal preco;
}
