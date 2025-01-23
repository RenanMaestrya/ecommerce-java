package br.ifrn.edu.jeferson.ecommerce.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;

public class ProdutoSpecification {
    public static Specification<Produto> comNomeContendo(String nome) {
        return (root, query, builder) ->
            nome == null ? null : builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Produto> comPrecoMaiorQue(BigDecimal preco) {
        return (root, query, builder) ->
            preco == null ? null : builder.greaterThanOrEqualTo(root.get("preco"), preco);
    }

    public static Specification<Produto> comPrecoMenorQue(BigDecimal preco) {
        return (root, query, builder) ->
            preco == null ? null : builder.lessThanOrEqualTo(root.get("preco"), preco);
    }
}
