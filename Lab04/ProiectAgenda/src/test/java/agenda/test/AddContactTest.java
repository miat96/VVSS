package agenda.test;

import agenda.exceptions.InvalidFormatException;
import agenda.model.base.Contact;
import agenda.model.repository.classes.RepositoryContactMock;
import agenda.model.repository.interfaces.RepositoryContact;
import com.sun.deploy.util.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


public class AddContactTest {

	private Contact con;
	private RepositoryContact rep;
	
	@Before
	public void setUp() throws Exception {
		rep = new RepositoryContactMock();
	}
	
	@Test
	public void testAdaugareContactReusita() //ECP
	{
		try {
			con = new Contact("Ana", "address1", "0711223344", "ana@yahoo.com");
		} catch (InvalidFormatException e) {
			assertTrue(false);
		}
		rep.addContact(con);
		for(Contact c : rep.getContacts())
			if (c.equals(con))
			{
				assertTrue(true);
				break;
			}
	}

    @Test
    public void testAdaugareContactNereusit() //ECP
    {
        try{
            rep.addContact((Contact) new Object());
        }
        catch(Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void testAdaugareContactReusitaLitereSiSpatiu() //ECP
    {
        try {
            con = new Contact("Popescu Ana", "address1", "0711223344", "ana@yahoo.com");
        } catch (InvalidFormatException e) {
            assertTrue(false);
        }
        rep.addContact(con);
        for(Contact c : rep.getContacts())
            if (c.equals(con))
            {
                assertTrue(true);
                break;
            }
    }

    @Test
    public void testAdaugareContactNumeCifre(){ //ECP
        try {
            con = new Contact("Ana4", "address1", "0711223344", "ana@yahoo.com");
            rep.addContact(con);
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAdaugareContactNumeEmpty(){ //ECP //BVA
        try {
            con = new Contact("", "address1", "0711223344", "ana@yahoo.com");
            rep.addContact(con);
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAdaugareContactTelefonEmpty(){ //ECP //BVA
        try {
            con = new Contact("Popescu", "address1", "", "ana@yahoo.com");
            rep.addContact(con);
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAdaugareContactTelefonLitere(){ //ECP
        try {
            con = new Contact("Popescu Ana", "address1", "071122334A", "ana@yahoo.com");
            rep.addContact(con);
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }


    @Test
    public void testAdaugareContactTelefonSpatiu(){ //ECP
        try {
            con = new Contact("Popescu Ana", "address1", "071122  44", "ana@yahoo.com");
            rep.addContact(con);
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }

	@Test
	public void testAdaugareContactLungimeTelefonInvalida(){  //ECP //BVA
		try {
			con = new Contact("Popescu Ana", "address1", "071122334455", "ana@yahoo.com");
		} catch (InvalidFormatException e) {
			assertTrue(true);
		}
	}

	
	@Test
	public void testAdaugareContactReusita2() //BVA
	{
		for(Contact c : rep.getContacts())
			rep.removeContact(c);

		String name = new String(new char[255]).replace("\0", "a");
		
		try {
			con = new Contact(name, "address1", "0711223344", "ana@yahoo.com");
			rep.addContact(con);
		} catch (InvalidFormatException e) {
			assertTrue(false);
		}
		int n  = rep.count();
		if (n == 1) {
			if (con.equals(rep.getContacts().get(0))) assertTrue(true);
			else assertTrue(false);
		}
		else assertTrue(false);
	}

    @Test
    public void testAdaugareContactReusita3() //BVA
    {
        for(Contact c : rep.getContacts())
            rep.removeContact(c);

        String name = new String(new char[254]).replace("\0", "a");

        try {
            con = new Contact(name, "address1", "0711223344", "ana@yahoo.com");
            rep.addContact(con);
        } catch (InvalidFormatException e) {
            assertTrue(false);
        }
        int n  = rep.count();
        if (n == 1) {
            if (con.equals(rep.getContacts().get(0))) assertTrue(true);
            else assertTrue(false);
        }
        else assertTrue(false);
    }

    @Test
    public void testAdaugareContactLungimeTelefonInvalida2(){  //ECP //BVA
        try {
            con = new Contact("Ana", "address1", "071122789", "ana@yahoo.com");
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAdaugareContactLungimeTelefonValida(){  //ECP //BVA
        try {
            con = new Contact("Ana", "address1", "0711223344", "ana@yahoo.com");
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAdaugareContactLungimeTelefonInvalida3(){  //ECP //BVA
        try {
            con = new Contact("Ana", "address1", "07112233445", "ana@yahoo.com");
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }
    }
	
}
