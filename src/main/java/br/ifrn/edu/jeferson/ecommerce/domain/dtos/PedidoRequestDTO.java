package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de pedido")
public class PedidoRequestDTO {
    @Schema(description = "ID do cliente do pedido", example = "1")
    @NotNull(message = "O id do cliente é obrigatório")
    private Long clienteId;

    @Schema(description = "Itens do pedido")
    @NotEmpty(message = "A lista de itens do pedido deve ter pelo menos UM item")
    @Valid
    private List<ItemPedidoRequestDTO> itensPedido;

    @Schema(hidden = true)
    public List<Long> getProdutosIds() {
        return itensPedido.stream().map(ItemPedidoRequestDTO::getProdutoId).collect(Collectors.toList());
    }
}
