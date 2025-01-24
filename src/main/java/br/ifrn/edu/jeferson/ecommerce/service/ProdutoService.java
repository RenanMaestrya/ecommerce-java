package br.ifrn.edu.jeferson.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.ProdutoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.CategoriaRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;
import br.ifrn.edu.jeferson.ecommerce.specification.ProdutoSpecification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private List<Categoria> buscarCategoriasPorId(List<Long> categoriaIds) {
        return categoriaRepository.findAllById(categoriaIds);
    }

    @CacheEvict(value = "produtos", allEntries = true)
    public ProdutoDTO salvar(ProdutoRequestDTO produtoDto) {
        log.info("Salvando novo produto: {}", produtoDto.getNome());
        var produto =  produtoMapper.toEntity(produtoDto);
        var categorias = buscarCategoriasPorId(produtoDto.getCategoriaIds());        
        produto.setCategorias(categorias);
        produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(produto);
    }

    public Page<ProdutoDTO> lista(
        Pageable pageable,
        String nome,
        BigDecimal precoMaiorQue,
        BigDecimal precoMenorQue
    ){
        Specification<Produto> spec = Specification.where(ProdutoSpecification.comNomeContendo(nome))
            .and(ProdutoSpecification.comPrecoMaiorQue(precoMaiorQue))
            .and(ProdutoSpecification.comPrecoMenorQue(precoMenorQue));

        Page<Produto> produtos = produtoRepository.findAll(spec, pageable);
        return produtoMapper.toDTOPage(produtos);
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    @CacheEvict(value = "produtos", key = "#id")
    public ProdutoDTO atualizar(Long id, ProdutoRequestDTO produtoDto) {
        log.info("Atualizando produto ID: {}", id);
        Produto produto = produtoRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado"));

        produtoMapper.updateEntityFromDTO(produtoDto, produto);
        var produtoAlterado = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(produtoAlterado);
    }

    @Cacheable(value = "produtos", key = "#id")
    public ProdutoDTO buscarPorId(Long id) {
        log.info("Buscando produto por ID: {}", id);
        Produto produto = produtoRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado"));
        return produtoMapper.toResponseDTO(produto);
    }

    public ProdutoDTO atualizarEstoque(Long id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado"));
        produto.setQuantidadeEstoque(quantidade);
        var produtoAlterado = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(produtoAlterado);
    }

    public List<ProdutoDTO> buscarPorCategoria(Long id) {
        List<Produto> produtos = produtoRepository.findByCategorias_Id(id);
        return produtoMapper.toDTOList(produtos);
    }
}
