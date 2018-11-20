package be.gauthier.alexandria.pojos;

import be.gauthier.alexandria.dao.*;

public class Version 
{
	private int game;
	private int console;
	private int tokenValue;
	private int realValue;
	
	//Références
	private Game gameObj=null;
	private Console consoleObj=null;
	
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
		//DAO<Console> dao=new ConsoleDAO();
		console=c;
		//consoleObj=dao.find(Integer.toString(console));
	}
	public void setTokenValue(int t)
	{
		tokenValue=t;
	}
	public void setRealValue(int r)
	{
		realValue=r;
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
}
