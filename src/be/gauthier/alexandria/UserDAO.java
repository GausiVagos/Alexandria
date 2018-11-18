package be.gauthier.alexandria;
import java.sql.*;

import javax.swing.JOptionPane;

public class UserDAO extends DAO<User> 
{
	public UserDAO(Connection conn)
	{
		super(conn);
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
	public User find(int id) {
		try
		{
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch(ClassNotFoundException er)
		{
			JOptionPane.showMessageDialog(null, "Classe de driver introuvable"+er.getMessage());
			System.exit(0);
		}
		
		
		return null;
	}
}
