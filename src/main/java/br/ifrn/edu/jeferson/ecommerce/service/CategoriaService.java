package br.ifrn.edu.jeferson.ecommerce.service;

import br.ifrn.edu.jeferson.ecommerce.domain.Categoria;
import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.CategoriaDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.BusinessException;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.CategoriaMapper;
import br.ifrn.edu.jeferson.ecommerce.mapper.ProdutoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.CategoriaRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaMapper mapper;
    @Autowired
    private CategoriaMapper categoriaMapper;
    @Autowired
    private ProdutoMapper produtoMapper;

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    private void verificaSeTemProduto(Long id) {
        if (produtoRepository.existsByCategorias_Id(id)) {
            throw new BusinessException("Não é possível deletar uma categoria que possui produtos associados");
        }
    }

    public CategoriaDTO salvar(CategoriaRequestDTO categoriaDto) {
        logger.info("Iniciando o processo para salvar ad categoria: {}", categoriaDto);

        var categoria = mapper.toEntity(categoriaDto);

        if (categoriaRepository.existsByNome(categoria.getNome())) {
            logger.warn("Nome já existente: {}", categoria.getNome());
            throw new BusinessException("Já existe uma categoria com esse nome");
        }

        categoriaRepository.save(categoria);
        logger.info("Categoria salva: {}", categoria);

        return mapper.toResponseDTO(categoria);
    }

    public Page<CategoriaDTO> lista(Pageable pageable) {
        logger.info("Listando categorias com paginação {}", pageable);
        Page<Categoria> categorias = categoriaRepository.findAll(pageable);
        return categorias.map(mapper::toResponseDTO);
    }

    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }

        verificaSeTemProduto(id);

        categoriaRepository.deleteById(id);
    }

    public CategoriaDTO atualizar(Long id, CategoriaRequestDTO categoriaDto) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!categoria.getNome().equals(categoriaDto.getNome()) && categoriaRepository.existsByNome( categoriaDto.getNome()) ) {
            throw  new BusinessException("Já existe uma categoria com esse nome");
        }

        categoriaMapper.updateEntityFromDTO(categoriaDto, categoria);
        var categoriaAlterada = categoriaRepository.save(categoria);

        return categoriaMapper.toResponseDTO(categoriaAlterada);
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));
        return categoriaMapper.toResponseDTO(categoria);
    }

    public ProdutoDTO adicionarProdutoACategoria(Long idCategoria, Long idProduto) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));
        Produto produto = produtoRepository.findById(idProduto).orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado"));

        categoria.getProdutos().add(produto);
        produto.getCategorias().add(categoria);
        produtoRepository.save(produto);
        categoriaRepository.save(categoria);
        return produtoMapper.toResponseDTO(produto);
    }

    public ProdutoDTO removerProdutoDaCategoria(Long idCategoria, Long idProduto) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));
        Produto produto = produtoRepository.findById(idProduto).
                orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado"));
            
        categoria.getProdutos().remove(produto);
        produto.getCategorias().remove(categoria);

        produtoRepository.save(produto);
        categoriaRepository.save(categoria);

        return produtoMapper.toResponseDTO(produto);
    }
}
