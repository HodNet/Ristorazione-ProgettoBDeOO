
public class Ristorante {
	private String ID;
	private String nome;
	private String citt�; 
	private String indirizzo; 
	private String numeroCivico; 
	private byte numeroSale; 
	private static final byte oraApertura = 20;
	private static final byte oraChiusura = 22;
	private String foto;
	
	public Ristorante(String ID, String nome, String citt�, String indirizzo, String numeroCivico, byte numeroSale, String foto) {
		this.ID = ID;
		this.nome = nome;
		this.citt� = citt�;
		this.indirizzo = indirizzo;
		this.numeroCivico = numeroCivico;
		this.numeroSale = numeroSale;
		this.foto = foto;
	}
	
	public String getID() {
		return ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCitt�() {
		return citt�;
	}

	public void setCitt�(String citt�) {
		this.citt� = citt�;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numero_civico) {
		this.numeroCivico = numero_civico;
	}
	
	public byte getNumeroSale() {
		return numeroSale;
	}

	public static byte getOraapertura() {
		return oraApertura;
	}

	public static byte getOrachiusura() {
		return oraChiusura;
	}
	
	public String getFoto() {
		return foto;
	}
}
