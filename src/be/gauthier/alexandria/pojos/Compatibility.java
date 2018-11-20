package be.gauthier.alexandria.pojos;

public class Compatibility 
{
	private Console oldVersion;
	private Console runsOn;
	
	//Les clés étrangères forment aussi la clé primaire concaténée,
	//on pourrait donc en avoir besoin pour identifier un enregistrement
	private int oldVersionId;
	private int runsOnId;
	
	//Accesseurs
	public Console getOldVersion()
	{
		return oldVersion;
	}
	public Console getRunsOn()
	{
		return runsOn;
	}
	public int getOldVersionId()
	{
		return oldVersionId;
	}
	public int getRunsOnId()
	{
		return runsOnId;
	}
	
	//Setteurs
	public void setOldVersion(Console c)
	{
		oldVersion=c;
		oldVersionId=c.getConsoleId();
	}
	public void setRunsOn(Console c)
	{
		runsOn=c;
		runsOnId=c.getConsoleId();
	}
	
	//Constructeurs
	public Compatibility() {}
	
	public Compatibility(Console o, Console r)
	{
		oldVersion=o;
		runsOn=r;
		oldVersionId=o.getConsoleId();
		runsOnId=r.getConsoleId();
	}
}
