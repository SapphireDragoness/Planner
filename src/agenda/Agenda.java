package agenda;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;

import appuntamento.Appuntamento;
import eccezioni.AgendaException;
import eccezioni.AppuntamentoException;

/**
 * Classe che contiene oggetti appuntamento e ne permette la manipolazione.
 * 
 * @author Linda Monfermoso, 20028464
 *
 */
public class Agenda implements Iterable<Appuntamento> {

	public final String nomeAgenda;
	private final LocalDate dataCreazioneAgenda;
	private final LocalTime oraCreazioneAgenda;
	private final String nomeUtente;
	private ArrayList<Appuntamento> agenda;

	private static int numeroAgende = 0;

	/**
	 * Costruisce un oggetto di tipo agenda, prendendo nome dell'agenda come input.
	 * 
	 * @param nome Nome dell'agenda
	 */
	public Agenda(String nome) {
		this.nomeAgenda = nome;
		this.dataCreazioneAgenda = LocalDate.now();
		this.oraCreazioneAgenda = LocalTime.now();
		this.nomeUtente = System.getProperty("user.name");
		agenda = new ArrayList<Appuntamento>();
		numeroAgende++;
	}

	/**
	 * Costruisce un oggetto di tipo agenda, inserendo nome di default.
	 */
	public Agenda() {
		this("Agenda " + numeroAgende);
	}

	/**
	 * Classe iteratore dell'oggetto Agenda.
	 * 
	 * @author Linda Monfermoso, 20028464
	 *
	 */
	class AgendaIterator implements Iterator<Appuntamento> {

		int indice = 0;

		/**
		 * Ritorna true se l'iterazione ha ancora elementi.
		 * 
		 * @return True se Agenda ha ancora elementi
		 */
		@Override
		public boolean hasNext() {
			return indice < agenda.size();
		}

		/**
		 * Ritorna il prossimo elemento dell'iterazione.
		 * 
		 * @return True se Agenda ha ancora elementi
		 */
		@Override
		public Appuntamento next() {
			return agenda.get(indice++);
		}

		/**
		 * Non supportato come da requisiti.
		 * 
		 * @throws UnsupportedOperationException
		 */
		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Oggetto iteratore di Agenda.
	 * 
	 * @return Iteratore dell'agenda
	 */
	@Override
	public Iterator<Appuntamento> iterator() {
		return new AgendaIterator();
	}

	/**
	 * Getter del nome dell'agenda.
	 * 
	 * @return Il nome dell'agenda
	 */
	public String getNome() {
		return nomeAgenda;
	}

	/**
	 * Ritorna un oggetto di tipo appuntamento dato un'indice in input.
	 * 
	 * @param indice L'indice dell'appuntamento
	 * @return L'appuntamento desiderato
	 */
	public Appuntamento getAppuntamento(int indice) {
		return agenda.get(indice);
	}

	/**
	 * Ritorna il numero di appuntamenti presenti in un'agenda.
	 * 
	 * @return Il numero di appuntamenti
	 */
	public int numeroAppuntamenti() {
		return agenda.size();
	}

	/**
	 * Rimuove tutti gli appuntamenti da un'agenda.
	 */
	public void svuotaAgenda() {
		agenda.clear();
	}

	/**
	 * Aggiunge un'appuntamento all'agenda, usando come valori del costruttore i
	 * valori in input.
	 * 
	 * @param data   La data dell'appuntamento
	 * @param orario L'orario dell'appuntamento
	 * @param durata La durata dell'appuntamento
	 * @param nome   Il nome della persona dell'appuntamento
	 * @param luogo  Il luogo dell'appuntamento
	 * @throws AppuntamentoException Sollevata se il formato di uno dei parametri
	 *                               non è corretto
	 * @throws AgendaException       Se ci sono conflitti
	 */
	public void aggiungiAppuntamento(String data, String orario, int durata, String nome, String luogo)
			throws AgendaException, AppuntamentoException {
		aggiungiAppuntamento(new Appuntamento(data, orario, durata, nome, luogo));
	}

	/**
	 * Aggiunge un oggetto di tipo appuntamento all'agenda, controllando conflitti e
	 * ordinando la lista degli appuntamenti in ordine cronologico.
	 * 
	 * @param appuntamento L'oggetto di tipo appuntamento
	 * @throws AgendaException Se ci sono conflitti
	 */
	public void aggiungiAppuntamento(Appuntamento appuntamento) throws AgendaException {
		checkConflitti(appuntamento);
		agenda.add(appuntamento);
		agenda.sort((appuntamento1, appuntamento2) -> appuntamento1.compareTo(appuntamento2));
	}

	private void checkConflitti(Appuntamento appuntamento) throws AgendaException {
		if (agenda.stream().anyMatch(a -> a.checkConflittiOrari(appuntamento))) {
			throw new AgendaException(
					"Appuntamento in conflitto con uno già presente, impossibile completare operazione.");
		}
	}

