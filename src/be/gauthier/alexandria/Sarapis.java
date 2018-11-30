package be.gauthier.alexandria;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.dao.AlexConn;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.dao.VersionDAO;
import be.gauthier.alexandria.pojos.Loan;
import be.gauthier.alexandria.pojos.User;
import be.gauthier.alexandria.pojos.Version;

//Sarapis est le système de transactions d'Alexandria.
public class Sarapis 
{
	private static Connection connect=AlexConn.getInstance();
	private static UserDAO udao=new UserDAO();
	private static VersionDAO vdao=new VersionDAO();
	
	public static boolean start()
	{	
		HashSet<Loan> pendingLoans=getPendingLoans();
		for(Loan l : pendingLoans)
		{
			l.ping();
			User lender=l.getLenderObj();
			User borrower=l.getBorrowerObj();
			int price=getPrice(l.getGameCopyObj().getGame(),l.getGameCopyObj().getConsole());
			
			lender.setUserTokens(lender.getUserTokens()+price);
			borrower.setUserTokens(borrower.getUserTokens()-price);
			
			udao.update(lender);
			udao.update(borrower);
		}
		
		return true;
	}
	
	private static HashSet<Loan> getPendingLoans()
	{
		HashSet<Loan> pendingLoans = new HashSet<>();
		Statement stmt=null;
		ResultSet res=null;
		String sql="select * from Loan where pending=true and copyHasBeenReturned=false;";
		
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			
			while(res.next())
			{
				pendingLoans.add(new Loan(res.getInt("loanId"),res.getInt("lender"),res.getInt("borrower"),res.getDate("startDate"),res.getBoolean("copyHasBeenReturned"),res.getBoolean("pending"), res.getInt("gameCopy")));
			}
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "Erreur SQL dans Sarapis/getPendingLoans");
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
			catch(SQLException ex){}
		}
		return pendingLoans;
	}
	
	private static int getPrice(int game, int console)
	{
		Version v= vdao.find(game+"/"+console);
		if(v!=null)
			return v.getTokenValue();
		else
			return 0;
	}
}
