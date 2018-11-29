package be.gauthier.alexandria;

import be.gauthier.alexandria.pojos.Version;

public class CatalogRow 
{
	private Version version;
	private int quantity;
	
	public Version getVersion()
	{
		return version;
	}
	public int getQuantity()
	{
		return quantity;
	}
	
	public CatalogRow(Version v, int q)
	{
		version=v;
		quantity=q;
	}
}
