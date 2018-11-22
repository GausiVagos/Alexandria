package be.gauthier.alexandria.dao;

import java.sql.*;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.pojos.*;

public class CompatibilityDAO extends DAO<Compatibility> {

	public CompatibilityDAO()
	{
		super();
	}
	
	@Override
	public boolean create(Compatibility toAdd) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String valid="select * from Compatibility where oldVersion="+toAdd.getOldVersion()+" and runsOn="+toAdd.getRunsOn()+";";
		String in="insert into Compatibility(oldVersion,runsOn) values("+toAdd.getOldVersion()+","+toAdd.getRunsOn()+");";
		ResultSet res=null;
		
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(valid);
			if(!res.next())
			{
				stmt.executeUpdate(in);
				hasWorked=true;
			}
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Insertion impossible");
		}
		finally
		{
			try
			{
				if(res!=null)
					res.close();
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
			}
		}
		return hasWorked;
	}

	@Override
	public boolean delete(Compatibility del) 
	{
		boolean hasWorked=false;
		Compatibility toRemove=find(del.getOldVersion()+"/"+del.getRunsOn());
		if(toRemove!=null)
		{
			Statement stmt=null;
			String delete="delete from Compatibility where oldVersion="+toRemove.getOldVersion()+" and runsOn="+toRemove.getRunsOn()+";";
			try
			{
				stmt=connect.createStatement();
				stmt.executeUpdate(delete);
				hasWorked=true;
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Suppression impossible");
			}
			finally
			{
				try
				{
					if(stmt!=null)
						stmt.close();
				}
				catch(SQLException e) {}
			}
		}
		return hasWorked;
	}

	@Override
	public boolean update(Compatibility modified) 
	{
		//On ne peut modifier les Compatibilités car les seuls champs qu'elles contiennent sont aussi les seuls à pouvoir les identifier dans la DB
		return false;
	}

	@Override
	public Compatibility find(String recherche) 
	{
		Statement stmt=null;
		Compatibility researched=null;
		String sql="";
		ResultSet res=null;
		try
		{
			String[] parts=recherche.split("/");
			int o=Integer.parseInt(parts[0]);
			int r=Integer.parseInt(parts[1]);
			sql="select * from Compatibility where oldVersion="+o+" and runsOn="+r;
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			if(res.next())
			{
				researched=new Compatibility(res.getInt(1),res.getInt(2));
			}
		}
		catch(Exception e){}
		finally
		{
			try
			{
				if(res!=null)
					res.close();
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
			}
		}
		return researched;
	}

}
