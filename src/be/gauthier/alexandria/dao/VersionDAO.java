package be.gauthier.alexandria.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.pojos.Copy;
import be.gauthier.alexandria.pojos.Reservation;
import be.gauthier.alexandria.pojos.Version;

public class VersionDAO extends DAO<Version> 
{
	public VersionDAO()
	{
		super();
	}
	
	@Override
	public boolean create(Version toAdd) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String valid="select * from Version where game="+toAdd.getGame()+" and console="+toAdd.getConsole()+";";
		String in="insert into Version(game,console,tokenValue,realValue) values("+toAdd.getGame()+","+toAdd.getConsole()+","+toAdd.getTokenValue()+","+toAdd.getRealValue()+");";
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
	public boolean delete(Version del) 
	{
		boolean hasWorked=false;
		Version toRemove=find(del.getGame()+"/"+del.getConsole());
		if(toRemove!=null)
		{
			Statement stmt=null;
			String delete="delete from Version where game="+toRemove.getGame()+" and console="+toRemove.getConsole()+";";
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
	public boolean update(Version modified) 
	{
		boolean hasWorked=false;
		Version toModify=find(modified.getGame()+"/"+modified.getConsole());
		if(toModify!=null)
		{
			Statement stmt=null;
			String modify="update Version set tokenValue="+modified.getTokenValue()+", realValue ="+modified.getRealValue()+" where game="+toModify.getGame()+" and console="+toModify.getConsole()+";";
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
	public Version find(String recherche) 
	{
		Statement stmt=null;
		Version researched=null;
		String sql="";
		ResultSet res=null;
		try
		{
			String[] parts=recherche.split("/");
			int g=Integer.parseInt(parts[0]);
			int c=Integer.parseInt(parts[1]);
			sql="select * from Version where game="+g+" and console="+c;
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			if(res.next())
			{
				researched=new Version(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4));
				
				//Listes
				sql="select * frop Copy where game="+g+" and console="+c;
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addCopy(new Copy(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getBoolean(5)));
				}
				
				sql="select * from Reservation where game="+g+" and console="+c;
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addReservation(new Reservation(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getString(5),res.getDate(6)));
				}
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
	
	public LinkedList<Version> findFromGame(String recherche)
	{
		Statement stmt=null;
		LinkedList<Version> researched=new LinkedList<Version>();
		String sql="";
		ResultSet res=null;
		try
		{
			int g=Integer.parseInt(recherche);
			sql="select * from Version where game="+g;
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			while(res.next())
			{
				researched.add(new Version(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4)));
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
	public LinkedList<Version> findFromConsole(String recherche)
	{
		Statement stmt=null;
		LinkedList<Version> researched=new LinkedList<Version>();
		String sql="";
		ResultSet res=null;
		try
		{
			int c=Integer.parseInt(recherche);
			sql="select * from Version where console="+c;
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			while(res.next())
			{
				researched.add(new Version(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4)));
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
