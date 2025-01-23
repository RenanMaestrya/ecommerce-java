package br.ifrn.edu.jeferson.ecommerce.domain.dtos;

import br.ifrn.edu.jeferson.ecommerce.domain.enums.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para atualização de status de um pedido")
public class PedidoAtualizarRequestDTO {
    @Schema(description = "Status do pedido", example = "PAGO")
    @NotNull(message = "Status é obrigatório")
    private StatusPedido statusPedido;
}
