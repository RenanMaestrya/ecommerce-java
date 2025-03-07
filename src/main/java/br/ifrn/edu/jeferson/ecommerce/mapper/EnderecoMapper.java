package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.EnderecoDTO;

import java.util.List;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoDTO toResponseDTO(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Endereco toEntity(EnderecoRequestDTO dto);

    List<EnderecoDTO> toDTOList(List<Endereco> enderecos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateEntityFromDTO(EnderecoRequestDTO dto, @MappingTarget Endereco endereco);
} 
