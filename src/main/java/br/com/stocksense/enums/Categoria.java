package br.com.stocksense.enums;

public enum Categoria {
	CATALISE("CATALISE"), 
	OLS("OLS");

	private String categoria;

	/**
	 * @param categoria
	 */
	private Categoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
