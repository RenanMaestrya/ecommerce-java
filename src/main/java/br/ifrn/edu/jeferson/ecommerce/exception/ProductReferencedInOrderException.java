package br.ifrn.edu.jeferson.ecommerce.exception;

public class ProductReferencedInOrderException extends RuntimeException {
    public ProductReferencedInOrderException(Long id) {
        super("Não é possível excluir o produto de ID " + id + " pois está vinculado a pedidos");
    }
}
