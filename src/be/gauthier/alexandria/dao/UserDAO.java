package be.gauthier.alexandria.dao;
import java.sql.*;
import javax.swing.JOptionPane;
import be.gauthier.alexandria.pojos.*;

public class UserDAO extends DAO<User> 
{
	public UserDAO()
	{
		super();
	}

	@Override
	public boolean create(User u) 
	{
		boolean hasWorked=false;
		Statement stmt=null;
		String valid="select * from User where userName='"+u.getUserName()+"';";
		String in="insert into User(userName,password,age,rating,userRank,userTokens,guarantee) values('"+u.getUserName()+"','"+u.getPassword()+"',"+u.getAge()+","+u.getRating()+",'"+u.getUserRank()+"',"+u.getUserTokens()+","+u.getGuarantee()+");";
		//L'userId ainsi que inscriptionDate seront générés par Access, inutile donc de les insérer par requête
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
	public boolean delete(User del) 
	{
		boolean hasWorked=false;
		User toRemove=find(del.getUserName());
		if(toRemove!=null)
		{
			Statement stmt=null;
			String washLends="delete from Loan where lender="+toRemove.getUserId()+";";
			JOptionPane.showMessageDialog(null, washLends);
			String washBorrowings="delete from Loan where borrower="+toRemove.getUserId()+";";
			JOptionPane.showMessageDialog(null, washBorrowings);
			String delete="delete from User where userId="+toRemove.getUserId()+";";
			try
			{
				stmt=connect.createStatement();
				//La table Loan doit être nettoyée manuellement, car l'intégrité référentielle ne s'y applique pas (2 clés étrangères de la même table)
				stmt.executeUpdate(washLends);
				stmt.executeUpdate(washBorrowings);
				
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
	public boolean update(User modified) 
	{
		boolean hasWorked=false;
		User toModify=find(modified.getUserName());
		if(toModify!=null)
		{
			Statement stmt=null;
			String modify="update User set password='"+modified.getPassword()+"', age="+modified.getAge()+", rating="+modified.getRating()+", userRank='"+modified.getUserRank()+"', userTokens="+modified.getUserTokens()+", guarantee="+modified.getGuarantee()+" where userId="+toModify.getUserId()+";";
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
	public User find(String recherche) 
	{
		User researched=null;
		//Premier cas : recherche par index
		Statement stmt=null;
		ResultSet res=null;
		String sql="";
		
		try
		{
			int index=Integer.parseInt(recherche);
			sql = "select * from User where userId = "+index+";";
			
		}
		catch(NumberFormatException e)//Deuxième cas : recherche par userName
		{
			sql = "select * from User where userName = '"+recherche+"';";
		}
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			 
			if(res.next())//Pour une raison inconnue, le first() provoque une exception là ou le next() marche parfaitement. Je ne cherche plus à comprendre.
			{
				researched=new User(res.getInt("userId"), res.getString("userName"), res.getString("password"), res.getInt("age"), res.getFloat("rating"), res.getString("userRank").toCharArray()[0], res.getInt("userTokens"), res.getInt("guarantee"), res.getDate("inscriptionDate"));
				//Ensuite, on remplit les listes
				
				sql="select * from Loan where lender = "+researched.getUserId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addLend(new Loan(res.getInt("loanId"),res.getInt("lender"),res.getInt("borrower"),res.getDate("startDate"),res.getBoolean("pending"),res.getInt("gameCopy")));
				}
				
				sql="select * from Loan where borrower = "+researched.getUserId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addBorrowing(new Loan(res.getInt("loanId"),res.getInt("lender"),res.getInt("borrower"),res.getDate("startDate"),res.getBoolean("pending"),res.getInt("gameCopy")));
				}
				
				sql="select * from Reservation where applicant="+researched.getUserId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					Reservation r=new Reservation(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getString(5),res.getDate(6));	
					researched.addReservation(r);
				}
				
				sql="select * from Copy where owner="+researched.getUserId();
				res=stmt.executeQuery(sql);
				while(res.next())
				{
					researched.addCopy(new Copy(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getBoolean(5)));
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
}