	/**
	 * Modifica la data di un appuntamento, effettuando controlli sulla validità dei
	 * dati inseriti ed eventuali conflitti.
	 * 
	 * @param indice L'indice dell'appuntamento da modificare
	 * @param data   La data da sostituire
	 * @throws AppuntamentoException Sollevata se il formato di uno dei parametri
	 *                               non è corretto
	 * @throws AgendaException       Se l'indice non è valido
	 */
	public void modificaData(int indice, String data) throws AgendaException, AppuntamentoException {
		checkIndice(indice);
		Appuntamento daModificare = agenda.get(indice);
		Appuntamento modificato = new Appuntamento(data, daModificare.getOrarioInizio().toString(),
				daModificare.getDurata(), daModificare.getNomePersona(), daModificare.getLuogo());
		rimuoviAppuntamento(indice);
		provaAggiunta(daModificare, modificato);
	}

	/**
	 * Modifica l'orario di un appuntamento, effettuando controlli sulla validità
	 * dei dati inseriti ed eventuali conflitti.
	 * 
	 * @param indice L'indice dell'appuntamento da modificare
	 * @param orario L'orario da sostituire
	 * @throws AppuntamentoException Sollevata se il formato di uno dei parametri
	 *                               non è corretto
	 * @throws AgendaException       Se l'indice non è valido
	 */
	public void modificaOrario(int indice, String orario) throws AgendaException, AppuntamentoException {
		checkIndice(indice);
		Appuntamento daModificare = agenda.get(indice);
		Appuntamento modificato = new Appuntamento(daModificare.getData().toString(), orario, daModificare.getDurata(),
				daModificare.getNomePersona(), daModificare.getLuogo());
		rimuoviAppuntamento(indice);
		provaAggiunta(daModificare, modificato);
	}

	/**
	 * Modifica la durata di un appuntamento, effettuando controlli sulla validità
	 * dei dati inseriti ed eventuali conflitti.
	 * 
	 * @param indice L'indice dell'appuntamento da modificare
	 * @param durata La durata da sostituire
	 * @throws AppuntamentoException Sollevata se il formato di uno dei parametri
	 *                               non è corretto
	 * @throws AgendaException       Se l'indice non è valido
	 */
	public void modificaDurata(int indice, int durata) throws AgendaException, AppuntamentoException {
		checkIndice(indice);
		Appuntamento daModificare = agenda.get(indice);
		Appuntamento modificato = new Appuntamento(daModificare.getData().toString(),
				daModificare.getOrarioInizio().toString(), durata, daModificare.getNomePersona(),
				daModificare.getLuogo());
		rimuoviAppuntamento(indice);
		provaAggiunta(daModificare, modificato);
	}

	/**
	 * Modifica il nome della persona di un appuntamento, effettuando controlli
	 * sulla validità dei dati inseriti ed eventuali conflitti.
	 * 
	 * @param indice L'indice dell'appuntamento da modificare
	 * @param nome La persona da sostituire
	 * @throws AppuntamentoException Sollevata se il formato di uno dei parametri
	 *                               non è corretto
	 * @throws AgendaException       Se l'indice non è valido
	 */
	public void modificaNomePersona(int indice, String nome) throws AgendaException, AppuntamentoException {
		checkIndice(indice);
		Appuntamento daModificare = agenda.get(indice);
		Appuntamento modificato = new Appuntamento(daModificare.getData().toString(),
				daModificare.getOrarioInizio().toString(), daModificare.getDurata(), nome, daModificare.getLuogo());
		rimuoviAppuntamento(indice);
		provaAggiunta(daModificare, modificato);
	}

	/**
	 * Modifica il luogo di un appuntamento, effettuando controlli sulla validità
	 * dei dati inseriti ed eventuali conflitti.
	 * 
	 * @param indice L'indice dell'appuntamento da modificare
	 * @param luogo Il luogo da sostituire
	 * @throws AppuntamentoException Sollevata se il formato di uno dei parametri
	 *                               non è corretto
	 * @throws AgendaException       Se l'indice non è valido
	 */
	public void modificaLuogo(int indice, String luogo) throws AppuntamentoException, AgendaException {
		checkIndice(indice);
		Appuntamento daModificare = agenda.get(indice);
		Appuntamento modificato = new Appuntamento(daModificare.getData().toString(),
				daModificare.getOrarioInizio().toString(), daModificare.getDurata(), daModificare.getNomePersona(),
				luogo);
		agenda.remove(indice);
		provaAggiunta(daModificare, modificato);
	}

	private void provaAggiunta(Appuntamento daModificare, Appuntamento modificato) throws AgendaException {
		try {
			aggiungiAppuntamento(modificato);
		} catch (AgendaException e) {
			agenda.add(daModificare);
			agenda.sort((appuntamento1, appuntamento2) -> appuntamento1.compareTo(appuntamento2));
			throw new AgendaException(e.getMessage());
		}
	}

	private void checkIndice(int indice) throws AgendaException {
		if ((indice >= agenda.size()) || indice < 0)
			throw new AgendaException("Appuntamento non presente nell'agenda.");
	}

