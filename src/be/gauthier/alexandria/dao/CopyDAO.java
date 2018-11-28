package be.gauthier.alexandria.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.pojos.*;

public class CopyDAO extends DAO<Copy> 
{
	public CopyDAO()
	{
		super();
	}
	
	@Override
	public boolean create(Copy toAdd) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String valid="select * from Copy where copyId="+toAdd.getCopyId()+";";
		String in="insert into Copy(owner,game,console,available) values("+toAdd.getOwner()+","+toAdd.getGame()+","+toAdd.getConsole()+",'"+toAdd.getAvailability()+"');";
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
	public boolean delete(Copy del) 
	{
		boolean hasWorked=false;
		Copy toRemove=find(Integer.toString(del.getCopyId()));
		if(toRemove!=null)
		{
			Statement stmt=null;
			String delete="delete from Copy where copyId="+toRemove.getCopyId()+";";
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
	public boolean update(Copy modified) 
	{
		boolean hasWorked=false;
		Copy toModify=find(Integer.toString(modified.getCopyId()));
		if(toModify!=null)
		{
			Statement stmt=null;
			String modify="update Copy set owner="+modified.getOwner()+", game="+modified.getGame()+", console="+modified.getConsole()+", available ='"+modified.getAvailability()+"' where copyId="+modified.getCopyId()+";";
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
	public Copy find(String recherche) 
	{
		Copy researched=null;

		Statement stmt=null;
		ResultSet res=null;
		String sql="";
		
		try
		{
			int index=Integer.parseInt(recherche);
			sql = "select * from Copy where copyId = "+index+";";
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			if(res.next())//Pour une raison inconnue, le first() provoque une exception là ou le next() marche parfaitement. Je ne cherche plus à comprendre.
			{
				researched=new Copy(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getBoolean(5));
				sql="select * from Loan where gameCopy = "+researched.getCopyId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addLoan(new Loan(res.getInt(1),res.getInt(2),res.getInt(3),res.getDate(4),res.getBoolean(5),res.getInt(6)));
				}
			}	
			
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Entrez un entier");
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
	
	public LinkedList<Copy> findAvailableCopies(Version v)
	{
		LinkedList<Copy> list=new LinkedList<Copy>();
		Statement stmt=null;
		ResultSet res=null;
		String sql="select * from Copy where game="+v.getGame()+" and console="+v.getConsole()+" and available='true';";
		
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			
			while(res.next())
			{
				list.add(new Copy(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getBoolean(5)));
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
		
		return list;
	}
	
	public Copy findLeastBorrowed(Version v)
	{
		Copy c=null;
		Statement stmt=null;
		ResultSet res=null;
		String sql1="select copyId from Copy where game="+v.getGame()+" and console="+v.getConsole()+" and available=true and copyId not in (select copyId from Copy inner join Loan on Copy.copyId=Loan.gameCopy)";
		String sql2="select copyId, count(*) from Copy inner join Loan on Copy.copyId=Loan.gameCopy where game="+v.getGame()+" and console="+v.getConsole()+" and available=true group by copyId order by count(*) ASC";
		
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql1);

			if(res.next())
			{
				c=find(Integer.toString(res.getInt(1)));
			}
			else
			{
				res=stmt.executeQuery(sql2);
				if(res.next())
				{
					c=find(Integer.toString(res.getInt(1)));
				}
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
		
		return c;
	}
}
