package be.gauthier.alexandria.pojos;
import java.util.*;

public class Console 
{
	//Attributs
	private int consoleId;
	private String consoleName;
	private String company;
	private String shortName;
	
	//Liaisons
	private Set<Compatibility> oldVersion=new HashSet<>();
	private Set<Compatibility> runsOn=new HashSet<>();
	
	//Accesseurs
	public int getConsoleId()
	{
		return consoleId;
	}
	public String getConsoleName()
	{
		return consoleName;
	}
	public String getCompany()
	{
		return company;
	}
	public String getShortName()
	{
		return shortName;
	}
	
	public Set<Compatibility> getOldVersion()
	{
		return oldVersion;
	}
	public Set<Compatibility> getRunsOn()
	{
		return runsOn;
	}
	
	//Setteurs
	public void setConsoleId(int i)
	{
		if(consoleId==0)
			consoleId=i;
	}
	public void setConsoleName(String n)
	{
		consoleName=n;
	}
	public void setCompany(String c)
	{
		company=c;
	}
	public void setShortName(String s)
	{
		shortName=s;
	}
	
	public void addOldVersion(Compatibility c)
	{
		if(!oldVersion.contains(c))
			oldVersion.add(c);
	}
	public void removeOldVersion(Compatibility c)
	{
		if(!oldVersion.contains(c))
			oldVersion.remove(c);
	}
	public void addRunsOn(Compatibility c)
	{
		if(!runsOn.contains(c))
			runsOn.add(c);
	}
	public void removeRunsOn(Compatibility c)
	{
		if(!runsOn.contains(c))
			runsOn.remove(c);
	}
	
	//Constructeurs
		public Console() {}
		
		public Console(int i, String n, String c, String s)
		{
			consoleId=i;
			consoleName=n;
			company=c;
			shortName=s;
		}
}
