package br.ifrn.edu.jeferson.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoDTO;
import br.ifrn.edu.jeferson.ecommerce.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Controller
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "API de gerenciamento de produtos")
public class ProdutoController {
    
    @Autowired
    ProdutoService produtoService;

    @CacheEvict(value = "produtos", allEntries = true)
    @Operation(summary = "Criar um novo produto")
    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@RequestBody ProdutoRequestDTO produtoDto) {
        return ResponseEntity.ok(produtoService.salvar(produtoDto));
    }

    @Cacheable(value = "produtos")
    @Operation(summary = "Listar produtos")
    @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "Quantidade de elementos por página", schema = @Schema(type = "integer", defaultValue = "20"))
    @Parameter(name = "sort", description = "Critério de ordenação: nome,preco,quantidadeEstoque", schema = @Schema(type = "string", allowableValues = {"nome", "preco", "quantidadeEstoque"}))
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> lista(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) BigDecimal precoMaiorQue,
        @RequestParam(required = false) BigDecimal precoMenorQue,
        @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(produtoService.lista(pageable, nome, precoMaiorQue, precoMenorQue));
    }

    @Cacheable(value = "produtos", key = "#id")
    @Operation(summary = "Buscar produto por id")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @CacheEvict(value = "produtos", key = "#id")
    @Operation(summary = "Deletar produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.ok().build();
    }

    @CacheEvict(value = "produtos", key = "#id")
    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtoDto) {
        return ResponseEntity.ok(produtoService.atualizar(id, produtoDto));
    }

    @CacheEvict(value = "produtos", key = "#id")
    @Operation(summary = "Atualizar estoque")
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoDTO> atualizarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        return ResponseEntity.ok(produtoService.atualizarEstoque(id, quantidade));
    }

    @Operation(summary = "Listar produtos por categoria")
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoDTO>> buscarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(produtoService.buscarPorCategoria(categoriaId));
    }
}
