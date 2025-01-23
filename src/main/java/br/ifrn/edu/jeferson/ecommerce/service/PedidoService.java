package br.ifrn.edu.jeferson.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ifrn.edu.jeferson.ecommerce.domain.ItemPedido;
import br.ifrn.edu.jeferson.ecommerce.domain.Pedido;
import br.ifrn.edu.jeferson.ecommerce.domain.Produto;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ItemPedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.enums.StatusPedido;
import br.ifrn.edu.jeferson.ecommerce.exception.BusinessException;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.PedidoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ClienteRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ItemPedidoRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.PedidoRepository;
import br.ifrn.edu.jeferson.ecommerce.repository.ProdutoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired  
    private PedidoMapper pedidoMapper;

    private void verificaSeTodosProdutosExistem(List<Produto> produtos, List<Long> produtosIds) {
        if (produtosIds.size() != produtos.size()) {
            throw new BusinessException("Um ou mais produtos não foram encontrados");
        }
    }

    private void verificaSeProdutoTemEstoqueSuficiente(Produto produto, Integer quantidade) {
        if (produto.getEstoque() < quantidade) {
            throw new BusinessException("Estoque insuficiente para o produto " + produto.getNome());
        }
    }

    public PedidoResponseDTO salvar(PedidoRequestDTO pedidoDto) {
        var produtosIds = pedidoDto.getProdutosIds();
        var produtos = produtoRepository.findAllById(produtosIds);

        verificaSeTodosProdutosExistem(produtos, produtosIds);

        var cliente = clienteRepository.findById(pedidoDto.getClienteId()).orElseThrow( () -> new ResourceNotFoundException("Cliente não encontrado"));
        var pedido =  pedidoMapper.toEntity(pedidoDto);
        var itens = new ArrayList<ItemPedido>();
        var atualizacaoDeEstoque = new ArrayList<Produto>();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO item : pedidoDto.getItensPedido()) {
            var produto = produtos.stream().filter(p -> p.getId().equals(item.getProdutoId())).findFirst().orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado"));
            verificaSeProdutoTemEstoqueSuficiente(produto, item.getQuantidade());

            var itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(item.getQuantidade());
            itemPedido.setPedido(pedido);
            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));

            itens.add(itemPedido);
            
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            atualizacaoDeEstoque.add(produto);
        }


        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);
        pedido.setItens(itens);
        pedido.setValorTotal(total);

        pedido = pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itens);
        produtoRepository.saveAll(atualizacaoDeEstoque);
        return pedidoMapper.toResponseDTO(pedido);
    }

    public Page<PedidoResponseDTO> lista(
        Pageable pageable
    ){
        Page<Pedido> pedidos = pedidoRepository.findAll(pageable);
        return pedidoMapper.toDTOPage(pedidos);
    }

    public void deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido statusPedido) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Pedido não encontrado"));
        pedido.setStatusPedido(statusPedido);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedido);
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Pedido não encontrado"));
        return pedidoMapper.toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listarPedidosPorCliente(Long clientId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clientId);
        return pedidoMapper.toDTOList(pedidos);
    }
}