	/**
	 * Rimuove un appuntameto tramite indice per evitare di rimuovere durante
	 * iterazione.
	 * 
	 * @param indice L'indice dell'appuntamento da rimuovere
	 * @throws AgendaException Se l'indice non è valido
	 */
	public void rimuoviAppuntamento(int indice) throws AgendaException {
		checkIndice(indice);
		agenda.remove(indice);
	}

	/**
	 * Stampa nome dell'agenda, data e ora di creazione e utente che l'ha creata.
	 * 
	 * @return La stringa rappresentante l'agenda
	 */
	@Override
	public String toString() {
		return nomeAgenda + ", creata il " + DateTimeFormatter.ISO_LOCAL_DATE.format(dataCreazioneAgenda) + " alle "
				+ DateTimeFormatter.ISO_LOCAL_TIME.format(oraCreazioneAgenda) + " da " + nomeUtente;
	}

	/**
	 * Trova appuntamenti comparandoli per data o nome della persona e li inserisce
	 * in una ArrayList.
	 * 
	 * @param dato La data o nome per cui cercare
	 * @return La lista di appuntamenti trovati
	 * @throws AppuntamentoException Se la data non è valida
	 */
	public ArrayList<Appuntamento> trovaPerDataNome(String dato) throws AppuntamentoException {
		ArrayList<Appuntamento> trovati = new ArrayList<>();
		if (dato.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			try {
				LocalDate.parse(dato);
			} catch (DateTimeParseException e) {
				throw new AppuntamentoException("La data deve essere valida e in formato 'YYYY-MM-DD'.");
			}
			agenda.stream().forEach(a -> {
				if (a.getData().equals(LocalDate.parse(dato))) {
					trovati.add(a);
					trovati.sort((appuntamento1, appuntamento2) -> appuntamento1.compareTo(appuntamento2));
				}
			});
		} else {
			agenda.stream().forEach(a -> {
				if (a.getNomePersona() == dato) {
					trovati.add(a);
					trovati.sort((appuntamento1, appuntamento2) -> appuntamento1.compareTo(appuntamento2));
				}
			});
		}
		return trovati;
	}

	/**
	 * Stampa gli appuntamenti trovati dal metodo "trovaPerDataNome".
	 * 
	 * @param trovati L'ArrayList di appuntamenti trovati
	 */
	public void stampaTrovati(ArrayList<Appuntamento> trovati) {
		if (!trovati.isEmpty()) {
			trovati.stream().forEach(a -> {
				System.out.println(a.toString());
			});
		} else {
			System.out.println("Non è stato trovato alcun appuntamento.");
		}
	}

	/**
	 * Elimina gli appuntamenti trovati dal metodo "trovaPerDataNome".
	 * 
	 * @param trovati L'ArrayList di appuntamenti trovati
	 */
	public void eliminaTrovati(ArrayList<Appuntamento> trovati) {
		agenda.removeAll(trovati);
	}

	/**
	 * Stampa gli appuntamenti contenuti nell'agenda.
	 */
	public void stampaAppuntamenti() {
		agenda.stream().forEach(a -> {
			System.out.println(agenda.indexOf(a) + ". " + a.toString());
		});
	}

	/**
	 * Compara due oggetti di tipo agenda.
	 * 
	 * @return true Se gli oggetti sono uguali, false altrimenti
	 */
	@Override
	public boolean equals(Object agenda) {
		return (this.getNome().equals(((Agenda) agenda).getNome())
				|| this.getClass().equals(((Agenda) agenda).getClass()));
	}

	/**
	 * Scrive su file di testo il contenuto di un'agenda, ovvero la rappresentazione
	 * in formato stringa degli appuntamenti in esso contenuti.
	 * 
	 * @throws IOException Se avvengono errori durante la scrittura del file
	 */
	public void scriviFile() throws IOException {
		PrintWriter output = new PrintWriter(this.nomeAgenda + ".txt");
		agenda.stream().forEach(a -> {
			output.write(a.toFileString() + "\n");
		});
		output.close();
	}

	/**
	 * Legge da file degli appuntamenti se sono formattati in maniera corretta
	 * (ovvero come csv).
	 * 
	 * @param nomeFile Il nome del file da cui leggere
	 * @throws AgendaException       Se il file non esiste
	 * @throws AppuntamentoException Se i dati non sono in formato valido
	 */
	public void leggiFile(String nomeFile) throws AgendaException, AppuntamentoException {
		String stringa;
		try {
			BufferedReader input = new BufferedReader(new FileReader(nomeFile));
			while ((stringa = input.readLine()) != null) {
				String[] campi = stringa.split(",");
				if (campi.length > 5)
					throw new AppuntamentoException();
				aggiungiAppuntamento(
						new Appuntamento(campi[0], campi[1], Integer.parseInt(campi[2]), campi[3], campi[4]));
			}
			input.close();
		} catch (FileNotFoundException e) {
			throw new AgendaException("Il file specificato non esiste.");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
