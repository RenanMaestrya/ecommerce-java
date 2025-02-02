package br.ifrn.edu.jeferson.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ifrn.edu.jeferson.ecommerce.domain.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    
    @Modifying
    @Query("DELETE FROM ItemPedido ip WHERE ip.pedido.id = :pedidoId")
    void deleteByPedidoId(Long pedidoId);
}
