package br.ifrn.edu.jeferson.ecommerce.mapper;

import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoDTO;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ItemPedidoMapper.class}
)
public interface PedidoMapper {
    
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "status", source = "statusPedido")
    PedidoDTO toResponseDTO(Pedido pedido);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Pedido toEntity(PedidoRequestDTO dto);

    List<PedidoDTO> toDTOList(List<Pedido> pedidos);

    default Page<PedidoDTO> toDTOPage(Page<Pedido> pedidos) {
        return pedidos.map(this::toResponseDTO);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Pedido updateEntityFromDTO(PedidoRequestDTO dto, @MappingTarget Pedido pedido);
}
