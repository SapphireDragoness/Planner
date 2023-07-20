package agende;

import java.util.ArrayList;
import agenda.Agenda;
import eccezioni.AgendaException;

/**
 * Classe statica che contiene oggetti di tipo agenda.
 * 
 * @author Linda Monfermoso, 20028464
 *
 */
public class Agende {
	
	private static final ArrayList<Agenda> listaAgende = new ArrayList<>();
	
	/**
	 * Ritorna il numero di agende presenti.
	 * 
	 * @return Il numero di agende presenti
	 */
	public static int numeroAgende() {
		return listaAgende.size();
	}
	
	/**
	 * Rimuove tutti gli oggetti agenda contenuti nella lista.
	 */
	public static void svuotaLista() {
		listaAgende.clear();
	}
	
	/**
	 * Aggiunge un'agenda con nome dato in input.
	 * 
	 * @param nome Nome dell'agenda
	 * @throws AgendaException Se esiste già un'agenda con quel nome
	 */
	public static void aggiungiAgenda(String nome) throws AgendaException {
		if(nome == "") {
			listaAgende.add(new Agenda());
		}
		else {
			checkNomeAgenda(nome);
			listaAgende.add(new Agenda(nome));
		}
	}
	
	private static void checkNomeAgenda(String nome) throws AgendaException {
		if (listaAgende.stream().anyMatch(a -> a.nomeAgenda.equals(nome))) {
	        throw new AgendaException("Esiste già un'agenda con questo nome.");
	    }
	}
	
	/**
	 * Ritorna l'agenda identificata dall'indice.
	 * 
	 * @param indice L'indice dell'agenda da ritornare
	 * @return L'agenda desiderata
	 */
	public static Agenda getAgenda(int indice) {
		return listaAgende.get(indice);
	}
	
	/**
	 * Elimina l'agenda identificata dall'indice.
	 * 
	 * @param indice L'indice dell'agenda da eliminare
	 * @throws AgendaException Se l'agenda non esiste
	 */
	public static void rimuoviAgenda(int indice) throws AgendaException {
		if(indice >= numeroAgende()) throw new AgendaException("L'agenda specificata non esiste.");
		listaAgende.remove(indice);
	}
	
	/**
	 * Stampa tutte le agende presenti nella lista.
	 */
	public static void stampaAgende() {
		int indice = 0;
		for(Agenda a : listaAgende) {
			System.out.println(indice + ". " + a.toString());
			indice++;
		}
	}
	
}
