package be.gauthier.alexandria;

import java.util.*;

import be.gauthier.alexandria.dao.*;
import be.gauthier.alexandria.pojos.*;

public class Ptolemy //Couche métier de l'application.
{
	//Fonctions liées User
	
	public static User register(String un, String pa, int age, char rank)
	{
		DAO<User> dao = new UserDAO();
		User u=new User(un, pa, age, rank);
		dao.create(u);
		return u;
	}
	
	public static User login(String log, String pass)
	{
		boolean valid=false;
		DAO<User> dao=new UserDAO();
		User u=dao.find(log);
		if(u!=null)
		{
			if(u.getPassword().equals(pass))
				valid=true;
		}
		return u;
	}
	
	public static void massivePing(IPingable[] list)
	{
		for(IPingable p : list)
		{
			p.ping();
		}
	}
	
	public static String getAnswer(char grade)
	{
		if(grade=='a')
			return "Sphinx";
		else
			return "42";
	}
}
