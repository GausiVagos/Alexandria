package be.gauthier.alexandria.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.pojos.*;

public class LoanDAO extends DAO<Loan> 
{
	public LoanDAO()
	{
		super();
	}
	
	@Override
	public boolean create(Loan obj) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String in="insert into Loan (lender,borrower,startDate,pending,gameCopy) values ("+obj.getLender()+","+obj.getBorrower()+", DATE '"+obj.getStartDate()+"','"+obj.getPending()+"',"+obj.getGameCopy()+");";
		
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
	public boolean delete(Loan del) 
	{
		boolean hasWorked=false;
		Loan toRemove=find(Integer.toString(del.getLoanId()));
		if(toRemove!=null)
		{
			Statement stmt=null;
			String delete="delete from Loan where loanId="+toRemove.getLoanId()+";";
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
	public boolean update(Loan modified) 
	{
		boolean hasWorked=false;
		Loan toModify=find(Integer.toString(modified.getLoanId()));
		if(toModify!=null)
		{
			Statement stmt=null;
			String modify="update Loan set lender="+modified.getLender()+", borrower="+modified.getBorrower()+", startDate= DATE'"+modified.getStartDate()+"', pending ='"+modified.getPending()+"', gameCopy="+modified.getGameCopy()+" where loanId="+toModify.getLoanId()+";";
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
	public Loan find(String recherche) 
	{
		Loan researched=null;

		Statement stmt=null;
		ResultSet res=null;
		String sql="";
		
		try
		{
			int index=Integer.parseInt(recherche);
			sql = "select * from Loan where loanId = "+index+";";
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			if(res.next())//Pour une raison inconnue, le first() provoque une exception là ou le next() marche parfaitement. Je ne cherche plus à comprendre.
			{
				researched=new Loan(res.getInt(1),res.getInt(2),res.getInt(3),res.getDate(4),res.getBoolean(5),res.getInt(6));
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

}
