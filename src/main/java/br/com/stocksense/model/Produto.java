package br.com.stocksense.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import br.com.stocksense.enums.Disponibilidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer id;
	
	@Column(name = "disponibilidade")
    private Disponibilidade disponibilidade;
    
    @Column(name = "imagens")
    private String imagens;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "estoque_anterior")
    private Integer estoqueAnterior;
    
    @Column(name = "entrada")
    private Integer entrada;
    
    @Column(name = "saida")
    private Integer saida;
    
    @Column(name = "saldo")
    private Integer saldo;
    
    @Column(name = "fornecedor")
    private String fornecedor;
    
    @Column(name = "preco_custo")
    private BigDecimal precoCusto;
    
    @Column(name = "preco_venda")
    private BigDecimal precoVenda;
    
    @Column(name = "categoria")
    private String categoria;
    
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
    
    @Column(name = "ativo")
    private Boolean ativo;
	
	
	
	
	
	
	

	private Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	private Disponibilidade getDisponibilidade() {
		return disponibilidade;
	}

	private void setDisponibilidade(Disponibilidade disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	private String getImagens() {
		return imagens;
	}

	private void setImagens(String imagens) {
		this.imagens = imagens;
	}

	private String getCodigo() {
		return codigo;
	}

	private void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	private String getDescricao() {
		return descricao;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private Integer getEstoqueAnterior() {
		return estoqueAnterior;
	}

	private void setEstoqueAnterior(Integer estoqueAnterior) {
		this.estoqueAnterior = estoqueAnterior;
	}

	private Integer getEntrada() {
		return entrada;
	}

	private void setEntrada(Integer entrada) {
		this.entrada = entrada;
	}

	private Integer getSaida() {
		return saida;
	}

	private void setSaida(Integer saida) {
		this.saida = saida;
	}

	private Integer getSaldo() {
		return saldo;
	}

	private void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	private String getFornecedor() {
		return fornecedor;
	}

	private void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	private BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	private void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	private String getLocacao() {
		return locacao;
	}

	private void setLocacao(String locacao) {
		this.locacao = locacao;
	}

}
