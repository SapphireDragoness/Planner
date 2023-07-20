package agenda;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eccezioni.AgendaException;
import eccezioni.AppuntamentoException;

class TestAgenda {

	@Test
	void testCostruttoreSenzaNome() {
		Agenda agenda1 = new Agenda();

		assertEquals(agenda1.getNome(), "Agenda 1");

		Agenda agenda2 = new Agenda();

		assertEquals(agenda2.getNome(), "Agenda 2");
	}
	
	@Test
	void testCostruttore() {
		Agenda agenda1 = new Agenda("Amici");

		assertEquals(agenda1.getNome(), "Amici");

		Agenda agenda2 = new Agenda("Colleghi");

		assertEquals(agenda2.getNome(), "Colleghi");

	}

	@Test
	void testAggiuntaRimozione() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");

		/* Lancia eccezione per appuntamenti in conflitto */
		AgendaException e1 = Assertions.assertThrows(AgendaException.class,
				() -> agenda1.aggiungiAppuntamento("2023-09-17", "12:30", 120, "Ann", "Cafè Leblanc"));
		assertEquals("Appuntamento in conflitto con uno già presente, impossibile completare operazione.",
				e1.getMessage());
		
		AgendaException e2 = Assertions.assertThrows(AgendaException.class,
				() -> agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park"));
		assertEquals("Appuntamento in conflitto con uno già presente, impossibile completare operazione.",
				e2.getMessage());
		assertEquals(agenda1.numeroAppuntamenti(), 1);
		/* Lancia eccezione per appuntamento inesistente */
		AgendaException e3 = Assertions.assertThrows(AgendaException.class,
				() -> agenda1.rimuoviAppuntamento(3));
		assertEquals("Appuntamento non presente nell'agenda.",
				e3.getMessage());
		
		agenda1.rimuoviAppuntamento(0);
		
		assertEquals(agenda1.numeroAppuntamenti(), 0);
	}

	@Test
	void testNumeroAppuntamenti() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");

		assertEquals(agenda1.numeroAppuntamenti(), 1);

		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");

		assertEquals(agenda1.numeroAppuntamenti(), 2);

		agenda1.rimuoviAppuntamento(1);
		agenda1.rimuoviAppuntamento(0);

