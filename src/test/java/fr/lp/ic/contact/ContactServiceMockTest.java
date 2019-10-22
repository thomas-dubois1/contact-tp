package fr.lp.ic.contact;

import java.util.Optional;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fr.lp.ic.contact.dao.IContactDao;
import fr.lp.ic.contact.exception.ContactException;
import fr.lp.ic.contact.exception.ContactNotFoundException;
import fr.lp.ic.contact.model.Contact;

public class ContactServiceMockTest {

	@Rule
	public EasyMockRule rule = new EasyMockRule(this);

	@TestSubject
	ContactService contactService = new ContactService();

	@Mock
	IContactDao contactDao;
	
	@Test(expected = ContactException.class)
	public void shouldFailIfNameAlreadyExist() throws IllegalAccessException, ContactException {
		EasyMock.expect(contactDao.findByName("Thomas")).andReturn(Optional.of(new Contact()));

		//Fin de l'enregistrement
		EasyMock.replay(contactDao);

		contactService.newContact("Thomas", "0123456789", "mail@mail.mail");
	}
	
	@Test
	public void shouldInsertElement() throws IllegalAccessException, ContactException {
		String name = "Thomas";
		EasyMock.expect(contactDao.findByName(name)).andReturn(Optional.empty());
		Capture<Contact> capturedContact = EasyMock.newCapture();

		EasyMock.expect(contactDao.save(EasyMock.capture(capturedContact))).andReturn(true);

		//Fin de l'enregistrement
		EasyMock.replay(contactDao);

		contactService.newContact(name, "0123456789", "mail@mail.mail");
		Contact value = capturedContact.getValue();
		Assert.assertEquals(name, value.getName());
		Assert.assertEquals("phone error", "0123456789", value.getPhone());
		Assert.assertEquals("email error", "mail@mail.mail", value.getEmail());

	}

	@Test(expected = ContactNotFoundException.class)
	public void shouldFailDeletionIfNameDoesntExist() throws IllegalAccessException, ContactException, ContactNotFoundException {
		String name = "Thomas";
		EasyMock.expect(contactDao.findByName(name)).andReturn(Optional.empty());
		EasyMock.replay(contactDao);
		contactService.deleteContact(name);
	}

	@Test(expected = ContactNotFoundException.class)
	public void shouldFailUpdateIfNameDoesntExist() throws IllegalAccessException, ContactException, ContactNotFoundException {
		String name = "Thomas";
		EasyMock.expect(contactDao.findByName(name)).andReturn(Optional.empty());
		EasyMock.replay(contactDao);
		Contact contact = new Contact();
		contact.setName(name);
		contact.setEmail("mail@mail.mail");
		contact.setPhone("0123456789");
		contactService.updateContact(name, contact);
	}
}
