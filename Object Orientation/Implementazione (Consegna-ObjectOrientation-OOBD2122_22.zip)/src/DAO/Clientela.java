package DAO;

public class Clientela {
	private String codCartaIdentitÓ;
	private String dataDiArrivo;
	private String tavoloID;
	
	public Clientela(String codCartaIdentitÓ, String dataDiArrivo, String tavoloID) {
		this.codCartaIdentitÓ = codCartaIdentitÓ;
		this.dataDiArrivo = dataDiArrivo;
		this.tavoloID = tavoloID;
	}

	public String getCodCartaIdentitÓ() {
		return codCartaIdentitÓ;
	}

	public String getDataDiArrivo() {
		return dataDiArrivo;
	}

	public String getTavoloID() {
		return tavoloID;
	}
}
