package br.com.stocksense.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.stocksense.model.Produto;

public interface ProdutoDAO extends JpaRepository<Produto, Integer> {}
