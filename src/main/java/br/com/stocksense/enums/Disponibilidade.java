package br.com.stocksense.enums;

public enum Disponibilidade {
	EM_ESTOQUE("Em Estoque"), // Disponível para venda
	ESGOTADO("Esgotado"), // Indisponível (em falta)
	PRE_VENDA("Pré Venda"), // Disponível em breve
	DESCONTINUADO("Descontinuado"); // Nunca mais será vendido

	private String disponibilidade;

	private Disponibilidade(String disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	private String getDisponibilidade() {
		return disponibilidade;
	}

	private void setDisponibilidade(String disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

}