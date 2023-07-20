package eccezioni;

/**
 * Eccezione sollevata quando si verificano problemi relativi agli appuntamenti.
 */
public class AppuntamentoException extends Exception {
	
	/**
	 * Costruisce l'eccezione.
	 */
	public AppuntamentoException() {
		super();
	}

	/**
	 * Costruisce l'eccezione con messaggio specificato.
	 * 
	 * @param msg	il messaggio
	 */
	public AppuntamentoException(String msg) {
		super(msg);
	}
	
	/**
	 * Costruisce l'eccezione con causa specificata.
	 * @param cause	la causa dell'eccezione
	 */
	public AppuntamentoException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Costruisce l'eccezione con messaggio e causa specificati.
	 * 
	 * @param msg	il messaggio
	 * @param cause	la causa dell'eccezione
	 */
	public AppuntamentoException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
