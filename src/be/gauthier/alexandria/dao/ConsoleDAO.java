package be.gauthier.alexandria.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.pojos.Compatibility;
import be.gauthier.alexandria.pojos.Console;
import be.gauthier.alexandria.pojos.Version;

public class ConsoleDAO extends DAO<Console> {

	public ConsoleDAO()
	{
		super();
	}
	
	@Override
	public boolean create(Console toAdd) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String valid="select * from Console where consoleName='"+toAdd.getConsoleName()+"';";
		String in="insert into Console(consoleName,company,shortName) values('"+toAdd.getConsoleName()+"','"+toAdd.getCompany()+"','"+toAdd.getShortName()+"');";
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
	public boolean delete(Console del) 
	{
		boolean hasWorked=false;
		Console toRemove=find(del.getConsoleName());
		if(toRemove!=null)
		{
			Statement stmt=null;
			//La table Compatibility doit être vidée manuellement car le "on delete cascade" ne s'applique pas (2 clés étrangères de la même table)
			String washOldVersion="delete from Compatibility where oldVersion="+toRemove.getConsoleId()+";";
			String washRunsOn="delete from Compatibility where runsOn="+toRemove.getConsoleId()+";";
			String delete="delete from Console where consoleId="+toRemove.getConsoleId()+";";
			try
			{
				stmt=connect.createStatement();
				stmt.executeUpdate(washOldVersion);
				stmt.executeUpdate(washRunsOn);
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
	public boolean update(Console modified) 
	{
		boolean hasWorked=false;
		Console toModify=find(modified.getConsoleName());
		if(toModify!=null)
		{
			Statement stmt=null;
			String modify="update Console set company='"+modified.getCompany()+"', shortName='"+modified.getShortName()+"' where consoleId="+modified.getConsoleId()+";";
			try
			{
				stmt=connect.createStatement();
				stmt.executeUpdate(modify);
				hasWorked=true;
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Modification impossible");
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
	public Console find(String recherche) 
	{
		Console researched=null;
		//Premier cas : recherche par index
		Statement stmt=null;
		ResultSet res=null;
		String sql="";
		
		try
		{
			int index=Integer.parseInt(recherche);
			sql = "select * from Console where consoleId = "+index+";";
			
		}
		catch(NumberFormatException e)//Deuxième cas : recherche par userName
		{
			sql = "select * from Console where consoleName = '"+recherche+"';";
		}
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			 
			if(res.next())//Pour une raison inconnue, le first() provoque une exception là ou le next() marche parfaitement. Je ne cherche plus à comprendre.
			{
				researched=new Console(res.getInt(1), res.getString(2), res.getString(3), res.getString(4));
				//Ensuite, on remplit les listes
				
				sql="select * from Version where console="+researched.getConsoleId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addVersion(new Version(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4)));
				}
				
				sql="select * from Compatibility where oldVersion = "+researched.getConsoleId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addOldVersion(new Compatibility(res.getInt(1),res.getInt(2)));
				}
				
				sql="select * from Compatibility where runsOn = "+researched.getConsoleId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addRunsOn(new Compatibility(res.getInt(1),res.getInt(2)));
				}
				
				res.close();
				stmt.close();
			}
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "Erreur de requête");
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
		
		
		return researched;
	}
	public LinkedList<Console> getAll()
	{
		LinkedList<Console> researched=new LinkedList<Console>();
		//Premier cas : recherche par index
		Statement stmt=null;
		ResultSet res=null;
		String sql="select * from Console";
		
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			 
			while(res.next())//Pour une raison inconnue, le first() provoque une exception là ou le next() marche parfaitement. Je ne cherche plus à comprendre.
			{
				researched.add(new Console(res.getInt(1),res.getString(2),res.getString(3),res.getString(4)));
			}
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "Erreur de requête");
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
		
		
		return researched;
	}
}
