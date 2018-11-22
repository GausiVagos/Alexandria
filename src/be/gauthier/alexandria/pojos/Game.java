package be.gauthier.alexandria.pojos;
import java.util.*;

public class Game 
{
	private int gameId;
	private String gameTitle;
	private String publisher;
	private int releaseYear;
	
	private Set<Version> listOfVersions=new HashSet<>();
	
	//Accesseurs
	public int getGameId()
	{
		return gameId;
	}
	public String getGameTitle()
	{
		return gameTitle;
	}
	public String getPublisher()
	{
		return publisher;
	}
	public int getReleaseYear()
	{
		return releaseYear;
	}
	public Set<Version> getlistOfVersions()
	{
		return listOfVersions;
	}
	
	//Setteurs
	public void setGameId(int i)
	{
		if(gameId==0)
			gameId=i;
	}
	public void setGameTitle(String t)
	{
		gameTitle=t;
	}
	public void setPublisher(String p)
	{
		publisher=p;
	}
	public void setReleaseYear(int d)
	{
		releaseYear=d;
	}
	
	public void addVersion(Version v)
	{
		if(!listOfVersions.contains(v))
			listOfVersions.add(v);
	}
	public void removeVersion(Version v)
	{
		if(listOfVersions.contains(v))
			listOfVersions.remove(v);
	}
	
	//Constructeurs
	public Game() {}
	public Game(String t, String p, int y)//Nouveau
	{
		gameTitle=t;
		publisher=p;
		releaseYear=y;
	}
	public Game(int i,String t, String p, int y)//DB
	{
		this(t,p,y);
		gameId=i;
	}
}
