package br.com.stocksense.enums;

public enum Fabricante {	
	ASHOK_LEYLAND("Ashok Leyland"),
    BYD("BYD"),
    DAF("DAF"),
    DONGFENG("Dongfeng"),
    FAW("FAW (First Automobile Works)"),
    FOTON("Foton"),
    FORD_TRUCKS("Ford Trucks"),
    FREIGHTLINER("Freightliner"),
    FUSO("Fuso (Mitsubishi Fuso Truck and Bus Corporation)"),
    HINO("Hino"),
    INTERNATIONAL("International (Navistar)"),
    ISUZU("Isuzu"),
    IVECO("Iveco"),
    KAMAZ("Kamaz"),
    KENWORTH("Kenworth"),
    MAN("MAN"),
    MERCEDES_BENZ("Mercedes-Benz"),
    PETERBILT("Peterbilt"),
    RENAULT_TRUCKS("Renault Trucks"),
    SCANIA("Scania"),
    SHACMAN("Shacman (Shaanxi Automobile Group)"),
    SINOTRUK("Sinotruk (China National Heavy Duty Truck Group)"),
    TATA_MOTORS("Tata Motors"),
    UD_TRUCKS("UD Trucks"),
    VOLKSWAGEN("Volkswagen Caminhões e Ônibus"),
    VOLVO("Volvo");
	
	private String fabricante;

	/**
	 * @param marca
	 */
	private Fabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getfabricante() {
		return fabricante;
	}

	public void setfabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	
	
	
	
	
	
	
	
	
	
}
