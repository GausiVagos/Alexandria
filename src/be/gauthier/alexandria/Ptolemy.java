package be.gauthier.alexandria;

import java.util.*;;

public class Ptolemy //Couche métier de l'application.
{
	//Fonctions liées User
	
	public static boolean register(String un, String pa, int age, char rank, Date d)
	{
		DAO dao = new UserDAO();
		User u=new User(un, pa, age, rank, d);
		return dao.create(u);
	}
	
	public static boolean login(String log, String pass)
	{
		boolean valid=false;
		DAO<User> dao=new UserDAO();
		User u=dao.find(log);
		if(u!=null)
		{
			if(u.getPassword().equals(pass))
				valid=true;
		}
		return valid;
	}
}
