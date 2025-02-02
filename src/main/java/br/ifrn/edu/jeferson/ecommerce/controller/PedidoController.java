package br.ifrn.edu.jeferson.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoAtualizarRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoDTO;
import br.ifrn.edu.jeferson.ecommerce.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Controller
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API de gerenciamento de pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @CacheEvict(value = {"pedidos", "clientes"}, allEntries = true)
    @Operation(summary = "Criar um novo pedido")
    @PostMapping
    public ResponseEntity<PedidoDTO> salvar(@RequestBody PedidoRequestDTO pedidoDto) {
        return ResponseEntity.ok(pedidoService.salvar(pedidoDto));
    }

    @Cacheable(value = "pedidos")
    @Operation(summary = "Listar pedidos")
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Quantidade de elementos por página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Critério de ordenação: id, dataPedido, valorTotal, statusPedido", schema = @Schema(type = "string", allowableValues = {"id","dataPedido", "valorTotal", "statusPedido"}))
    @GetMapping
    public ResponseEntity<Page<PedidoDTO>> listar(
        @PageableDefault(sort = "dataPedido", direction = Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(pedidoService.lista(pageable));
    }

    @CacheEvict(value = "pedidos", allEntries = true)
    @Operation(summary = "Deletar um pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @CacheEvict(value = "pedidos", key = "#id")
    @Operation(summary = "Atualizar status de um pedido")
    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizarStatusPedido(@PathVariable Long id, @RequestBody PedidoAtualizarRequestDTO statusPedidoDto) {
        return ResponseEntity.ok(pedidoService.atualizarStatusPedido(id, statusPedidoDto.getStatusPedido()));
    }

    @Cacheable(value = "pedidos", key = "#id")
    @Operation(summary = "Buscar um pedido por id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @Cacheable(value = "pedidos", key = "'cliente-' + #clienteId")
    @Operation(summary = "Listar pedidos de um cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorCliente(clienteId));
    }
}
