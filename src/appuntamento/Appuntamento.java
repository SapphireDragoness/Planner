package appuntamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import eccezioni.AppuntamentoException;

/**
 * Classe rappresentante un appuntamento, implementa Comparable per comparare gli oggetti.
 * 
 * @author Linda Monfermoso, 20028464
 *
 */
public class Appuntamento implements Comparable<Appuntamento> {

	private final LocalDate dataFormattata;
	private final LocalTime orarioInizio;
	private final LocalTime orarioFine;
	private final int durataAppuntamento;
	private final String nomePersona;
	private final String luogoAppuntamento;

	/**
	 * Costruisce un oggetto di tipo appuntamento prendendo i dati data, orario,
	 * durata, nome e luogo in input.
	 * 
	 * @param data   La data dell'appuntamento
	 * @param orario L'orario dell'appuntamento
	 * @param durata La durata dell'appuntamento
	 * @param nome   Il nome della persona dell'appuntamento
	 * @param luogo  Il luogo dell'appuntamento
	 * @throws AppuntamentoException Sollevata se il formato di uno dei parametri
	 *                               non è corretto
	 */
	public Appuntamento(String data, String orario, int durata, String nome, String luogo)
			throws AppuntamentoException {
		checkFormato(data, orario, durata, nome, luogo);
		this.durataAppuntamento = durata;
		this.dataFormattata = LocalDate.parse(data);
		this.orarioInizio = LocalTime.parse(orario);
		this.orarioFine = orarioInizio.plusMinutes(durata);
		this.nomePersona = nome;
		this.luogoAppuntamento = luogo;
	}

	private void checkFormato(String data, String orario, int durata, String nome, String luogo)
			throws AppuntamentoException {
		try {
			LocalDate.parse(data);
		} catch (DateTimeParseException e) {
			throw new AppuntamentoException("La data deve essere valida e in formato 'YYYY-MM-DD'.");
		}
		try {
			LocalTime.parse(orario);
		} catch (DateTimeParseException e) {
			throw new AppuntamentoException("L'orario deve essere valido e in formato 'hh:mm'.");
		}
		if (durata <= 0)
			throw new AppuntamentoException("La durata non può essere negativa o zero.");
		if (nome.matches(".*\\d.*"))
			throw new AppuntamentoException("Il nome non può contenere cifre.");
		// il luogo può contere cifre
	}

	/**
	 * Getter per la data.
	 * 
	 * @return La data dell'appuntamento
	 */
	public LocalDate getData() {
		return dataFormattata;
	}

	/**
	 * Getter per l'orario d'inizio.
	 * 
	 * @return L'orario d'inizio dell'appuntamento
	 */
	public LocalTime getOrarioInizio() {
		return orarioInizio;
	}

	/**
	 * Getter per la durata.
	 * 
	 * @return La durata dell'appuntamento
	 */
	public int getDurata() {
		return durataAppuntamento;
	}

	/**
	 * Getter per la'orario di fine.
	 * 
	 * @return L'orario di fine dell'appuntamento
	 */
	public LocalTime getOrarioFine() {
		return orarioFine;
	}

	/**
	 * Getter per il nome della persona.
	 * 
	 * @return Il nome della persona dell'appuntamento
	 */
	public String getNomePersona() {
		return nomePersona;
	}

	/**
	 * Getter per il luogo.
	 * 
	 * @return Il luogo dell'appuntamento
	 */
	public String getLuogo() {
		return luogoAppuntamento;
	}

	/**
	 * Prende in input un oggetto di tipo appuntamento e lo compara con un altro,
	 * individuando se ci sono conflitti a livello di orario.
	 * 
	 * @param appuntamento L'appuntamento da comparare
	 * @return False se non ci sono conflitti, True altrimenti
	 */
	public boolean checkConflittiOrari(Appuntamento appuntamento) {
		return appuntamento.equals(this) || (appuntamento.compareTo(this) == 0)
				|| (appuntamento.getOrarioInizio().isAfter(this.orarioInizio)
						&& appuntamento.getOrarioInizio().isBefore(this.orarioFine)
						|| appuntamento.getOrarioFine().isAfter(this.orarioInizio)
								&& appuntamento.getOrarioFine().isBefore(this.orarioFine)
						|| appuntamento.getOrarioInizio().isBefore(this.orarioInizio)
								&& appuntamento.getOrarioFine().isAfter(this.orarioFine));
	}
	
	/**
	 * Ritorna una stringa rappresentante i dati dell'appuntamento.
	 * 
	 * @return La stringa formattata
	 */
	@Override
	public String toString() {
		return "Data: " + dataFormattata + ", Orario: " + orarioInizio + ", Durata: " + durataAppuntamento
				+ ", Persona: " + nomePersona + ", Luogo: " + luogoAppuntamento;
	}

	/**
	 * Ritorna una stringa, rappresentante i dati dell'appuntamento, formattata per
	 * essere scritta su file.
	 * 
	 * @return La stringa formattata
	 */
	public String toFileString() {
		return dataFormattata + "," + orarioInizio + "," + durataAppuntamento + "," + nomePersona + ","
				+ luogoAppuntamento;
	}

	/**
	 * Compara due oggetti di tipo appuntamento.
	 * 
	 * @return true Se gli oggetti sono uguali, false altrimenti
	 */
	@Override
	public boolean equals(Object appuntamento) {
		return ((appuntamento.getClass() == getClass())
				&& (((Appuntamento) appuntamento).getData().equals(this.dataFormattata)))
				&& (((Appuntamento) appuntamento).getOrarioInizio().equals(this.orarioInizio))
				&& (((Appuntamento) appuntamento).getOrarioFine().equals(this.orarioFine))
				&& (((Appuntamento) appuntamento).getNomePersona().equals(this.nomePersona))
				&& (((Appuntamento) appuntamento).getLuogo().equals(this.luogoAppuntamento));
	}

	/**
	 * Compara due appuntamenti per data.
	 * 
	 * @param appuntamento L'appuntamento da comparare a quest'oggetto
	 * 
	 * @return 0 se i due appuntamenti coincidono temporalmente, 1 se l'oggetto
	 *         appuntamento è prima dell'appuntamento in argomento, -1 se l'oggetto
	 *         appuntamento è dopo l'appuntamento in argomento
	 */
	@Override
	public int compareTo(Appuntamento appuntamento) {
		return this.getOrarioFine().atDate(this.getData())
				.compareTo(appuntamento.getOrarioFine().atDate(appuntamento.getData()));
	}

}
