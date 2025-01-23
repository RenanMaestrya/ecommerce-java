package br.ifrn.edu.jeferson.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ifrn.edu.jeferson.ecommerce.domain.Cliente;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteRequestDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.ClienteResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.domain.dtos.PedidoResponseDTO;
import br.ifrn.edu.jeferson.ecommerce.exception.BusinessException;
import br.ifrn.edu.jeferson.ecommerce.exception.ResourceNotFoundException;
import br.ifrn.edu.jeferson.ecommerce.mapper.ClienteMapper;
import br.ifrn.edu.jeferson.ecommerce.mapper.PedidoMapper;
import br.ifrn.edu.jeferson.ecommerce.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;   

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private PedidoMapper pedidoMapper;

    private void verificaSeCpfJaExiste(String cpf) {
        if (clienteRepository.existsByCpf(cpf)) {
            throw new BusinessException(String.format("Cliente com CPF %s já cadastrado", cpf));
        }
    }

    private void verificaSeEmailJaExiste(String email) {
        if (clienteRepository.existsByEmail(email)) {
            throw new BusinessException(String.format("O email %s já está sendo usado", email));
        }
    }

    private void verificaSePossuiPedidos(Long id) {
        if (clienteRepository.existsByIdAndPedidosIsNotEmpty(id)) {
            throw new BusinessException("Cliente possui pedidos e não pode ser deletado");
        }
    }

    private void validaCliente(ClienteRequestDTO clienteDto) {
        verificaSeCpfJaExiste(clienteDto.getCpf());
        verificaSeEmailJaExiste(clienteDto.getEmail());
    }

    public ClienteResponseDTO salvar(ClienteRequestDTO clienteDto) {
        validaCliente(clienteDto);

        var cliente =  clienteMapper.toEntity(clienteDto);
        clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    public Page<ClienteResponseDTO> lista(
        Pageable pageable
    ){
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        return clienteMapper.toDTOPage(clientes);
    }

    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        verificaSePossuiPedidos(id);

        clienteRepository.deleteById(id);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteDto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Cliente não encontrado"));

        validaCliente(clienteDto);

        clienteMapper.updateEntityFromDTO(clienteDto, cliente);
        var clienteAlterado = clienteRepository.save(cliente);

        return clienteMapper.toResponseDTO(clienteAlterado);
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Cliente não encontrado"));
        return clienteMapper.toResponseDTO(cliente);
    }

    public List<PedidoResponseDTO> listarPedidosDoCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return pedidoMapper.toDTOList(cliente.getPedidos());
    }

}
