package agenda.model.repository.classes;

import agenda.model.base.Activity;
import agenda.model.repository.interfaces.RepositoryActivity;
import agenda.model.repository.interfaces.RepositoryContact;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Clasa gestioneaza activitatiile, precum realizeaza citirea din fisier, adaugarea de activitati, etc.
 */
public class RepositoryActivityFile implements RepositoryActivity{

	private static final String filename = "files/activities.txt";
	private List<Activity> activities;
	
	public RepositoryActivityFile(RepositoryContact repcon) throws Exception
	{
		activities = new LinkedList<Activity>(); 
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			int i = 0;
			while (( line = br.readLine())!= null)
			{
				Activity act = Activity.fromString(line, repcon);
				if (act == null) 
					throw new Exception("Error in file activities at line "+i, new Throwable("Invalid Activity"));
				activities.add(act);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (br!=null) br.close();
		}
	}
	

	public List<Activity> getActivities() {
		return new LinkedList<Activity>(activities);
	}


	public boolean addActivity(Activity activity) {
		int  i = 0;      //1
		boolean conflicts = false;
		
		while( i < activities.size() )  //2
		{
			if ((activities.get(i).getStart().compareTo(activity.getDuration()) < 0) &&
                    (activity.getStart().compareTo(activities.get(i).getDuration()) < 0)) //3
				conflicts = true;  //4
			i++; //5
		}
		if ( !conflicts )  //6
		{
			activities.add(activity);  //7
			return true;
		}
		return false; //8
	}


	public boolean removeActivity(Activity activity) {
		int index = activities.indexOf(activity);
		if (index<0) return false;
		activities.remove(index);
		return true;
	}


	public boolean saveActivities() {
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(filename));
			for(Activity a : activities)
				pw.println(a.toString());
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
		return activities.size();
	}
	

	public List<Activity> activitiesByName(String name) {
		List<Activity> result1 = new LinkedList<Activity>();
		for (Activity a : activities)
			if (a.getName().equals(name) == false) result1.add(a);
		List<Activity> result = new LinkedList<Activity>();
		while (result1.size() > 0 )
		{
			Activity ac = result1.get(0);
			int index = 0;
			for (int i = 1; i<result1.size(); i++)
				if (ac.getStart().compareTo(result1.get(i).getStart())<0) 
				{
					index = i;
					ac = result1.get(i);
				}
			
			result.add(ac);
			result1.remove(index);
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public List<Activity> activitiesOnDate(String name, Date d) {
		List<Activity> result1 = new LinkedList<Activity>();
		for (Activity a : activities)
			if (a.getName().equals(name))
				if ((a.getStart().getYear() == d.getYear() &&
					a.getStart().getMonth() == d.getMonth() &&
					a.getStart().getDate() == d.getDate()) ||
					( a.getDuration().getYear() == d.getYear() &&
					a.getDuration().getMonth() == d.getMonth() &&
					a.getDuration().getDate() == d.getDate())) result1.add(a);
		List<Activity> result = new LinkedList<Activity>();
		while (result1.size() > 0 )
		{
			Activity ac = result1.get(0);
			int index = 0;
			for (int i = 1; i<result1.size(); i++)
				if (ac.getStart().compareTo(result1.get(i).getStart())>0)
				{
					index = i;
					ac = result1.get(i);
				}

			result.add(ac);
			result1.remove(index);
		}
		return result;

//        List<Activity> result = new LinkedList<Activity>();
//        for (Activity a : activities)
//            if (a.getName().equals(name))
//                if (a.getStart().compareTo(d) <= 0 && d.compareTo(a.getDuration()) <= 0 ) result.add(a);
//        return result;
	}
}
