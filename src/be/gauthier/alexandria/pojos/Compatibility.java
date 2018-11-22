package be.gauthier.alexandria.pojos;

import be.gauthier.alexandria.IPingable;
import be.gauthier.alexandria.dao.*;

public class Compatibility implements IPingable
{
	private int oldVersion;
	private int runsOn;
	
	private Console oldVersionObj=null;
	private Console runsOnObj=null;
	
	//Accesseurs
	public int getOldVersion()
	{
		return oldVersion;
	}
	public int getRunsOn()
	{
		return runsOn;
	}
	public Console getOldVersionObj()
	{
		return oldVersionObj;
	}
	public Console getRunsOnObj()
	{
		return runsOnObj;
	}
	
	//Setteurs
	public void setOldVersion(int c)
	{
		DAO<Console> dao=new ConsoleDAO();
		oldVersion=c;
		oldVersionObj=dao.find(Integer.toString(c));
	}
	public void setRunsOn(int c)
	{
		DAO<Console> dao=new ConsoleDAO();
		runsOn=c;
		runsOnObj=dao.find(Integer.toString(c));
	}
	public void setOldVersionObj(Console o)
	{
		oldVersionObj=o;
	}
	public void setRunsOnObj(Console r)
	{
		runsOnObj=r;
	}
	
	//Constructeurs
	public Compatibility() {}
	
	public Compatibility(int o, int r)
	{
		oldVersion=o;
		runsOn=r;
		//Références instanciées par Ptolemy.ping() ou les setteurs
	}
	@Override
	public void ping() 
	{
		DAO<Console> dao=new ConsoleDAO();
		setOldVersionObj(dao.find(Integer.toString(oldVersion)));
		setRunsOnObj(dao.find(Integer.toString(runsOn)));
	}
}
