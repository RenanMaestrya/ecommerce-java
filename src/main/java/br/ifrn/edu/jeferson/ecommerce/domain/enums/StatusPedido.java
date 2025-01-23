package br.ifrn.edu.jeferson.ecommerce.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPedido {
    AGUARDANDO("Aguardando pagamento"),
    CANCELADO("Cancelar Pedido"),
    PAGO("Pago"),
    ENVIADO("Enviado");

    private String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }
}
