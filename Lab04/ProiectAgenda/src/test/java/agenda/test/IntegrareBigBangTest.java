package agenda.test;

import agenda.exceptions.InvalidFormatException;
import agenda.model.base.Activity;
import agenda.model.base.Contact;
import agenda.model.repository.classes.RepositoryActivityFile;
import agenda.model.repository.classes.RepositoryContactFile;
import agenda.model.repository.interfaces.RepositoryActivity;
import agenda.model.repository.interfaces.RepositoryContact;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntegrareBigBangTest {

    private Contact con;
    private RepositoryContact repContact;

    private Activity act;
    private RepositoryActivity repActivity;

    @Before
    public void setUp() throws Exception {
        repContact = new RepositoryContactFile();
        repActivity = new RepositoryActivityFile(repContact);
    }

    @Test
    public void testAdaugareContactReusita() //ECP
    {
        try {
            con = new Contact("Ana", "address1", "0711223344", "ana@yahoo.com");
        } catch (InvalidFormatException e) {
            assertTrue(false);
        }
        repContact.addContact(con);
        for(Contact c : repContact.getContacts())
            if (c.equals(con))
            {
                assertTrue(true);
                break;
            }
    }

    @Test
    public void testAdaugareActivitateValid(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            for (Activity a : repActivity.getActivities())
                repActivity.removeActivity(a);

            act = new Activity("Ana", df.parse("09/08/2019 12:10"), df.parse("09/08/2019 15:30"), null, "ceva");
            repActivity.addActivity(act);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(1 == repActivity.count());
    }

    @Test
    public void testAfisActivityValid() {
        for (Activity act : repActivity.getActivities())
            repActivity.removeActivity(act);

        Calendar c = Calendar.getInstance();
        c.set(2013, 3, 20, 12, 00);
        Date start = c.getTime();

        c.set(2013, 3, 20, 12, 30);
        Date end = c.getTime();

        Activity act = new Activity("name1", start, end,
                new LinkedList<Contact>(), "description2");

        repActivity.addActivity(act);

        c.set(2013, 3, 20);

        List<Activity> result = repActivity.activitiesOnDate("name1", c.getTime());
        assertTrue(result.size() == 1);
    }

    @Test
    public void testIntegration1(){
        //P->A->B->C
        int n = repContact.count();
        Contact c = null;
        try {
            c = new Contact("name", "address1", "0711223344", "aaa@yahoo.com");
            repContact.addContact(c);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        assertTrue(n + 1 == repContact.count());

        n = repActivity.count();
        Activity act = null;
        Calendar calendar = Calendar.getInstance();
        try {
            List<Contact> contacts = new ArrayList<Contact>();
            contacts.add(c);

            calendar.set(2017, 3, 20, 12, 00);
            Date start = calendar.getTime();

            calendar.set(2017, 3, 20, 12, 30);
            Date end = calendar.getTime();
            act = new Activity("name", start, end, contacts, "Lunch break");
            repActivity.addActivity(act);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(repActivity.count() == n+1);


        calendar.set(2017, 03, 20);

        List<Activity> result = repActivity.activitiesOnDate("name", calendar.getTime());
        System.out.println(result.size());
        assertTrue(result.size() == 1);

    }

    @Test
    public void testIntegration2(){
        //P->A->B->C A-invalid
        int n = repContact.count();
        Contact c = null;
        try {
            c = new Contact("name1", "address1", "0711223344", "aaa@yahoo.com");
            repContact.addContact(c);
        } catch (InvalidFormatException e) {
            assertTrue(true);
        }

        assertTrue(n == repContact.count());

        n = repActivity.count();
        Activity act = null;
        Calendar calendar = Calendar.getInstance();
        try {
            List<Contact> contacts = new ArrayList<Contact>();
            contacts.add(c);

            calendar.set(2017, 3, 20, 12, 00);
            Date start = calendar.getTime();

            calendar.set(2017, 3, 20, 12, 30);
            Date end = calendar.getTime();
            act = new Activity("name", start, end, contacts, "Lunch break");
            repActivity.addActivity(act);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(repActivity.count() == n+1);


        calendar.set(2017, 03, 20);

        List<Activity> result = repActivity.activitiesOnDate("name", calendar.getTime());
        System.out.println(result.size());
        assertTrue(result.size() == 1);

    }

    @Test
    public void testIntegration3(){
        //P->A->B->C B-invalid
        int n = repContact.count();
        Contact c = null;
        try {
            c = new Contact("name", "address1", "0711223344", "aaa@yahoo.com");
            repContact.addContact(c);
        } catch (InvalidFormatException e) {
        }

        assertTrue(n+1 == repContact.count());

        n = repActivity.count();
        Activity act = null;
        Calendar calendar = Calendar.getInstance();
        try {
            List<Contact> contacts = new ArrayList<Contact>();
            contacts.add(c);

            calendar.set(2017, 3, 20, 12, 00);
            Date start = calendar.getTime();

            calendar.set(2017, 3, 20, 14, 30);
            Date end = calendar.getTime();
            act = new Activity("name", start, end, contacts, "Lunch break");
            repActivity.addActivity(act);
            assertFalse(repActivity.addActivity(act));

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(repActivity.count() == n+1);


        calendar.set(2017, 03, 20);

        List<Activity> result = repActivity.activitiesOnDate("name", calendar.getTime());
        System.out.println(result.size());
        assertTrue(result.size() == 1);

    }

    @Test
    public void testIntegration4(){
        //P->A->B->C C-invalid
        int n = repContact.count();
        Contact c = null;
        try {
            c = new Contact("name", "address1", "0711223344", "aaa@yahoo.com");
            repContact.addContact(c);
        } catch (InvalidFormatException e) {
        }

        assertTrue(n+1 == repContact.count());

        n = repActivity.count();
        Activity act = null;
        Calendar calendar = Calendar.getInstance();
        try {
            List<Contact> contacts = new ArrayList<Contact>();
            contacts.add(c);

            calendar.set(2017, 3, 20, 12, 00);
            Date start = calendar.getTime();

            calendar.set(2017, 3, 20, 14, 30);
            Date end = calendar.getTime();
            act = new Activity("name", start, end, contacts, "Lunch break");
            repActivity.addActivity(act);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(repActivity.count() == n+1);


        calendar.set(2010, 03, 20);

        List<Activity> result = repActivity.activitiesOnDate("name", calendar.getTime());
        assertTrue(result.size() == 0);

    }



}
