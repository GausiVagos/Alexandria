package be.gauthier.alexandria.pojos;
import java.util.*;

import be.gauthier.alexandria.IPingable;
import be.gauthier.alexandria.dao.*;

public class Version implements IPingable
{
	private int game;
	private int console;
	private int tokenValue;
	private int realValue;
	
	//Références
	private Game gameObj=null;
	private Console consoleObj=null;
	
	private Set<Copy> listOfCopies = new HashSet<>();
	private Set<Reservation> listOfReservations = new HashSet<>();
	
	//Accesseurs
	public int getGame()
	{
		return game;
	}
	public int getConsole()
	{
		return console;
	}
	public int getTokenValue()
	{
		return tokenValue;
	}
	public int getRealValue()
	{
		return realValue;
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
	public void setGame(int g)
	{
		DAO<Game>dao=new GameDAO();
		game=g;
		gameObj=dao.find(Integer.toString(game));
	}
	public void setConsole(int c)
	{
		DAO<Console> dao=new ConsoleDAO();
		console=c;
		consoleObj=dao.find(Integer.toString(console));
	}
	public void setTokenValue(int t)
	{
		tokenValue=t;
	}
	public void setRealValue(int r)
	{
		realValue=r;
	}
	public void setGameObj(Game g)
	{
		gameObj=g;
	}
	public void setConsoleObj(Console c)
	{
		consoleObj=c;
	}
	
	public void addCopy(Copy c)
	{
		if(!listOfCopies.contains(c))
			listOfCopies.add(c);
	}
	public void addReservation(Reservation r)
	{
		if(!listOfReservations.contains(r))
			listOfReservations.add(r);
	}
	
	//Constructeur
	public Version() {}
	public Version(int g, int c, int t, int r)
	{
		game=g;
		console=c;
		tokenValue=t;
		realValue=r;
		//Les attributs références sont initialisés avec la fonction ping() de Ptolemy, pour éviter la récursivité des constructeurs.
	}
	@Override
	public void ping() 
	{
		DAO<Game> gdao=new GameDAO();
		DAO<Console> cdao=new ConsoleDAO();
		setGameObj(gdao.find(Integer.toString(game)));
		setConsoleObj(cdao.find(Integer.toString(console)));
		
	}
}
