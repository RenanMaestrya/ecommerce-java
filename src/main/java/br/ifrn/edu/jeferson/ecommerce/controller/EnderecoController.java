package br.ifrn.edu.jeferson.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/clientes/{clientId}/enderecos")
@Tag(name = "Endereços", description = "API de gerenciamento de endereços dos clientes")
public class EnderecoController {
    
    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Criar um novo endereço para o cliente")
    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> salvar(
            @PathVariable Long clientId, 
            @RequestBody EnderecoRequestDTO enderecoDto) {
        return ResponseEntity.ok(enderecoService.salvar(clientId, enderecoDto));
    }

    @Operation(summary = "Listar endereços de um cliente")
    @GetMapping
    public ResponseEntity<EnderecoResponseDTO> listar(@PathVariable Long clientId) {
        return ResponseEntity.ok(enderecoService.listar(clientId));
    }

    @Operation(summary = "Deletar um endereço de um cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long clientId, @PathVariable Long id) {
        enderecoService.deletar(clientId, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar um endereço de um cliente")
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizar(
            @PathVariable Long clientId, 
            @PathVariable Long id, 
            @RequestBody EnderecoRequestDTO enderecoDto) {
        return ResponseEntity.ok(enderecoService.atualizar(clientId, id, enderecoDto));
    }
}
