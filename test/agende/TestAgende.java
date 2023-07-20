package agende;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eccezioni.AgendaException;

class TestAgende {

	@BeforeEach
	void setup() {
		Agende.svuotaLista();
	}
	
	@Test
	void testAggiungiRimuoviAgenda() throws AgendaException {
		Agende.aggiungiAgenda("Amici");
		Agende.aggiungiAgenda("");
		Agende.aggiungiAgenda("Colleghi");
		
		/* Nome già usato */
		AgendaException e1 = Assertions.assertThrows(AgendaException.class, () -> Agende.aggiungiAgenda("Amici"));
		assertEquals("Esiste già un'agenda con questo nome.", e1.getMessage());
		
		assertEquals(Agende.getAgenda(0).getNome(), "Amici");
		assertEquals(Agende.getAgenda(1).getNome(), "Agenda 1");
		assertEquals(Agende.getAgenda(2).getNome(), "Colleghi");	
		
		/* Rimozione out of bounds */
		AgendaException e2 = Assertions.assertThrows(AgendaException.class, () -> Agende.rimuoviAgenda(3));
		assertEquals("L'agenda specificata non esiste.", e2.getMessage());
		
		Agende.rimuoviAgenda(1);
		
		assertEquals(Agende.getAgenda(0).getNome(), "Amici");
		assertEquals(Agende.getAgenda(1).getNome(), "Colleghi");
	}
	
	@Test
	void testGetAgenda() throws AgendaException {
		Agende.aggiungiAgenda("Amici");
		Agende.aggiungiAgenda("Colleghi");
		
		/* Get out of bounds */
		IndexOutOfBoundsException e1 = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Agende.getAgenda(3));
		assertEquals("Index 3 out of bounds for length 2", e1.getMessage());
		
		assertEquals(Agende.getAgenda(0).getNome(), "Amici");
		assertEquals(Agende.getAgenda(1).getNome(), "Colleghi");
	}

}
