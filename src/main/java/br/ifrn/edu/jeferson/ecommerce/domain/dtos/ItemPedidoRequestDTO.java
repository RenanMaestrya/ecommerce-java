package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de item de pedido")
public class ItemPedidoRequestDTO {
    
    @Schema(description = "O ID do produto desejado", example = "1")
    @NotNull(message = "O id do produto é obrigatório")
    private Long produtoId;

    @Schema(description = "A quantidade de itens", example = "1")
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior que 0")
    private Integer quantidade;
}
