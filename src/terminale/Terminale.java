package terminale;

import java.io.IOException;
import java.util.ArrayList;

import agenda.Agenda;
import agende.Agende;
import appuntamento.Appuntamento;
import eccezioni.AgendaException;
import eccezioni.AppuntamentoException;
import jbook.util.Input;

/**
 * 
 * @author Linda Monfermoso, 20028464
 *
 */

public class Terminale {

	public static void main(String[] args) {
		
		System.out.println("***Gestore agende***");
		
		while(true) {
			System.out.println("\nSelezionare l'operazione che si vuole svolgere:"
					+ "\n1. Visualizza agende create"
					+ "\n2. Creare nuova agenda"
					+ "\n3. Eliminare agenda"
					+ "\n4. Effettuare operazioni su agenda"
					+ "\n0. Uscire dal programma");
			
           	try {
				switch (Input.readInt("\nScelta: ")){
				    case 1:
				        visualizzaAgende();
				        break;
				    case 2:
				        creaAgenda();
				        break;
				    case 3:
				        eliminaAgenda();
				        break;
				    case 4:
				        operazioniAgenda();
				        break;
				    case 0:
				        return;
				    default:
				        System.out.println("Operazione non valida.");
				}
			} catch (Exception e) {
				System.out.println("Operazione non valida.");
			}
		}
	}

	/* Metodi privati */
	
	private static boolean checkPresenzaAgende() {
		if(Agende.numeroAgende() == 0) {
			System.out.println("Nessuna agenda presente.");
			return false;
		}
		return true;
	}


	private static void visualizzaAgende() {
		if(!checkPresenzaAgende()) return; 
		System.out.println("\nAgende presenti:");
		Agende.stampaAgende();
	}
	
