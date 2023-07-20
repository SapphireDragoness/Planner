package appuntamento;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eccezioni.AppuntamentoException;

class TestAppuntamento {

	@Test
	void testCostruttoreFormati() {
		/* test formato date */
		AppuntamentoException e = Assertions.assertThrows(AppuntamentoException.class, () -> new Appuntamento("20-10-10", "12:00", 60, "Ann", "Cafè Leblanc"));
		assertEquals(e.getMessage(), "La data deve essere valida e in formato 'YYYY-MM-DD'.");
		e = Assertions.assertThrows(AppuntamentoException.class, () -> new Appuntamento("2023-15-10", "12:00", 60, "Ann", "Cafè Leblanc"));
		assertEquals(e.getMessage(), "La data deve essere valida e in formato 'YYYY-MM-DD'.");
		
		/* test formato orari */
		e = Assertions.assertThrows(AppuntamentoException.class, () -> new Appuntamento("2023-10-10", "25:00", 60, "Ann", "Cafè Leblanc"));
		assertEquals(e.getMessage(), "L'orario deve essere valido e in formato 'hh:mm'.");
		e = Assertions.assertThrows(AppuntamentoException.class, () -> new Appuntamento("2023-10-10", "21", 60, "Ann", "Cafè Leblanc"));
		assertEquals(e.getMessage(), "L'orario deve essere valido e in formato 'hh:mm'.");
		
		/* test durate */
		e = Assertions.assertThrows(AppuntamentoException.class, () -> new Appuntamento("2023-10-10", "12:00", -60, "Ann", "Cafè Leblanc"));
		assertEquals(e.getMessage(), "La durata non può essere negativa o zero.");
		e = Assertions.assertThrows(AppuntamentoException.class, () -> new Appuntamento("2023-10-10", "12:00", 0, "Ann", "Cafè Leblanc"));
		assertEquals(e.getMessage(), "La durata non può essere negativa o zero.");
		
		/* test formato nomi */
		e = Assertions.assertThrows(AppuntamentoException.class, () -> new Appuntamento("2023-10-10", "12:00", 60, "Ann7", "Cafè Leblanc"));
		assertEquals(e.getMessage(), "Il nome non può contenere cifre.");
	}
	
	@Test
	void testGetters() throws AppuntamentoException {
		Appuntamento appuntamento1 = new Appuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		assertEquals(appuntamento1.getData(), LocalDate.parse("2023-09-17"));
		assertEquals(appuntamento1.getOrarioInizio(), LocalTime.parse("12:00"));
		assertEquals(appuntamento1.getDurata(), 60);
		assertEquals(appuntamento1.getOrarioFine(), LocalTime.parse("13:00"));
		assertEquals(appuntamento1.getNomePersona(), "Makoto");
		assertEquals(appuntamento1.getLuogo(), "Inokashira Park");
		
		Appuntamento appuntamento2 = new Appuntamento("2023-10-10", "20:00", 60, "Ann", "Cafè Leblanc");
		assertEquals(appuntamento2.getData(), LocalDate.parse("2023-10-10"));
		assertEquals(appuntamento2.getOrarioInizio(), LocalTime.parse("20:00"));
		assertEquals(appuntamento2.getDurata(), 60);
		assertEquals(appuntamento2.getOrarioFine(), LocalTime.parse("21:00"));
		assertEquals(appuntamento2.getNomePersona(), "Ann");
		assertEquals(appuntamento2.getLuogo(), "Cafè Leblanc");
	}
	
	@Test 
	void testToString() throws AppuntamentoException {
		Appuntamento appuntamento1 = new Appuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		assertEquals(appuntamento1.toString(), "Data: 2023-09-17, Orario: 12:00, Durata: 60, Persona: Makoto, Luogo: Inokashira Park");
		
		Appuntamento appuntamento2 = new Appuntamento("2023-10-10", "20:00", 60, "Ann", "Cafè Leblanc");
		assertEquals(appuntamento2.toString(), "Data: 2023-10-10, Orario: 20:00, Durata: 60, Persona: Ann, Luogo: Cafè Leblanc");
	}
	
	@Test
	void testCheckConflittiOrari() throws AppuntamentoException {
		Appuntamento appuntamento1 = new Appuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		Appuntamento appuntamento2 = new Appuntamento("2023-09-17", "12:30", 120, "Ann", "Cafè Leblanc");
		Appuntamento appuntamento3 = new Appuntamento("2023-09-17", "14:00", 60, "Futaba", "Akihabara");
		Appuntamento appuntamento4 = new Appuntamento("2023-09-17", "15:00", 60, "Ryuji", "Tsukishima");
		
		assertTrue(appuntamento1.checkConflittiOrari(appuntamento2));
		assertFalse(appuntamento1.checkConflittiOrari(appuntamento3));
		assertTrue(appuntamento2.checkConflittiOrari(appuntamento3));
		assertFalse(appuntamento1.checkConflittiOrari(appuntamento4));
		assertFalse(appuntamento3.checkConflittiOrari(appuntamento4));
	}
	
	@Test
	void testEquals() throws AppuntamentoException {
		Appuntamento appuntamento1 = new Appuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		Appuntamento appuntamento2 = new Appuntamento("2023-09-17", "12:00", 60, "Ann", "Cafè Leblanc");
		Appuntamento appuntamento3 = new Appuntamento("2023-09-17", "12:00", 60, "Makoto", "Inokashira Park");
		
		assertTrue(appuntamento1.equals(appuntamento3));
		assertFalse(appuntamento1.equals(appuntamento2));
	}
	
	@Test
	void testCompareTo() throws AppuntamentoException {
		Appuntamento appuntamento1 = new Appuntamento("2023-09-17", "11:00", 60, "Makoto", "Inokashira Park");
		Appuntamento appuntamento2 = new Appuntamento("2023-09-17", "12:30", 20, "Ann", "Cafè Leblanc");
		Appuntamento appuntamento3 = new Appuntamento("2023-09-17", "11:00", 60, "Futaba", "Akihabara");
		Appuntamento appuntamento4 = new Appuntamento("2023-08-17", "11:00", 60, "Futaba", "Akihabara");
		
		assertEquals(appuntamento1.compareTo(appuntamento2), -1);
		assertEquals(appuntamento2.compareTo(appuntamento1), 1);
		assertEquals(appuntamento1.compareTo(appuntamento3), 0);
		assertEquals(appuntamento1.compareTo(appuntamento4), 1);
	}
}