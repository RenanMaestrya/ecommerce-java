package br.ifrn.edu.jeferson.ecommerce.mapper;

import java.util.List;

import org.mapstruct.*;
import org.springframework.data.domain.Page;

import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ProdutoResponseDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    Produto toEntity(ProdutoRequestDTO produtoRequestDTO);

    ProdutoResponseDTO toResponseDTO(Produto produto);

    List<ProdutoResponseDTO> toDTOList(List<Produto> produtos);

    default Page<ProdutoResponseDTO> toDTOPage(Page<Produto> produtos) {
        return produtos.map(this::toResponseDTO);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    void updateEntityFromDTO(ProdutoRequestDTO produtoRequestDTO, @MappingTarget Produto produto);
}