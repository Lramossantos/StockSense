package br.com.stocksense.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.stocksense.model.Produto;

public interface ProdutoDAO extends JpaRepository<Produto, Integer> {
    
    boolean existsByCodigo(String codigo);
    
    Optional<Produto> findByCodigo(String codigo);
    
    List<Produto> findByAtivo(boolean ativo);
    
    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
    
    List<Produto> findBySaldoLessThanEqual(Integer saldo);
}