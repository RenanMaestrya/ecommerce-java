package br.ifrn.edu.jeferson.ecommerce.mapper;

import java.util.List;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteDTO;
import org.mapstruct.*;
import org.springframework.data.domain.Page;


@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClienteMapper {
   
    
    ClienteDTO toResponseDTO(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Cliente toEntity(ClienteRequestDTO dto);

    List<ClienteDTO> toDTOList(List<Cliente> clientes);

    default Page<ClienteDTO> toDTOPage(Page<Cliente> clientes) {
        return clientes.map(this::toResponseDTO);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Cliente updateEntityFromDTO(ClienteRequestDTO dto, @MappingTarget Cliente cliente);
}
