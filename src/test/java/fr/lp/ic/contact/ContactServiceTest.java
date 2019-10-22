package fr.lp.ic.contact;

import org.junit.Test;

import fr.lp.ic.contact.exception.ContactException;


public class ContactServiceTest {

	private ContactService service = new ContactService();
	private static final String VALIDE_PHONE_NUMBER = "0123456789";
	private static final String VALIDE_EMAIL = "mail@mail.fr";

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailedIfNameLessThanThree() throws ContactException, IllegalAccessException {
		//Empty test 
		service.newContact("ab",VALIDE_PHONE_NUMBER, VALIDE_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailedIfNameGreaterThanForty() throws ContactException, IllegalAccessException {
		service.newContact("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",VALIDE_PHONE_NUMBER, VALIDE_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailedIfNameIsNull() throws ContactException, IllegalAccessException {
		service.newContact(null,VALIDE_PHONE_NUMBER, VALIDE_EMAIL);
	}
}
