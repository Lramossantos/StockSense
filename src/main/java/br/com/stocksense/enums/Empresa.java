package br.com.stocksense.enums;

public enum Empresa {
	CATALISE("CATALISE"), 
	OLS("OLS");

	private String empresa;

	
	private Empresa(String empresa) {
		this.empresa = empresa;
	}


	public String getEmpresa() {
		return empresa;
	}


	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	
	
}