	private static void creaAgenda() {
		String nomeAgenda = Input.readString("\nNome per la nuova agenda (invio per nome di default): ");
		try {
			Agende.aggiungiAgenda(nomeAgenda);
		} catch (AgendaException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void eliminaAgenda() {
		if(!checkPresenzaAgende()) return;
		visualizzaAgende();
		try {
			int indiceAgenda = Input.readInt("\nSelezionare indice dell'agenda da eliminare: ");
			Agende.rimuoviAgenda(indiceAgenda);
		} catch (Exception e) {
			System.out.println("Inserire un numero da 0 a " + (Agende.numeroAgende() - 1));;
		}
	}

	private static void operazioniAgenda() {
		if(!checkPresenzaAgende()) return;
		visualizzaAgende();
		Agenda selezione = null;
		try {
			int indiceAgenda = Input.readInt("\nSelezionare indice dell'agenda su cui si vuole operare: ");
			selezione = Agende.getAgenda(indiceAgenda);
		} catch (Exception e) {
			System.out.println("Inserire un numero da 0 a " + (Agende.numeroAgende() - 1));
		}
		while(true) {
			System.out.println("\nSelezionare l'operazione che si vuole svolgere:"
					+ "\n1. Visualizza apputamenti presenti"
					+ "\n2. Creare nuovo appuntamento"
					+ "\n3. Eliminare appuntamento"
					+ "\n4. Modificare appuntamento"
					+ "\n5. Cercare appuntamenti"
					+ "\n6. Scrivere appuntamenti su file"
					+ "\n7. Leggere appuntamenti da file"
					+ "\n0. Tornare al menù principale");
			
           	try {
				switch (Input.readInt("\nScelta: ")){
				    case 1:
				        visualizzaAppuntamenti(selezione);
				        break;
				    case 2:
				        creaAppuntamento(selezione);
				        break;
				    case 3:
				        eliminaAppuntamento(selezione);
				        break;
				    case 4:
				        modificaAppuntamento(selezione);
				        break;
				    case 5:
				    	cercaAppuntamento(selezione);
				        break;
				    case 6:
				    	scriviSuFile(selezione);
				        break;
				    case 7:
				        leggiDaFile(selezione);
				        break;
				    case 0:
				        return;
				    default:
				        System.out.println("Operazione non valida.");
				}
			} catch (Exception e) {
				System.out.println("Operazione non valida.");
			}
		}
	}

	private static boolean checkPresenzaAppuntamenti(Agenda agenda) {
		if(agenda.numeroAppuntamenti() == 0) {
			System.out.println("Nessun appuntamento presente.");
			return false;
		}
		return true;
	}

	private static void visualizzaAppuntamenti(Agenda agenda) {
		if(!checkPresenzaAppuntamenti(agenda)) return;
		System.out.println("Appuntamenti presenti: ");
		agenda.stampaAppuntamenti();
	}
	
	private static void creaAppuntamento(Agenda agenda) {
		String data = Input.readString("Inserire data (formato YYYY-MM-DD): ");
		String orario = Input.readString("Inserire orario (formato hh:mm): ");
		int durata = Input.readInt("Inserire durata (in minuti): ");
		String nome = Input.readString("Inserire persona (non sono ammessi numeri): ");
		String luogo = Input.readString("Inserire luogo: ");
		try {
			agenda.aggiungiAppuntamento(data, orario, durata, nome, luogo);
		} catch (AgendaException e) {
			System.out.println(e.getMessage());
		} catch (AppuntamentoException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void eliminaAppuntamento(Agenda agenda) {
		if(!checkPresenzaAppuntamenti(agenda)) return;
		visualizzaAppuntamenti(agenda);
		try {
			int indiceAppuntamento = Input.readInt("\nSelezionare indice dell'appuntamento da eliminare: ");
			agenda.rimuoviAppuntamento(indiceAppuntamento);
		} catch (Exception e) {
			System.out.println("Inserire un numero da 0 a " + (agenda.numeroAppuntamenti() - 1));;
		}
	}
	
	private static void modificaAppuntamento(Agenda agenda) {
		if(!checkPresenzaAppuntamenti(agenda)) return;
		visualizzaAppuntamenti(agenda);
		int indiceAppuntamento = 0;
		try {
			indiceAppuntamento = Input.readInt("\nSelezionare indice dell'appuntamento da modificare: ");
		} catch (Exception e) {
			System.out.println("Inserire un numero da 0 a " + (Agende.numeroAgende() - 1));
		}
		while(true) {
			System.out.println("\nSelezionare cosa si vuole modificare:"
					+ "\n1. Modificare data"
					+ "\n2. Modificare orario"
					+ "\n3. Modificare durata"
					+ "\n4. Modificare persona"
					+ "\n5. Modificare luogo"
					+ "\n0. Ritornare");
			
           	try {
				switch (Input.readInt("\nScelta: ")){
				    case 1:
				    	String data = Input.readString("Inserire nuova data: ");
				        agenda.modificaData(indiceAppuntamento, data);
				        break;
				    case 2:
				    	String orario = Input.readString("Inserire nuovo orario: ");
				        agenda.modificaOrario(indiceAppuntamento, orario);
				        break;
				    case 3:
				    	int durata = Input.readInt("Inserire nuova durata: ");
				        agenda.modificaDurata(indiceAppuntamento, durata);
				        break;
				    case 4:
				    	String nome = Input.readString("Inserire nuova persona: ");
				        agenda.modificaNomePersona(indiceAppuntamento, nome);
				        break;
				    case 5:
				    	String luogo = Input.readString("Inserire nuovo luogo: ");
				        agenda.modificaLuogo(indiceAppuntamento, luogo);
				        break;
				    case 0:
				        return;
				    default:
				        System.out.println("Operazione non valida.");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private static void cercaAppuntamento(Agenda agenda) {
		if(!checkPresenzaAppuntamenti(agenda)) return;
		String selezione = Input.readString("Inserire data o nome della persona che si vogliono cercare: ");
		ArrayList<Appuntamento> trovati = null;
		try {
			trovati = agenda.trovaPerDataNome(selezione);
		} catch (AppuntamentoException e) {
			System.out.println(e.getMessage());
		}
		agenda.stampaTrovati(trovati);
		String scelta = Input.readString("Eliminare gli appuntamenti trovati?(yes/no, default no)");
		if(scelta.equalsIgnoreCase("yes")) {
			agenda.eliminaTrovati(trovati);
		}
	}
	
	private static void scriviSuFile(Agenda agenda) {
		if(!checkPresenzaAppuntamenti(agenda)) return;
		try {
			agenda.scriviFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("File " + agenda.getNome() + ".txt scritto correttamente." );
	}
	
	private static void leggiDaFile(Agenda agenda) {
		String nomeFile = Input.readString("Inserire nome del file (compresa estensione): ");
		try {
			agenda.leggiFile(nomeFile);
		} catch (AgendaException e) {
			System.out.println(e.getMessage());
		} catch (AppuntamentoException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("File " + nomeFile + " letto correttamente." );
	}
}

