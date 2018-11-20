package be.gauthier.alexandria;

import java.util.*;

import be.gauthier.alexandria.dao.DAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.Loan;
import be.gauthier.alexandria.pojos.User;;

public class Ptolemy //Couche métier de l'application.
{
	//Fonctions liées User
	
	public static boolean register(String un, String pa, int age, char rank)
	{
		DAO dao = new UserDAO();
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
	
	public static void ping(Loan l)
	{
		DAO<User> udao=new UserDAO();
		l.setLenderObj(udao.find(Integer.toString(l.getLender())));
		l.setBorrowerObj(udao.find(Integer.toString(l.getBorrower())));
		//l.setGameCopyObj();
	}
}
