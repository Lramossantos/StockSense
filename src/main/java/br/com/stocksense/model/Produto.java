package br.com.stocksense.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.stocksense.enums.Disponibilidade;
import br.com.stocksense.enums.Empresa;
import br.com.stocksense.enums.Fabricante;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Size(min = 5, max = 35, message = "O nome deve conter entre 5 e 35 caracteres.")
	@NotBlank(message = "O nome não pode ser vazio.")
	@Column(name = "nome")
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(name = "disponibilidade")
	@NotNull(message = "Selecione uma disponibilidade")
	private Disponibilidade disponibilidade;

	@Column(name = "codigo")
	@NotNull(message = "O campo Código não pode ser vazio.")
	private String codigo;

	@Column(name = "descricao")
	@NotBlank(message = "O campo Descrição não pode ser vazio.")
	private String descricao;

	@Column(name = "entrada")
	private Integer entrada;

	@Column(name = "saida")
	private Integer saida;

	@Column(insertable = false, updatable = false)
	private Integer saldo;

	@Column(name = "fornecedor")
	@NotNull(message = "O campo Fornecedor está vazio, selecione uma opção.")
	private String fornecedor;

	@Column(name = "preco_custo")
	private BigDecimal precoCusto;

	@Column(name = "preco_venda")
	private BigDecimal precoVenda;

	@Column(name = "lucro_venda")
	private BigDecimal LucroVenda;

	@Column(name = "empresa")
	@NotNull(message = "Selecione uma empresa")
	private Empresa empresa;

	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;

	@Column(name = "ultima_atualizacao")
	private LocalDateTime ultimaAtualizacao;

	@Column(name = "ativo")
	@NotNull(message = "O campo Status não pode ser vazio. ")
	private Boolean ativo;

	@Column(name = "fabricante")
	@NotNull(message = "Selecione o Fabricante aqui. ")
	private Fabricante fabricante;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Disponibilidade getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(Disponibilidade disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getEntrada() {
		return entrada;
	}

	public void setEntrada(Integer entrada) {
		this.entrada = entrada;
	}

	public Integer getSaida() {
		return saida;
	}

	public void setSaida(Integer saida) {
		this.saida = saida;
	}

	public Integer getSaldo() {
		return saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public BigDecimal getLucroVenda() {
		return LucroVenda;
	}

	public void setLucroVenda(BigDecimal lucroVenda) {
		LucroVenda = lucroVenda;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	// Adiciona quantidade de entrada e atualiza saldo
	public void registrarEntrada(int quantidade) {
		if (quantidade <= 0) {
			throw new IllegalArgumentException("A quantidade de entrada deve ser positiva.");
		}
		if (this.entrada == null) {
			this.entrada = 0;
		}
		if (this.saldo == null) {
			this.saldo = 0;
		}
		this.entrada += quantidade;
		this.saldo += quantidade;
		this.ultimaAtualizacao = LocalDateTime.now();
	}

	public void registrarSaida(int quantidade) {
		if (quantidade <= 0) {
			throw new IllegalArgumentException("A quantidade de saída deve ser positiva.");
		}
		if (this.saldo == null) {
			this.saldo = 0;
		}
		if (quantidade > this.saldo) {
			throw new IllegalArgumentException("Estoque insuficiente para a saída solicitada.");
		}
		if (this.saida == null) {
			this.saida = 0;
		}
		this.saida += quantidade;
		this.saldo -= quantidade;
		this.ultimaAtualizacao = LocalDateTime.now();
	}

}
