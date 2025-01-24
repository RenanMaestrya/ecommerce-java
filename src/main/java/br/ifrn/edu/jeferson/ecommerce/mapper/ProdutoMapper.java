package br.ifrn.edu.jeferson.ecommerce.mapper;

import java.util.List;

import org.mapstruct.*;
import org.springframework.data.domain.Page;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "quantidadeEstoque", source = "estoque")
    Produto toEntity(ProdutoRequestDTO produtoRequestDTO);

    @Mapping(target = "estoque", source = "quantidadeEstoque")
    ProdutoDTO toResponseDTO(Produto produto);

    List<ProdutoDTO> toDTOList(List<Produto> produtos);

    default Page<ProdutoDTO> toDTOPage(Page<Produto> produtos) {
        return produtos.map(this::toResponseDTO);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "quantidadeEstoque", source = "estoque")
    void updateEntityFromDTO(ProdutoRequestDTO produtoRequestDTO, @MappingTarget Produto produto);
}