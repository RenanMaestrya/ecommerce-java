package br.ifrn.edu.jeferson.ecommerce.exception;

public class OrderReferencedByItemsException extends RuntimeException {
    public OrderReferencedByItemsException(Long id) {
        super("Não é possível excluir o pedido de ID " + id + " pois possui itens vinculados");
    }
}
