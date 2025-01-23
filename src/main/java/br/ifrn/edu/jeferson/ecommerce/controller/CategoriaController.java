package br.ifrn.edu.jeferson.ecommerce.controller;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "API de gerenciamento de categorias dos Produtos")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Criar uma nova categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@RequestBody CategoriaRequestDTO categoriaDto) {
        return ResponseEntity.ok(categoriaService.salvar(categoriaDto));
    }

    @Operation(summary = "Listar uma nova categoria")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        return ResponseEntity.ok(categoriaService.lista());
    }

    @Operation(summary = "Deletar uma nova categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar uma nova categoria")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, categoriaDto));
    }

    @Operation(summary = "Adicionar um produto a uma categoria")
    @PostMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> adicionarProdutoACategoria(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(categoriaService.adicionarProdutoACategoria(categoriaId, produtoId));
    }

    @Operation(summary = "Remover um produto de uma categoria")
    @DeleteMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> removerProdutoDaCategoria(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(categoriaService.removerProdutoDaCategoria(categoriaId, produtoId));
    }
}
