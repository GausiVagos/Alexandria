package be.gauthier.alexandria.pojos;
import java.util.*;

import be.gauthier.alexandria.IPingable;
import be.gauthier.alexandria.dao.*;

public class Copy implements IPingable
{
	private int copyId;
	private int owner;
	private int game;
	private int console;
	private boolean available;
	
	private Set<Loan> listOfLoans=new HashSet<>();
	
	private User ownerObj=null;
	private Game gameObj=null;
	private Console consoleObj=null;
	
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
	public Game getGameObj()
	{
		return gameObj;
	}
	public Console getConsoleObj()
	{
		return consoleObj;
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
		ownerObj=dao.find(Integer.toString(u));
	}
	public void setGame(int g)
	{
		DAO<Game> dao=new GameDAO();
		game=g;
		gameObj=dao.find(Integer.toString(g));
		
	}
	public void setConsole(int c)
	{
		DAO<Console> dao=new ConsoleDAO();
		console=c;
		consoleObj=dao.find(Integer.toString(c));
	}
	public void setAvailability(boolean b)
	{
		available=b;
	}
	public void setOwnerObj(User o)
	{
		ownerObj=o;
	}
	public void setGameObj(Game g)
	{
		gameObj=g;
	}
	public void setConsoleObj(Console c)
	{
		consoleObj=c;
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
	public Copy(int o, int g, int c, boolean a)//Nouveau
	{
		owner=o;
		game=g;
		console=c;
		available=a;
	}
	public Copy(int i, int o, int g, int c, boolean a)
	{
		this(o,g,c,a);
		copyId=i;
	}
	
	@Override
	public void ping() 
	{
		DAO<User> udao=new UserDAO();
		DAO<Game> gdao=new GameDAO();
		DAO<Console> cdao=new ConsoleDAO();
		setOwnerObj(udao.find(Integer.toString(owner)));
		setGameObj(gdao.find(Integer.toString(game)));
		setConsoleObj(cdao.find(Integer.toString(console)));
	}
}
