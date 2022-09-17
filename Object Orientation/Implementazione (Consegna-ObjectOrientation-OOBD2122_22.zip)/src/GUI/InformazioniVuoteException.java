package GUI;

public class InformazioniVuoteException extends Exception {
	private String message = "La casella di testo non può essere vuota. Inserisci qualcosa e riprova";

	public InformazioniVuoteException() {}
			
	public InformazioniVuoteException(String errorMessage) {
		message = errorMessage;
	}
			
	public String getMessage() {
		return message;
	}
}
