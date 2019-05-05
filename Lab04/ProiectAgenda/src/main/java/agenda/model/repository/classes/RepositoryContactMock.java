package agenda.model.repository.classes;

import agenda.exceptions.InvalidFormatException;
import agenda.model.base.Contact;
import agenda.model.repository.interfaces.RepositoryContact;
import agenda.model.repository.validator.ContactValidator;

import java.util.LinkedList;
import java.util.List;

/**
 * Clasa gestioneaza contactele mock pentru teste
 */
public class RepositoryContactMock implements RepositoryContact {


	private List<Contact> contacts;
	private ContactValidator contactValidator;
	
	public RepositoryContactMock() {
		contactValidator = new ContactValidator();
		contacts = new LinkedList<Contact>();
		try {
			Contact c = new Contact("Name Ana", "address1", "0711223344", "aaa@yahoo.com");
			contacts.add(c);
			c = new Contact("Name paul", "address 2", "0711223344", "bbb@yahoo.com");
			contacts.add(c);
			c = new Contact("Name Radu", "address 3", "0711223388", "ccc@yahoo.com");
			contacts.add(c);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Contact> getContacts() {
		return new LinkedList<Contact>(contacts);
	}

	@Override
	public void addContact(Contact contact) {
//		if(contactValidator.isValid(contact)){
			contacts.add(contact);
		//}
	}

	@Override
	public boolean removeContact(Contact contact) {
		int index = contacts.indexOf(contact);
		if (index < 0) return false;
		else contacts.remove(index);
		return true;
	}

	@Override
	public boolean saveContracts() {
		return true;
	}

	@Override
	public int count() {
		return contacts.size();
	}

	@Override
	public Contact getByName(String string) {
		for(Contact c : contacts)
			if (c.getName().equals(string)) return c;
		return null;
	}
	
}
