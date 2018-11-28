package be.gauthier.alexandria;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import be.gauthier.alexandria.dao.DAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.Console;
import be.gauthier.alexandria.pojos.Copy;
import be.gauthier.alexandria.pojos.Game;
import be.gauthier.alexandria.pojos.Loan;
import be.gauthier.alexandria.pojos.Reservation;
import be.gauthier.alexandria.pojos.User;
import be.gauthier.alexandria.pojos.Version;

public class Ptolemy //Couche métier de l'application.
{
	//Fonctions liées User
	
	public static User register(String un, String pa, int age, char rank)
	{
		DAO<User> dao = new UserDAO();
		User u=new User(un, pa, age, rank);
		if(dao.create(u))
			return dao.find(u.getUserName());//On retourne la version complète du User
		else
			return null;
	}
	
	public static User login(String log, String pass)
	{
		DAO<User> dao=new UserDAO();
		User u=dao.find(log);
		if(u!=null && u.getPassword().equals(pass))
		{
			return u;
		}
		else 
		{
			return null;
		}
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
			return "N'est pas mort ce qui à jamais dort";
		else
			return "42";
	}
	
	public static Set<Copy> getAvailableCopies(User u)
	{
		Set<Copy> list=new HashSet<>();
		if(!u.getListOfCopies().isEmpty())
		{
			for(Copy c : u.getListOfCopies())
			{
				if(c.getAvailability())
					list.add(c);
			}
		}
		
		return list;
	}
	
	public static Set<Loan> getPendingLends(User u)
	{
		Set<Loan> list=new HashSet<>();
		if(!u.getListOfLends().isEmpty())
		{
			for(Loan l : u.getListOfLends())
			{
				if(l.getPending())
					list.add(l);
			}
		}
		
		return list;
	}
	
	public static Set<Loan> getPendingBorrowings(User u)
	{
		Set<Loan> list=new HashSet<>();
		if(!u.getListOfBorrowings().isEmpty())
		{
			for(Loan l : u.getListOfBorrowings())
			{
				if(l.getPending())
					list.add(l);
			}
		}
		
		return list;
	}
	
	public static Set<Reservation> getWaitingReservations(User u)
	{
		Set<Reservation> list=new HashSet<>();
		if(!u.getListOfBorrowings().isEmpty())
		{
			for(Reservation r : u.getListOfReservations())
			{
				if(r.getReservationStatus().equals("En attente"))
					list.add(r);
			}
		}
		
		return list;
	}
	
	public static boolean addTokens(User u, int i)
	{
		DAO<User> dao=new UserDAO();
		u.setUserTokens(u.getUserTokens()+i);
		return dao.update(u);
	}
	
	public static LinkedList<Console> consolesFromVersions(LinkedList<Version> versions)
	{
		LinkedList<Console> lc=new LinkedList<Console>();
		for(Version v : versions)
		{
			v.ping();
			lc.add(v.getConsoleObj());
		}
		return lc;
	}
	
	public static LinkedList<Game> gamesFromVersions(LinkedList<Version> versions)
	{
		LinkedList<Game> lg=new LinkedList<Game>();
		for(Version v : versions)
		{
			v.ping();
			lg.add(v.getGameObj());
		}
		return lg;
	}
}
