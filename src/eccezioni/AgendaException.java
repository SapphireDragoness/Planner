package eccezioni;

/**
 * Eccezione sollevata quando si verifica qualche problema all'interno dell'agenda.
 */
public class AgendaException extends Exception {
	
	/**
	 * Costruisce l'eccezione.
	 */
	public AgendaException() {
		super();
	}

	/**
	 * Costruisce l'eccezione con messaggio specificato.
	 * 
	 * @param msg	il messaggio
	 */
	public AgendaException(String msg) {
		super(msg);
	}
	
	/**
	 * Costruisce l'eccezione con causa specificata.
	 * @param cause	la causa dell'eccezione
	 */
	public AgendaException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Costruisce l'eccezione con messaggio e causa specificati.
	 * 
	 * @param msg	il messaggio
	 * @param cause	la causa dell'eccezione
	 */
	public AgendaException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}