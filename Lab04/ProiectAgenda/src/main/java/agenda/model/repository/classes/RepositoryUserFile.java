package agenda.model.repository.classes;

import agenda.model.base.User;
import agenda.model.repository.interfaces.RepositoryUser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Clasa gestioneaza utilizatorii, precum realizeaza citirea din fisier, adaugarea de activitati, etc.
 */
public class RepositoryUserFile implements RepositoryUser{

	private List<User> users;
	private static final String filename = "files/users.txt";
	
	public RepositoryUserFile() throws Exception 
	{
		users = new LinkedList<User>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			int i = 0;
			while (( line = br.readLine())!= null)
			{
				User u = User.fromString(line);
				if (u == null) 
					throw new Exception("Error in file users at line "+i, new Throwable("Invalid Activity"));
				users.add(u);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (br!=null) br.close();
		}
	}
	

	public User getByUsername(String username) {
		for (User u : users)
			if (u.getUsername().equals(username)) return u;
		return null;
	}


	public User getByName(String name) {
		for (User u : users)
			if (u.getName().equals(name)) return u;
		return null;
	}


	public boolean changePasswd(User user, String oldPasswd, String newPasswd) {
		int index = users.indexOf(user);
		if (index < 0) return false;
		return users.get(index).setPassword(oldPasswd, newPasswd);
	}


	public boolean save() {
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(new FileOutputStream(filename));
			for(User u : users)
				pw.println(u.toString());
		}catch (Exception e)
		{
			return false;
		}
		finally{
			if (pw!=null) pw.close();
		}
		return true;
	}


	public List<User> getUsers() {
		return new LinkedList<User>(users);
	}


	public int getCount() {
		return users.size();
	}

}
