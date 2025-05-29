package br.com.stocksense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.stocksense.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {}
