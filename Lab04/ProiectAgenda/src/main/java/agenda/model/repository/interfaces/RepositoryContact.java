package agenda.model.repository.interfaces;

import agenda.model.base.Contact;

import java.util.List;

public interface RepositoryContact {

	List<Contact> getContacts();
	void addContact(Contact contact);
	boolean removeContact(Contact contact);
	boolean saveContracts();
	int count();
	Contact getByName(String string);
}
