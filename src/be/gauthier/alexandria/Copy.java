package be.gauthier.alexandria;
import java.util.*;

public class Copy 
{
	private int copyId;
	private User owner;
	private Game game;
	private Console console;
	private boolean available;
	private Set<Loan> listOfLoans=new HashSet<>();
	
	private int ownerId;
	private int gameId;
	private int consoleId;
	
	//Accesseurs
	public int getCopyId()
	{
		return copyId;
	}
	public User getOwner()
	{
		return owner;
	}
	public Game getGame()
	{
		return game;
	}
	public Console getConsole()
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
	
	//Setteurs
	
	public void setCopyId(int i)
	{
		if(copyId==0)
			copyId=i;
	}
	public void setOwner(User u)
	{
		if(owner==null)
		{
			owner=u;
			ownerId=u.getUserId();
		}
	}
	public void setGame(Game g)
	{
		if(game==null)
		{
			game=g;
			gameId=g.getGameId();
		}
	}
	public void setConsole(Console c)
	{
		if(console==null)
		{
			console=c;
			consoleId=c.getConsoleId();
		}
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
}
