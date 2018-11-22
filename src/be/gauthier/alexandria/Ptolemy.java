package be.gauthier.alexandria;

import java.util.*;

import be.gauthier.alexandria.dao.*;
import be.gauthier.alexandria.pojos.*;

public class Ptolemy //Couche m�tier de l'application.
{
	//Fonctions li�es User
	
	public static boolean register(String un, String pa, int age, char rank)
	{
		DAO<User> dao = new UserDAO();
		User u=new User(un, pa, age, rank);
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
	
	public static void massivePing(IPingable[] list)
	{
		for(IPingable p : list)
		{
			p.ping();
		}
	}
}
