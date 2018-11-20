package be.gauthier.alexandria.pojos;
import java.util.*;

import be.gauthier.alexandria.dao.DAO;
import be.gauthier.alexandria.dao.UserDAO;

public class Copy 
{
	private int copyId;
	private int owner;
	private int game;
	private int console;
	private boolean available;
	
	private Set<Loan> listOfLoans=new HashSet<>();
	
	private User ownerObj;
	private Game gameObj;
	private Console consoleObj;
	
	//Accesseurs
	public int getCopyId()
	{
		return copyId;
	}
	public int getOwner()
	{
		return owner;
	}
	public int getGame()
	{
		return game;
	}
	public int getConsole()
	{
		return console;
	}
	public boolean getAvailability()
	{
		return available;
	}
	
	public Set<Loan> getListOfLoans()
	{
		return listOfLoans;
	}
	
	public User getOwnerObj()
	{
		return ownerObj;
	}
	
	//Setteurs
	
	public void setCopyId(int i)
	{
		if(copyId==0)
			copyId=i;
	}
	public void setOwner(int u)
	{
		DAO<User>dao=new UserDAO();
		owner=u;
	}
	public void setGame(int g)
	{
		game=g;
		
	}
	public void setConsole(int c)
	{
		console=c;
	}
	public void setAvailability(boolean b)
	{
		available=b;
	}
	
	public void addLoan(Loan l)
	{
		if(!listOfLoans.contains(l))
			listOfLoans.add(l);
	}
	public void removeLoan(Loan l)
	{
		if(listOfLoans.contains(l))
			listOfLoans.remove(l);
	}
	
	//Constructeurs
	public Copy() {}
	public Copy(int i, int o, int g, int c, boolean a)
	{
		copyId=i;
		owner=o;
		game=g;
		console=c;
		available=a;
	}
}
