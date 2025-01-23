package br.ifrn.edu.jeferson.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifrn.edu.jeferson.ecommerce.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    boolean existsByClienteId(Long clientId);
    Endereco findByClienteId(Long clientId);
    Optional<Endereco> findByIdAndClienteId(Long id, Long clientId); 
}
