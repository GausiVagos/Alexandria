package be.gauthier.alexandria.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.pojos.Reservation;
import be.gauthier.alexandria.pojos.Version;

public class ReservationDAO extends DAO<Reservation> 
{
	public ReservationDAO()
	{
		super();
	}
	
	@Override
	public boolean create(Reservation toAdd) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String in="insert into Reservation (applicant,game,console,reservationStatus) values ("+toAdd.getApplicant()+","+toAdd.getGame()+","+toAdd.getConsole()+",'"+toAdd.getReservationStatus()+"');";
		try
		{
			stmt=connect.createStatement();
			stmt.executeUpdate(in);
			hasWorked=true;
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Insertion impossible");
		}
		finally
		{
			try
			{
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
	public boolean delete(Reservation del) 
	{
		boolean hasWorked=false;
		Reservation toRemove=find(Integer.toString(del.getReservationId()));
		if(toRemove!=null)
		{
			Statement stmt=null;
			String delete="delete from Reservation where reservationId="+toRemove.getReservationId()+";";
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
	public boolean update(Reservation modified) 
	{
		boolean hasWorked=false;
		Reservation toModify=find(Integer.toString(modified.getReservationId()));
		if(toModify!=null)
		{
			Statement stmt=null;
			String modify="update Reservation set applicant="+modified.getApplicant()+", game="+modified.getGame()+", console="+modified.getConsole()+", reservationStatus ='"+modified.getReservationStatus()+"', reservationDate=DATE'"+modified.getReservationDate()+"' where reservationId="+toModify.getReservationId()+";";
			JOptionPane.showMessageDialog(null, modify);
			try
			{
				stmt=connect.createStatement();
				stmt.executeUpdate(modify);
				hasWorked=true;
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Modification impossible : "+ex.getMessage());
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
	public Reservation find(String recherche) 
	{
		Reservation researched=null;

		Statement stmt=null;
		ResultSet res=null;
		String sql="";
		
		try
		{
			int index=Integer.parseInt(recherche);
			sql = "select * from Reservation where reservationId = "+index+";";
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			if(res.next())//Pour une raison inconnue, le first() provoque une exception là ou le next() marche parfaitement. Je ne cherche plus à comprendre.
			{
				researched=new Reservation(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getString(5),res.getDate(6));
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
	
	public LinkedList<Reservation> findForAVersion(Version v)
	{
		LinkedList<Reservation> reserv=new LinkedList<Reservation>();
		Statement stmt=null;
		ResultSet res=null;
		String sql="";
		
		try
		{
			sql = "select * from Reservation where game="+v.getGame()+" and console = "+v.getConsole()+" and reservationStatus='En attente'";
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			while(res.next())
			{
				reserv.add(new Reservation(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getString(5),res.getDate(6)));
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
		
		return reserv;
	}
}
