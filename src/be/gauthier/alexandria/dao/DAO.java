package be.gauthier.alexandria.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public abstract class DAO<T> 
{	
protected static Connection connect = null;
	
	public DAO(){
		connect=AlexConn.getInstance();
	}
	
	public abstract boolean create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);
	
	public abstract T find(String recherche);

}
