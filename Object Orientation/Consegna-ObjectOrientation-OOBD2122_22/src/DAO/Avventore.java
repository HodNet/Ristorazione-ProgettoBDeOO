package DAO;

public class Avventore {
	private String codCartaIdentitÓ;
	private String nome;
	private String cognome;
	private String numeroDiTelefono;
	
	public Avventore(String codCartaIdentitÓ, String nome, String cognome, String numeroDiTelefono) {
		this.codCartaIdentitÓ = codCartaIdentitÓ;
		this.nome = nome;
		this.cognome = cognome;
		this.numeroDiTelefono = numeroDiTelefono;
	}

	public String getCodCartaIdentitÓ() {
		return codCartaIdentitÓ;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNumeroDiTelefono() {
		return numeroDiTelefono;
	}
}