		assertEquals(agenda1.numeroAppuntamenti(), 0);
	}

	@Test
	void testSvuota() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");
		agenda1.aggiungiAppuntamento("2023-08-17", "11:00", 60, "Futaba", "Akihabara");
		agenda1.svuotaAgenda();

		assertEquals(agenda1.numeroAppuntamenti(), 0);
	}

	@Test
	void testModificaData() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-08-17", "15:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");

		/* Lancia eccezione per appuntamento inesistente */
		AgendaException e1 = Assertions.assertThrows(AgendaException.class,
				() -> agenda1.modificaData(3, "2023-09-17"));
		assertEquals("Appuntamento non presente nell'agenda.", e1.getMessage());
		/* Lancia eccezione per dati in formato non valido */
		AppuntamentoException e2 = Assertions.assertThrows(AppuntamentoException.class,
				() -> agenda1.modificaData(0, "2023-13-17"));
		assertEquals("La data deve essere valida e in formato 'YYYY-MM-DD'.", e2.getMessage());
		/* Lancia eccezione per appuntamenti in conflitto */
		AgendaException e3 = Assertions.assertThrows(AgendaException.class,
				() -> agenda1.modificaData(0, "2023-09-17"));
		assertEquals("Appuntamento in conflitto con uno già presente, impossibile completare operazione.",
				e3.getMessage());

		/* Gli appuntamenti sono rimasti invariati */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-08-17, Orario: 15:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");

		agenda1.modificaData(0, "2023-09-16");
		/* L'appuntamento è stato modificato correttamente */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-16, Orario: 15:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");
	}

	@Test
	void testModificaOrario() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");

		/* Lancia eccezione per appuntamento inesistente */
		AgendaException e1 = Assertions.assertThrows(AgendaException.class, () -> agenda1.modificaOrario(3, "20:00"));
		assertEquals("Appuntamento non presente nell'agenda.", e1.getMessage());
		/* Lancia eccezione per dati in formato non valido */
		AppuntamentoException e2 = Assertions.assertThrows(AppuntamentoException.class,
				() -> agenda1.modificaOrario(0, "56:00"));
		assertEquals("L'orario deve essere valido e in formato 'hh:mm'.", e2.getMessage());
		/* Lancia eccezione per appuntamenti in conflitto */
		AgendaException e3 = Assertions.assertThrows(AgendaException.class, () -> agenda1.modificaOrario(0, "15:00"));
		assertEquals("Appuntamento in conflitto con uno già presente, impossibile completare operazione.",
				e3.getMessage());

		/* Gli appuntamenti sono rimasti invariati */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-17, Orario: 12:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");

		agenda1.modificaOrario(0, "09:00");
		/* L'appuntamento è stato modificato correttamente */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-17, Orario: 09:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");
	}

	@Test
	void testModificaDurata() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");

		/* Lancia eccezione per appuntamento inesistente */
		AgendaException e1 = Assertions.assertThrows(AgendaException.class, () -> agenda1.modificaDurata(3, 30));
		assertEquals("Appuntamento non presente nell'agenda.", e1.getMessage());
		/* Lancia eccezione per dati in formato non valido */
		AppuntamentoException e2 = Assertions.assertThrows(AppuntamentoException.class,
				() -> agenda1.modificaDurata(0, -10));
		assertEquals("La durata non può essere negativa o zero.", e2.getMessage());
		/* Lancia eccezione per appuntamenti in conflitto */
		AgendaException e3 = Assertions.assertThrows(AgendaException.class, () -> agenda1.modificaDurata(0, 300));
		assertEquals("Appuntamento in conflitto con uno già presente, impossibile completare operazione.",
				e3.getMessage());

		/* Gli appuntamenti sono rimasti invariati */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-17, Orario: 12:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");

		agenda1.modificaDurata(0, 120);
		/* L'appuntamento è stato modificato correttamente */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-17, Orario: 12:00, Durata: 120, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");
	}

	@Test
	void testModificaNomePersona() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");

		/* Lancia eccezione per appuntamento inesistente */
		AgendaException e1 = Assertions.assertThrows(AgendaException.class,
				() -> agenda1.modificaNomePersona(3, "Ann"));
		assertEquals("Appuntamento non presente nell'agenda.", e1.getMessage());
		/* Lancia eccezione per dati in formato non valido */
		AppuntamentoException e2 = Assertions.assertThrows(AppuntamentoException.class,
				() -> agenda1.modificaNomePersona(0, "Ann7"));
		assertEquals("Il nome non può contenere cifre.", e2.getMessage());

		/* Gli appuntamenti sono rimasti invariati */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-17, Orario: 12:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");

		agenda1.modificaNomePersona(0, "Ann");
		/* L'appuntamento è stato modificato correttamente */
		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-17, Orario: 12:00, Durata: 60, Persona: Ann, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");
	}
	
	@Test
	void testCercaElimina() throws AgendaException, AppuntamentoException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-10-17", "12:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-01-17", "12:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "13:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");
		agenda1.aggiungiAppuntamento("2023-01-17", "15:00", 60, "Ryuji", "Tsukishima");
		agenda1.aggiungiAppuntamento("2023-12-17", "15:00", 60, "Ryuji", "Tsukishima");
		
		assertEquals(agenda1.trovaPerDataNome("2023-09-17").toString(), "[Data: 2023-09-17, Orario: 13:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park, Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima]");
		assertEquals(agenda1.trovaPerDataNome("Ryuji").toString(), "[Data: 2023-01-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima, Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima, Data: 2023-12-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima]");
		
		/* Lancia eccezione per dato in formato non valido */ 
		AppuntamentoException e1 = Assertions.assertThrows(AppuntamentoException.class, () -> agenda1.trovaPerDataNome("2023-15-13"));
		assertEquals("La data deve essere valida e in formato 'YYYY-MM-DD'.", e1.getMessage());
		
		/* Test eliminazione */
		agenda1.eliminaTrovati(agenda1.trovaPerDataNome("Ryuji"));
		assertEquals(agenda1.numeroAppuntamenti(), 3);
		//agenda1.stampaAppuntamenti();
		agenda1.eliminaTrovati(agenda1.trovaPerDataNome("2023-09-17"));
		assertEquals(agenda1.numeroAppuntamenti(), 2);
		//agenda1.stampaAppuntamenti();
	}

	@Test
	void testScriviLeggiFile() throws AgendaException, AppuntamentoException, IOException {
		Agenda agenda1 = new Agenda("Amici");
		agenda1.aggiungiAppuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		agenda1.aggiungiAppuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");

		agenda1.scriviFile();
		agenda1.svuotaAgenda();
		agenda1.leggiFile("Amici.txt");

		assertEquals(agenda1.getAppuntamento(0).toString(),
				"Data: 2023-09-17, Orario: 12:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		assertEquals(agenda1.getAppuntamento(1).toString(),
				"Data: 2023-09-17, Orario: 15:00, Durata: 60, Persona: Ryuji, Luogo: Tsukishima");

		/* leggere un file contenente appuntamenti in conflitto solleva un'eccezione */
		AgendaException e1 = Assertions.assertThrows(AgendaException.class, () -> agenda1.leggiFile("Amici.txt"));
		assertEquals("Appuntamento in conflitto con uno già presente, impossibile completare operazione.", e1.getMessage());
		/* leggere un file inesistente solleva un'eccezione */
		AgendaException e2 = Assertions.assertThrows(AgendaException.class,
				() -> agenda1.leggiFile("File_Inesistente.txt"));
		assertEquals("Il file specificato non esiste.", e2.getMessage());
		/* leggere un file con dati in formato non corretto solleva un'eccezione */
		AppuntamentoException e3 = Assertions.assertThrows(AppuntamentoException.class,
				() -> agenda1.leggiFile("Formati_Non_Corretti.txt"));
		assertEquals("La data deve essere valida e in formato 'YYYY-MM-DD'.", e3.getMessage());
	}
}
