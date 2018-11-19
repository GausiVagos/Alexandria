package be.gauthier.alexandria;
import java.sql.*;
import java.util.Date;

import javax.swing.JOptionPane;

public class UserDAO extends DAO<User> 
{
	public UserDAO()
	{
		super();
	}

	@Override
	public boolean create(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
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
