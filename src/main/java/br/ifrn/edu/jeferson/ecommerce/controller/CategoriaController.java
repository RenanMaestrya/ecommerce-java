package br.ifrn.edu.jeferson.ecommerce.controller;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoDTO;
import br.ifrn.edu.jeferson.ecommerce.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Controller
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "API de gerenciamento de categorias dos Produtos")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @CacheEvict(value = "categorias", allEntries = true)
    @Operation(summary = "Criar uma nova categoria")
    @PostMapping
    public ResponseEntity<CategoriaDTO> salvar(@RequestBody CategoriaRequestDTO categoriaDto) {
        return ResponseEntity.ok(categoriaService.salvar(categoriaDto));
    }

    @Cacheable(value = "categorias")
    @Operation(summary = "Listar categorias")
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Quantidade de elementos por página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Critério de ordenação: nome,descricao", schema = @Schema(type = "string", allowableValues = {"nome", "descricao"}))
    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(categoriaService.lista(pageable));
    }

    @CacheEvict(value = "categorias", allEntries = true)
    @Operation(summary = "Deletar uma nova categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.ok().build();
    }
    
    @CacheEvict(value = {"categorias", "produtos"}, allEntries = true)
    @Operation(summary = "Adicionar um produto a uma categoria")
    @PostMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<ProdutoDTO> adicionarProdutoACategoria(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(categoriaService.adicionarProdutoACategoria(categoriaId, produtoId));
    }
    
    @CacheEvict(value = "categorias", allEntries = true)
    @Operation(summary = "Atualizar uma nova categoria")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, categoriaDto));
    }

    @CacheEvict(value = {"categorias", "produtos"}, allEntries = true)
    @Operation(summary = "Remover um produto de uma categoria")
    @DeleteMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<ProdutoDTO> removerProdutoDaCategoria(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(categoriaService.removerProdutoDaCategoria(categoriaId, produtoId));
    }
}
