package br.ifrn.edu.jeferson.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API de gerenciamento de clientes")
public class ClienteController {
    
    @Autowired 
    ClienteService clienteService;

    @Operation(summary = "Criar um novo cliente")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@RequestBody ClienteRequestDTO clienteDto) {
        return ResponseEntity.ok(clienteService.salvar(clienteDto));
    }

    @Operation(summary = "Listar clientes")
    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> lista(
        Pageable pageable
    ) {
        return ResponseEntity.ok(clienteService.lista(pageable));
    }

    @Operation(summary = "Buscar cliente por id")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Operation(summary = "Deletar cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(Long id) {
        clienteService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(Long id, @RequestBody ClienteRequestDTO clienteDto) {
        return ResponseEntity.ok(clienteService.atualizar(id, clienteDto));
    }

    @Operation(summary = "Listar pedidos do cliente")
    @GetMapping("/{id}/pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosDoCliente(Long id) {
        return ResponseEntity.ok(clienteService.listarPedidosDoCliente(id));
    }

}
