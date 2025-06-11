/**
 * 
 */
package br.com.stocksense.enums;

/**
 * 
 */
public enum Fornecedor {
	BR_COMPANY("BR COMPANY"),
    SPTRUCK("SPTRUCK"),
    TRUCK_EIXO("TRUCK EIXO"),
    C_DIESEL("C DIESEL"),
    MOTORVOL("MOTORVOL"),
    DIRICARTI("DIRICARTI"),
    WALTEC("WALTEC"),
    DIESELVIA("DIESELVIA"),
    WAISER("WAISER"),
    JCREI("JCREI"),
    VERLI("VERLI"),
    LNG("LNG"),
    AMERICANDIESEL("AMERICANDIESEL"),
    FALMA("FALMA"),
    INCOPARTS("INCOPARTS"),
    JOTAGE_FORTPCS("JOTAGE FORTPÇS"),
    BIGTRUCK("BIGTRUCK"),
    AMORTESUL("AMORTESUL"),
    LOTTO_IMPORTACAO("LOTTO IMPORTAÇÃO"),
    EQUIP_FREIOS("EQUIP FREIOS"),
    VANUCCI_PCS("VANUCCI PÇS"),
    BZR_FLEX("BZR FLEX"),
    G4_AMORTECEDORES("G4 AMORTECEDORES"),
    IMPACTO_AMORTECEDORES("IMPACTO AMORTECEDORES"),
    DGRS_SUSPENTECH("DGRS SUSPENTECH"),
    CBA_DIESEL("CBA DIESEL"),
    JSW_BOIAS_E_INSTRUM_ELETRONICOS("JSW BOIAS E INSTRUM ELETRONICOS");
	
	private String fornecedor;

	Fornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	
}
