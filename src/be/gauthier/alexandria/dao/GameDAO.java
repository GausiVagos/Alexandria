package be.gauthier.alexandria.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.pojos.*;

public class GameDAO extends DAO<Game> 
{
	public GameDAO()
	{
		super();
	}
	
	@Override
	public boolean create(Game toAdd) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String valid="select * from Game where gameTitle='"+toAdd.getGameTitle()+"';";
		String in="insert into Game(gameTitle,publisher,releaseYear) values('"+toAdd.getGameTitle()+"','"+toAdd.getPublisher()+"',"+toAdd.getReleaseYear()+");";
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
	public boolean delete(Game obj) 
	{
		
		return false;
	}

	@Override
	public boolean update(Game obj) 
	{
		
		return false;
	}

	@Override
	public Game find(String recherche) 
	{
		Game researched=null;
		//Premier cas : recherche par index
		Statement stmt=null;
		ResultSet res=null;
		String sql="";
		
		try
		{
			int index=Integer.parseInt(recherche);
			sql = "select * from Game where gameId = "+index+";";
			
		}
		catch(NumberFormatException e)//Deuxième cas : recherche par userName
		{
			sql = "select * from Game where gameTitle = '"+recherche+"';";
		}
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			 
			if(res.next())//Pour une raison inconnue, le first() provoque une exception là ou le next() marche parfaitement. Je ne cherche plus à comprendre.
			{
				researched=new Game(res.getInt(1),res.getString(2),res.getString(3),res.getInt(4));
				
				sql="select * from Version where game = "+researched.getGameId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addVersion(new Version(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4)));
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
		
		
		return researched;
	}

}
