package agenda.model.repository.classes;

import agenda.exceptions.InvalidFormatException;
import agenda.model.base.Contact;
import agenda.model.repository.interfaces.RepositoryContact;
import agenda.model.repository.validator.ContactValidator;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Clasa gestioneaza contactele, precum realizeaza citirea din fisier, adaugarea de activitati, etc.
 */
public class RepositoryContactFile implements RepositoryContact {

	private static final String filename = "files/contacts.txt";
	private List<Contact> contacts;
	private ContactValidator contactValidator;

	public RepositoryContactFile() throws Exception {
		contacts = new LinkedList<Contact>();
		contactValidator = new ContactValidator();

		BufferedReader br = null;
//		String currentDir = new File(".").getAbsolutePath();
//		System.out.println(currentDir);
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				Contact c = null;
				try {
					c = Contact.fromString(line, "#");
				} catch (InvalidFormatException e) {
					throw new Exception("Error in file contacts at line " + i,
							new Throwable(e.getCause().getMessage()));
				}
				contacts.add(c);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			if (br != null) br.close();
		}
	}


	public List<Contact> getContacts() {
		return new LinkedList<Contact>(contacts);
	}


	public void addContact(Contact contact) {
//		if(contactValidator.isValid(contact)){
			contacts.add(contact);
		//}
	}


	public boolean removeContact(Contact contact) {
		int index = contacts.indexOf(contact);
		if (index < 0)
			return false;
		else
			contacts.remove(index);
		return true;
	}


	public boolean saveContracts() {
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(filename));
			for(Contact c : contacts)
				pw.println(c.toString());
		}catch (Exception e)
		{
			return false;
		}
		finally{
			if (pw!=null) pw.close();
		}
		return true;
	}


	public int count() {
		return contacts.size();
	}


	public Contact getByName(String string) {
		for (Contact c : contacts)
			if (c.getName().equals(string))
				return c;
		return null;
	}

}
