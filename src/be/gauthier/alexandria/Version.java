package be.gauthier.alexandria;

public class Version 
{
	private Game game;
	private Console console;
	private int tokenValue;
	private int realValue;
	
	//Clés étrangères
	private int gameId;
	private int consoleId;
	
	//Accesseurs
	public Game getGame()
	{
		return game;
	}
	public Console getConsole()
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
	
	public int getGameId()
	{
		return gameId;
	}
	public int getConsoleId()
	{
		return consoleId;
	}
	
	//Setteurs
	public void setGame(Game g)
	{
		if(game==null) //Seulement pour le remplissage d'un objet vide
		{
			game=g;
			gameId=g.getGameId();
		}
		//ATTENTION AUX DOUBLONS!
	}
	public void setConsole(Console c)
	{
		if(console==null)
		{
			console=c;
			consoleId=c.getConsoleId();
		}
		//ATTENTION AUX DOUBLONS!
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
	public Version(Game g, Console c, int t, int r)
	{
		game=g;
		gameId=g.getGameId();
		console=c;
		consoleId=c.getConsoleId();
		tokenValue=t;
		realValue=r;
	}
}
